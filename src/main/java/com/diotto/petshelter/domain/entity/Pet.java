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
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private PetType petType;

    @Column(nullable = false)
    private BiologicalSex biologicalSex;

    private String zipCode;
    private Integer addressNumber;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String streetName;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String addressCity;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String state;

    private Double age;
    private Double weight;
    private String breed;



}