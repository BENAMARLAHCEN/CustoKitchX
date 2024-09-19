package util;

import model.Client;
import model.Material;
import model.Project;
import model.Workforce;

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

    public static void printWorkforceData(Workforce workforce) {
        System.out.println("====================================");
        System.out.println("ID: " + workforce.getId());
        System.out.println("Nom: " + workforce.getNom());
        System.out.println("Taux horaire: " + workforce.getTauxHoraire());
        System.out.println("Nombre d'heures: " + workforce.getHeuresTravail());
        System.out.println("Productivité ouvrier: " + workforce.getProductiviteOuvrier());
        System.out.println("Taux TVA: " + workforce.getTauxTVA());
        System.out.println("Projet ID: " + workforce.getProjetId());
        System.out.println("====================================");
    }

    public static void printAllWorkforcesData(List<Workforce> workforces) {
        for (Workforce workforce : workforces) {
            printWorkforceData(workforce);
        }
    }

    public static void printProjectData(Project project) {
        System.out.println("====================================");
        System.out.println("ID: " + project.getId());
        System.out.println("Nom: " + project.getNomProjet());
        System.out.println("Marge bénéficiaire: " + project.getMargeBeneficiaire());
        System.out.println("Coût total: " + project.getCoutTotal());
        System.out.println("État projet: " + project.getEtatProjet());
        System.out.println("Client ID: " + project.getClientId());
        System.out.println("====================================");
    }

    public static void printAllProjectsData(List<Project> projects) {
        for (Project project : projects) {
            printProjectData(project);
        }
    }

    public static void printWelcomeMessage() {
        System.out.println("Welcome to the project management system");
    }



}
