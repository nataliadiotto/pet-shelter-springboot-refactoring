package service;

import domain.DTO.PetDTO;
import domain.entity.Pet;
import domain.enums.FilterType;
import domain.enums.PetType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PetService {

    Pet savePet(Pet Pet) throws IOException;
    List<Pet> listAll();
    List<Pet> filterPets(PetType petType, Map<FilterType, String> filters);
    void updatePet(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException;
    Pet deletePet(Long id) throws IOException;

    Pet savePetFromDTO(PetDTO petDTO);
}
