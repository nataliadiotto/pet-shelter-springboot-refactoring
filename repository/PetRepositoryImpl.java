package repository;

import domain.entity.Pet;
import service.FileReaderService;
import service.FileWriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PetRepositoryImpl implements PetRepository {

    private static PetRepositoryImpl instance;
    private Map<Path, Pet> pets;
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;

    private PetRepositoryImpl(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
        this.pets = new HashMap<>();
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
        refreshCache();
    }


    public Map<Path, Pet> findAll() {
        if (pets == null || pets.isEmpty()) {
            try {
                pets = fileReaderService.readAllPets();
            } catch (IOException e) {
                System.err.println("Failed to read pet files: " + e.getMessage());
                pets = new HashMap<>();
            }
        }
        //return new TreeMap<>(pets);
        return pets;
    }

    @Override
    public void updatePetByIndex(Pet updatedPet, Path oldPath) throws IOException {
        if (updatedPet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        if (oldPath != null && Files.exists(oldPath)) {
            Files.delete(oldPath);
            refreshCache();
        }

        fileWriterService.savePetToFile(updatedPet);

    }

    @Override
    public void deletePetByIndex(Pet existingPet, Path oldFilePath, List<Pet> pets, int targetIndex) throws IOException {
        if (existingPet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        if (oldFilePath != null) {
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);

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
