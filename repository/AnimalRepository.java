package repository;

import domain.entity.Animal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface AnimalRepository {

    void save(Animal animal) throws IOException;
    Map<Path, Animal> findAll();

    void updateAnimal(Animal updatedAnimal, Path oldPath) throws IOException;

    void deleteAnimal(Animal animal);


}
