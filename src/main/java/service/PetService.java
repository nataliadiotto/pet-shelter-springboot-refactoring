package service;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.BiologicalSex;
import domain.enums.FilterType;
import domain.strategy.filters.*;
import domain.strategy.PetFilterStrategy;
import domain.utils.Constants;
import domain.utils.InputHelper;
import repository.PetRepositoryImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

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

        Integer addressNumber = InputHelper.parseIntegerOrDefault((userResponses.get("address number")));
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

    public void updatePet(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException {
        if (updatedData == null || updatedData.isEmpty()) {
            throw new IllegalArgumentException("Not enough data to update pet.");
        }

        Pet existingPet = filteredPets.get(index - 1);
        Path originalFilePath = existingPet.getFilePath();

        updateIfNotBlank(updatedData, "firstName", String.class, existingPet::setFirstName);
        updateIfNotBlank(updatedData, "lastName", String.class, existingPet::setLastName);

        updateIfNotBlank(updatedData, "addressNumber", Integer.class, existingPet::setAddressNumber);
        updateIfNotBlank(updatedData, "addressName", String.class, existingPet::setAddressName);
        updateIfNotBlank(updatedData, "addressCity", String.class, existingPet::setAddressCity);

        updateIfNotBlank(updatedData, "age", Double.class, existingPet::setAge);

        updateIfNotBlank(updatedData, "weight", Double.class, existingPet::setWeight);

        updateIfNotBlank(updatedData, "breed", String.class, existingPet::setBreed);

        petRepository.updatePetByPath(existingPet, originalFilePath);
    }

    public void deletePet(int targetIndex, List<Pet> pets) throws IOException {
        Pet existingPet = pets.get(targetIndex - 1);
        Path oldFilePath = existingPet.getFilePath();

        petRepository.deletePetByPath(existingPet, oldFilePath);

    }

    private String normalizeText(String value, boolean mandatory) {
        if (value == null || value.trim().isEmpty()) {
            if (mandatory) throw new IllegalArgumentException("Mandatory field missing.");
            return Constants.NOT_INFORMED;
        }
        return value.trim();
    }


}
