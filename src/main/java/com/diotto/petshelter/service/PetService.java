package com.diotto.petshelter.service;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.errors.ResourceNotFoundException;

import java.util.List;

public interface PetService {

    Pet registerPet(PetDTO PetDTO);
    List<Pet> listAll();

    List<Pet> searchPets(PetType type, BiologicalSex biologicalSex, String name, String streetName, String city, Integer addressNumber, Double age, Double weight, String breed);
    Pet updatePet(Long id, PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFoundException;

    Pet deletePet(Long id) throws ResourceNotFoundException;
    Pet convertPetFromDTO(PetDTO petDTO);
}
