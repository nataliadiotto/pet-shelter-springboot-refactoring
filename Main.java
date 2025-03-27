import controller.AnimalController;
import domain.UserMenu;
import repository.AnimalRepositoryImpl;
import service.AnimalService;
import service.FileReaderService;
import service.FileWriterService;
import service.UserInterfaceService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        UserMenu userMenu = new UserMenu();
        FileReaderService fileReaderService = new FileReaderService();
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        FileWriterService fileWriterService = new FileWriterService();
        AnimalService animalService = new AnimalService(animalRepository, fileWriterService);
        AnimalController animalController = new AnimalController(animalService);

        // Cria o UserInterfaceService com as dependências injetadas
        UserInterfaceService userInterfaceService = new UserInterfaceService(
                fileReaderService, animalController, userMenu);

        //System.out.println(fileReaderService.readSpecificLine(fileReaderService.readFile("/Users/Natalia/animal-shelter/register-form.txt"), 3));
        // Coleta as respostas do usuário
        userInterfaceService.start("/Users/Natalia/animal-shelter/register-form.txt");

    }
}
