package service;

import domain.Animal;
import domain.utils.AnimalType;
import domain.utils.BiologicalSex;
import repository.AnimalRepositoryImpl;

import java.io.IOException;

public class AnimalService {


    private final AnimalRepositoryImpl animalRepository;
    private final FileWriterService fileWriterService;

    public AnimalService(AnimalRepositoryImpl animalRepository, FileWriterService fileWriterService) {
        this.animalRepository = animalRepository;
        this.fileWriterService = fileWriterService;
    }

    //TODO test saveAnimal()
    public void saveAnimal(String firstName, String lastName, AnimalType animalType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) throws IOException {
        //Validate name and surname content
        if (containsInvalidCharacters(firstName) || containsInvalidCharacters(lastName)) {
                throw new IllegalArgumentException("First and last names must contain only A-Z letters.");
        }

        //validate breed content
        if (containsInvalidCharacters(breed)) {
            throw new IllegalArgumentException("Breed must contain only A-Z letters.");
        }

        //validate input and maximum age
        if (!isValidDecimal(String.valueOf(age))) {
            throw new IllegalArgumentException("Age must contain only numbers!");
        }
        if (age > 20 || age == 0) {
            throw new IllegalArgumentException("Age must be between 0.1 and 20");
        }

        //validate input and min and max weight
        if (!isValidDecimal(String.valueOf(weight))) {
            throw new IllegalArgumentException("Weight must contain only numbers!");
        }
        if (weight > 60 || weight < 0.1) {
            throw new IllegalArgumentException("Weight must be between 0.1 and 60");
        }

        Animal animal = new Animal(firstName,
                lastName,
                animalType,
                biologicalSex,
                addressNumber,
                addressName,
                addressCity,
                age,
                weight,
                breed);
        animalRepository.save(animal);
        System.out.println("Animal created in Service" + animal);
        String fileName = "test3";
        String fileContent = "test test test";
        fileWriterService.createAnimalFile(animal);

    }

    private boolean containsInvalidCharacters(String text) {
        return text == null || !text.matches("[a-zA-Z ]+");
    }

    public boolean isValidDecimal(String input) {
        return input.matches("^[+-]?\\d+(?:[.,]\\d+)?$");
    }

}
