package com.diotto.petshelter.controller;

import com.diotto.petshelter.domain.DTO.PetDTO;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import com.diotto.petshelter.errors.ResourceNotFound;
import com.diotto.petshelter.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

   }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test //registerPet() success
    @DisplayName("Should create a new pet when valid data is provided")
    void shouldCreatePetWhenValidData() throws Exception {
        when(petService.registerPet(any())).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", equalTo("Sweet Nothing")));

        verify(petService, times(1)).registerPet(any());
    }

    @Test //registerPet() failure
    @DisplayName("Should return 400 Bad Request when pet data is invalid")
    void shouldReturnBadRequestForInvalidData() throws Exception {
       petDTO.setFirstName("");
       petDTO.setAge(-1.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(petDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("firstName")))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("age")));

    }

    @Test //listAllPets() success
    @DisplayName("Should list all pets successfully")
    void shouldReturnListOfAllPets() throws Exception {
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

    @Test//listAllPets() failure
    @DisplayName("Should return 404 Not Found when no pets are registered")
    void shouldReturnNotFoundWhenNoPetsExist() throws Exception {
       when(petService.listAll()).thenThrow(new ResourceNotFound("pets"));

       mockMvc.perform(MockMvcRequestBuilders.get("/v1/pets"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message", is("There are no registers of pets in the system.")));

       verify(petService, times(1)).listAll();

    }

    @Test //searchPets() success
    @DisplayName("Should return pets matching the given filters")
    void shouldReturnFilteredPets() throws Exception {
       when(petService.searchPets(eq(PetType.CAT), any(), any(), any(), any(), any(), any(), eq(8.0), any()))
               .thenReturn(List.of(pet));

       mockMvc.perform(MockMvcRequestBuilders.get("/v1/pets/search/")
               .param("petType", "CAT")
               .param("weight", "8.0"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()", equalTo(1)))
               .andExpect(jsonPath("$[0].petType", equalTo("Cat")))
               .andExpect(jsonPath("$[0].weight", equalTo("8.0kg")));

       verify(petService, times(1)).searchPets(
               eq(PetType.CAT),
               any(), any(), any(), any(), any(), any(),
               eq(8.0),
               any());

    }

    @Test //searchPets() failure
    @DisplayName("Should return 404 Not Found when no pets match the search filters")
    void shouldReturnNotFoundWhenNoPetsMatchFilters() throws Exception {
        when(petService.searchPets(eq(PetType.CAT), any(), any(), any(), any(), any(), any(), eq(8.0), any()))
                .thenThrow(new ResourceNotFound("pets with the provided filters"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pets/search/")
                        .param("petType", "CAT")
                        .param("weight", "8.0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("There are no registers of pets with the provided filters in the system.")));

        verify(petService, times(1)).searchPets(
                eq(PetType.CAT), any(), any(), any(), any(), any(), any(), eq(8.0), any()
        );
    }

    @Test //updateById() success
    @DisplayName("Should update a pet by ID with valid data")
    void shouldUpdatePetByIdWhenValid() throws Exception {
        when(petService.updatePet(anyLong(), any())).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/pets/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", equalTo("Sweet Nothing")));

        verify(petService, times(1)).updatePet(eq(1L), any());
   }

    @Test //updateById() failure
    @DisplayName("Should return 404 Not Found when updating non-existent pet by ID")
    void shouldReturnNotFoundWhenUpdatingNonExistentPet() throws Exception {
        when(petService.updatePet(anyLong(), any()))
                .thenThrow(new ResourceNotFound("Pet", "ID", 1L));

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/pets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(petDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Pet not found with: ID = '1'")));

        verify(petService, times(1)).updatePet(eq(1L), any());

    }


    @Test //deleteById() success
    @DisplayName("Should delete a pet by ID successfully")
    void shouldDeletePetByIdSuccessfully() throws Exception {
       mockMvc.perform(delete("/v1/pets/{id}", 1L))
                        .andExpect(status().isNoContent());

       verify(petService, times(1)).deletePet(eq(1L));
    }

    @Test //deleteById() failure
    @DisplayName("Should return 404 Not Found when deleting non-existent pet by ID")
    void shouldReturnNotFoundWhenDeletingNonExistentPet() throws Exception {
        doThrow(new ResourceNotFound("Pet", "ID", 1L))
                .when(petService).deletePet(anyLong());

        mockMvc.perform(delete("/v1/pets/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Pet not found with: ID = '1'")));

        verify(petService, times(1)).deletePet(eq(1L));

    }

}