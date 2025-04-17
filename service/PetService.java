package service;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import domain.strategy.*;
import domain.strategy.filters.*;
import domain.utils.Constants;
import repository.PetRepositoryImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

import static domain.utils.InputHelper.*;

public class PetService {

    private final PetRepositoryImpl petRepository;

    private static final Map<FilterType, PetFilterStrategy> STRATEGY_MAP = Map.of(
            FilterType.NAME, new NameFilterStrategy(),
            FilterType.SEX, new SexFilterStrategy(),
            FilterType.AGE, new AgeFilterStrategy(),
            FilterType.WEIGHT, new WeightFilterStrategy(),
            FilterType.BREED, new BreedFilterStrategy(),
            FilterType.ADDRESS, new AddressFilterStrategy()
    );

    public PetService(FileWriterService fileWriterService, FileReaderService fileReaderService) {
        this.petRepository = PetRepositoryImpl.getInstance(fileReaderService, fileWriterService);
    }

    public void savePet(String firstName, String lastName, PetType petType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) throws IOException {
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

        //create new pet
        Pet pet = new Pet(firstName,
                lastName,
                petType,
                biologicalSex,
                addressNumber,
                addressName,
                addressCity,
                age,
                weight,
                breed);
        System.out.println("DEBUG SERVICE: Trying to save -> " + pet);
        petRepository.save(pet);
        System.out.println("DEBUG SERVICE: Save method executed");
        System.out.println("Pet created in Service" + pet);

    }

    public List<Pet> listAll() {
        Map<Path, Pet> petMap = petRepository.findAll();

       if (petMap.isEmpty()) {
           System.out.println("No registered pets.");
       }

       return new ArrayList<>(petMap.values());
    }

    public List<Pet> filterPets(PetType petType, Map<FilterType, String> filters) {
        Map<Path, Pet> petMap = petRepository.findAll();

        if (petMap.isEmpty()) {
            System.out.println("No registered pets.");
        }

        List<Pet> pets = new ArrayList<>(petMap.values());

        //Filter by PetType
        List<Pet> filteredPets = pets.stream().filter(pet -> pet.getPetType()
                        .equals(petType))
                .toList();


        //Apply each filter
        for (Map.Entry<FilterType, String> entry : filters.entrySet()) {
            PetFilterStrategy strategy = STRATEGY_MAP.get(entry.getKey());

            if (strategy != null) {
                filteredPets = strategy.filter(filteredPets, entry.getValue());
            }
        }

        return filteredPets;
    }

    public void updatePet(int targetIndex, List<Pet> pets, Map<String, Object> updatedData) throws IOException {
        if (updatedData == null || updatedData.isEmpty()) {
            throw new IllegalArgumentException("Not enough data to update pet.");
        }

        if (targetIndex < 1 || targetIndex > pets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }


        Pet originalPet = pets.get(targetIndex - 1);

        Path oldFilePath = originalPet.getFilePath();

        updateIfNotBlank(updatedData, "firstName", String.class, originalPet::setFirstName);
        updateIfNotBlank(updatedData, "lastName", String.class, originalPet::setLastName);

        updateIfNotBlank(updatedData, "addressNumber", Integer.class, originalPet::setAddressNumber);
        updateIfNotBlank(updatedData, "addressName", String.class, originalPet::setAddressName);
        updateIfNotBlank(updatedData, "addressCity", String.class, originalPet::setAddressCity);

        updateIfNotBlank(updatedData, "age", Double.class, originalPet::setAge);

        updateIfNotBlank(updatedData, "weight", Double.class, originalPet::setWeight);

        updateIfNotBlank(updatedData, "breed", String.class, originalPet::setBreed);


        System.out.println("DEBUG SERVICE: Trying to update -> " + originalPet);
        petRepository.updatePetByIndex(originalPet, oldFilePath);
        System.out.println("Pet updated in Service" + originalPet);
    }

    public void deletePetByIndex(int targetIndex, List<Pet> pets) throws IOException {

        if (targetIndex < 1 || targetIndex > pets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        Pet existingPet = pets.get(targetIndex - 1);
        Path oldFilePath = existingPet.getFilePath();
        System.out.println("DEBUG SERVICE | Path: " + oldFilePath);

        System.out.println("DEBUG SERVICE: Trying to delete -> " + existingPet);
        petRepository.deletePetByIndex(existingPet, oldFilePath, pets, targetIndex);


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
