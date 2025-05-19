package domain.utils;

import domain.enums.PetType;

import static domain.utils.ConsoleVisuals.*;

public class UserMenus {

    private final String mainMenu = """ 
                1. 🐾 Register new pet
                2. 🔧 Edit the data of a registered pet
                3. ❌ Delete pet
                4. 📋 View all pets
                5. 🔎 Search pets
                6. 🚪 Exit
                """;


    private final String findPetMenu = """
            1. Find pet by first or last name
            2. Find pet by gender
            3. Find pet by age
            4. Find pet by weight
            5. Find pet by breed
            6. Find pet by address
            7. Return to main menu
            """;

    public UserMenus() {
    }

    public void displayMainMenu() {
        System.out.println(BOLD_BLUE + "-------------- MAIN MENU --------------"
        + RESET);
        System.out.println(BLUE + mainMenu +
                RESET);
    }

    public void displayListPetsMenu(PetType petType){
        String pet = "";
        if (petType.equals(PetType.DOG)) {
            //dog emoji
            pet = "\uD83D\uDC36";
        } else {
            //cat emoji
            pet = "\uD83D\uDC31";
        }
        System.out.printf(BOLD_PURPLE + "-------------- FIND %S " + pet + " --------------\n", petType.name() +
                RESET);
        System.out.println(PURPLE + findPetMenu
                + RESET);
    }

}
