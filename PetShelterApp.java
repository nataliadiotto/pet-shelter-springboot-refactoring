import controller.PetController;
import domain.utils.UserMenus;
import service.PetService;
import service.FileReaderService;
import service.FileWriterService;
import service.UserInterfaceService;

import java.io.IOException;

public class PetShelterApp {
    public static void main(String[] args) throws IOException {

        UserMenus userMenus = new UserMenus();
        FileReaderService fileReaderService = new FileReaderService();
        //PetRepositoryImpl petRepository = PetRepositoryImpl.getInstance();
        FileWriterService fileWriterService = new FileWriterService();
        PetService petService = new PetService(fileWriterService, fileReaderService);
        PetController petController = new PetController(petService);

        // Cria o UserInterfaceService com as dependências injetadas
        UserInterfaceService userInterfaceService = new UserInterfaceService(
                fileReaderService, petController, userMenus);

        //System.out.println(fileReaderService.readSpecificLine(fileReaderService.readFile("/Users/Natalia/pet-shelter/register-form.txt"), 3));
        // Coleta as respostas do usuário
        userInterfaceService.start("/Users/Natalia/pet-shelter/register-form.txt");

    }
}
