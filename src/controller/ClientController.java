package controller;

import model.Client;
import service.ClientService;
import util.InputValidate;
import util.PrintData;

import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;

    private final Scanner scanner ;

    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RED_BOLD = "\u001B[1;31m";

    public ClientController() {
        this.clientService = new ClientService();
        this.scanner = new Scanner(System.in);
    }

    public void CientsMenu() {
        System.out.println(BLUE_BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE_BOLD + "║         === Menu Principal ===         ║" + RESET);
        System.out.println(BLUE_BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(GREEN_BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN_BOLD + "║ 1. Create a new client                 ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 2. Update a client                     ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 3. Delete a client                     ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 4. Get a client by id                  ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 5. Get all clients                     ║" + RESET);
        System.out.println(RED_BOLD + "║ 0. Exit                                ║" + RESET);
        System.out.println(GREEN_BOLD + "╚════════════════════════════════════════╝" + RESET);
        boolean exit = false;
        do {
            int choice = InputValidate.getValidateInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    updateClient();
                    break;
                case 3:
                    deleteClient();
                    break;
                case 4:
                    getClientById();
                    break;
                case 5:
                    getAllClients();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (!exit);

    }

    public void getAllClients() {
        try {
            clientService.getAllClients().forEach(PrintData::printClientData);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createClient() {
        String nom = InputValidate.getValidateName("Enter client name: ");
        System.out.print("Enter client address: ");
        String adresse = scanner.nextLine();
        System.out.print("Enter client phone number: ");
        String telephone = scanner.nextLine();
        boolean estProfessionnel= InputValidate.getValidateBoolean("Is the client a professional? (true/false): ");
        double remise = InputValidate.getValidateRemise("Enter client discount: ");
        try {
            clientService.createClient(nom, adresse, telephone, estProfessionnel, remise);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client created successfully");
    }

    public void updateClient() {
        int id = InputValidate.getValidateInt("Enter client id: ");
        String nom = InputValidate.getValidateName("Enter client name: ");
        System.out.print("Enter client address: ");
        String adresse = scanner.nextLine();
        System.out.print("Enter client phone number: ");
        String telephone = scanner.nextLine();
        boolean estProfessionnel= InputValidate.getValidateBoolean("Is the client a professional? (true/false): ");
        double remise = InputValidate.getValidateRemise("Enter client discount: ");
        try {
            clientService.updateClient(id, nom, adresse, telephone, estProfessionnel, remise);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client updated successfully");
    }

    public void deleteClient() {
        int id = InputValidate.getValidateInt("Enter client id: ");
        try {
            clientService.deleteClient(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client deleted successfully");
    }

    public void getClientById() {
        int id = InputValidate.getValidateInt("Enter client id: ");
        try {
            Optional<Client> client = clientService.getClientById(id);
            client.ifPresentOrElse((c) -> PrintData.printClientData(c), () -> System.out.println("Client not found"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
