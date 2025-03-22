package service;

import controller.AnimalController;
import domain.UserMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class UserInterfaceService {
     private final FileReaderService fileReaderService;
     private final AnimalController animalController;
     private final Scanner sc;
     private final UserMenu userMenu;

    public UserInterfaceService(FileReaderService fileReaderService, AnimalController animalController, UserMenu userMenu) {
        this.fileReaderService = fileReaderService;
        this.animalController = animalController;
        this.userMenu = userMenu;
        this.sc = new Scanner(System.in);
    }

    public void start(String filePath) throws IOException {
        int userChoice = -1;

        while (true){
            userMenu.displayMenu();
            try {
                userChoice = sc.nextInt();
                sc.nextLine();

                if (userChoice >= 1 && userChoice <= 6) {
                    break;
                } else {
                    System.out.println("Please choose a number between 1 and 6!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid option! Please enter a valid number.");
                sc.nextLine();
            }
        }

        handleMenuOption(userChoice, filePath);

    }

    private void handleMenuOption(int userChoice, String filePath) throws IOException {
        switch (userChoice) {
            case 1:
                List<String> collectedRegisterResponses = collectRegisterResponses(filePath);
                animalController.registerAnimal(collectedRegisterResponses);

                break;
        }
    }

    public List<String> collectRegisterResponses(String filePath) throws IOException {
        List<String> responses = new ArrayList<>();
        List<String> questions = fileReaderService.readFile(filePath);

        System.out.println("\nREGISTER NEW ANIMAL:");
        for (String question: questions) {
            System.out.println(question);
            String response = sc.nextLine();
            responses.add(response);
        }
        return responses;
        }
}





