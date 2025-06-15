package com.diotto.petshelter.domain.DTO;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.utils.Constants;

import lombok.Data;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
public class PetResponseDTO {

    private Long id;
    private String fullName;
    private String petType;
    private String biologicalSex;
    private String zipCode;
    private String address;
    private String weight;
    private String age;
    private String breed;

    public PetResponseDTO(Pet pet) {
        this.id = pet.getId();
        this.fullName = capitalize(pet.getFirstName()) + " " + capitalize(pet.getLastName());
        this.petType = capitalize(pet.getPetType().toString());
        this.biologicalSex = capitalize(pet.getBiologicalSex().toString());

        this.zipCode = pet.getZipCode() == null
                ? Constants.NOT_FOUND
                : pet.getZipCode();

        this.address = getAddressNumberStr(pet.getAddressNumber()) + ", "
                + capitalize(pet.getStreetName()) + ", "
                + capitalize(pet.getAddressCity());

            if (pet.getState() != null && !pet.getState().isBlank()) {
                this.address += " - " + pet.getState().toUpperCase();
        }

        this.weight = pet.getWeight() == null
                ? Constants.NOT_INFORMED
                : String.format(Locale.ENGLISH, "%.1fkg", pet.getWeight());

        this.age = pet.getAge() == null
                ? Constants.NOT_INFORMED
                : String.format(Locale.ENGLISH, "%.1f years old", pet.getAge());

        this.breed = pet.getBreed() == null
                ? Constants.NOT_INFORMED
                : capitalize(pet.getBreed());
    }
    private String getAddressNumberStr(Integer addressNumber){
        return addressNumber == null ? Constants.NOT_INFORMED : String.valueOf(addressNumber);
    }

    private String capitalize(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        return Arrays.stream(input.split(" "))
                .map(word -> word.isEmpty() ? "" : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }


}
