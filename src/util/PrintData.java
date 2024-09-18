package util;

import model.Client;
import model.Material;

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

    public static void printMaterialData(Material material) {
        System.out.println("====================================");
        System.out.println("ID: " + material.getId());
        System.out.println("Nom: " + material.getNom());
        System.out.println("Coût unitaire: " + material.getCoutUnitaire());
        System.out.println("Quantité: " + material.getQuantite());
        System.out.println("Coût transport: " + material.getCoutTransport());
        System.out.println("Taxe TVA: " + material.getTauxTVA());
        System.out.println("Coefficient qualité: " + material.getCoefficientQualite());
        System.out.println("Projet ID: " + material.getProjetId());
        System.out.println("====================================");
    }

    public static void printAllMaterialsData(List<Material> materials) {
        for (Material material : materials) {
            printMaterialData(material);
        }
    }

}
