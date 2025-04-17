package repository;

import domain.entity.Pet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface PetRepository {

    void save(Pet pet) throws IOException;
    Map<Path, Pet> findAll();

    void updatePetByIndex(Pet updatedPet, Path oldPath) throws IOException;


    void deletePetByIndex(Pet existingPet, Path oldFilePath, List<Pet> pets, int targetIndex) throws IOException;
}
