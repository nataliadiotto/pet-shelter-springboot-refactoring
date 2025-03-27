package service;

import domain.Animal;

import java.io.IOException;
import java.nio.file.*;
public class FileWriterService {
    private static final String BASE_DIR = "/Users/Natalia/animal-shelter/registeredAnimalsDir";

    public static Path ensureDataDirectory() throws IOException {
        Path registeredAnimalsDir = Paths.get(BASE_DIR);
        return Files.createDirectories(registeredAnimalsDir);
    }

    public static String formatFileContent(Animal animal) {
        return String.format(
                        "1 - %s\n" +
                        "2 - %s\n" +
                        "3 - %s\n" +
                        "4 - %s\n" +
                        "5 - %.1f years old\n" +
                        "6 - %.1fkg\n" +
                        "7 - %s",
                animal.getFullName(),
                animal.getAnimalType(),
                animal.getBiologicalSex(),
                animal.getFullAddress(),
                animal.getAge(),
                animal.getWeight(),
                animal.getBreed());
    }

    public static String formatFileName(Animal animal) {
        return String.format("%d-%S%S",
                System.currentTimeMillis(),
                animal.getLastName(),
                animal.getFirstName());
    }
    public void createAnimalFile(Animal animal) throws IOException {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        Path directory = ensureDataDirectory();
        String fileContent = formatFileContent(animal);
        String fileName = formatFileName(animal);
        Path filePath = directory.resolve(fileName + ".TXT");

        //write and create file
        Files.write(
                filePath,
                fileContent.getBytes(),
                StandardOpenOption.CREATE_NEW);

    }





}
