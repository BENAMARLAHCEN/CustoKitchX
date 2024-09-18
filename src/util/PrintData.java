package util;

import model.Client;

import java.util.List;

public class PrintData {

    public static void printClientData(Client client) {

        System.out.println("====================================");
        System.out.println("ID: " + client.getId());
        System.out.println("Nom: " + client.getNom());
        System.out.println("Adresse: " + client.getAdresse());
        System.out.println("Téléphone: " + client.getTelephone());
        System.out.println("Est professionnel: " + client.isEstProfessionnel());
        System.out.println("Remise: " + client.getRemise());
        System.out.println("====================================");
    }

    public static void printAllClientsData(List<Client> clients) {
            for (Client client : clients) {
                printClientData(client);
            }
    }
}
