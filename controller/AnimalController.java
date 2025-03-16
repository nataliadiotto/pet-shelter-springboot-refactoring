package controller;

import domain.Animal;
import service.AnimalService;
import service.FileReaderService;

import java.io.IOException;

public class AnimalController {

    private final AnimalService animalService;
    private final FileReaderService fileReaderService;

    public AnimalController(AnimalService animalService, FileReaderService fileReaderService) {
        this.animalService = animalService;
        this.fileReaderService = fileReaderService;
    }

    public void registerAnimal(Animal animal) throws IOException {
       fileReaderService.fileReader();
       animalService.registerNewAnimal(animal);

    }

}
