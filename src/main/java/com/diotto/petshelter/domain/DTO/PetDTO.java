package com.diotto.petshelter.domain.DTO;

import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String lastName;

    @NotNull
    private PetType petType;

    @NotNull
    private BiologicalSex biologicalSex;

    //Optional field
    @Min(value = 1)
    private Integer addressNumber;

    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9 ]+$")
    private String streetName;

    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String addressCity;

    @DecimalMin(value = "0.1")
    @DecimalMax(value = "20.0")
    private Double age;

    @DecimalMin(value = "0.5")
    @DecimalMax(value = "60.0")
    private Double weight;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String breed;

}
