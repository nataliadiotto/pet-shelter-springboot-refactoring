package src.main.java.domain.DTO;

public record PetUpdtRequestDTO(
            String firstName,
            String lastName,
            Integer addressNumber,
            String streetName,
            String addressCity,
            Double age,
            Double weight,
            String breed
    ) {}


