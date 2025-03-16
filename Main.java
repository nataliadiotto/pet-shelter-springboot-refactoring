import service.FileReaderService;
import service.UserInterfaceService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReaderService fileReaderService = new FileReaderService();
        fileReaderService.fileReader();

        //UserInterfaceService userInterfaceService = new UserInterfaceService();
        //userInterfaceService.initMenu();

    }
}
