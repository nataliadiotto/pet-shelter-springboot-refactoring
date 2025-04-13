package repository;

import domain.entity.Animal;
import service.FileReaderService;
import service.FileWriterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AnimalRepositoryImpl implements AnimalRepository {

    private static AnimalRepositoryImpl instance;
    private Map<Path, Animal> animals;
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;

    private AnimalRepositoryImpl(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
        this.animals = new HashMap<>();
        this.fileReaderService = fileReaderService;
    }

    public static AnimalRepositoryImpl getInstance(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        if (instance == null) {
            instance = new AnimalRepositoryImpl(fileReaderService, fileWriterService);
        }
        return instance;
    }
    @Override
    public void save(Animal animal) throws IOException {
        System.out.println("DEBUG REPOSITORY: Trying to save -> " + animal);
        fileWriterService.saveAnimalToFile(animal);
        refreshCache();
        System.out.println("DEBUG: Animal saved successfully. Current list: " + findAll());
    }


    public Map<Path, Animal> findAll() {
        if (animals == null || animals.isEmpty()) {
            try {
                animals = fileReaderService.readAllAnimals();
            } catch (IOException e) {
                System.err.println("Failed to read animal files: " + e.getMessage());
                animals = new HashMap<>();
            }
        }
        return animals;
    }

    @Override
    public void updateAnimalByIndex(Animal updatedAnimal, Path oldPath) throws IOException {
        if (updatedAnimal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        if (oldPath != null && Files.exists(oldPath)) {
            Files.delete(oldPath);
            refreshCache();
        }

        fileWriterService.saveAnimalToFile(updatedAnimal);

    }

    @Override
    public void deleteAnimalByIndex(Animal existingAnimal, Path oldFilePath, List<Animal> animals, int targetIndex) throws IOException {
        if (existingAnimal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        System.out.println("DEBUG REPOSITORY | Path: " + oldFilePath);

        if (oldFilePath != null) {
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
                System.out.println("Success deleting animal: " + existingAnimal +
                        " at " + oldFilePath);

                System.out.println("Current animals: " + findAll());
                refreshCache();
            } else {
                System.out.println("File does not exist: " + oldFilePath);
            }

        }
    }

    public void refreshCache() {
        this.animals.clear();
    }
}
