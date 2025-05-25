package controller;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.FilterType;
import service.PetServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import static domain.utils.ConsoleVisuals.*;


public class PetController {

    private final PetServiceImpl petServiceImpl;

    public PetController(PetServiceImpl petServiceImpl) {
        this.petServiceImpl = petServiceImpl;
    }

    public void registerPet(Map<String, String> userResponses) throws IOException, InterruptedException {
        try {
            petServiceImpl.savePet(userResponses);
            System.out.print(BOLD_YELLOW + "Saving pet");
            animatedTransition();
            System.out.println(BOLD_GREEN + " ✅ Pet successfully registered!" + RESET);
        } catch (IllegalArgumentException e) {
            showError("Error registering pet: " + e.getMessage());
        }
    }


    public void listAllPets() throws InterruptedException {
        List<Pet> pets = petServiceImpl.listAll();

        System.out.print(BOLD_YELLOW + "Listing pets");
        animatedTransition();

        if (pets.isEmpty()) {
            System.out.println(BOLD_RED + "No pets found." + RESET);
        } else {
            int i = 0;
            for (Pet pet : pets) {
                i++;
                System.out.print(BOLD_GREEN + i + ". " + RESET);
                System.out.println(GREEN + pet + RESET);
            }
        }
    }

    public List<Pet> filterByCriteria(PetType petType, Map<FilterType, String> filters) throws InterruptedException {
        List<Pet> filteredPets = petServiceImpl.filterPets(petType, filters);

        System.out.printf(BOLD_YELLOW + "Searching %ss", petType.name().toLowerCase());
        animatedTransition();
        System.out.println();

        if (filteredPets.isEmpty()) {
            System.out.println(BOLD_RED + "No pets found." + RESET);
        } else {
            int i = 0;
            for (Pet pet : filteredPets) {
                i++;
                System.out.println(BOLD_PURPLE + i +
                        ". " + RESET +
                        PURPLE + pet + RESET);
            }
        }
        return filteredPets;
    }

    public void updatePetByIndex(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException, InterruptedException {
        if (index < 1 || index > filteredPets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        petServiceImpl.updatePet(index, filteredPets, updatedData);

        System.out.print(BOLD_YELLOW + "Updating pet");
        animatedTransition();
        System.out.println(BOLD_GREEN + " ✅ Pet successfully updated!" + RESET);
    }

    public void deletePetByIndex(int index, List<Pet> filteredPets) throws IOException, InterruptedException {
        if (index < 1 || index > filteredPets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        petServiceImpl.deletePet(index, filteredPets);

        System.out.print(BOLD_YELLOW + "Deleting pet");
        animatedTransition();
        System.out.println(BOLD_GREEN + " ✅ Pet successfully deleted!" + RESET);

    }

    private void showError(String message){
            System.err.println(BOLD_RED + "Error: " + message + RESET);
        }


}


