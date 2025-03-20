package controller;

import domain.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import service.AnimalService;
import service.FileReaderService;

import java.io.IOException;
import java.util.List;

public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //TODO test registerAnimal()
        //print saved animal
    public void registerAnimal(List<String> userResponses) throws IOException {
        String name = userResponses.get(0);
        String surname = userResponses.get(0);
        AnimalType animalType = AnimalType.valueOf(userResponses.get(1).toUpperCase());
        BiologicalSex biologicalSex = BiologicalSex.valueOf(userResponses.get(2).toUpperCase());
        String foundAtAddress = userResponses.get(3);
        Double age = Double.parseDouble(userResponses.get(4));
        Double weight = Double.parseDouble(userResponses.get(5));
        String breed = userResponses.get(6);

       Animal animal = new Animal(name, surname, animalType, biologicalSex, foundAtAddress, age, weight, breed);

       animalService.saveAnimal(animal);
        System.out.println("Animal salvo na controller" + animal);

    }

}
