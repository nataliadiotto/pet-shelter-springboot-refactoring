package com.diotto.petshelter.domain.DTO;

public record PetUpdtRequestDTO(
            String firstName,
            String lastName,
            String zipCode,
            Integer addressNumber,
            String streetName,
            String addressCity,
            Double age,
            Double weight,
            String breed
    ) {}


