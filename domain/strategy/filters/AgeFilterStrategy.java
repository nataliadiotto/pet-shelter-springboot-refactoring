package domain.strategy.filters;

import domain.entity.Animal;
import domain.strategy.AnimalFilterStrategy;

import java.util.List;

public class AgeFilterStrategy implements AnimalFilterStrategy {

    @Override
    public List<Animal> filter(List<Animal> animals, String value) {
        try{
            Double age = Double.valueOf(value);
            return animals.stream()
                    .filter(animal -> age.equals(animal.getAge()))
                    .toList();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid age!");
        }
    }
}
