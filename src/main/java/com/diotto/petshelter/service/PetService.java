package com.diotto.petshelter.service;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.errors.ResourceNotFound;

import java.util.List;

public interface PetService {

    Pet registerPet(PetDTO PetDTO);
    List<Pet> listAll() throws ResourceNotFound;

    List<Pet> searchPets(PetType type, BiologicalSex biologicalSex, String name, String streetName, String city, Integer addressNumber, Double age, Double weight, String breed) throws ResourceNotFound;
    Pet updatePet(Long id, PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFound;

    void deletePet(Long id) throws ResourceNotFound;
    Pet convertPetFromDTO(PetDTO petDTO);
}
