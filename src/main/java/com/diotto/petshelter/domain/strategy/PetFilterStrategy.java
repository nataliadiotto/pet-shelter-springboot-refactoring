package com.diotto.petshelter.domain.strategy;

import com.diotto.petshelter.domain.entity.Pet;

import java.util.List;

public interface PetFilterStrategy {
    List<Pet> filter(List<Pet> pets, String value);

}
