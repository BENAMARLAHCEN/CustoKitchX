package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class InputValidate {

    public static Scanner scanner = new Scanner(System.in);

    public static double getValidateDouble(String message) {
        double value = 0;
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    public static int getValidateInt(String message) {
        int value = 0;
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    public static boolean getValidateBoolean(String message) {
        boolean value = false;
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            try {
                value = Boolean.parseBoolean(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter true or false.");
            }
        }
        return value;
    }

    public static String getValidateName(String message) {
        String value = "";
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            value = scanner.nextLine();
            if (value.matches("^[a-zA-Z\\s]*$")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid name.");
            }
        }
        return value;
    }

    public static LocalDate getValidateDate(String message) {
        String value = "";
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            try {
            value = scanner.nextLine();
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid date in the format yyyy-mm-dd.");
                continue;
            }
                LocalDate date = LocalDate.parse(value);
            if (date.isAfter(LocalDate.now())) {
                isValid = true;
                return date;
            } else {
                System.out.println("Invalid input. Please enter a future date.");
            }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please enter a valid date in the format yyyy-mm-dd.");
            }
        }
        return null;
    }

    public static String getValidateWorkforceType(String message) {
        String value = "";
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            value = scanner.nextLine();
            if (value.matches("^[a-zA-Z\\s]*$") && (value.equalsIgnoreCase("Ouvrier de base") || value.equalsIgnoreCase("Spécialiste"))) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid workforce type.");
            }
        }
        return value;
    }

    public static double getValidateRemise(String message) {
        double value = 0;
        boolean isValid = false;
        System.out.print(message);
        while (!isValid) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                if (value >= 0 && value <= 100) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid discount percentage.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
}
