package domain;

import domain.enums.AnimalType;
import domain.enums.BiologicalSex;

import java.util.Objects;

public class Animal {

        private String firstName;
        private String lastName;
        private AnimalType animalType;
        private BiologicalSex biologicalSex;
        private Integer addressNumber;
        private String addressName;
        private String addressCity;
        private Double age;
        private Double weight;
        private String breed;

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
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public AnimalType getAnimalType() {
                return animalType;
        }

        public void setAnimalType(AnimalType animalType) {
                this.animalType = animalType;
        }

        public BiologicalSex getBiologicalSex() {
                return biologicalSex;
        }

        public void setBiologicalSex(BiologicalSex biologicalSex) {
                this.biologicalSex = biologicalSex;
        }

        public Integer getAddressNumber() {
                return addressNumber;
        }

        public void setAddressNumber(Integer addressNumber) {
                this.addressNumber = addressNumber;
        }

        public String getAddressName() {
                return addressName;
        }

        public void setAddressName(String addressName) {
                this.addressName = addressName;
        }

        public String getAddressCity() {
                return addressCity;
        }

        public void setAddressCity(String addressCity) {
                this.addressCity = addressCity;
        }

        public Double getAge() {
                return age;
        }

        public void setAge(Double age) {
                this.age = age;
        }

        public Double getWeight() {
                return weight;
        }

        public void setWeight(Double weight) {
                this.weight = weight;
        }

        public String getBreed() {
                return breed;
        }

        public void setBreed(String breed) {
                this.breed = breed;
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
                return "Animal{" +
                        "firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", animalType=" + animalType +
                        ", biologicalSex=" + biologicalSex +
                        ", addressNumber=" + addressNumber +
                        ", addressName='" + addressName + '\'' +
                        ", addressCity='" + addressCity + '\'' +
                        ", age=" + age +
                        ", weight=" + weight +
                        ", breed='" + breed + '\'' +
                        '}';
        }
}