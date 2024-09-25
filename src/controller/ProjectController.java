package controller;

import model.Client;
import model.Devis;
import model.Project;
import service.*;
import util.InputValidate;
import util.PrintData;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class ProjectController {
    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RED_BOLD = "\u001B[1;31m";

    private final ProjectService projectService;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final ClientService clientService;
    private final ComponentService componentService;
    private final DevisService devisService;
    private final Scanner scanner;

    public ProjectController() {
        this.projectService = new ProjectService();
        this.materialService = new MaterialService();
        this.workforceService = new WorkforceService();
        this.clientService = new ClientService();
        this.componentService = new ComponentService();
        this.scanner = new Scanner(System.in);
        this.devisService = new DevisService();
    }

    public void ProjectsMenu() {
        System.out.println(BLUE_BOLD + "╔═════════════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE_BOLD + "║=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===║" + RESET);
        System.out.println(BLUE_BOLD + "╚═════════════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println(BLUE_BOLD + "╔═════════════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE_BOLD + "║                              === Menu Principal ===                                 ║" + RESET);
        System.out.println(BLUE_BOLD + "╚═════════════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println(GREEN_BOLD + "╔════════════════════════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN_BOLD + "║ 1. Créer un nouveau projet                                                         ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 2. Afficher les projets existants                                                  ║" + RESET);
        System.out.println(GREEN_BOLD + "║ 3. Calculer le coût d'un projet                                                    ║" + RESET);
        System.out.println(RED_BOLD + "║ 4. Quitter                                                                         ║" + RESET);
        System.out.println(GREEN_BOLD + "╚════════════════════════════════════════════════════════════════════════════════════╝" + RESET);
        boolean exit = false;
        int choice = 0;
        do {
            System.out.print("Choisissez une option : ");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
                continue;
            }
            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    showProjects();
                    break;
                case 3:
                    int id = InputValidate.getValidateInt("Entrez l'ID du projet : ");
                    showProjectDetails(id);
                    updateProjectDevis(id);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide");
            }
        } while (!exit);
    }

    private void updateProjectDevis(int id) {
        Optional<Project> project = Optional.ofNullable(projectService.getProject(id));
        if (project.isPresent()) {
            Devis devis = projectService.getDevisByProjectId(id);
            if (devis != null){
                PrintData.printDevisData(devis);
            }else {
                System.out.println("Devis introuvable.");
            }
            System.out.println("--- Mise à jour du Devis ---");
            System.out.print("Souhaitez-vous mettre à jour le devis ? (y/n) : ");
            if (scanner.next().equalsIgnoreCase("y")) {
                if (devis.isAccepte()){
                    System.out.println("Devis déjà accepté ou refusé.");
                }
                else {
                if (InputValidate.getValidateName("Voulez-vous modifier Tva ? (y/n) : ").equalsIgnoreCase("y")){
                   double TVA = InputValidate.getValidateDouble("Entrez le nouveau taux de TVA : ");
                   if (componentService.applyTVA(id, TVA)) {
                       System.out.println("TVA modifiée avec succès.");
                   }
                }
                 if (InputValidate.getValidateName("Voulez-vous modifier la marge bénéficiaire ? (y/n) : ").equalsIgnoreCase("y")){
                    double margeBeneficiaire = InputValidate.getValidateDouble("Entrez le nouveau pourcentage de marge bénéficiaire : ");
                    if (projectService.applyMargeBeneficiaire(id, margeBeneficiaire)) {
                        project.get().setMargeBeneficiaire(margeBeneficiaire);
                        System.out.println("Marge bénéficiaire modifiée avec succès.");
                    }
                 }

                 double totalCost = projectService.calculateProjectCost(id);
                    project.get().setCoutTotal(totalCost);
                    if (InputValidate.getValidateName("Voulez-vous accepter le devis ? (y/n) : ").equalsIgnoreCase("y")) {
                        devis.setAccepte(true);
                        devis.setDateValidite(InputValidate.getValidateDate("Entrez la date de validité du devis (format : yyyy-mm-dd) : "));
                    }
                    Devis newDevis = new Devis(devis.getId(),totalCost*(1 +project.get().getMargeBeneficiaire()/100),LocalDate.now(),devis.getDateValidite(),devis.isAccepte(),devis.getProjectId());
                    PrintData.printDevisData(newDevis);
                    System.out.print("Souhaitez-vous enregistrer les modifications ? (y/n) : ");
                    if (scanner.next().equalsIgnoreCase("y")) {
                        projectService.updateProject(project.get());
                        devisService.createDevis(newDevis);
                        System.out.println("Nouveau devis enregistré avec succès.");
                    } else {
                        System.out.println("Opération annulée.");
                    }
                }
            }
        }
    }

    public Client SelectClient() {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        int choice = InputValidate.getValidateInt("Entrez votre choix : ");
        do {
            switch (choice) {
                case 1:
                    System.out.println("--- Recherche de client existant ---");
                    String nomClient = InputValidate.getValidateName("Entrez le nom du client : ");
                    Optional<Client> client = clientService.getClientByNom(nomClient);
                    if (client.isPresent()) {
                        System.out.println("Client trouvé !");
                        PrintData.printClientData(client.get());
                        System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                        String response = scanner.next();
                        if (response.equalsIgnoreCase("y")) {
                            return client.get();
                        }
                    } else {
                        System.out.println("Client non trouvé.");
                        System.out.print("Voulez-vous réessayer ? (y/n) : ");
                        String response = scanner.next();
                        if (response.equalsIgnoreCase("n")) {
                            return null;
                        }
                    }
                case 2:
                    System.out.println("--- Ajout d'un nouveau client ---");
                    String nom = InputValidate.getValidateName("Entrez le nom du client : ");
                    System.out.print("Entrez l'adresse du client : ");
                    String adresse = scanner.nextLine();
                    scanner.nextLine();
                    System.out.print("Entrez le numéro de téléphone du client : ");
                    String telephone = scanner.next();
                    boolean estProfessionnel = InputValidate.getValidateBoolean("Le client est-il un professionnel ? (true/false) : ");
                    double remise = InputValidate.getValidateDouble("Entrez le pourcentage de remise du client : ");
                    return clientService.createClient(nom, adresse, telephone, estProfessionnel, remise);
                default:
                    return null;
            }
        } while (true);
    }

    public void createProject() {
        Client client = SelectClient();
        if (client == null) {
            System.out.println("Opération annulée.");
            return;
        }
        System.out.println("--- Création d'un Nouveau Projet ---");
        String nomProjet = InputValidate.getValidateName("Entrez le nom du projet : ");
        Project project = projectService.createProject(nomProjet, client.getId());
        if (project == null) {
            System.out.println("Erreur lors de la création du projet.");
            return;
        }
        System.out.println("--- Ajout des matériaux ---");
        do {
            System.out.print("Entrez le nom du matériau : ");
            String nomMateriau = scanner.nextLine();
            scanner.nextLine();
            double quantite = InputValidate.getValidateDouble("Entrez la quantité de ce matériau (m²) : ");
            double coutUnitaire = InputValidate.getValidateDouble("Entrez le coût unitaire de ce matériau (€/m²) : ");
            double coutTransport = InputValidate.getValidateDouble("Entrez le coût de transport de ce matériau (€) : ");
            double coefficientQualite = 0;
            do {
                System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
                try {
                    coefficientQualite = scanner.nextDouble();
                } catch (Exception e) {
                    System.out.println("Veuillez entrer un nombre valide.");
                    scanner.next();
                    continue;
                }
                if (coefficientQualite < 1.0 || coefficientQualite > 2) {
                    System.out.println("Le coefficient de qualité doit être supérieur ou égal à 1.0 et inférieur ou égal à 2.0.");
                } else {
                    break;
                }
            } while (true);
            materialService.addMaterial(nomMateriau, coutUnitaire, quantite, coutTransport, coefficientQualite, project.getId());
            System.out.println("Matériau ajouté avec succès !");
            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
        } while (scanner.next().equalsIgnoreCase("y"));
        System.out.println("--- Ajout de la main-d'œuvre ---");
        do {
            String type = InputValidate.getValidateWorkforceType("Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
            double tauxHoraire = InputValidate.getValidateDouble("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            double heuresTravaillees = InputValidate.getValidateDouble("Entrez le nombre d'heures travaillées : ");
            double facteurProductivite;
            do {
                System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
                try {
                    facteurProductivite = scanner.nextDouble();
                } catch (Exception e) {
                    System.out.println("Veuillez entrer un nombre valide.");
                    scanner.next();
                    continue;
                }
                if (facteurProductivite < 1.0) {
                    System.out.println("Le facteur de productivité doit être supérieur ou égal à 1.0.");
                } else {
                    break;
                }
            } while (true);

            boolean success = workforceService.addWorkforce(type, tauxHoraire, heuresTravaillees, facteurProductivite, project.getId());
            if (success) {
                System.out.println("Main-d'œuvre ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de la main-d'œuvre.");
                continue;
            }
            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
        } while (scanner.next().equalsIgnoreCase("y"));

        System.out.println("--- Calcul du coût total ---");
        System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        boolean applyTVA = scanner.next().equalsIgnoreCase("y");
        double tauxTVA = 0;
        if (applyTVA) {
            tauxTVA = InputValidate.getValidateDouble("Entrez le taux de TVA à appliquer (%) : ");
        }
        boolean tvaSuccess = componentService.applyTVA(project.getId(), tauxTVA);
        if (!tvaSuccess) {
            System.out.println("Erreur lors de l'application de la TVA.");
            return;
        }
        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        boolean applyMarge = scanner.next().equalsIgnoreCase("y");
        double margeBeneficiaire = 0;
        if (applyMarge) {
            margeBeneficiaire = InputValidate.getValidateDouble("Entrez le pourcentage de marge bénéficiaire à appliquer (%) : ");
        }
        boolean margeSuccess = projectService.applyMargeBeneficiaire(project.getId(), margeBeneficiaire);
        project.setMargeBeneficiaire(margeBeneficiaire);
        if (!margeSuccess) {
            System.out.println("Erreur lors de l'application de la marge bénéficiaire.");
            return;
        }

        double coutTotal = projectService.calculateProjectCost(project.getId());
        project.setCoutTotal(coutTotal);

        showProjectDetails(project.getId());

        System.out.println("--- Enregistrement du Devis ---");
        LocalDate dateEmission = InputValidate.getValidateDate("Entrez la date d'émission du devis (format : yyyy-mm-dd) : ");
        System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        LocalDate dateValidite = null;
        if (scanner.next().equalsIgnoreCase("y")) {
            double montantEstime = coutTotal * (1 + margeBeneficiaire / 100);
            System.out.println("Shoisir devis accepté ou non (y/n) : ");
            boolean devisAccepte ;
            if (scanner.next().equalsIgnoreCase("y")) {
                dateValidite = InputValidate.getValidateDate("Entrez la date de validité du devis (format : yyyy-mm-dd) : ");
                devisAccepte = true;
                montantEstime = montantEstime * (1 + client.getRemise() / 100);
            } else {
                devisAccepte = false;
            }
            boolean success = projectService.createDevis(montantEstime, dateEmission, dateValidite, devisAccepte, project.getId());
            if (success) {
                System.out.println("Devis enregistré avec succès !");
            } else {
                System.out.println("Erreur lors de l'enregistrement du devis.");
            }
        } else {
            System.out.println("Opération annulée.");
        }
        System.out.println("Projet complet créé avec succès.");
    }

    public void showProjects() {
        System.out.println("--- Liste des Projets ---");
        projectService.getProjects().forEach(project -> {
            System.out.println("============================================================================================================");
            showProjectDetails(project.getId());
            System.out.println("------------------------------------");
            showDevisDetails(project.getId());
            System.out.println("------------------------------------");
            System.out.println("============================================================================================================");
        });
    }

    public void showDevisDetails(int id) {
        Optional<Devis> devis = Optional.ofNullable(projectService.getDevisByProjectId(id));
        if (devis.isPresent()) {
            System.out.println("--- Détail du Devis ---");
            System.out.println("Montant estimé : " + devis.get().getMontantEstime() + " €");
            System.out.println("Date d'émission : " + devis.get().getDateEmission());
            System.out.println("Date de validité : " + devis.get().getDateValidite());
            System.out.println("Devis accepté : " + devis.get().isAccepte());
        } else {
            System.out.println("Devis introuvable.");
        }
    }

    public void showProjectDetails(int id) {
        Optional<Project> project = Optional.ofNullable(projectService.getProject(id));
        if (project.isPresent()) {
            System.out.println("Nom du projet : " + project.get().getNomProjet());
            clientService.getClientById(project.get().getClientId()).ifPresentOrElse(client -> {
                System.out.println("Client : " + client.getNom());
                System.out.println("Adresse du chantier : " + client.getAdresse());
            }, () -> {
                System.out.println("Client introuvable.");
            });
            System.out.println("--- Détail des Coûts ---");
            System.out.println("1. Matériaux :");
            materialService.getMaterialsByProject(id).ifPresent(materials -> {
                materials.forEach(material -> {
                    System.out.println("- " + material.getNom() + " : " + material.getCoutTotal() + " € (quantité : " + material.getQuantite() + " m² " + ", coût unitaire : " + material.getCoutUnitaire() + " €/m²" + ", qualité : " + material.getCoefficientQualite() + ", transport : " + material.getCoutTransport() + " €)");
                });
                System.out.println("**Coût total des matériaux avant TVA : " + materials.stream().mapToDouble((m) -> m.getCoutTotal()).sum() + " €**");
                System.out.println("**Coût total des matériaux avec TVA (" + materials.getFirst().getTauxTVA() + "%) : " + materials.stream().mapToDouble((m) -> m.getCoutTotalTVA()).sum() + " €**");
            });
            System.out.println("2. Main-d'œuvre :");
            workforceService.getWorkforcesByProject(id).ifPresentOrElse(
                    workforces -> {
                        workforces.forEach(workforce -> {
                            System.out.println("- " + workforce.getNom() + " : " + workforce.getCoutTotal() + " € (taux horaire : " + workforce.getTauxHoraire() + " €/h, heures travaillées : " + workforce.getHeuresTravail() + " h, productivité : " + workforce.getProductiviteOuvrier() + ")");
                        });
                        System.out.println("**Coût total de la main-d'œuvre avant TVA : " + workforces.stream().mapToDouble((w) -> w.getCoutTotal()).sum() + " €**");
                        System.out.println("**Coût total de la main-d'œuvre avec TVA (" + workforces.getFirst().getTauxTVA() + "%) : " + workforces.stream().mapToDouble((w) -> w.getCoutTotalTVA()).sum() + " €**");
                    },
                    () -> {
                        System.out.println("Main-d'œuvre introuvable.");
                    });
            System.out.println("3. Coût total avant marge : " + project.get().getCoutTotal() + " €");
            System.out.println("4. Marge bénéficiaire (" + project.get().getMargeBeneficiaire() + "%) : " + project.get().getCoutTotal() * project.get().getMargeBeneficiaire() / 100 + " €");
            System.out.println("**Coût total final du projet : " + (project.get().getCoutTotal() + project.get().getCoutTotal() * project.get().getMargeBeneficiaire() / 100) + " €**");
        } else {
            System.out.println("Projet introuvable.");
        }
    }
}