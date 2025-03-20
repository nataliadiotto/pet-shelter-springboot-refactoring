package domain;

public class UserMenu {

    private String menuText = "\n-------------- MENU --------------\n"  +
            "1. Register new animal\n" +
            "2. Edit the data of a registered animal\n" +
            "3. Delete a registered animal\n" +
            "4. List all registered animals\n" +
            "5. List animals by criteria (age, name, breed)\n" +
            "6. Exit\n" +
            "Enter the number of your option: ";

    public UserMenu() {
        this.menuText = menuText;
    }

    public void displayMenu() {
        System.out.println(menuText);
    }

}
