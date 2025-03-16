package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderService {

    public void fileReader() throws IOException{
        Stream<String> lines = Files.lines(Paths.get("/Users/Natalia/animal-shelter/register-form.txt"));
        lines.forEach(System.out::println);
    }


    }





