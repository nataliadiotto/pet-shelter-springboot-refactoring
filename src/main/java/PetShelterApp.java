import controller.PetController;
import service.PetServiceImpl;

import java.io.IOException;
import java.nio.file.Paths;

import static domain.utils.ConsoleVisuals.*;

public class PetShelterApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(BOLD_BLUE + "🐾 Welcome to the Pet Shelter! 🐾" + RESET);
        System.out.println(BLUE + "Please follow the instructions to register or manage pets.\n" + RESET);

        // Dependencies creation
        PetController petController = createPetController();
        UserMenus userMenus = new UserMenus();
        FileReaderService fileReaderService = new FileReaderService();

        // Interface with dependencies injections creation
        ConsoleInteractionManager ui = new ConsoleInteractionManager(fileReaderService, petController, userMenus);

        // Register form path, with fallback to local file
        String filePath = args.length > 0 ? args[0] : Paths.get("register-form.txt").toAbsolutePath().toString();

        // Application start
        ui.start(filePath);
    }

    private static PetController createPetController() {
        FileReaderService fileReader = new FileReaderService();
        FileWriterService fileWriter = new FileWriterService();
        PetServiceImpl petServiceImpl = new PetServiceImpl(fileWriter, fileReader);
        return new PetController(petServiceImpl);
    }
}
