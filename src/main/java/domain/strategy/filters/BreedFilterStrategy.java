package src.main.java.domain.strategy.filters;

import src.main.java.domain.entity.Pet;
import src.main.java.domain.strategy.PetFilterStrategy;

import java.util.List;

public class BreedFilterStrategy implements PetFilterStrategy {

    @Override
    public List<Pet> filter(List<Pet> pets, String value) {
        String valueLower = value.toLowerCase();
        try{
            return pets.stream()
                    .filter(pet -> pet.getBreed()
                            .toLowerCase()
                            .contains(valueLower))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid breed: " + value);
            return List.of();
        }
    }
}
