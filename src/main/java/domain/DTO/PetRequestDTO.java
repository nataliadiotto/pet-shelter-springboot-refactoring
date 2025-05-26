package src.main.java.domain.DTO;

import java.util.Objects;

public record PetRequestDTO(String firstName, String lastName, Integer addressNumber, String streetName, String addressCity, Double age, Double weight, String breed) {

    public PetRequestDTO(String firstName, String lastName, Integer addressNumber, String streetName, String addressCity, Double age, Double weight, String breed) {
        this.firstName = Objects.requireNonNull(firstName, "First name is required.");
        this.lastName = Objects.requireNonNull(lastName, "Last name is required.");
        this.addressNumber = addressNumber;
        this.streetName = Objects.requireNonNull(streetName, "Street name is required.");
        this.addressCity = Objects.requireNonNull(addressCity, "City name is required.");
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

}
