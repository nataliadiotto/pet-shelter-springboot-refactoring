import repository.AnimalRepositoryImpl;
import service.UserInterfaceService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //FileReaderService fileReaderService = new FileReaderService();
//        List<String> questions = fileReaderService.readFile();
        //System.out.println(questions);
        UserInterfaceService userInterfaceService = new UserInterfaceService();
        userInterfaceService.collectRegisterResponses();
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        System.out.println(animalRepository.findAll());


        //UserInterfaceService userInterfaceService = new UserInterfaceService();
        //userInterfaceService.initMenu();

    }
}
