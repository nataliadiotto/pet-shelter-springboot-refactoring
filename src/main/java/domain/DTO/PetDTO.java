package src.main.java.domain.DTO;

import src.main.java.domain.enums.BiologicalSex;
import src.main.java.domain.enums.PetType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetDTO {

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "First name must contain only letters and spaces.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "Last name must contain only letters and spaces.")
    private String lastName;

    @NotNull(message = "Pet type is required.")
    private PetType petType;

    @NotNull(message = "Biological sex is required.")
    private BiologicalSex biologicalSex;

    // Optional field — if needed, add @NotNull
    @Min(value = 1, message = "Address number must be at least 1.")
    private Integer addressNumber;

    @Size(max = 50, message = "Street name must be less than 50 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9 ]+$", message = "Street name must contain only letters and numbers.")
    private String streetName;

    @Size(max = 50, message = "City name must be less than 50 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "City name must contain only letters.")
    private String addressCity;

    @DecimalMin(value = "0.1", message = "Age must be at least 0.1 year.")
    @DecimalMax(value = "20.0", message = "Age must not exceed 20 years.")
    private Double age;

    @DecimalMin(value = "0.5", message = "Weight must be at least 0.5 kg.")
    @DecimalMax(value = "60.0", message = "Weight must not exceed 60 kg.")
    private Double weight;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]*$", message = "Breed must contain only letters and spaces.")
    private String breed;

}
