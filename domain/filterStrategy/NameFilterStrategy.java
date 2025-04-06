package domain.filterStrategy;

import domain.Animal;

import java.util.List;

public class NameFilterStrategy implements AnimalFilterStrategy {

    @Override
    public List<Animal> filter(List<Animal> animals, String value) {
        String valueLower = value.toLowerCase();
        try{
            return animals.stream()
                    .filter(animal -> animal.getFullName()
                            .toLowerCase()
                            .contains(valueLower))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid name: " + value);
            return List.of();
        }
    }
}
