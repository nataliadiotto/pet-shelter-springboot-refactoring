package controller;

import domain.utils.AnimalType;
import domain.utils.BiologicalSex;
import domain.utils.Constants;
import service.AnimalService;

import java.io.IOException;
import java.util.Map;

//TODO Fix bug saving two animals in a row

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

        Integer addressNumber = Integer.parseInt(userResponses.get("address number"));

        String addressName = userResponses.get("address name");
        String addressCity = userResponses.get("address city");

        Double age = null;
        try {
            age = Double.valueOf(getValueOrDefault(userResponses.get("5 - What is the approximate age of the animal?")));
        } catch (NumberFormatException e) {
            showError("Age must be a valid number!");
        }

        Double weight = null;
        try {
            weight = Double.parseDouble(userResponses.get("6 - What is the approximate weight of the animal?"));
        } catch (NumberFormatException e) {
            showError("Weight must be a valid number!");
        }

        String breed = getValueOrDefault(userResponses.get("7 - What is the breed of the animal?"));

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


    //validate if String fields were informed
    private String getValueOrDefault(String value) {
        return (value == null || value.trim().isEmpty())
                ? Constants.NOT_INFORMED
                : value.trim();
    }


    private void showError(String message) {
        System.err.println("Error: " + message);
    }



}
