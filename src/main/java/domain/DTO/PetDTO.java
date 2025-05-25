package domain.DTO;

import domain.entity.Pet;
import domain.utils.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PetDTO {


    private String fullName;
    private String fullAddress;
    private String formattedWeight;
    private String formattedAge;
    private String breed;

    public PetDTO(Pet pet) {
        this.fullName = capitalize(pet.getFirstName()) + " " + capitalize(pet.getLastName());
        this.fullAddress = pet.getAddressNumber() + ", " + capitalize(pet.getAddressName()) + ", " + capitalize(pet.getAddressCity());
        this.formattedWeight = pet.getWeight() != null ? String.format("%.1fkg", pet.getWeight()) : Constants.NOT_INFORMED;
        this.formattedAge = pet.getAge() != null ? String.format("%.1f years old", pet.getAge()) : Constants.NOT_INFORMED;
        this.breed = pet.getBreed();
    }

    private String capitalize(String input) {
        if (input == null) return "";
        return Arrays.stream(input.split(" "))
                .map(word -> word.isEmpty() ? "" : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
