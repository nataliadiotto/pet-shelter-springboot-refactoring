package domain.DTO;

import domain.enums.BiologicalSex;
import domain.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PetDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private PetType petType;
    private BiologicalSex biologicalSex;

    private Integer addressNumber;
    private String addressName;
    private String addressCity;

    private Double age;
    private Double weight;
    private String breed;

}
