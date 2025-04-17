package controller;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import service.PetService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    public void registerPet(Map<String, String> userResponses) throws IOException {
        String firstName = userResponses.get("first name");
        String lastName = userResponses.get("last name");
        if (firstName == null || firstName.trim().isEmpty()) {throw new IllegalArgumentException("Pet's first name is mandatory.");}
        if (lastName == null || lastName.trim().isEmpty()) {throw new IllegalArgumentException("Pet's last name is mandatory.");}

        PetType petType = null;
        try {
            int petTypeResponse = Integer.parseInt(userResponses.get("2 - What type of pet is it (Cat = 1/Dog = 2)?"));

            petType = PetType.fromValue(petTypeResponse);
        } catch (NumberFormatException e) {
            showError("Pet type choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            showError("Invalid option! Choose 1 for Cat or 2 for Dog.");
        }

        BiologicalSex biologicalSex = null;
        try {
            int biologicalSexResponse = Integer.parseInt(userResponses.get("3 - What is the pet's gender (Female = 1/Male = 2)?"));

            biologicalSex = BiologicalSex.fromValue(biologicalSexResponse);
        } catch (NumberFormatException e) {
            showError("Pet gender choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            showError("Invalid option! Choose 1 for Female or 2 for Male.");
        }

        String addressNumberStr = userResponses.get("address number");
        Integer addressNumber = null;
        if (addressNumberStr != null && !addressNumberStr.trim().isEmpty()) {
            try {
                addressNumber = Integer.valueOf(addressNumberStr.trim());
            } catch (NumberFormatException ignored) {
                addressNumber = null;
            }
        }

        String addressName = userResponses.get("address name");
        String addressCity = userResponses.get("address city");

        String ageStr = userResponses.get("5 - What is the approximate age of the pet?");
        Double age = null;
        if (ageStr != null && !ageStr.trim().isEmpty()) {
            try {
                age = Double.valueOf(ageStr.trim());
            } catch (NumberFormatException ignored) {
                age = null;
            }
        }

        String weightStr = userResponses.get("6 - What is the approximate weight of the pet?");
        Double weight = null;
        if (weightStr != null && !weightStr.trim().isEmpty()) {
            try {
                weight = Double.valueOf(weightStr.trim());
            } catch (NumberFormatException ignored) {
                weight = null;
            }
        }

        String breed = userResponses.get("7 - What is the breed of the pet?");

       petService.savePet(firstName,
               lastName,
               petType,
               biologicalSex,
               addressNumber,
               addressName,
               addressCity,
               age,
               weight,
               breed);
        System.out.println("Pet insertion validated in Controller");

    }

    public void listAllPets() {
        List<Pet> pets = petService.listAll();

        if (pets.isEmpty()) {
            System.out.println("No pets found.");
        } else {
            int i = 0;
            for (Pet pet : pets) {
                i++;
                System.out.println(i + ". " + pet);
            }
        }
    }

    public List<Pet> filterByCriteria(PetType petType, Map<FilterType, String> filters) {
        System.out.printf("Searching %ss...\n", petType.name().toLowerCase());
        List<Pet> filteredPets = petService.filterPets(petType, filters);

        if (filteredPets.isEmpty()) {
            System.out.println("No pets found.");
        } else {
            int i = 0;
            for (Pet pet : filteredPets) {
                i++;
                System.out.println(i + ". " + pet);
            }
        }
        return filteredPets;
    }

    public void updatePetByIndex(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException {
        petService.updatePet(index, filteredPets, updatedData);
    }

    public void deletePetByIndex(int petIndex, List<Pet> pets) throws IOException {
        petService.deletePetByIndex(petIndex, pets);
    }

    private void showError(String message){
            System.err.println("Error: " + message);
        }


}


