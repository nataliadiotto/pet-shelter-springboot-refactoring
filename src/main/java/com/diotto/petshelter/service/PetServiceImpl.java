package com.diotto.petshelter.service;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.DTO.PetUpdtRequestDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.domain.utils.Constants;
import com.diotto.petshelter.errors.BusinessRuleException;
import com.diotto.petshelter.external.viacep.client.CepService;
import com.diotto.petshelter.external.viacep.dto.ViaCepResponse;
import com.diotto.petshelter.publisher.PetEventPublisher;
import com.diotto.petshelter.repository.PetSpecifications;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.errors.ResourceNotFound;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.diotto.petshelter.repository.PetRepository;


import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private final CepService cepService;
    private final PetEventPublisher publisher;


    @Autowired
    public PetServiceImpl(PetRepository petRepository, ModelMapper modelMapper, CepService cepService, PetEventPublisher publisher) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
        this.cepService = cepService;
        this.publisher = publisher;
    }

    @Override
    public Pet registerPet(PetDTO petDTO) throws BadRequestException {
        if (petDTO.getZipCode().isBlank() || petDTO.getZipCode().isEmpty()) {
            String zipCode = getValidZipCode(petDTO);

            petDTO.setZipCode(zipCode);
        }

        Pet pet = convertPetFromDTO(petDTO);
        Pet savedPet = petRepository.save(pet);

        PetResponseDTO petResponseDTO = new PetResponseDTO(savedPet);
        publisher.publishPetCreatedEvent(petResponseDTO);

        return savedPet;
    }


    @Override
    public List<Pet> listAll() {
        List<Pet> pets = petRepository.findAll();
        if (pets.isEmpty()) {
            throw new ResourceNotFound("pets");
        }

        return pets;
    }

    @Override
    public List<Pet> searchPets(String type, BiologicalSex biologicalSex, String name, String zipCode, String streetName, String city, Integer addressNumber, String state, Double age, Double weight, String breed) throws ResourceNotFound, BusinessRuleException {
        int filterCount = countFilters(biologicalSex, name, zipCode, streetName, city, addressNumber, state, age, weight, breed);

        if (filterCount > 2 || filterCount == 0){
            throw new BusinessRuleException("You must apply at least 1 and at most 2 additional filters (besides pet type).");
        }

        if (type == null || type.isBlank()) {
            throw new BusinessRuleException("Pet type filter is mandatory.");
        }
        PetType petTypeEnum;
        try {
            petTypeEnum = PetType.fromString(type);
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Invalid pet type value.");
        }

        Specification<Pet> spec = Specification.where(null);
        spec = spec.and(PetSpecifications.hasType(petTypeEnum));

        if (biologicalSex != null) {
            spec = spec.and(PetSpecifications.hasGender(biologicalSex));
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and(PetSpecifications.hasName(name));
        }

        if (zipCode != null && !zipCode.isBlank()) {
            spec = spec.and(PetSpecifications.hasZipCode(zipCode));
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

        if (state != null && !state.isBlank()) {
            spec = spec.and(PetSpecifications.hasState(state));
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

        List<Pet> filteredPets = petRepository.findAll(spec);
        if (filteredPets.isEmpty()) {
            throw new ResourceNotFound("pets with the provided filters");
        }

        return filteredPets;
    }

    @Override
    public Pet updatePet(Long id, PetUpdtRequestDTO dto) throws ResourceNotFound {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Pet", "ID", id));

        if (dto.firstName() != null) existingPet.setFirstName(dto.firstName());
        if (dto.lastName() != null) existingPet.setLastName(dto.lastName());
        if (dto.zipCode() != null) existingPet.setZipCode(dto.zipCode());
        if (dto.addressNumber() != null) existingPet.setAddressNumber(dto.addressNumber());
        if (dto.streetName() != null) existingPet.setStreetName(dto.streetName());
        if (dto.addressCity() != null) existingPet.setAddressCity(dto.addressCity());
        if (dto.state() != null) existingPet.setState(dto.state());
        if (dto.age() != null) existingPet.setAge(dto.age());
        if (dto.weight() != null) existingPet.setWeight(dto.weight());
        if (dto.breed() != null) existingPet.setBreed(dto.breed());

        return petRepository.save(existingPet);
    }

    //TODO: refactor
    @Override
    public void deletePet(Long id) throws ResourceNotFound {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Pet", "ID", id));

        petRepository.delete(existingPet);
    }

    @Override
    public Pet convertPetFromDTO(PetDTO petDTO) throws BadRequestException {
        try {
            return modelMapper.map(petDTO, Pet.class);
        } catch (Exception ex) {
            throw new BadRequestException("Failed to convert PetDTO to Pet", ex);
        }
    }

    private int countFilters(BiologicalSex biologicalSex, String name, String zipCode, String streetName, String city, Integer addressNumber, String state, Double age, Double weight, String breed) {
        int count = 0;
        if (biologicalSex != null) count++;
        if (name != null) count++;
        if (zipCode != null) count++;
        if (streetName != null) count++;
        if (city != null) count++;
        if (addressNumber != null) count++;
        if (state != null) count++;
        if (age != null) count++;
        if (weight != null) count++;
        if (breed != null) count++;

        return count;
    }

    private String getValidZipCode(PetDTO petDTO){
        List<ViaCepResponse> results = cepService.getCepByAddress(petDTO.getState(), petDTO.getAddressCity(), petDTO.getStreetName());

        if (!results.isEmpty()) {
            return results.get(0).cep();
        }

        return Constants.NOT_FOUND;
    }

}
