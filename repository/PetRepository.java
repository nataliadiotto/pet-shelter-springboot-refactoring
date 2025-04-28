package repository;

import domain.entity.Pet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface PetRepository {

    void save(Pet pet) throws IOException;
    TreeMap<Path, Pet> findAll();

    void updatePetByPath(Pet updatedPet, Path oldPath) throws IOException;


    void deletePetByPath(Pet existingPet, Path oldFilePath) throws IOException;
}
