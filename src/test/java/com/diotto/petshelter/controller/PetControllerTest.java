package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
@AutoConfigureMockMvc
class PetControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private PetService petService;

   Pet pet;
   Pet pet2;
   PetDTO petDTO;
   PetResponseDTO petResponseDTO;

   @InjectMocks
   private PetController petController;

   @BeforeEach
   public void setup(){
       petDTO = new PetDTO("Sweet",
               "Nothing",
               PetType.CAT,
               BiologicalSex.FEMALE,
               1313,
               "Folklore Avenue",
               "New York",
               3.0,
               8.0,
               "Scottish Fold");

       pet = new Pet();
       pet.setFirstName("Sweet");
       pet.setLastName("Nothing");
       pet.setPetType(PetType.CAT);
       pet.setBiologicalSex(BiologicalSex.FEMALE);
       pet.setAddressNumber(1313);
       pet.setStreetName("Folklore Avenue");
       pet.setAddressCity("New York");
       pet.setAge(3.0);
       pet.setWeight(8.0);
       pet.setBreed("Scottish Fold");

       pet2 = new Pet();
       pet2.setFirstName("Blank");
       pet2.setLastName("Space");
       pet2.setPetType(PetType.CAT);
       pet2.setBiologicalSex(BiologicalSex.MALE);
       pet2.setAddressNumber(1313);
       pet2.setStreetName("Folklore Avenue");
       pet2.setAddressCity("New York");
       pet2.setAge(3.0);
       pet2.setWeight(8.0);
       pet2.setBreed("Scottish Fold");

       //mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
   }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    //registerPet()
    void shouldCreateNewPetWhenValidData() throws Exception {
        when(petService.registerPet(any())).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", equalTo("Sweet Nothing")));

        verify(petService, times(1)).registerPet(any());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidData(){}

    @Test
    //listAllPets()
    void shouldListAllPets() throws Exception {
       when(petService.listAll()).thenReturn(List.of(pet, pet2));

       mockMvc.perform(MockMvcRequestBuilders.get("/v1/pets"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()", equalTo(2)))
               .andExpect(jsonPath("$.[0].fullName", equalTo("Sweet Nothing")))
               .andExpect(jsonPath("$.[0].age", equalTo("3.0 years old")))
               .andExpect(jsonPath("$.[0].breed", equalTo("Scottish Fold")))
               .andExpect(jsonPath("$.[1].fullName", equalTo("Blank Space")))
               .andExpect(jsonPath("$.[1].biologicalSex", equalTo("Male")));

       verify(petService, times(1)).listAll();


    }

    @Test
    void filterPets() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deletePetById() {
    }
}