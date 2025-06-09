package com.diotto.petshelter.service;
import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.errors.BusinessRuleException;
import com.diotto.petshelter.errors.ResourceNotFound;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PetService {

    Pet registerPet(PetDTO PetDTO) throws BadRequestException;
    List<Pet> listAll() throws ResourceNotFound;

    List<Pet> searchPets(String type, BiologicalSex biologicalSex, String name, String streetName, String city, Integer addressNumber, Double age, Double weight, String breed) throws ResourceNotFound, BusinessRuleException;

    Pet updatePet(Long id, PetUpdtRequestDTO petUpdtRequestDTO) throws ResourceNotFound;

    void deletePet(Long id) throws ResourceNotFound;
    Pet convertPetFromDTO(PetDTO petDTO) throws BadRequestException;
}
