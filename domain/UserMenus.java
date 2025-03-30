package domain;

public class UserMenus {

    private String mainMenu = """ 
                -------------- MENU --------------
                1. Register new animal
                2. Edit the data of a registered animal
                3. Delete a registered animal
                4. List all registered animals
                5. List animals by criteria (age, name, breed)
                6. Exit
                Enter the number of your option:
                """;


    private String findAnimalMenu = "";

    public UserMenus() {
    }

    public void displayMainMenu() {
        System.out.println(mainMenu);
    }

}
