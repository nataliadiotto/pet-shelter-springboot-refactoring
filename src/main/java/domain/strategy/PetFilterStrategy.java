package domain.strategy;

import domain.entity.Pet;

import java.util.List;

public interface PetFilterStrategy {
    List<Pet> filter(List<Pet> pets, String value);

}
