package service;

import controller.PetController;
import domain.entity.Pet;
import domain.utils.UserMenus;
import domain.enums.PetType;
import domain.enums.FilterType;
import domain.utils.InputHelper;

import java.io.IOException;
import java.util.*;

import static domain.utils.ConsoleVisuals.*;


public class ConsoleInteractionManager {
     private final FileReaderService fileReaderService;
     private final PetController petController;
     private final Scanner sc;
     private final InputHelper inputHelper;
     private final UserMenus userMenus;


    public ConsoleInteractionManager(FileReaderService fileReaderService, PetController petController, UserMenus userMenus) {
        this.fileReaderService = fileReaderService;
        this.petController = petController;
        this.userMenus = userMenus;
        this.sc = new Scanner(System.in);
        this.inputHelper = new InputHelper(sc);

    }

    public void start(String filePath) throws IOException, InterruptedException {
        int userChoice;

        while (true) {
            userMenus.displayMainMenu();
            userChoice = inputHelper.readInt("Enter the number of your option: ");

            handleMainMenuOption(userChoice, filePath);

            inputHelper.waitForEnter("\nPress enter to return to main menu...");

        }
    }

    private void handleMainMenuOption(int userChoice, String filePath) throws IOException, InterruptedException {
        switch (userChoice) {
            case 1 -> {
                //Register new pet
                Map<String, String> collectedRegisterResponses = collectRegisterResponses(filePath);
                try {
                    petController.registerPet(collectedRegisterResponses);
                } catch (Exception e) {
                    System.out.println("ERROR: Failed to register pet: " + e.getMessage());
                }
            }
            case 2 -> {
                //Edit pet
                try {
                    System.out.println(BOLD_GREEN + "-------------- 🔧 UPDATE PET --------------" + RESET);
                    handleUpdateMenu();

                } catch (Exception e) {
                    System.out.println("An error occurred while updating pet: " + e.getMessage());
                }
            }
            case 3 -> {
                //Delete registered pet
                try {
                    System.out.println(BOLD_CYAN + "-------------- ❌ DELETE PET --------------" + RESET);
                    handleDeleteMenu();

                } catch (Exception e) {
                    System.out.println("An error occurred while deleting pet: ");
                    e.printStackTrace();
                }
            }
            case 4 -> {
                //List all pets
                System.out.println(BOLD_GREEN + "\n=================================");
                System.out.println("📋 Registered Pets:");
                System.out.println("=================================" + RESET);
                try {
                    petController.listAllPets();
                } catch (Exception e) {
                    System.out.println("An error occurred while listing pets: " + e.getMessage());
                }
            }
            case 5 -> {
                //Filter pets
                try {
                    handleListPetMenu();
                } catch (Exception e) {
                    System.out.println("An error occurred while filtering pets: " + e.getMessage());
                }
            }
            case 6 -> {
                //Exit
                int exitInput = inputHelper.readInt(BOLD_RED + "Do you really wish to leave? (1 - Yes / 2 - No)\n> " + RESET);
                if (exitInput == 1) {
                    System.out.print(BOLD_YELLOW + "Exiting program");
                    animatedTransition();
                    System.exit(0);
                } else if (exitInput == 2) {

                } else {
                    System.out.println("Invalid option!");
                }
            }
        }
    }


    public Map<String, String> collectRegisterResponses(String filePath) throws IOException {
        Map<String, String>  responses = new HashMap<>();
        List<String> questions = fileReaderService.readFileToList(filePath);

        System.out.println(BOLD_CYAN + "\n 🐾 REGISTER NEW PET:" + RESET);
        for (String question: questions) {
            System.out.println(CYAN + question);

            if (question.contains("pet's first and last name")) {

                String firstName = inputHelper.readLine("Pet's first name: ");
                responses.put("first name", firstName);

                String lastName = inputHelper.readLine("Pet's last name: ");
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
                responses.put(question, input);

            } else {
                String response = inputHelper.readLine("> ");
                responses.put(question, response);
            }
        }

        return responses;
    }

