package domain.utils;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

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

        public static Integer parseIntegerOrDefault(String value) {
            try {
                return Integer.parseInt(value.trim());
            } catch (Exception e) {
                return null;
            }
        }



        public static Double parseDouble(String value) {
            if (value == null || value.trim().isEmpty()) return null;
            try {
                return Double.parseDouble(value.replace(",", ".").trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Please enter a valid number.");
            }
        }

        public static  <T> T parseEnum(String input, Function<Integer, T> converter, String errorMessage) {
            try {
                return converter.apply(Integer.parseInt(input.trim()));
            } catch (Exception e) {
                throw new IllegalArgumentException(errorMessage);
            }
        }

        public static boolean containsInvalidCharacters(String text) {
            return !text.matches("^[A-Za-zÀ-ÿ\\s]+$"); // inclui acentos e espaço
        }

        public static  <T> void updateIfNotBlank(Map<String, Object> data, String key, Class<T> type, Consumer<T> setter) {
            Object value = data.get(key);

            String stringValue = value.toString().trim();

            if ("0".equals(stringValue)) {
                if (type == String.class) {
                    setter.accept(type.cast(Constants.NOT_INFORMED)); // e.g., "Not Informed"
                } else {
                    setter.accept(null); // For numbers, null means not informed
                }
                return;
            }

            if (isNullOrEmpty(value)) {
                return; // User wants to keep existing value (pressed Enter)
            }

            try {
                if (type == Integer.class) {
                    setter.accept(type.cast(Integer.parseInt(value.toString())));
                } else if (type == Double.class) {
                    setter.accept(type.cast(Double.parseDouble(value.toString())));
                } else if (type == String.class) {
                    setter.accept(type.cast(value.toString()));
                } else {
                    setter.accept(type.cast(value));
                }
            } catch (Exception e) {
                System.out.println("Invalid type or conversion error for key " + key + ": " + value);
            }
        }

        public boolean isValidDecimal(String input) {
            return input.matches("^[+-]?\\d+(?:[.,]\\d+)?$");
        }

    }

