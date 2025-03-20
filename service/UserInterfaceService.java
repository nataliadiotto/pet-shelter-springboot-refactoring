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

    public void initMenu() throws IOException {
        int input = -1;

        while (true){
            userMenu.displayMenu();
            try {
                input = sc.nextInt();
                sc.nextLine();

                if (input >= 1 && input <= 6) {
                    break;
                } else {
                    System.out.println("Please choose a number between 1 and 6!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid option! Please enter a number.");
                sc.nextLine();
            }
        }

        handleMenuOption(input);

    }

    private void handleMenuOption(int input) throws IOException {
        switch (input) {
            case 1:
                List<String> collectedRegisterResponses = collectRegisterResponses();
                animalController.registerAnimal(collectedRegisterResponses);

                break;
        }
    }

    public List<String> collectRegisterResponses() throws IOException {
        List<String> responses = new ArrayList<>();
        List<String> questions = fileReaderService.readFile();

        System.out.println("\nREGISTER NEW ANIMAL:");
        for (String question: questions) {
            System.out.println(question);
            String response = sc.nextLine();
            responses.add(response);
        }
        return responses;
        }
}





