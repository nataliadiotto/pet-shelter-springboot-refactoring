package com.diotto.petshelter.service;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.entity.PetSpecifications;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.FilterType;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.domain.strategy.PetFilterStrategy;
import com.diotto.petshelter.domain.strategy.filters.*;
import com.diotto.petshelter.domain.utils.Constants;
import com.diotto.petshelter.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.diotto.petshelter.repository.PetRepository;


import java.util.List;
import java.util.Map;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    private static final Map<FilterType, PetFilterStrategy> STRATEGY_MAP = Map.of(
            FilterType.NAME, new NameFilterStrategy(),
            FilterType.SEX, new SexFilterStrategy(),
            FilterType.AGE, new AgeFilterStrategy(),
            FilterType.WEIGHT, new WeightFilterStrategy(),
            FilterType.BREED, new BreedFilterStrategy(),
            FilterType.ADDRESS, new AddressFilterStrategy()
    );

    @Autowired
    public PetServiceImpl(PetRepository petRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Pet registerPet(PetDTO petDTO) {
        Pet pet = convertPetFromDTO(petDTO);
        return petRepository.save(pet);
    }


    @Override
    public List<Pet> listAll() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> searchPets(PetType type, BiologicalSex biologicalSex, String name, String streetName, String city, Integer addressNumber, Double age, Double weight, String breed) {
        Specification<Pet> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and(PetSpecifications.hasType(type));
        }

        if (biologicalSex != null) {
            spec = spec.and(PetSpecifications.hasGender(biologicalSex));
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and(PetSpecifications.hasName(name));
        }

        if (streetName != null && !streetName.isBlank()) {
            spec = spec.and(PetSpecifications.hasStreet(streetName));
        }

        if (city != null && !city.isBlank()) {
            spec = spec.and(PetSpecifications.hasCity(city));
        }

        if (addressNumber != null) {
            spec = spec.and(PetSpecifications.hasAddressNumber(addressNumber));
        }

        if (age != null) {
            spec = spec.and(PetSpecifications.hasAge(age));
        }

        if (weight != null) {
            spec = spec.and(PetSpecifications.hasWeight(weight));
        }

        if (breed != null && !breed.isBlank()) {
            spec = spec.and(PetSpecifications.hasBreed(breed));
        }

        return petRepository.findAll(spec);
    }

    @Override
    public Pet updatePet(Long id, PetUpdtRequestDTO dto) throws ResourceNotFoundException {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "ID", id));

        if (dto.firstName() != null) existingPet.setFirstName(dto.firstName());
        if (dto.lastName() != null) existingPet.setLastName(dto.lastName());
        if (dto.addressNumber() != null) existingPet.setAddressNumber(dto.addressNumber());
        if (dto.streetName() != null) existingPet.setStreetName(dto.streetName());
        if (dto.addressCity() != null) existingPet.setAddressCity(dto.addressCity());
        if (dto.age() != null) existingPet.setAge(dto.age());
        if (dto.weight() != null) existingPet.setWeight(dto.weight());
        if (dto.breed() != null) existingPet.setBreed(dto.breed());

        return petRepository.save(existingPet);
    }

    //TODO: refactor
    @Override
    public Pet deletePet(Long id) throws ResourceNotFoundException {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "ID", id));

        petRepository.delete(existingPet);

        return existingPet;
    }

    @Override
    public Pet convertPetFromDTO(PetDTO petDTO) {
        return modelMapper.map(petDTO, Pet.class);
    }

    private String normalizeText(String value, boolean mandatory) {
        if (value == null || value.trim().isEmpty()) {
            if (mandatory) throw new IllegalArgumentException("Mandatory field missing.");
            return Constants.NOT_INFORMED;
        }
        return value.trim();
    }


}
