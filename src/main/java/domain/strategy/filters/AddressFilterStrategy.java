package domain.strategy.filters;

import domain.entity.Pet;
import domain.strategy.PetFilterStrategy;

import java.util.List;

public class AddressFilterStrategy implements PetFilterStrategy {

    @Override
    public List<Pet> filter(List<Pet> pets, String value) {
        String valueLower = value.toLowerCase();
        try{
            return pets.stream()
                    .filter(pet -> pet.getFullAddress()
                            .toLowerCase()
                            .contains(valueLower))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid address: " + value);
            return List.of();
        }
    }
}
