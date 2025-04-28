package service;

import domain.entity.Pet;
import domain.enums.PetType;
import domain.enums.BiologicalSex;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.*;
//TODO refactor readSpecificLine to not depend on readFile()
public class FileReaderService {
    public List<String> readFileToList(String filePath) throws IOException{
        if (!isFileValid(filePath)) {
            throw new IOException("File does not exist or is not readable: " + filePath);
        }

        try (Stream<String> lines = Files.lines(get(filePath))){
            return lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
            return List.of();
        }

    }

    public TreeMap<Path, Pet> readAllPets() throws IOException {
        Path dirPath = getRegisteredPetsDir();
        if (!isDirValid(dirPath)) {
            throw new IOException("Directory does not exist or is not readable: " + dirPath);
        }

        TreeMap<Path, Pet> petMap = new TreeMap<>();
        try (DirectoryStream<Path> streamDir = Files.newDirectoryStream(dirPath, "*.TXT")) {
            for (Path path : streamDir) {
                if (Files.isRegularFile(path)) {
                    List<String> lines = Files.readAllLines(path);

                    Pet pet = parsePetFromFile(lines);
                    pet.setFilePath(path);

                    petMap.put(path, pet);
                }

            }
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
            return new TreeMap<>();
        }
        return petMap;
    }

    public Pet parsePetFromFile(List<String> lines) throws IOException {
        if (lines.size() != 7) {
            throw new IOException("Invalid file format. Expected 7 lines but got " + lines.size());
        }

        //split first and last names
        String fullName = extractValue(lines.get(0));
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length));

        PetType petType = PetType.fromString(extractValue(lines.get(1)));

        BiologicalSex biologicalSex = BiologicalSex.fromString(extractValue(lines.get(2)));

        //split number, name and addressCity
        String fullAddress = extractValue(lines.get(3));
        String[] addressParts = fullAddress.split(",");

        Integer addressNumber = null;
        String addressNumberStr = addressParts[0].trim();
        if (!addressNumberStr.equalsIgnoreCase("Not informed") && !addressNumberStr.isBlank()) {
            addressNumber = Integer.valueOf(addressNumberStr);
        }

        String addressCity = addressParts[addressParts.length - 1].trim();
        String addressName = String.join(", ", Arrays.copyOfRange(addressParts, 1, addressParts.length - 1)).trim();


        Double age = null;
        String ageStr = extractValue(lines.get(4)).replace(" years old", "").replace(",", ".").trim();
        if (!ageStr.equalsIgnoreCase("Not informed") && !ageStr.isBlank()) {
            age = Double.valueOf(ageStr);
        }

        Double weight = null;
        String weightStr = extractValue(lines.get(5)).replace("kg", "").replace(",", ".").trim();
        if (!weightStr.equalsIgnoreCase("Not informed") && !weightStr.isBlank()) {
            weight = Double.valueOf(weightStr);
        }

        String breed = extractValue(lines.get(6));

        return new Pet(firstName,
                lastName,
                petType,
                biologicalSex,
                addressNumber,
                addressName,
                addressCity,
                age,
                weight,
                breed);


    }

    private String extractValue(String line) {
        return line.substring(line.indexOf('-') + 1).trim();
    }


    private boolean isFileValid(String filePath){
        Path path = get(filePath);
        return Files.exists(path) && Files.isReadable(path);
    }

    private Path getRegisteredPetsDir() throws IOException {
        return Paths.get(FileWriterService.ensureDataDirectory().toUri());
    }
    private boolean isDirValid(Path path) throws IOException {
        return Files.exists(path)
                && Files.isDirectory(path)
                && !Files.isHidden(path)
                && Files.isReadable(path)
                && Files.isWritable(path);
    }



    





    }