    private List<Pet> handleListPetMenu() throws InterruptedException {

        PetType petType;
        while (true) {
            System.out.println("Choose pet type to search (Cat = 1 / Dog = 2 / 0 to return): ");
            int petInput = inputHelper.readInt("> ");

            if (petInput == 0) {
                System.out.println("Returning to main menu...");
                return new ArrayList<>();
            }

            if (petInput == 1 || petInput == 2) {
                petType = PetType.fromValue(petInput);
                break;
            }

            System.out.println("Invalid option. Please enter 1 for Cat, 2 for Dog, or 0 to return.");
        }

        Map<FilterType, String> filters = new HashMap<>();
        Set<Integer> usedCriteria = new HashSet<>();

        int maxFilters = 2;
        int filtersAdded = 0;
        while (filtersAdded < maxFilters) {
            userMenus.displayListPetsMenu(petType);

            System.out.println("Choose a filter to search: ");
            int criterion = inputHelper.readInt("> ");

            if (criterion < 1 || criterion > 7) {
                System.out.println("Please choose a valid filter.\n");
                continue;
            }
            if (usedCriteria.contains(criterion)) {
                System.out.println("You've already used this filter. Please choose a different one.\n");
                continue;
            }

            switch (criterion) {
                case 1 -> {
                    String name = inputHelper.readNonEmptyLine("Enter name to search: ");
                    filters.put(FilterType.NAME, name);
                }
                case 2 -> {
                    String sex = inputHelper.readNonEmptyLine("Choose pet's biological sex (Female/Male): ");
                    filters.put(FilterType.SEX, sex);
                }
                case 3 -> {
                    Double age = inputHelper.readDouble("Enter age to search: ");
                    filters.put(FilterType.AGE, String.valueOf(age));
                }
                case 4 -> {
                    Double weight = inputHelper.readDouble("Enter weight to search: ");
                    filters.put(FilterType.WEIGHT, String.valueOf(weight));
                }
                case 5 -> {
                    String breed = inputHelper.readNonEmptyLine("Enter breed to search: ");
                    filters.put(FilterType.BREED, breed);
                }
                case 6 -> {
                    String address = inputHelper.readNonEmptyLine("Enter address it was found to search: ");
                    filters.put(FilterType.ADDRESS, address);
                }
                case 7 -> {
                    System.out.println("Returning to main menu...");
                    return new ArrayList<>();
                }
            }
            usedCriteria.add(criterion);
            filtersAdded++;

            if (filtersAdded < maxFilters) {
                int addAnother = inputHelper.readInt("Would you like to add a second filter (Yes = 1 / No = 2)? " +
                        "\n> ");
                if (addAnother != 1) break;
            }

        }

        return filters.isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(petController.filterByCriteria(petType, filters));

    }

        private void handleUpdateMenu() throws IOException, InterruptedException {

            List<Pet> filteredPets = handleListPetMenu();

            if (filteredPets.isEmpty()) {
                return;
            }

            int petIndex;
            while (true) {
                petIndex = inputHelper.readInt("\nChoose the pet's number to update (or 0 to go back to the menu): ");

                if (petIndex == 0) {
                    System.out.println("Returning to search menu...");
                    return;
                }

                if (petIndex >= 1 && petIndex <= filteredPets.size()) {
                    break;
                }

                System.out.println("Invalid index. Please enter the numerical index of one of the filteredPets in the list.");
                System.out.println();

            }

            System.out.println("\nEnter the new information to update, leave it blank if you wish to maintain the current data.");

            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("firstName", inputHelper.readLine("First name: "));
            updatedData.put("lastName", inputHelper.readLine("Last name: "));
            updatedData.put("addressCity", inputHelper.readLine("City: "));
            updatedData.put("addressName", inputHelper.readLine("Address name: "));

            System.out.println("\n--------------------------------------------------------------------------------------------------------");
            System.out.println("The following fields may be optional, enter '0' if you don't want to update nor keep the current data.\n");

            updatedData.put("addressNumber", inputHelper.readLine("Address number: "));
            updatedData.put("age", inputHelper.readLine("Age: "));
            updatedData.put("weight", inputHelper.readLine("Weight: "));
            updatedData.put("breed", inputHelper.readLine("Breed: "));

            petController.updatePetByIndex(petIndex, filteredPets, updatedData);

        }

        private void handleDeleteMenu() throws IOException, InterruptedException {

            List<Pet> pets = handleListPetMenu();
            if (pets.isEmpty()) {
                return;
            }

            int petIndex;
            while (true) {
                petIndex = inputHelper.readInt("\nChoose the pet's number to delete (or 0 to go back to the menu): ");

                if (petIndex == 0) {
                    System.out.println("Returning to search menu...");
                    return;
                }

                if (petIndex >= 1 && petIndex <= pets.size()) {
                    break;
                }

                System.out.println("Invalid index. Please enter the numerical index of one of the pets in the list.");
                System.out.println();
            }

            String confirmation = inputHelper.readNonEmptyLine(BOLD_RED + "Do you really wish to delete pet number " + petIndex + "?" +
                    "\nEnter YES or NO:" + RESET +
                    "\n> ");

            if (confirmation.equalsIgnoreCase("yes")) {
                petController.deletePetByIndex(petIndex, pets);
            } else {
                System.out.println("Deletion cancelled.");
            }
        }



    }






