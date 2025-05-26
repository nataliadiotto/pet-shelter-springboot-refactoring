package src.main.java.domain.DTO;

import src.main.java.domain.entity.Pet;
import src.main.java.domain.enums.BiologicalSex;
import src.main.java.domain.enums.PetType;
import src.main.java.domain.utils.Constants;
import lombok.Data;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
public class PetResponseDTO {


    private String fullName;
    private String petType;
    private String biologicalSex;
    private String fullAddress;
    private String formattedWeight;
    private String formattedAge;
    private String breed;

    public PetResponseDTO(Pet pet) {
        this.fullName = capitalize(pet.getFirstName()) + " " + capitalize(pet.getLastName());
        this.petType = capitalize(pet.getPetType().toString());
        this.biologicalSex = capitalize(pet.getBiologicalSex().toString());
        this.fullAddress = pet.getAddressNumber() + ", " + capitalize(pet.getStreetName()) + ", " + capitalize(pet.getAddressCity());
        this.formattedWeight = pet.getWeight() != null ? String.format(Locale.ENGLISH, "%.1fkg", pet.getWeight()) : Constants.NOT_INFORMED;
        this.formattedAge = pet.getAge() != null ? String.format(Locale.ENGLISH, "%.1f years old", pet.getAge()) : Constants.NOT_INFORMED;
        this.breed = pet.getBreed();
    }

    private String capitalize(String input) {
        if (input == null) return "";
        return Arrays.stream(input.split(" "))
                .map(word -> word.isEmpty() ? "" : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
