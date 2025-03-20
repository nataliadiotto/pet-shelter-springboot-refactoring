package repository;

import domain.Animal;

import java.util.List;

public interface AnimalRepository {

    void save(Animal animal);
    List<Animal> findByName(String name);
    List<Animal> findBySex(Enum BiologicaSex);
    List<Animal> findByAge(Double age);
    List<Animal> findByWeight(Double weight);
    List<Animal> findByBreed(String breed);
    List<Animal> findByFoundAtAddress(String address);
    List<Animal> findAll();
    void updateAnimal(Animal animal);
    void deleteAnimal(Animal animal);


}
