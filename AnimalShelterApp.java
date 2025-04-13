import controller.AnimalController;
import domain.utils.UserMenus;
import service.AnimalService;
import service.FileReaderService;
import service.FileWriterService;
import service.UserInterfaceService;

import java.io.IOException;

public class AnimalShelterApp {
    public static void main(String[] args) throws IOException {

        UserMenus userMenus = new UserMenus();
        FileReaderService fileReaderService = new FileReaderService();
        //AnimalRepositoryImpl animalRepository = AnimalRepositoryImpl.getInstance();
        FileWriterService fileWriterService = new FileWriterService();
        AnimalService animalService = new AnimalService(fileWriterService, fileReaderService);
        AnimalController animalController = new AnimalController(animalService);

        // Cria o UserInterfaceService com as dependências injetadas
        UserInterfaceService userInterfaceService = new UserInterfaceService(
                fileReaderService, animalController, userMenus);

        //System.out.println(fileReaderService.readSpecificLine(fileReaderService.readFile("/Users/Natalia/animal-shelter/register-form.txt"), 3));
        // Coleta as respostas do usuário
        userInterfaceService.start("/Users/Natalia/animal-shelter/register-form.txt");

    }
}
