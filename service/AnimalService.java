package service;

import domain.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import repository.AnimalRepositoryImpl;

public class AnimalService {


    private final AnimalRepositoryImpl animalRepository;

    public AnimalService(AnimalRepositoryImpl animalRepository) {
        this.animalRepository = animalRepository;
    }

    //TODO test saveAnimal()
    public void saveAnimal(String firstName, String lastName, AnimalType animalType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) {
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
    }

    private boolean containsInvalidCharacters(String text) {
        return text == null || !text.matches("[a-zA-Z ]+");
    }

    public boolean isValidDecimal(String input) {
        return input.matches("^[+-]?\\d+(?:[.,]\\d+)?$");
    }

}
