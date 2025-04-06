package service;

import controller.AnimalController;
import domain.UserMenus;
import domain.utils.InputHelper;

import java.io.IOException;
import java.util.*;


public class UserInterfaceService {
     private final FileReaderService fileReaderService;
     private final AnimalController animalController;
     private final Scanner sc;
     private final InputHelper inputHelper;
     private final UserMenus userMenus;


    public UserInterfaceService(FileReaderService fileReaderService, AnimalController animalController, UserMenus userMenus) {
        this.fileReaderService = fileReaderService;
        this.animalController = animalController;
        this.userMenus = userMenus;
        this.sc = new Scanner(System.in);
        this.inputHelper = new InputHelper(sc);

    }

    public void start(String filePath) throws IOException {
        int userChoice;

        while (true) {
            userMenus.displayMainMenu();
            userChoice = inputHelper.readInt("Enter the number of your option: ");

            handleMainMenuOption(userChoice, filePath);

            inputHelper.waitForEnter("\nPress enter to return to main menu...");

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
                System.out.println("\n=================================");
                System.out.println("📋 Registered Animals:");
                System.out.println("=================================");

                try {
                    animalController.listAllAnimals();
                } catch (Exception e) {
                    System.out.println("An error occurred while listing animals: " + e.getMessage());
                }
                break;

            case 5:
                //Find animals by criteria
                handleListAnimalMenu();

                break;

            case 6:
                int exitInput = inputHelper.readInt("Do you really want to leave? (1 - Yes / 2 - No)\n> ");

                if (exitInput == 1) {
                    System.out.println("Exiting program...");
                    System.exit(0);
                } else if (exitInput == 2) {
                    break;
                } else {
                    System.out.println("Invalid option!");
                    break;
                }

        }
    }

    public Map<String, String> collectRegisterResponses(String filePath) throws IOException {
        Map<String, String>  responses = new HashMap<>();
        List<String> questions = fileReaderService.readRegisterFileToList(filePath);

        System.out.println("DEBUG: Collecting responses for a new animal...");

        System.out.println("\nREGISTER NEW ANIMAL:");
        for (String question: questions) {
            System.out.println(question);

            if (question.contains("animal's first and last name")) {

                System.out.println("DEBUG: Collecting input for new animal...");

                String firstName = inputHelper.readLine("Animal's first name: ");
                responses.put("first name", firstName);
                System.out.println("DEBUG: Collected first name -> " + firstName);

                String lastName = inputHelper.readLine("Animal's last name: ");
                responses.put("last name", lastName);

            } else if (question.contains("address it was found")) {
                String addressNumber = inputHelper.readLine("Number: ");
                responses.put("address number", addressNumber);

                String addressName = inputHelper.readNonEmptyLine("St./Ave./Rd./Pl./Sq. name: ");
                responses.put("address name", addressName);

                String addressCity = inputHelper.readNonEmptyLine("City: ");
                responses.put("address city", addressCity);

            } else if (question.contains("approximate age") || question.contains("approximate weight")) {
                String input = inputHelper.readLine("> ");
                responses.put(question, input); // Pode validar depois se for numérico

            } else {
                String response = inputHelper.readLine("> ");
                responses.put(question, response);
            }
        }

        System.out.println("DEBUG: Final collected responses -> " + responses);
        return responses;
    }

    private void handleListAnimalMenu() {
        userMenus.displayListAnimalsMenu();
        int userChoice = inputHelper.readInt("> ");
    }

}





