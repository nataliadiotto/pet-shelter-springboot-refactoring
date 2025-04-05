package domain;

public class UserMenus {

    private final String mainMenu = """ 
                -------------- MAIN MENU --------------
                1. Register new animal
                2. Edit the data of a registered animal
                3. Delete a registered animal
                4. List all registered animals
                5. List animals by criteria (age, name, breed)
                6. Exit
                """;


    private final String findAnimalMenu = """
            -------------- FIND ANIMAL --------------
            1. Find animal by first or last name
            2. Find animal by gender
            3. Find animal by age
            4. Find animal by weight
            5. Find animal by breed
            6. Find animal by address
            7. Return to main menu
            """;

    public UserMenus() {
    }

    public void displayMainMenu() {
        System.out.println(mainMenu);
    }

    public void displayListAnimalsMenu(){
        System.out.println(findAnimalMenu);
    }

}
