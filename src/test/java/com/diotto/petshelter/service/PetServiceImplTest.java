package com.diotto.petshelter.service;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

    @InjectMocks
    PetServiceImpl service;

    @Mock
    PetRepository petRepository;
    @Mock
    ModelMapper modelMapper;

    String firstName = "Sweet";
    String lastName = "Nothing";
    PetType petType = PetType.CAT;
    BiologicalSex gender = BiologicalSex.FEMALE;
    Integer addressNumber = 1313;
    String streetName ="Folklore Avenue";
    String city ="New York";
    Double age = 3.0;
    Double weight = 8.0;
    String breed ="Scottish Fold";

    Pet pet;
    PetDTO petDTO;
    PetResponseDTO petResponseDTO;

    @BeforeEach
    public void setup(){
        pet = new Pet(1L, firstName, lastName, petType, gender, addressNumber, streetName, city, age, weight, breed);
        petDTO = new PetDTO(firstName, lastName, petType, gender, addressNumber, streetName, city, age, weight, breed);
        petResponseDTO = new PetResponseDTO(pet);
    }

    @Test
    void registerPet() {
    }

    @Test
    void listAll() {
    }

    @Test
    void searchPets() {
    }

    @Test
    void updatePet() {
    }

    @Test
    void deletePet() {
    }

    @Test
    void convertPetFromDTO() {
    }
}