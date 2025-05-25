package controller;

import domain.DTO.PetDTO;
import domain.DTO.PetResponseDTO;
import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PetService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("v1/pets")
@RestController
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> registerPet(@RequestBody @Valid PetDTO petDTO) throws IOException, InterruptedException {
        Pet newPet = petService.savePetFromDTO(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(newPet);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listAllPets() throws InterruptedException {
        List<Pet> pets = petService.listAll();
        List<PetResponseDTO> responseDTOS = pets.stream()
                .map(PetResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //TODO: refactor
    @GetMapping
    public List<Pet> filterByCriteria(PetType petType, Map<FilterType, String> filters) throws InterruptedException {

        return petService.filterPets(petType, filters);
    }

    //TODO: refactor
    @PatchMapping("/{id}")
    public void updatePetById(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException, InterruptedException {
        if (index < 1 || index > filteredPets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        petService.updatePet(index, filteredPets, updatedData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetResponseDTO> deletePetById(@PathVariable("id") Long id) throws IOException, InterruptedException {
        Pet deletedPet = petService.deletePet(id);
        return ResponseEntity.ok(new PetResponseDTO(deletedPet));
    }

    private void showError(String message){
            System.err.println("Error: " + message);
        }


}


