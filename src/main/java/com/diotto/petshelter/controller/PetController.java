package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diotto.petshelter.service.PetService;
import com.diotto.petshelter.errors.ResourceNotFound;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "Pet Controller", description = "Endpoints for managing pets")
@RequestMapping("v1/pets")
@RestController
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Register a new pet", description = "Saves a new pet in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PetResponseDTO> registerPet(@RequestBody @Valid PetDTO petDTO) {
        Pet newPet = petService.registerPet(petDTO);
        PetResponseDTO petResponseDTO = new PetResponseDTO(newPet);
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(petResponseDTO);
    }


    @Operation(summary = "List all pets", description = "Retrieves a list of all pets in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of pets returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No pets found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listAllPets() throws ResourceNotFound {
        List<Pet> pets = petService.listAll();
        List<PetResponseDTO> responseDTOS = pets.stream()
                .map(PetResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @Operation(summary = "Search pets", description = "Search and filter pets by provided parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered list of pets returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No pets match the filters", content = @Content)
    })
    @GetMapping("/search/")
    public ResponseEntity<List<PetResponseDTO>> filterPets(@RequestParam(required = true) PetType petType,
                                                           @RequestParam(required = false) BiologicalSex sex,
                                                           @RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String streetName,
                                                           @RequestParam(required = false) String city,
                                                           @RequestParam(required = false) Integer addressNumber,
                                                           @RequestParam(required = false) Double age,
                                                           @RequestParam(required = false) Double weight,
                                                           @RequestParam(required = false) String breed) throws ResourceNotFound {

        List<Pet> pets = petService.searchPets(petType, sex, name, streetName, city, addressNumber, age, weight, breed);
        List<PetResponseDTO> responseDTOS = pets.stream()
                .map(PetResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @Operation(summary = "Update pet", description = "Update fields of a pet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PetResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updateById(@PathVariable Long id,
                                                     @RequestBody PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFound {
       Pet updatedPetDTO = petService.updatePet(id, petUpdtRequestDTO);
       return ResponseEntity.ok(new PetResponseDTO(updatedPetDTO));
    }

    @Operation(summary = "Delete pet", description = "Delete a pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable("id") Long id) throws IOException, InterruptedException, ResourceNotFound {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

}


