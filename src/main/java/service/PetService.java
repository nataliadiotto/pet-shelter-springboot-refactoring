package service;

import domain.entity.Pet;
import domain.enums.FilterType;
import domain.enums.PetType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PetService {

    void savePet(Map<String, String> userResponses) throws IOException;
    List<Pet> listAll();
    List<Pet> filterPets(PetType petType, Map<FilterType, String> filters);
    void updatePet(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException;
    void deletePet(int targetIndex, List<Pet> pets) throws IOException;

}
