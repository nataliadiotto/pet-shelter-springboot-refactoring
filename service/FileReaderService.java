package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderService {

    List<String> stringList = new ArrayList<>();
    public List<String>  readFile() throws IOException{
        try (Stream<String> lines = Files.lines(Paths
                .get("/Users/Natalia/animal-shelter/register-form.txt"))){
            lines.forEach(line -> {stringList.add(line);
                                    System.out.println(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }





    }





