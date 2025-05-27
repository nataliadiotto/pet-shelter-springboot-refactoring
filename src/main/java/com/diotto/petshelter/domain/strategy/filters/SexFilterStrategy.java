package com.diotto.petshelter.domain.strategy.filters;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.strategy.PetFilterStrategy;
import com.diotto.petshelter.domain.enums.BiologicalSex;

import java.util.List;

public class SexFilterStrategy implements PetFilterStrategy {

        @Override
        public List<Pet> filter(List<Pet> pets, String value) {
            BiologicalSex sex = BiologicalSex.fromString(value);
            if (sex == null) {
                System.out.println("Invalid biological sex: " + value);
                return List.of();
            }

            return pets.stream()
                    .filter(pet -> sex.equals(pet.getBiologicalSex()))
                    .toList();
        }
}
