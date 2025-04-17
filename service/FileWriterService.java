package service;

import domain.entity.Pet;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FileWriterService {
    private static final String BASE_DIR = "/Users/Natalia/pet-shelter/registeredPetsDir";
    private static final String FILE_EXTENSION = ".TXT";


    public static Path ensureDataDirectory() throws IOException {
        Path registeredPetsDir = Paths.get(BASE_DIR);
        return Files.createDirectories(registeredPetsDir);
    }


    public static String formatFileContent(Pet pet) {
        return String.format(Locale.ENGLISH, """
                1 - %s
                2 - %s
                3 - %s
                4 - %s
                5 - %s
                6 - %s
                7 - %s""",
                pet.getFullName(),
                pet.getPetType(),
                pet.getBiologicalSex().toString(),
                pet.getFullAddress(),
                pet.formatAge(),
                pet.formatWeight(),
                pet.getBreed());

    }

    public static String formatFileName(Pet pet) {
        //Retrieve system timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));

        String lastName = sanitize(pet.getLastName());
        String firstName = sanitize(pet.getFirstName());

        // Fallbacks se estiverem vazios
        if (lastName.isEmpty()) lastName = "NO_LASTNAME";
        if (firstName.isEmpty()) firstName = "NO_FIRSTNAME";

        //Create filename
        return String.format("%s-%S%S",
                timestamp,
                lastName,
                firstName);
    }
    public void savePetToFile(Pet pet) throws IOException {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        Path oldFilePath = pet.getFilePath(); // salva referência do antigo caminho

        Path directory = ensureDataDirectory();
        String fileContent = formatFileContent(pet);
        String fileName = formatFileName(pet);
        Path newFilePath = directory.resolve(fileName + FILE_EXTENSION);

        if (oldFilePath != null && !oldFilePath.equals(newFilePath) && Files.exists(oldFilePath)) {
            Files.delete(oldFilePath); // remove o arquivo antigo
        }

        // Atualiza o filePath com o novo caminho
        pet.setFilePath(newFilePath);

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
