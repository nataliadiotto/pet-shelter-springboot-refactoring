package com.diotto.petshelter.domain.entity;

import lombok.NoArgsConstructor;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityScan
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

    private PetType petType;
    private BiologicalSex biologicalSex;
    private Integer addressNumber;
    private String streetName;

    @NotNull
    @NotBlank
    private String addressCity;
    private Double age;
    private Double weight;
    private String breed;



}