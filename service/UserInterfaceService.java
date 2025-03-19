package service;

import controller.AnimalController;
import domain.Animal;
import domain.UserMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceService {
     FileReaderService fileReaderService = new FileReaderService();
     AnimalController animalController;
     Scanner sc = new Scanner(System.in);
    UserMenu userMenu;
    private Animal animal;

    public UserInterfaceService(AnimalController animalController, Scanner sc, UserMenu userMenu, Animal animal, FileReaderService fileReaderService) {
        this.animalController = animalController;
        this.sc = new Scanner(System.in);
        this.userMenu = userMenu;
        this.animal = animal;
        this.fileReaderService = new FileReaderService();
    }

    public UserInterfaceService() {

    }


    public int initMenu(){
        int input = -1;

        while (true){
            System.out.println(userMenu);

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

        return input;

    }

    private void handleMenuOption(int input) throws IOException {
        input = initMenu();
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

        System.out.println("REGISTER NEW ANIMAL:");
        for (String question: questions) {
            System.out.println(question);
            String response = sc.nextLine();
            responses.add(response);
        }
        return responses;
        }
}





