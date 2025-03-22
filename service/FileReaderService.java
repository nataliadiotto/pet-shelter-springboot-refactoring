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
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO change the real path for a path coming from the main
public class FileReaderService {

    List<String> stringList = new ArrayList<>();
    public List<String>  readFile(String filePath) throws IOException{
        try (Stream<String> lines = Files.lines(Paths
                .get(filePath))){
            lines.forEach(line -> {stringList.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    





    }





