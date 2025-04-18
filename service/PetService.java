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
import java.util.function.Function;

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

    public void savePet(Map<String, String> userResponses) throws IOException {
        String firstName = normalizeText(userResponses.get("first name"), true);
        String lastName = normalizeText(userResponses.get("last name"), true);

        if (containsInvalidCharacters(firstName) || containsInvalidCharacters(lastName)) {
            throw new IllegalArgumentException("First and last names must contain only letters A-Z.");
        }

        PetType petType = parseEnum(userResponses.get("2 - What type of pet is it (Cat = 1/Dog = 2)?"), PetType::fromValue, "Invalid pet type option.");
        BiologicalSex biologicalSex = parseEnum(userResponses.get("3 - What is the pet's gender (Female = 1/Male = 2)?"), BiologicalSex::fromValue, "Invalid gender option.");

        Integer addressNumber = parseIntegerOrDefault(userResponses.get("address number"));
        String addressName = normalizeText(userResponses.get("address name"), false);
        String addressCity = normalizeText(userResponses.get("address city"), false);

        Double age = parseDouble(userResponses.get("5 - What is the approximate age of the pet?"));
        if (age != null && age < 1) {
            age = Double.parseDouble(String.format("%.1f", age)); // padroniza para 0.x
        }
        if (age != null && (age <= 0 || age > 20)) {
            throw new IllegalArgumentException("Age must be between 0.1 and 20 years.");
        }

        Double weight = parseDouble(userResponses.get("6 - What is the approximate weight of the pet?"));
        if (weight != null && (weight < 0.5 || weight > 60)) {
            throw new IllegalArgumentException("Weight must be between 0.5 and 60 kg.");
        }

        String breed = normalizeText(userResponses.get("7 - What is the breed of the pet?"), false);
        if (!breed.equals(Constants.NOT_INFORMED) && containsInvalidCharacters(breed)) {
            throw new IllegalArgumentException("Breed must contain only letters A-Z.");
        }

        Pet pet = new Pet(firstName, lastName, petType, biologicalSex,
                addressNumber, addressName, addressCity, age, weight, breed);
        petRepository.save(pet);
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

        petRepository.updatePetByIndex(originalPet, oldFilePath);
    }

    public void deletePetByIndex(int targetIndex, List<Pet> pets) throws IOException {

        if (targetIndex < 1 || targetIndex > pets.size()) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        Pet existingPet = pets.get(targetIndex - 1);
        Path oldFilePath = existingPet.getFilePath();

        petRepository.deletePetByIndex(existingPet, oldFilePath, pets, targetIndex);

    }

    private String normalizeText(String value, boolean mandatory) {
        if (value == null || value.trim().isEmpty()) {
            if (mandatory) throw new IllegalArgumentException("Mandatory field missing.");
            return Constants.NOT_INFORMED;
        }
        return value.trim();
    }

    private Integer parseIntegerOrDefault(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Double.parseDouble(value.replace(",", ".").trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number.");
        }
    }

    private <T> T parseEnum(String input, Function<Integer, T> converter, String errorMessage) {
        try {
            return converter.apply(Integer.parseInt(input.trim()));
        } catch (Exception e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean containsInvalidCharacters(String text) {
        return !text.matches("^[A-Za-zÀ-ÿ\\s]+$"); // inclui acentos e espaço
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
