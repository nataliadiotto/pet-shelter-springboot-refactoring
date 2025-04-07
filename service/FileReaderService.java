package service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.file.Paths.*;
//TODO refactor readSpecificLine to not depend on readFile()
public class FileReaderService {
    public List<String> readRegisterFileToList(String filePath) throws IOException{
        if (!isFileValid(filePath)) {
            throw new IOException("File does not exist or is not readable: " + filePath);
        }

        try (Stream<String> lines = Files.lines(get(filePath))){
            return lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
            return List.of();
        }

    }

    public List<String> readRegisteredAnimalFile() throws IOException {
        Path dirPath = getRegisteredAnimalsDir();
        if (!isDirValid(dirPath)) {
            throw new IOException("Directory does not exist or is not readable: " + dirPath);
        }

        List<String> animalData = new ArrayList<>();
        try (DirectoryStream<Path> streamDir = Files.newDirectoryStream(dirPath)) {
            for (Path path : streamDir) {
                List<String> fileLines = readRegisterFileToList(String.valueOf(path));
                animalData.addAll(fileLines);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
            return List.of();
        }
        return animalData;
    }

    public String readSpecificLine(List<String> lines, Integer specificLine) throws IOException {
        if (specificLine < 0 || specificLine > lines.size()){
            throw new IllegalArgumentException("Line number must be non-negative and less than " + lines.size());
        }

        return lines.get(specificLine);
    }

    private boolean isFileValid(String filePath){
        Path path = get(filePath);
        return Files.exists(path) && Files.isReadable(path);
    }

    private Path getRegisteredAnimalsDir() throws IOException {
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





