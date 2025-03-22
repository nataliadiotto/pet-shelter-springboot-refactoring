package service;

import domain.Animal;
import repository.AnimalRepositoryImpl;

public class AnimalService {


    private final AnimalRepositoryImpl animalRepository;

    public AnimalService(AnimalRepositoryImpl animalRepository) {
        this.animalRepository = animalRepository;
    }

    //TODO test saveAnimal()
    public void saveAnimal(Animal animal) {
        //Validate name and surname

        if (isNameValid(animal.getName()) || isNameValid(animal.getName())) {
            throw new IllegalArgumentException("Name and surname must not be blank and .");

        }




        animalRepository.save(animal);
        System.out.println("Animal salvo na service" + animal);
    }

//    private splitNameSurname(String name){
//        name.trim();
//        String[] nameParts = name.split("\\s+");
//        String firstName = nameParts[0];
//        String surname = nameParts[1];
//    }

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z]+") && name != null && name.isEmpty();
    }
}
