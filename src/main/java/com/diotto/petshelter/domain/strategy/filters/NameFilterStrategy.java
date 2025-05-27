package com.diotto.petshelter.domain.strategy.filters;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.strategy.PetFilterStrategy;

import java.util.List;

public class NameFilterStrategy implements PetFilterStrategy {

    @Override
    public List<Pet> filter(List<Pet> pets, String value) {
        String valueLower = value.toLowerCase();
        try{
            return pets.stream()
                    .filter(pet -> pet.getFirstName()
                            .toLowerCase()
                            .contains(valueLower))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid name: " + value);
            return List.of();
        }
    }
}
