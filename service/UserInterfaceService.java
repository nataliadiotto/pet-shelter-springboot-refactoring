package service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterfaceService {
    Scanner sc = new Scanner(System.in);
    String menu = "\n-------------- MENU --------------\n"  +
                "1. Register new animal\n" +
                "2. Edit the data of a registered animal\n" +
                "3. Delete a registered animal\n" +
                "4. List all registered animals\n" +
                "5. List animals by criteria (age, name, breed)\n" +
                "6. Exit\n" +
                "Enter the number of your option: ";
    public void initMenu(){
        int input = -1;

        while (true){
            System.out.println(menu);

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

        sc.close();
    }

}




