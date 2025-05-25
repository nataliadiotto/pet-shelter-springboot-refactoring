package src.main.java.domain.strategy;

import src.main.java.domain.entity.Pet;

import java.util.List;

public interface PetFilterStrategy {
    List<Pet> filter(List<Pet> pets, String value);

}
