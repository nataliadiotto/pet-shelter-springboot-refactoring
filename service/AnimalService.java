package service;

import domain.Animal;
import repository.AnimalRepositoryImpl;

public class AnimalService {


    private final AnimalRepositoryImpl animalRepository;

    public AnimalService(AnimalRepositoryImpl animalRepository) {
        this.animalRepository = animalRepository;
    }


    public void saveAnimal(Animal animal) {
        animalRepository.save(animal);
        System.out.println("Animal salvo na service" + animal);
    }
}
