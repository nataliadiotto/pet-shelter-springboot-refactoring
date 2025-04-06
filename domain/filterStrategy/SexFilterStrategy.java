package domain.filterStrategy;

import domain.Animal;
import domain.enums.BiologicalSex;

import java.util.List;

public class SexFilterStrategy implements AnimalFilterStrategy {

        @Override
        public List<Animal> filter(List<Animal> animals, String value) {
            BiologicalSex sex = BiologicalSex.fromString(value);
            if (sex == null) {
                System.out.println("Invalid biological sex: " + value);
                return List.of();
            }

            return animals.stream()
                    .filter(animal -> sex.equals(animal.getBiologicalSex()))
                    .toList();
        }
}
