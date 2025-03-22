package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Paths.*;
//TODO refactor readSpecificLine to not depend on readFile()
public class FileReaderService {

   private final List<String> stringList = new ArrayList<>();
    public List<String> readFile(String filePath) throws IOException{
        if (!isFileValid(filePath)) {
            throw new IOException("File does not exist or is not readable: " + filePath);
        }
        try (Stream<String> lines = Files.lines(get(filePath))){
            lines.forEach(stringList::add);
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
        }
        return stringList;
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



    





    }





