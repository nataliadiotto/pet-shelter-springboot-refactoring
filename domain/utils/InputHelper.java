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

        public static boolean isNotBlank(String value) {
            return value != null && !value.trim().isEmpty();
        }


        public static boolean isExplicitNull(Object value) {
            return "null".equalsIgnoreCase(String.valueOf(value).trim());
        }

        public static boolean isNullOrEmpty(Object value) {
            return value == null || String.valueOf(value).trim().isEmpty();
        }

        public static boolean containsInvalidCharacters(String text) {
            return text == null || !text.matches("[a-zA-Z ]+");
        }

        public boolean isValidDecimal(String input) {
            return input.matches("^[+-]?\\d+(?:[.,]\\d+)?$");
        }

    }

