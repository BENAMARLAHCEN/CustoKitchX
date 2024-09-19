import controller.ClientController;
import controller.ProjectController;
import model.Client;
import model.Material;
import repository.MaterialRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        ProjectController projectController = new ProjectController();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        do {
            System.out.println("1. Gestion des clients");
            System.out.println("2. Gestion des projets");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");
            int choice = scanner.nextInt();

            switch (choice){
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



}}