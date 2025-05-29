package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diotto.petshelter.service.PetService;
import com.diotto.petshelter.errors.ResourceNotFound;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequestMapping("v1/pets")
@RestController
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> registerPet(@RequestBody @Valid PetDTO petDTO) {
        Pet newPet = petService.registerPet(petDTO);
        PetResponseDTO petResponseDTO = new PetResponseDTO(newPet);
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(petResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listAllPets() throws InterruptedException {
        List<Pet> pets = petService.listAll();
        List<PetResponseDTO> responseDTOS = pets.stream()
                .map(PetResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<PetResponseDTO>> filterPets(@RequestParam(required = true) PetType petType,
                                                           @RequestParam(required = false) BiologicalSex sex,
                                                           @RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String streetName,
                                                           @RequestParam(required = false) String city,
                                                           @RequestParam(required = false) Integer addressNumber,
                                                           @RequestParam(required = false) Double age,
                                                           @RequestParam(required = false) Double weight,
                                                           @RequestParam(required = false) String breed) {

        List<Pet> pets = petService.searchPets(petType, sex, name, streetName, city, addressNumber, age, weight, breed);
        List<PetResponseDTO> responseDTOS = pets.stream()
                .map(PetResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //TODO: refactor
    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updateById(@PathVariable Long id,
                                                     @RequestBody PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFound {
       Pet updatedPetDTO = petService.updatePet(id, petUpdtRequestDTO);
       return ResponseEntity.ok(new PetResponseDTO(updatedPetDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetResponseDTO> deletePetById(@PathVariable("id") Long id) throws IOException, InterruptedException, ResourceNotFound {
        Pet deletedPet = petService.deletePet(id);
        return ResponseEntity.ok(new PetResponseDTO(deletedPet));
    }

}


