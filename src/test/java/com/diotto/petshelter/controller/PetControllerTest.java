package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.service.PetService;
import com.diotto.petshelter.service.PetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    //registerPet
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
    void listAllPets() {
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