package domain.strategy;

import domain.entity.Animal;

import java.util.List;

public interface AnimalFilterStrategy {
    List<Animal> filter(List<Animal> animals, String value);

}
