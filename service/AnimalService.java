package service;

import domain.entity.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import domain.strategy.*;
import domain.strategy.filters.*;
import repository.AnimalRepositoryImpl;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimalService {

    private final AnimalRepositoryImpl animalRepository;
    private final FileWriterService fileWriterService;
    private final FileReaderService fileReaderService;

    private static final Map<FilterType, AnimalFilterStrategy> STRATEGY_MAP = Map.of(
            FilterType.NAME, new NameFilterStrategy(),
            FilterType.SEX, new SexFilterStrategy(),
            FilterType.AGE, new AgeFilterStrategy(),
            FilterType.WEIGHT, new WeightFilterStrategy(),
            FilterType.BREED, new BreedFilterStrategy(),
            FilterType.ADDRESS, new AddressFilterStrategy()
    );

    public AnimalService(FileWriterService fileWriterService, FileReaderService fileReaderService) {
        this.animalRepository = AnimalRepositoryImpl.getInstance(fileReaderService, fileWriterService);
        this.fileWriterService = fileWriterService;
        this.fileReaderService = fileReaderService;
    }

    //TODO test saveAnimal()
    public void saveAnimal(String firstName, String lastName, AnimalType animalType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) throws IOException {
        //Validate name and surname content
        if (containsInvalidCharacters(firstName) || containsInvalidCharacters(lastName)) {
                throw new IllegalArgumentException("First and last names must contain only A-Z letters.");
        }

        //validate breed content
        if (!breed.trim().isEmpty()) {
            if (containsInvalidCharacters(breed)) {
                throw new IllegalArgumentException("Breed must contain only A-Z letters.");
            }
        }

        //validate minimum and maximum age
        if (age != null && (age <= 0 || age > 20)) {
            throw new IllegalArgumentException("Age must be between 0.1 and 20.");
        }

        //validate min and max weight
        if (weight != null && (weight > 60 || weight < 0.1)) {
            throw new IllegalArgumentException("Weight must be between 0.1 and 60.");
        }

        //create new animal
        Animal animal = new Animal(firstName,
                lastName,
                animalType,
                biologicalSex,
                addressNumber,
                addressName,
                addressCity,
                age,
                weight,
                breed);
        System.out.println("DEBUG SERVICE: Trying to save -> " + animal);
        animalRepository.save(animal);
        System.out.println("DEBUG SERVICE: Save method executed");
        System.out.println("Animal created in Service" + animal);

    }

    public List<Animal> listAll() {
        Map<Path, Animal> animalMap = animalRepository.findAll();

       if (animalMap.isEmpty()) {
           System.out.println("No registered animals.");
       }

       return new ArrayList<>(animalMap.values());
    }

    public List<Animal> filterAnimals(AnimalType animalType, Map<FilterType, String> filters) {
        Map<Path, Animal> animalMap = animalRepository.findAll();

        if (animalMap.isEmpty()) {
            System.out.println("No registered animals.");
        }

        List<Animal> animals = new ArrayList<>(animalMap.values());

        //Filter by AnimalType
        List<Animal> filteredAnimals = animals.stream().filter(animal -> animal.getAnimalType()
                        .equals(animalType))
                .toList();


        //Apply each filter
        for (Map.Entry<FilterType, String> entry : filters.entrySet()) {
            AnimalFilterStrategy strategy = STRATEGY_MAP.get(entry.getKey());

            if (strategy != null) {
                filteredAnimals = strategy.filter(filteredAnimals, entry.getValue());
            }
        }

        return filteredAnimals;
    }

    private boolean containsInvalidCharacters(String text) {
        return text == null || !text.matches("[a-zA-Z ]+");
    }

    public boolean isValidDecimal(String input) {
        return input.matches("^[+-]?\\d+(?:[.,]\\d+)?$");
    }

}
