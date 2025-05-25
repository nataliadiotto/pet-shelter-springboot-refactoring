package src.main.java.service;

import src.main.java.domain.DTO.PetDTO;
import src.main.java.domain.entity.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {

    Pet registerPet(PetDTO PetDTO) throws IOException;
    List<Pet> listAll();
    //List<Pet> filterPets(PetType petType, Map<FilterType, String> filters);
    //void updatePet(int index, List<Pet> filteredPets, Map<String, Object> updatedData) throws IOException;
    Pet deletePet(Long id) throws IOException;
    Pet convertPetFromDTO(PetDTO petDTO);
}
