package domain.strategy.filters;

import domain.entity.Pet;
import domain.enums.BiologicalSex;
import domain.strategy.PetFilterStrategy;

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
