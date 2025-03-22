package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//TODO change the real path for a path coming from the main
public class FileReaderService {

    List<String> stringList = new ArrayList<>();
    public List<String> readFile(String filePath) throws IOException{
        if (!isFileValid(filePath)) {
            throw new IOException("File does not exist or is not readable: " + filePath);
        }
        try (Stream<String> lines = Files.lines(Paths
                .get(filePath))){
            lines.forEach(line -> {stringList.add(line);
            });
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
        }
        return stringList;
    }

    public boolean isFileValid(String filePath){
        return Files.exists(Paths.get(filePath)) && Files.isReadable(Paths.get(filePath));
    }



    





    }





