package controller;

import model.Client;
import service.ClientService;
import util.PrintData;

import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;

    private final Scanner scanner ;

    public ClientController() {
        this.clientService = new ClientService();
        this.scanner = new Scanner(System.in);
    }

    public void CientsMenu() {
        System.out.println("1. Create a new client");
        System.out.println("2. Update a client");
        System.out.println("3. Delete a client");
        System.out.println("4. Get a client by id");
        System.out.println("5. Get all clients");
        System.out.println("0. Exit");
        boolean exit = false;
        do {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
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



    private void getAllClients() {
        try {
            clientService.getAllClients().forEach(PrintData::printClientData);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createClient() {
        System.out.print("Enter client name: ");
        scanner.nextLine();
        String nom = scanner.nextLine();
        System.out.print("Enter client address: ");
        String adresse = scanner.nextLine();
        System.out.print("Enter client phone number: ");
        String telephone = scanner.nextLine();
        System.out.print("Is the client a professional? (true/false): ");
        boolean estProfessionnel = scanner.nextBoolean();
        System.out.print("Enter client discount: ");
        double remise = scanner.nextDouble();
        try {
            clientService.createClient(nom, adresse, telephone, estProfessionnel, remise);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client created successfully");
    }

    public void updateClient() {
        System.out.print("Enter client id: ");
        int id = scanner.nextInt();
        System.out.print("Enter client name: ");
        String nom = scanner.nextLine();
        System.out.print("Enter client address: ");
        String adresse = scanner.nextLine();
        System.out.print("Enter client phone number: ");
        String telephone = scanner.nextLine();
        System.out.print("Is the client a professional? (true/false): ");
        boolean estProfessionnel = scanner.nextBoolean();
        System.out.print("Enter client discount: ");
        double remise = scanner.nextDouble();
        try {
            clientService.updateClient(id, nom, adresse, telephone, estProfessionnel, remise);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client updated successfully");
    }

    public void deleteClient() {
        System.out.print("Enter client id: ");
        int id = scanner.nextInt();
        try {
            clientService.deleteClient(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client deleted successfully");
    }

    public void getClientById() {
        System.out.print("Enter client id: ");
        int id = scanner.nextInt();
        try {
            Optional<Client> client = clientService.getClientById(id);
            client.ifPresentOrElse((c) -> PrintData.printClientData(c), () -> System.out.println("Client not found"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
