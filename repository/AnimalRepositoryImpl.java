package repository;

import domain.entity.Animal;
import service.FileReaderService;
import service.FileWriterService;

import java.io.IOException;
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
        fileWriterService.createAnimalFile(animal);
        animals.clear();
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
    public void updateAnimal(Animal animal) {

    }

    @Override
    public void deleteAnimal(Animal animal) {

    }
}
