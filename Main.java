import service.FileReaderService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReaderService fileReaderService = new FileReaderService();
        //System.out.println(fileReaderService.fileBufferedReader());
        fileReaderService.fileReader();

    }
}
