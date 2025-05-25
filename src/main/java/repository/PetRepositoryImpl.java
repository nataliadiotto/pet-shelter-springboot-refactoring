package repository;

import domain.entity.Pet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PetRepositoryImpl implements PetRepository {

    private static PetRepositoryImpl instance;
    private TreeMap<Path, Pet> pets;
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;

    private PetRepositoryImpl(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
        this.pets = new TreeMap<>();
        this.fileReaderService = fileReaderService;
    }

    public static PetRepositoryImpl getInstance(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        if (instance == null) {
            instance = new PetRepositoryImpl(fileReaderService, fileWriterService);
        }
        return instance;
    }
    @Override
    public void save(Pet pet) throws IOException {
        fileWriterService.savePetToFile(pet);
        pets.put(pet.getFilePath(), pet);
        refreshCache();
    }


    public TreeMap<Path, Pet> findAll() {
        if (pets == null || pets.isEmpty()) {
            try {
                pets = fileReaderService.readAllPets();
            } catch (IOException e) {
                System.err.println("Failed to read pet files: " + e.getMessage());
                pets = new TreeMap<>();
            }
        }
        return pets;
    }

    @Override
    public void updatePetByPath(Pet updatedPet, Path originalPath) throws IOException {
        if (updatedPet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        if (originalPath != null && Files.exists(originalPath)) {
            Files.delete(originalPath);
            refreshCache();
        }

        fileWriterService.savePetToFile(updatedPet);
        pets.put(updatedPet.getFilePath(), updatedPet);

    }

    @Override
    public void deletePetByPath(Pet existingPet, Path oldFilePath) throws IOException {
        if (existingPet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        if (oldFilePath != null) {
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
                pets.remove(oldFilePath);

                refreshCache();
            } else {
                System.out.println("File does not exist: " + oldFilePath);
            }

        }
    }

    public void refreshCache() {
        this.pets.clear();
    }
}
