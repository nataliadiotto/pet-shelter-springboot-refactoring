package com.diotto.petshelter.repository;

import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecifications {

    public static Specification<Pet> hasType(PetType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                equal(root.get("petType"), type);
    }

    public static Specification<Pet> hasGender(BiologicalSex gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                equal(root.get("biologicalSex"), gender);
    }

    public static Specification<Pet> hasName (String name){
        return ((root, query, criteriaBuilder) -> {
            String likePattern = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern)
            );
        });
    }

    public static Specification<Pet> hasZipCode(String zipCode) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("zipCode")), "%" + zipCode + "%");
    }
    public static Specification<Pet> hasStreet(String streetName) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("streetName")), "%" + streetName.toLowerCase() + "%");
    }

    public static Specification<Pet> hasCity(String city) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("addressCity")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Pet> hasAddressNumber(Integer number) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                equal(root.get("addressNumber"), number);
    }

    public static Specification<Pet> hasAge(Double age) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                between(root.get("age"), age - 1, age + 1);
    }

    public static Specification<Pet> hasWeight(Double weight) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                between(root.get("weight"), weight - 0.1, weight + 0.1);
    }

    public static Specification<Pet> hasBreed (String breed) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("breed")), "%" + breed.toLowerCase() + "%");
    }

}
