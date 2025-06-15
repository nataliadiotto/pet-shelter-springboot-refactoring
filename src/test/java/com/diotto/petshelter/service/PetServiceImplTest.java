package com.diotto.petshelter.service;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.domain.utils.Constants;
import com.diotto.petshelter.errors.BusinessRuleException;
import com.diotto.petshelter.errors.ResourceNotFound;
import com.diotto.petshelter.external.viacep.client.CepService;
import com.diotto.petshelter.external.viacep.dto.ViaCepResponse;
import com.diotto.petshelter.repository.PetRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

    @InjectMocks
    PetServiceImpl service;

    @Mock
    private CepService cepService;

    @Mock
    PetRepository petRepository;
    @Mock
    ModelMapper modelMapper;

    String firstName = "Sweet";
    String lastName = "Nothing";
    PetType petType = PetType.CAT;
    String zipCode = "13484339";
    BiologicalSex gender = BiologicalSex.FEMALE;
    Integer addressNumber = 1313;
    String streetName ="Folklore Avenue";
    String city ="New York";
    String state = "NY";
    Double age = 3.0;
    Double weight = 8.0;
    String breed ="Scottish Fold";

    Pet pet;
    PetDTO petDTO;
    PetResponseDTO petResponseDTO;
    PetUpdtRequestDTO petUpdtRequestDTO;

    @BeforeEach
    public void setup(){
        pet = new Pet(1L, firstName, lastName, petType, gender, zipCode, addressNumber, streetName, city, state, age, weight, breed);
        petDTO = new PetDTO(firstName, lastName, petType, gender, zipCode, addressNumber, streetName, city, state, age, weight, breed);
        petResponseDTO = new PetResponseDTO(pet);
        petUpdtRequestDTO = new PetUpdtRequestDTO( firstName, lastName, zipCode, addressNumber, streetName, city, age, weight, breed);
    }

    @Test //registerPet() success
    @DisplayName("Should register pet successfully")
    void shouldRegisterPetSuccessfully() throws BadRequestException {
        when(modelMapper.map(petDTO, Pet.class)).thenReturn(pet);
        when(petRepository.save(pet)).thenReturn(pet);

        Pet savedPet = service.registerPet(petDTO);

        assertNotNull(savedPet);
        assertEquals(firstName, savedPet.getFirstName());
        assertEquals(gender, savedPet.getBiologicalSex());
        assertEquals(city, savedPet.getAddressCity());

        verify(modelMapper).map(petDTO, Pet.class);
        verify(petRepository).save(pet);
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Should fetch zip code from CepService when PetDTO has no zip code")
    void shouldFetchZipCodeWhenPetDTOHasNoZipCode() throws BadRequestException {
        PetDTO dtoWithoutZip = new PetDTO(firstName, lastName, petType, gender, "", addressNumber, streetName, city, state, age, weight, breed);

        ViaCepResponse mockedResponse = new ViaCepResponse("12345678", streetName, "", "", city, state, "", "", "", "");
        when(cepService.getCepByAddress(state, city, streetName)).thenReturn(List.of(mockedResponse));

        when(modelMapper.map(dtoWithoutZip, Pet.class)).thenReturn(pet);
        when(petRepository.save(pet)).thenReturn(pet);

        Pet savedPet = service.registerPet(dtoWithoutZip);

        assertNotNull(savedPet);
        assertEquals(pet.getFirstName(), savedPet.getFirstName());

        verify(cepService, times(1)).getCepByAddress(state, city, streetName);

        verify(petRepository, times(1)).save(pet);
    }

    @Test
    @DisplayName("Should set zip code as NOT_FOUND when CepService returns empty list")
    void shouldSetZipCodeAsNotFoundWhenCepServiceReturnsEmpty() throws BadRequestException {
        PetDTO dtoWithoutZip = new PetDTO(firstName, lastName, petType, gender, "", addressNumber, streetName, city, state, age, weight, breed);

        when(cepService.getCepByAddress(state, city, streetName)).thenReturn(emptyList());
        when(modelMapper.map(dtoWithoutZip, Pet.class)).thenReturn(pet);
        when(petRepository.save(pet)).thenReturn(pet);

        Pet savedPet = service.registerPet(dtoWithoutZip);

        verify(cepService, times(1)).getCepByAddress(state, city, streetName);
        assertEquals(Constants.NOT_FOUND, dtoWithoutZip.getZipCode());
    }

    @Test //listAll() success
    @DisplayName("Should list all pets successfully")
    void shouldListAllPetsSuccessfully() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> petList = service.listAll();

        assertNotNull(petList);
        assertEquals(1, petList.size());
        assertEquals(Pet.class, petList.get(0).getClass());
        assertEquals(firstName, petList.get(0).getFirstName());
        assertEquals(city, petList.get(0).getAddressCity());


        verify(petRepository).findAll();
        verifyNoMoreInteractions(petRepository);
    }

    @Test //listAll() failure
    @DisplayName("Should throw 404 Not Found when no pets are registered")
    void shouldThrowNotFoundWhenNoPetsExist() {
        when(petRepository.findAll()).thenReturn(emptyList());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> service.listAll());

        assertEquals("There are no registers of pets in the system.", exception.getMessage());
    }

    @Test //searchPets() success
    @DisplayName("Should return pets matching the given filters")
    void shouldReturnFilteredPets(){
        when(petRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(pet));

        List<Pet> petList = service.searchPets(PetType.CAT.toString(), null, null, null, null, null, null, null, 8.0, null);

        assertNotNull(petList);
        assertEquals(1, petList.size());
        assertEquals(pet, petList.get(0));
        assertEquals(pet.getWeight(), petList.get(0).getWeight());

        verify(petRepository).findAll(any(Specification.class));
    }

    @Test //searchPets() failure
    @DisplayName("Should throw 404 Not Found when no pets match the search filters")
    void shouldThrowNotFoundWhenNoPetsMatchFilters() {
        when(petRepository.findAll(any(Specification.class)))
                .thenReturn(emptyList());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, ()
                -> service.searchPets(PetType.CAT.toString(), null, "Test", null,null,  null, null, null, null, null));

        assertEquals("There are no registers of pets with the provided filters in the system.", exception.getMessage());

    }

    @Test //searchPets() failure - no PetType selected
    @DisplayName("Should throw 400 Business Rule Exception when PetType filter is not selected")
    void shouldThrowBusinessRuleWhenNoPetTypeFilter() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, ()
                -> service.searchPets(null, null, "Test", null, "Test", null, null, null, null, null));

        assertEquals("Pet type filter is mandatory.", exception.getMessage());

    }

    @Test //searchPets() failure - more than two filters
    @DisplayName("Should throw 400 Business Rule Exception when quantity of filters exceed")
    void shouldThrowBusinessRuleWhenMoreFiltersSelected() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, ()
                -> service.searchPets(PetType.CAT.toString(), BiologicalSex.FEMALE, "Test", null, null, "Test", null, null, null, null));

        assertEquals("You must apply at least 1 and at most 2 additional filters (besides pet type).", exception.getMessage());

    }

    @Test
    @DisplayName("Should update a pet by ID with valid data")
    void shouldUpdatePetByIdWhenValid() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        when(petRepository.save(any())).thenReturn(pet);

        Pet updatedPet = service.updatePet(1L, petUpdtRequestDTO);

        assertNotNull(updatedPet);
        assertEquals(firstName, updatedPet.getFirstName());
        assertEquals(age, updatedPet.getAge());
        assertEquals(breed, updatedPet.getBreed());

        verify(petRepository, times(1)).findById(anyLong());
        verify(petRepository, times(1)).save(any());
        verifyNoMoreInteractions(petRepository);

    }

    @Test
    @DisplayName("Should return 404 Not Found when updating non-existent pet by ID")
    void shouldThrowNotFoundWhenUpdatingNonExistentPet() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, ()
                -> service.updatePet(1L, petUpdtRequestDTO));

        assertEquals("Pet not found with: ID = '1'", exception.getMessage());

    }

    @Test
    @DisplayName("Should delete a pet by ID when it is valid")
    void shouldDeleteByIdWhenIdExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).delete(any(Pet.class));

        service.deletePet(1L);

        verify(petRepository).findById(1L);
        verify(petRepository).delete(pet);
        verifyNoMoreInteractions(petRepository);
    }

    @Test
    @DisplayName("Should return 404 Not Found when deleting non-existent pet by ID")
    void shouldThrowNotFoundWhenDeletingNonExistentPet() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, ()
                -> service.updatePet(1L, petUpdtRequestDTO));

        assertEquals("Pet not found with: ID = '1'", exception.getMessage());
    }


}