package domain.utils;

import java.util.Scanner;
public class InputHelper {
        private final Scanner scanner;

        public InputHelper(Scanner scanner) {
            this.scanner = scanner;
        }

        public int readInt(String prompt) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Try again.");
                }
            }
        }

        public double readDouble(String prompt) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Try again.");
                }
            }
        }

        public String readLine(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        }

        public String readNonEmptyLine(String prompt) {
            while (true) {
                String input = readLine(prompt);
                if (!input.isEmpty()) {
                    return input;
                }
                System.out.println("Input cannot be empty. Please try again.");
            }
        }

        public void waitForEnter(String message) {
            System.out.println(message);
            scanner.nextLine();
        }
    }

