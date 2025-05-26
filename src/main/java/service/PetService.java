package src.main.java.service;

import src.main.java.domain.DTO.PetDTO;
import src.main.java.domain.DTO.PetRequestDTO;
import src.main.java.domain.entity.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {

    Pet registerPet(PetDTO PetDTO);
    List<Pet> listAll();

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
    Pet updatePet(Long id, PetRequestDTO petRequestDTO) throws ResourceNotFoundException;

    //List<Pet> filterPets(PetType petType, Map<FilterType, String> filters);
    Pet deletePet(Long id);
    Pet convertPetFromDTO(PetDTO petDTO);
}
