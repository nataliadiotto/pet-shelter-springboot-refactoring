package controller;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.PetService;

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

    @PostMapping()
    public void registerPet(Map<String, String> userResponses) throws IOException, InterruptedException {
            petService.savePet(userResponses);
    }

    @GetMapping
    public void listAllPets() throws InterruptedException {
        List<Pet> pets = petService.listAll();

    }

    @GetMapping
    public List<Pet> filterByCriteria(PetType petType, Map<FilterType, String> filters) throws InterruptedException {

        return petService.filterPets(petType, filters);
    }

    @PatchMapping("/{id}")
    public void updatePetByIndex(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException, InterruptedException {
        if (index < 1 || index > filteredPets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        petService.updatePet(index, filteredPets, updatedData);
    }

    @DeleteMapping("/{id}")
    public void deletePetByIndex(int index, List<Pet> filteredPets) throws IOException, InterruptedException {
        if (index < 1 || index > filteredPets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        petService.deletePet(index, filteredPets);


    }

    private void showError(String message){
            System.err.println("Error: " + message);
        }


}


