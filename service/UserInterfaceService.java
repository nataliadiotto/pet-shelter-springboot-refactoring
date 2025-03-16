package service;

import controller.AnimalController;
import domain.Animal;
import domain.UserMenu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterfaceService {
    AnimalController animalController;
    Scanner sc;
    UserMenu userMenu;
    Animal animal;
    public UserInterfaceService(AnimalController animalController, Scanner sc, UserMenu userMenu) {
        this.animalController = animalController;
        this.sc = new Scanner(System.in);;
        this.userMenu = userMenu;
    }


    private int initMenu(){
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
        FileReaderService fileReaderService;
        input = initMenu();
        switch (input) {
            case 1:
                System.out.println("Register new animal");
                animalController.registerAnimal(animal);
                break;
        }
    }
}




