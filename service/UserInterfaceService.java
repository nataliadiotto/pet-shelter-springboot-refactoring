package service;

import controller.AnimalController;
import domain.UserMenus;

import java.io.IOException;
import java.util.*;


public class UserInterfaceService {
     private final FileReaderService fileReaderService;
     private final AnimalController animalController;
     private final Scanner sc;
     private final UserMenus userMenus;


    public UserInterfaceService(FileReaderService fileReaderService, AnimalController animalController, UserMenus userMenus) {
        this.fileReaderService = fileReaderService;
        this.animalController = animalController;
        this.userMenus = userMenus;
        this.sc = new Scanner(System.in);

    }

    public void start(String filePath) throws IOException {
        int userChoice;

        while (true) {
            userMenus.displayMainMenu();
            System.out.print("Enter the number of your option: ");

            String input = sc.nextLine().trim();

            try {
                userChoice = Integer.parseInt(input);

                if (userChoice == 6) {
                    System.out.println("Exiting program...");
                    break;
                }

                handleMainMenuOption(userChoice, filePath);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option! Please enter a number.");
            }

            System.out.println("\nPress enter to return to main menu...");
            sc.nextLine();
        }
    }

    private void handleMainMenuOption(int userChoice, String filePath) throws IOException {
        switch (userChoice) {
            case 1:
                //Register new animal
                Map<String, String> collectedRegisterResponses = collectRegisterResponses(filePath);
                System.out.println("DEBUG: Collected responses -> " + collectedRegisterResponses);
                try {
                    animalController.registerAnimal(collectedRegisterResponses);
                } catch (Exception e) {
                    System.out.println("ERROR: Failed to register animal: " + e.getMessage());
                }
                break;
            case 2:
                //Edit animal

                break;
            case 3:
                //Delete registered animal

                break;
            case 4:
                //Find all animals

                break;
            case 5:
                //Find animals by criteria
                handleListAnimalMenu();

                break;
            case 6:
                //Exit

                break;

        }
    }

    public Map<String, String> collectRegisterResponses(String filePath) throws IOException {
        Map<String, String>  responses = new HashMap<>();
        List<String> questions = fileReaderService.readFileToList(filePath);

        System.out.println("DEBUG: Collecting responses for a new animal...");

        System.out.println("\nREGISTER NEW ANIMAL:");
        for (String question: questions) {
            System.out.println(question);
            System.out.print("> ");  // Facilita a visualização da entrada

            if (question.contains("animal's first and last name")) {

                System.out.println("DEBUG: Collecting input for new animal...");
                System.out.print("Animal's first name: ");
                String firstName = sc.nextLine().trim();
                responses.put("first name", firstName);
                System.out.println("DEBUG: Collected first name -> " + firstName);

                System.out.print("Animal's last name: ");
                String lastName = sc.nextLine().trim();
                responses.put("last name", lastName);
            } else if (question.contains("address it was found")) {
                System.out.print("Number: ");
                String addressNumber = sc.nextLine().trim();
                responses.put("address number", addressNumber);

                System.out.print("St./Ave./Rd./Pl./Sq. name: ");
                String addressName = sc.nextLine().trim();
                responses.put("address name", addressName);

                System.out.print("City: ");
                String addressCity = sc.nextLine().trim();
                responses.put("address city", addressCity);
            } else if (question.contains("approximate age") || question.contains("approximate weight")) {
                // Garantir que a entrada seja limpa corretamente para números
                String input = sc.nextLine().trim();
                responses.put(question, input); // Pode validar depois se for numérico

            } else {
                String response = sc.nextLine().trim();
                responses.put(question, response);
            }
        }

        System.out.println("DEBUG: Final collected responses -> " + responses);
        return responses;
    }

    private void handleListAnimalMenu() {
        userMenus.displayListAnimalsMenu();
        int userChoice = sc.nextInt();
    }

}





