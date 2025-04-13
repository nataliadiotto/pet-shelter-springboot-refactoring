package repository;

import domain.entity.Animal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface AnimalRepository {

    void save(Animal animal) throws IOException;
    Map<Path, Animal> findAll();

    void updateAnimalByIndex(Animal updatedAnimal, Path oldPath) throws IOException;


    void deleteAnimalByIndex(Animal existingAnimal, Path oldFilePath, List<Animal> animals, int targetIndex) throws IOException;
}
