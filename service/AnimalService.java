package service;

import domain.Animal;
import repository.AnimalRepository;
import repository.AnimalRepositoryImpl;

public class AnimalService {


    private final AnimalRepositoryImpl animalRepository;

    public AnimalService(AnimalRepositoryImpl animalRepository) {
        this.animalRepository = animalRepository;
    }

    public void registerNewAnimal(Animal animal) {
        Animal newAnimal = new Animal("null",
                "null",
                "null",
                "",
                "",
                "",
                "",
                "");

        animalRepository.addAnimal(animal);
    }

}
