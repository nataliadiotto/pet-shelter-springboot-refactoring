package service;

import domain.Animal;

import java.io.IOException;
import java.nio.file.*;

public class FileWriterService {

    public static Path createAnimalFile(String fileName, String fileContent) throws IOException {

        Path directory = ensureDataDirectory();
        Path filePath = directory.resolve(fileName + ".txt");

        //write and create file
        return Files.write(filePath,
                fileContent.getBytes(),
                StandardOpenOption.CREATE_NEW);

    }

    public static Path ensureDataDirectory() throws IOException {
        Path registeredAnimalslDirectory = Paths.get("/Users/Natalia/animal-shelter/registeredAnimals");
        return Files.createDirectories(registeredAnimalslDirectory);
    }


}
