package src.main.java.service;

import src.main.java.domain.DTO.PetDTO;
import src.main.java.domain.DTO.PetRequestDTO;
import src.main.java.domain.entity.Pet;
import src.main.java.domain.enums.FilterType;
import src.main.java.domain.strategy.PetFilterStrategy;
import src.main.java.domain.strategy.filters.*;
import src.main.java.domain.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.main.java.repository.PetRepository;

import java.util.*;

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

//    public List<Pet> filterPets(PetType petType, Map<FilterType, String> filters) {
//        List<Pet> pets = petRepository.findAll();
//
//        if (pets.isEmpty()) {
//            System.out.println("No registered pets.");
//        }
//
//        //Filter by PetType
//        List<Pet> filteredPets = pets.stream().filter(pet -> pet.getPetType()
//                        .equals(petType))
//                .toList();
//
//
//        //Apply each filter
//        for (Map.Entry<FilterType, String> entry : filters.entrySet()) {
//            PetFilterStrategy strategy = STRATEGY_MAP.get(entry.getKey());
//
//            if (strategy != null) {
//                filteredPets = strategy.filter(filteredPets, entry.getValue());
//            }
//        }
//
//        return filteredPets;
//    }
//
    @Override
    public Pet updatePet(Long id, PetRequestDTO petRequestDTO) throws ResourceNotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(id);

        if (optionalPet.isEmpty()){
            throw new ResourceNotFoundException("Pet", "ID", id);
        }

        Pet existingPet = optionalPet.get();

        existingPet.set

        Pet existingPet = filteredPets.get(index - 1);
        Path originalFilePath = existingPet.getFilePath();

        updateIfNotBlank(updatedData, "firstName", String.class, existingPet::setFirstName);
        updateIfNotBlank(updatedData, "lastName", String.class, existingPet::setLastName);

        updateIfNotBlank(updatedData, "addressNumber", Integer.class, existingPet::setAddressNumber);
        updateIfNotBlank(updatedData, "addressName", String.class, existingPet::setAddressName);
        updateIfNotBlank(updatedData, "addressCity", String.class, existingPet::setAddressCity);

        updateIfNotBlank(updatedData, "age", Double.class, existingPet::setAge);

        updateIfNotBlank(updatedData, "weight", Double.class, existingPet::setWeight);

        updateIfNotBlank(updatedData, "breed", String.class, existingPet::setBreed);

        petRepository.updatePetByPath(existingPet, originalFilePath);
    }

    //TODO: refactor
    @Override
    public Pet deletePet(Long id) {
        return deletePet(id);
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
