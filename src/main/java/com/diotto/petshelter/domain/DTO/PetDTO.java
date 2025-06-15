package com.diotto.petshelter.domain.DTO;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
public class PetDTO {

    @Schema(example = "Clara")
    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String firstName;

    @Schema(example = "Bow")
    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String lastName;

    @Schema(example = "CAT")
    @NotNull
    private PetType petType;

    @Schema(example = "FEMALE")
    @NotNull
    private BiologicalSex biologicalSex;

    @Schema(example = "13480100")
    private String zipCode;

    //Optional field
    @Schema(example = "1313")
    @Min(value = 1)
    private Integer addressNumber;

    @Schema(example = "Folklore Street")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9 ,.-]+$")
    private String streetName;

    @Schema(example = "Pennsylvania")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]+$")
    private String addressCity;

    @Schema(example = "SP")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$")
    private String state;

    @Schema(example = "13.0")
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "20.0")
    private Double age;

    @Schema(example = "4.2")
    @DecimalMin(value = "0.5")
    @DecimalMax(value = "60.0")
    private Double weight;

    @Schema(example = "British Shorthair")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]+$")
    private String breed;

}
