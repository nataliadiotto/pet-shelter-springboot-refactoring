package domain.utils;

public class UserMenus {

    private final String mainMenu = """ 
                -------------- MAIN MENU --------------
                1. Register new pet
                2. Edit the data of a registered pet
                3. Delete a registered pet
                4. List all registered pets
                5. List pets by criteria (age, name, breed)
                6. Exit
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
        System.out.println(mainMenu);
    }

    public void displayListPetsMenu(){
        System.out.println(findPetMenu);
    }

}
