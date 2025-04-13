package service;

import domain.entity.Animal;

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
                5 - %s
                6 - %s
                7 - %s""",
                animal.getFullName(),
                animal.getAnimalType(),
                animal.getBiologicalSex().toString(),
                animal.getFullAddress(),
                animal.formatAge(),
                animal.formatWeight(),
                animal.getBreed());

    }

    public static String formatFileName(Animal animal) {
        //Retrieve system timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));

        String lastName = sanitize(animal.getLastName());
        String firstName = sanitize(animal.getFirstName());

        // Fallbacks se estiverem vazios
        if (lastName.isEmpty()) lastName = "NO_LASTNAME";
        if (firstName.isEmpty()) firstName = "NO_FIRSTNAME";

        //Create filename
        return String.format("%s-%S%S",
                timestamp,
                lastName,
                firstName);
    }
    public void saveAnimalToFile(Animal animal) throws IOException {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        Path oldFilePath = animal.getFilePath(); // salva referência do antigo caminho

        System.out.println("DEBUG - OLD FILE PATH: " + oldFilePath);


        Path directory = ensureDataDirectory();
        String fileContent = formatFileContent(animal);
        String fileName = formatFileName(animal);
        Path newFilePath = directory.resolve(fileName + FILE_EXTENSION);

        if (oldFilePath != null && !oldFilePath.equals(newFilePath) && Files.exists(oldFilePath)) {
            Files.delete(oldFilePath); // remove o arquivo antigo
        }

        // Atualiza o filePath com o novo caminho
        animal.setFilePath(newFilePath);

        System.out.println("DEBUG: Salvando arquivo em: " + newFilePath);

        Files.writeString(
                newFilePath,
                fileContent,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }


    private static String sanitize(String input){
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }


}
