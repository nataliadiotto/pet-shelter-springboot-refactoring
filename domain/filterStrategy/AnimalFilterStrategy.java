package domain.filterStrategy;

import domain.Animal;

import java.util.List;

public interface AnimalFilterStrategy {
    List<Animal> filter(List<Animal> animals, String value);

}
