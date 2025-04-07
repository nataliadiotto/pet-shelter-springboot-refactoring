package controller;

import domain.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import service.AnimalService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//TODO Refactor listAnimals to retrieve from files, not in memory list

public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    public void registerAnimal(Map<String, String> userResponses) throws IOException {
        String firstName = userResponses.get("first name");
        String lastName = userResponses.get("last name");
        if (firstName == null || firstName.trim().isEmpty()) {throw new IllegalArgumentException("Animal's first name is mandatory.");}
        if (lastName == null || lastName.trim().isEmpty()) {throw new IllegalArgumentException("Animal's last name is mandatory.");}

        AnimalType animalType = null;
        try {
            int animalTypeResponse = Integer.parseInt(userResponses.get("2 - What type of animal is it (Cat = 1/Dog = 2)?"));

            animalType = AnimalType.fromValue(animalTypeResponse);
        } catch (NumberFormatException e) {
            showError("Animal type choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            showError("Invalid option! Choose 1 for Cat or 2 for Dog.");
        }

        BiologicalSex biologicalSex = null;
        try {
            int biologicalSexResponse = Integer.parseInt(userResponses.get("3 - What is the animal's gender (Female = 1/Male = 2)?"));

            biologicalSex = BiologicalSex.fromValue(biologicalSexResponse);
        } catch (NumberFormatException e) {
            showError("Animal gender choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            showError("Invalid option! Choose 1 for Female or 2 for Male.");
        }

        String addressNumberStr = userResponses.get("address number");
        Integer addressNumber = null;

        if (addressNumberStr != null && !addressNumberStr.trim().isEmpty()) {
            try {
                addressNumber = Integer.valueOf(addressNumberStr.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        String addressName = userResponses.get("address name");
        String addressCity = userResponses.get("address city");

        String ageStr = userResponses.get("5 - What is the approximate age of the animal?");
        Double age = null;
        if (ageStr != null && !ageStr.trim().isEmpty()) {
            try {
                age = Double.valueOf(ageStr.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        String weightStr = userResponses.get("6 - What is the approximate weight of the animal?");
        Double weight = null;
        if (weightStr != null && !weightStr.trim().isEmpty()) {
            try {
                weight = Double.valueOf(weightStr.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        String breed = userResponses.get("7 - What is the breed of the animal?");

       animalService.saveAnimal(firstName,
               lastName,
               animalType,
               biologicalSex,
               addressNumber,
               addressName,
               addressCity,
               age,
               weight,
               breed);
        System.out.println("Animal insertion validated in Controller");

    }

    public void listAllAnimals() {
        List<Animal> animals = animalService.listAll();

        if (animals.isEmpty()) {
            System.out.println("No animals found.");
        } else {
            int i = 0;
            for (Animal animal : animals) {
                i++;
                System.out.println(i + ". " + animal);
            }
        }
    }

    public void filterByCriteria(AnimalType animalType, Map<FilterType, String> filters) {
        System.out.printf("Searching %ss...\n", animalType.name().toLowerCase());
        List<Animal> filteredAnimals = animalService.filterAnimals(animalType, filters);

        if (filteredAnimals.isEmpty()) {
            System.out.println("No animals found.");
        } else {
            int i = 0;
            for (Animal animal : filteredAnimals) {
                i++;
                System.out.println(i + ". " + animal);
            }

        }
    }
        private void showError(String message){
            System.err.println("Error: " + message);
        }

    }


