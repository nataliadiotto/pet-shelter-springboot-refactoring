package com.diotto.petshelter.domain.DTO;

public record PetUpdtRequestDTO(
            String firstName,
            String lastName,
            String zipCode,
            Integer addressNumber,
            String streetName,
            String addressCity,
            String state,
            Double age,
            Double weight,
            String breed
    ) {}


