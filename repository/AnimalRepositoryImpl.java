package repository;

import domain.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {

    private List<Animal> animals;
    private AnimalRepositoryImpl() {
        this.animals = new ArrayList<>();
    }
    @Override
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
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
        return null;
    }

    @Override
    public void updateAnimal(Animal animal) {

    }

    @Override
    public void deleteAnimal(Animal animal) {

    }
}
