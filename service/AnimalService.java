package service;

import domain.entity.Animal;
import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import domain.strategy.*;
import domain.strategy.filters.*;
import domain.utils.Constants;
import repository.AnimalRepositoryImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

import static domain.utils.InputHelper.*;

public class AnimalService {

    private final AnimalRepositoryImpl animalRepository;

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
    }

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

    public void updateAnimal(int targetIndex, List<Animal> animals, Map<String, Object> updatedData) throws IOException {
        if (updatedData == null || updatedData.isEmpty()) {
            throw new IllegalArgumentException("Not enough data to update animal.");
        }

        if (targetIndex < 1 || targetIndex > animals.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }


        Animal originalAnimal = animals.get(targetIndex - 1);

        Path oldFilePath = originalAnimal.getFilePath();

        updateIfNotBlank(updatedData, "firstName", String.class, originalAnimal::setFirstName);
        updateIfNotBlank(updatedData, "lastName", String.class, originalAnimal::setLastName);

        updateIfNotBlank(updatedData, "addressNumber", Integer.class, originalAnimal::setAddressNumber);
        updateIfNotBlank(updatedData, "addressName", String.class, originalAnimal::setAddressName);
        updateIfNotBlank(updatedData, "addressCity", String.class, originalAnimal::setAddressCity);

        updateIfNotBlank(updatedData, "age", Double.class, originalAnimal::setAge);

        updateIfNotBlank(updatedData, "weight", Double.class, originalAnimal::setWeight);

        updateIfNotBlank(updatedData, "breed", String.class, originalAnimal::setBreed);


        System.out.println("DEBUG SERVICE: Trying to update -> " + originalAnimal);
        animalRepository.updateAnimal(originalAnimal, oldFilePath);
        System.out.println("Animal updated in Service" + originalAnimal);
    }

    private <T> void updateIfNotBlank(Map<String, Object> data, String key, Class<T> type, Consumer<T> setter) {
        Object value = data.get(key);

        String stringValue = value.toString().trim();

        if ("0".equals(stringValue)) {
            if (type == String.class) {
                setter.accept(type.cast(Constants.NOT_INFORMED)); // e.g., "Not Informed"
            } else {
                setter.accept(null); // For numbers, null means not informed
            }
            return;
        }

        if (isNullOrEmpty(value)) {
            return; // User wants to keep existing value (pressed Enter)
        }

        try {
            if (type == Integer.class) {
                setter.accept(type.cast(Integer.parseInt(value.toString())));
            } else if (type == Double.class) {
                setter.accept(type.cast(Double.parseDouble(value.toString())));
            } else if (type == String.class) {
                setter.accept(type.cast(value.toString()));
            } else {
                setter.accept(type.cast(value));
            }
        } catch (Exception e) {
            System.out.println("Invalid type or conversion error for key " + key + ": " + value);
        }
    }


}
