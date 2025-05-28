package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diotto.petshelter.service.PetService;
import com.diotto.petshelter.service.ResourceNotFoundException;

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

    //TODO: refactor with Specification
    //@GetMapping
//    public List<Pet> filterByCriteria(PetType petType, Map<FilterType, String> filters) throws InterruptedException {
//
//        return petService.filterPets(petType, filters);
//    }

    //TODO: refactor
    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updateById(@PathVariable Long id,
                                                     @RequestBody PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFoundException {
       Pet updatedPetDTO = petService.updatePet(id, petUpdtRequestDTO);
       return ResponseEntity.ok(new PetResponseDTO(updatedPetDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetResponseDTO> deletePetById(@PathVariable("id") Long id) throws IOException, InterruptedException, ResourceNotFoundException {
        Pet deletedPet = petService.deletePet(id);
        return ResponseEntity.ok(new PetResponseDTO(deletedPet));
    }

}


