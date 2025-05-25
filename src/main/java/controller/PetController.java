package controller;

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
    public ResponseEntity<Pet> registerPet(@RequestBody @Valid Pet pet) throws IOException, InterruptedException {
        Pet newPet = petService.savePet(pet);
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(newPet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listAllPets() throws InterruptedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.listAll());
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
    public ResponseEntity<Pet> deletePetById(@PathVariable("id") Long id) throws IOException, InterruptedException {
        Pet deletedPet = petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(deletedPet);


    }

    private void showError(String message){
            System.err.println("Error: " + message);
        }


}


