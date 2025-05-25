package domain.entity;

import domain.enums.BiologicalSex;
import domain.enums.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Data
@AllArgsConstructor
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    private final PetType petType;
    private final BiologicalSex biologicalSex;
    private Integer addressNumber;
    private String addressName;

    @NotNull
    @NotBlank
    private String addressCity;
    private Double age;
    private Double weight;
    private String breed;

}