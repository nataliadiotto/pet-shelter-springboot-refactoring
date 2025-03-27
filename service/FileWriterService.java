package service;

import domain.Animal;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FileWriterService {
    private static final String BASE_DIR = "/Users/Natalia/animal-shelter/registeredAnimalsDir";
    private static final String FILE_EXTENSION = ".TXT";


    public static Path ensureDataDirectory() throws IOException {
        Path registeredAnimalsDir = Paths.get(BASE_DIR);
        return Files.createDirectories(registeredAnimalsDir);
    }

    public static String formatFileContent(Animal animal) {
        return String.format(Locale.ENGLISH, """
                1 - %s
                2 - %s
                3 - %s
                4 - %s
                5 - %.1f years old
                6 - %.1fkg
                7 - %s""",
                animal.getFullName(),
                animal.getAnimalType(),
                animal.getBiologicalSex(),
                animal.getFullAddress(),
                animal.getAge(),
                animal.getWeight(),
                animal.getBreed());
    }

    public static String formatFileName(Animal animal) {
        //Retrieve system timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));

        //Create filename
        return String.format("%s-%S%S",
                timestamp,
                sanitize(animal.getLastName()),
                sanitize(animal.getFirstName()));
    }
    public void createAnimalFile(Animal animal) throws IOException {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        Path directory = ensureDataDirectory();
        String fileContent = formatFileContent(animal);
        String fileName = formatFileName(animal);
        Path filePath = directory.resolve(fileName + FILE_EXTENSION);

        //TODO Refactor method to rewrite file once animal is edited
        //write and create file
        Files.writeString(
                filePath,
                fileContent, // Add this
                StandardOpenOption.CREATE_NEW);

    }

    private static String sanitize(String input){
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }


}
