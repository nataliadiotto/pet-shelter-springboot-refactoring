package repository;

import domain.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {

    private static AnimalRepositoryImpl instance;
    private List<Animal> animals;

    private AnimalRepositoryImpl() {
        this.animals = new ArrayList<>();
    }

    public static AnimalRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AnimalRepositoryImpl();
        }
        return instance;
    }
    @Override
    public void save(Animal animal) {
        System.out.println("DEBUG REPOSITORY: Trying to save -> " + animal);
        animals.add(animal);
        System.out.println("DEBUG: Animal saved successfully. Current list: " + animals);
    }


    @Override
    public List<Animal> findByName(String name) {
        return null;
    }

    @Override
    public List<Animal> findBySex(Enum BiologicaSex) {
        return null;
    }

    @Override
    public List<Animal> findByAge(Double age) {
        return null;
    }

    @Override
    public List<Animal> findByWeight(Double weight) {
        return null;
    }

    @Override
    public List<Animal> findByBreed(String breed) {
        return null;
    }

    @Override
    public List<Animal> findByFoundAtAddress(String address) {
        return null;
    }

    @Override
    public List<Animal> findAll() {
        return animals != null ? animals : Collections.emptyList();
    }

    @Override
    public void updateAnimal(Animal animal) {

    }

    @Override
    public void deleteAnimal(Animal animal) {

    }
}
