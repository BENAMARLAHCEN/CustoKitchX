import controller.ClientController;
import controller.ProjectController;
import model.Client;
import model.Material;
import repository.MaterialRepository;
import util.InputValidate;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RED_BOLD = "\u001B[1;31m";

    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        ProjectController projectController = new ProjectController();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        do {
            System.out.println(BLUE_BOLD + "╔════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE_BOLD + "║         === Main Menu ===              ║" + RESET);
            System.out.println(BLUE_BOLD + "╚════════════════════════════════════════╝" + RESET);
            System.out.println(GREEN_BOLD + "╔════════════════════════════════════════╗" + RESET);
            System.out.println(GREEN_BOLD + "║ 1. Gestion des clients                 ║" + RESET);
            System.out.println(GREEN_BOLD + "║ 2. Gestion des projets                 ║" + RESET);
            System.out.println(RED_BOLD + "║ 0. Quitter                             ║" + RESET);
            System.out.println(GREEN_BOLD + "╚════════════════════════════════════════╝" + RESET);
            int choice = InputValidate.getValidateInt("Entrez votre choix: ");

            switch (choice) {
                case 1:
                    clientController.CientsMenu();
                    break;
                case 2:
                    projectController.ProjectsMenu();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }
        } while (!exit);
    }
}