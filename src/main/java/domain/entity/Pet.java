package domain.entity;

import domain.enums.BiologicalSex;
import domain.enums.PetType;
import domain.utils.Constants;
import domain.utils.InputHelper;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;

@Entity
@Data
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private final PetType petType;
    private final BiologicalSex biologicalSex;
    private Integer addressNumber;
    private String addressName;
    private String addressCity;
    private Double age;
    private Double weight;
    private String breed;

        public Pet(String firstName, String lastName, PetType petType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.petType = petType;
                this.biologicalSex = biologicalSex;
                this.addressNumber = addressNumber;
                this.addressName = addressName;
                this.addressCity = addressCity;
                this.age = age;
                this.weight = weight;
                this.breed = breed;
        }

        public String getFormatFirstName() {
                return capitalizeWords(firstName);
        }

        public String getFormatLastName() {
                return capitalizeWords(lastName);
        }


        public String getFullName(){return capitalizeWords(firstName) + " " + capitalizeWords(lastName);}

        public String formatAddressNumber() {
                return (InputHelper.isNotBlank(String.valueOf(addressNumber)) && addressNumber != null && addressNumber != 0)
                        ? String.valueOf(addressNumber)
                        : Constants.NOT_INFORMED;
        }

        public String getFormatAddressName() {
                return capitalizeWords(addressName);
        }


        public String getAddressCity() {
                return capitalizeWords(addressCity);
        }

        public String getFullAddress(){return formatAddressNumber() + ", " +
                capitalizeWords(addressName) +
                ", " + capitalizeWords(addressCity);}


        public String formatAge() {
                return (age != null && age != 0)
                        ? String.format("%.1f years old", age).replace(",", ".")
                        : Constants.NOT_INFORMED;
        }


        public String formatWeight() {
                return (weight != null && weight != 0)
                        ? String.format("%.1fkg", weight).replace(",", ".")
                        : Constants.NOT_INFORMED;
        }

        public String getBreed() {
                return (InputHelper.isNotBlank(breed) && !breed.trim().isEmpty())
                        ? capitalizeWords(breed)
                        : Constants.NOT_INFORMED;
        }



    public static String capitalizeWords(String input){
                if (input == null || input.isEmpty()) {
                        return input;
                }

                StringBuilder result = new StringBuilder();
                boolean capitalizeNext = true;

                for (char c : input.toCharArray()) {
                        if (Character.isWhitespace(c)) {
                                capitalizeNext = true;
                                result.append(c);
                        } else if (capitalizeNext) {
                                result.append(Character.toTitleCase(c));
                                capitalizeNext = false;
                        } else {
                                result.append(Character.toLowerCase(c));
                        }
                }
                return result.toString();
        }

        @Override
        public String toString() {
                return String.format(Locale.ENGLISH, """
                %s - %s - %s - %s, %s - %s - %s - %s - %s""",
                        getFullName(),
                        getPetType(),
                        biologicalSex.toString(),
                        formatAddressNumber(),
                        getFormatAddressName(),
                        getAddressCity(),
                        formatAge(),
                        formatWeight(),
                        getBreed());
        }
}