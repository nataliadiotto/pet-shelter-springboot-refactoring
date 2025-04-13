package domain.entity;

import domain.enums.AnimalType;
import domain.enums.BiologicalSex;
import domain.utils.Constants;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

import static domain.utils.InputHelper.*;

public class Animal {

    private String firstName;
    private String lastName;
    private final AnimalType animalType;
    private final BiologicalSex biologicalSex;
    private Integer addressNumber;
    private String addressName;
    private String addressCity;
    private Double age;
    private Double weight;
    private String breed;
    private Path filePath;

        public Animal(String firstName, String lastName, AnimalType animalType, BiologicalSex biologicalSex, Integer addressNumber, String addressName, String addressCity, Double age, Double weight, String breed) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.animalType = animalType;
                this.biologicalSex = biologicalSex;
                this.addressNumber = addressNumber;
                this.addressName = addressName;
                this.addressCity = addressCity;
                this.age = age;
                this.weight = weight;
                this.breed = breed;
        }

        public String getFirstName() {
                return capitalizeWords(firstName);
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return capitalizeWords(lastName);
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getFullName(){return capitalizeWords(firstName) + " " + capitalizeWords(lastName);}

        public AnimalType getAnimalType() {
                return animalType;
        }

        public BiologicalSex getBiologicalSex() {
                return biologicalSex;
        }

        public Integer getAddressNumber() {
                return addressNumber;
        }

        public void setAddressNumber(Integer addressNumber) {
                this.addressNumber = addressNumber;
        }

        public String formatAddressNumber() {
                return (isNotBlank(String.valueOf(addressNumber)) && addressNumber != null && addressNumber != 0)
                        ? String.valueOf(addressNumber)
                        : Constants.NOT_INFORMED;
        }

        public String getAddressName() {
                return capitalizeWords(addressName);
        }

        public void setAddressName(String addressName) {
                this.addressName = addressName;
        }

        public String getAddressCity() {
                return capitalizeWords(addressCity);
        }

        public void setAddressCity(String addressCity) {
                this.addressCity = addressCity;
        }

        public String getFullAddress(){return formatAddressNumber() + ", " +
                capitalizeWords(addressName) +
                ", " + capitalizeWords(addressCity);}

        public Double getAge() {
                return age;
        }

        public void setAge(Double age) {
                this.age = age;
        }

        public String formatAge() {
                return (age != null && age != 0)
                        ? String.format("%.1f years old", age).replace(",", ".")
                        : Constants.NOT_INFORMED;
        }

        public Double getWeight() {
                return weight;
        }

        public void setWeight(Double weight) {
                this.weight = weight;
        }

        public String formatWeight() {
                return (weight != null && weight != 0)
                        ? String.format("%.1fkg", weight).replace(",", ".")
                        : Constants.NOT_INFORMED;
        }

        public String getBreed() {
                return (isNotBlank(breed) && !breed.trim().isEmpty())
                        ? capitalizeWords(breed)
                        : Constants.NOT_INFORMED;
        }

        public void setBreed(String breed) {
                this.breed = breed;
        }

        public Path getFilePath() {
            return filePath;
        }

        public void setFilePath(Path filePath) {
            this.filePath = filePath;
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
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Animal animal = (Animal) o;
                return Objects.equals(firstName, animal.firstName) && Objects.equals(lastName, animal.lastName) && animalType == animal.animalType && biologicalSex == animal.biologicalSex && Objects.equals(addressNumber, animal.addressNumber) && Objects.equals(addressName, animal.addressName) && Objects.equals(addressCity, animal.addressCity) && Objects.equals(age, animal.age) && Objects.equals(weight, animal.weight) && Objects.equals(breed, animal.breed);
        }

        @Override
        public int hashCode() {
                return Objects.hash(firstName, lastName, animalType, biologicalSex, addressNumber, addressName, addressCity, age, weight, breed);
        }

        @Override
        public String toString() {
                return String.format(Locale.ENGLISH, """
                %s - %s - %s - %s, %s - %s - %s - %s - %s""",
                        getFullName(),
                        getAnimalType(),
                        biologicalSex.toString(),
                        formatAddressNumber(),
                        getAddressName(),
                        getAddressCity(),
                        formatAge(),
                        formatWeight(),
                        getBreed());
        }
}