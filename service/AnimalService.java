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
        //Validate name and surname
        if (!isNameValid(firstName) || !isNameValid(lastName)) {
            throw new IllegalArgumentException("First and last names must contain only A-Z letters.");
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

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z]+") && name != null && !name.isEmpty();
    }
}
