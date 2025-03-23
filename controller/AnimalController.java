package controller;

import domain.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import service.AnimalService;
import service.FileReaderService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
            System.out.println("Animal type choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid option! Choose 1 for Cat or 2 for Dog.");
        }

        BiologicalSex biologicalSex = null;
        try {
            int biologicalSexResponse = Integer.parseInt(userResponses.get("3 - What is the animal's gender (Female = 1/Male = 2)?"));

            biologicalSex = BiologicalSex.fromValue(biologicalSexResponse);
        } catch (NumberFormatException e) {
            System.out.println("Animal gender choice must be a valid number!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid option! Choose 1 for Female or 2 for Male.");
        }


        Integer addressNumber = Integer.valueOf(userResponses.get("address number"));
        String addressName = userResponses.get("address name");
        String addressCity = userResponses.get("address city");

        Double age = null;
        try {
            age = Double.valueOf(userResponses.get("5 - What is the approximate age of the animal?"));
        } catch (NumberFormatException e) {
            System.out.println("Age must be a valid number!");
        }

        Double weight = null;
        try {
            weight = Double.parseDouble(userResponses.get("6 - What is the approximate weight of the animal?"));
        } catch (NumberFormatException e) {
            System.out.println("Weight must be a valid number!");
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

}
