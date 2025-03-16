package domain;

import domain.enums.AnimalType;
import domain.enums.BiologicalSex;

import java.util.Objects;

public class Animal {

        String name;
        String surname;
        AnimalType animalType;
        BiologicalSex biologicalSex;
        String foundAtAddress;
        Double age;
        Double weight;
        Double breed;

        public Animal(String name, String surname, AnimalType animalType, BiologicalSex biologicalSex, String foundAtAddress, Double age, Double weight, Double breed) {
                this.name = name;
                this.surname = surname;
                this.animalType = animalType;
                this.biologicalSex = biologicalSex;
                this.foundAtAddress = foundAtAddress;
                this.age = age;
                this.weight = weight;
                this.breed = breed;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
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

        public String getFoundAtAddress() {
                return foundAtAddress;
        }

        public void setFoundAtAddress(String foundAtAddress) {
                this.foundAtAddress = foundAtAddress;
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

        public Double getBreed() {
                return breed;
        }

        public void setBreed(Double breed) {
                this.breed = breed;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Animal animal = (Animal) o;
                return Objects.equals(name, animal.name) && Objects.equals(surname, animal.surname) && animalType == animal.animalType && biologicalSex == animal.biologicalSex && Objects.equals(foundAtAddress, animal.foundAtAddress) && Objects.equals(age, animal.age) && Objects.equals(weight, animal.weight) && Objects.equals(breed, animal.breed);
        }

        @Override
        public int hashCode() {
                return Objects.hash(name, surname, animalType, biologicalSex, foundAtAddress, age, weight, breed);
        }

        @Override
        public String toString() {
                return "Animal{" +
                        "name='" + name + '\'' +
                        ", surname='" + surname + '\'' +
                        ", animalType=" + animalType +
                        ", biologicalSex=" + biologicalSex +
                        ", foundAtAddress='" + foundAtAddress + '\'' +
                        ", age=" + age +
                        ", weight=" + weight +
                        ", breed=" + breed +
                        '}';
        }
}
