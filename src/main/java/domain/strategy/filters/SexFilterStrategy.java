package src.main.java.domain.strategy.filters;

import src.main.java.domain.entity.Pet;
import src.main.java.domain.enums.BiologicalSex;
import src.main.java.domain.strategy.PetFilterStrategy;

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
