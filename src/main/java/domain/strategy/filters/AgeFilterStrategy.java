package src.main.java.domain.strategy.filters;

import src.main.java.domain.entity.Pet;
import src.main.java.domain.strategy.PetFilterStrategy;

import java.util.List;

public class AgeFilterStrategy implements PetFilterStrategy {

    @Override
    public List<Pet> filter(List<Pet> pets, String value) {
        try{
            Double age = Double.valueOf(value);
            return pets.stream()
                    .filter(pet -> age.equals(pet.getAge()))
                    .toList();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid age!");
        }
    }
}
