package service;

import controller.AnimalController;
import domain.UserMenu;

import java.io.IOException;
import java.util.*;


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
                Map<String, String> collectedRegisterResponses = collectRegisterResponses(filePath);
                animalController.registerAnimal(collectedRegisterResponses);

                break;
        }
    }

    public Map<String, String> collectRegisterResponses(String filePath) throws IOException {
        Map<String, String>  responses = new HashMap<>();
        List<String> questions = fileReaderService.readFileToList(filePath);

        System.out.println("\nREGISTER NEW ANIMAL:");
        for (String question: questions) {
            if (question.contains("animal's first and last name")) {
                System.out.println(question);

                System.out.print("Animal's first name: ");
                String firstName = sc.nextLine();
                responses.put("first name", firstName);

                System.out.print("Animal's last name: ");
                String lastName = sc.nextLine();
                responses.put("last name", lastName);
            } else if (question.contains("address it was found")) {
                System.out.println(question);

                System.out.print("Number: ");
                Integer addressNumber = Integer.parseInt(sc.nextLine());
                responses.put("address number", String.valueOf(addressNumber));

                System.out.print("St./Ave./Rd./Pl./Sq. name: ");
                String addressName = sc.nextLine();
                responses.put("address name", addressName);

                System.out.print("City: ");
                String addressCity = sc.nextLine();
                responses.put("address city", addressCity);
            } else {
                System.out.println(question);
                String response = sc.nextLine();
                responses.put(question, response);
            }

        }
        return responses;
    }

}





