package domain.strategy.filters;

import domain.entity.Pet;
import domain.strategy.PetFilterStrategy;

import java.util.List;

public class WeightFilterStrategy implements PetFilterStrategy {

    @Override
    public List<Pet> filter(List<Pet> pets, String value) {
        try{
            Double weight = Double.valueOf(value);
            return pets.stream()
                    .filter(pet -> weight.equals(pet.getWeight()))
                    .toList();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid weight!");
        }
    }
}
