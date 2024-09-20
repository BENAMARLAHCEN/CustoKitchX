package controller;

import model.Client;
import model.Devis;
import model.Project;
import service.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class ProjectController {
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final ClientService clientService;
    private final ComponentService componentService;
    private final Scanner scanner;

    public ProjectController() {
        this.projectService = new ProjectService();
        this.materialService = new MaterialService();
        this.workforceService = new WorkforceService();
        this.clientService = new ClientService();
        this.componentService = new ComponentService();
        this.scanner = new Scanner(System.in);
    }

    public void ProjectsMenu() {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        System.out.println("=== Menu Principal ===");
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("4. Quitter");
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
                    System.out.print("Entrez l'ID du projet : ");
                    int id = scanner.nextInt();
                    showProjectDetails(id);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide");
            }
        } while (!exit);
    }

    public Client SelectClient() {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int choice = scanner.nextInt();
        do {
        switch (choice) {
            case 1:
                System.out.println("--- Recherche de client existant ---");
                System.out.print("Entrez le nom du client : ");
                scanner.nextLine();
                String nomClient = scanner.nextLine();
                Optional<Client> client = clientService.getClientByNom(nomClient);
                if (client.isPresent()) {
                    System.out.println("Client trouvé !");
                    System.out.println("Nom : " + client.get().getNom());
                    System.out.println("Adresse : " + client.get().getAdresse());
                    System.out.println("Numéro de téléphone : " + client.get().getTelephone());
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
                System.out.print("Entrez le nom du client : ");
                scanner.nextLine();
                String nom = scanner.nextLine();
                System.out.print("Entrez l'adresse du client : ");
                String adresse = scanner.nextLine();
                System.out.print("Entrez le numéro de téléphone du client : ");
                String telephone = scanner.next();
                System.out.print("Le client est-il un professionnel ? (true/false) : ");
                boolean estProfessionnel = scanner.nextBoolean();
                System.out.print("Entrez le pourcentage de remise du client (0-100) : ");
                double remise = scanner.nextDouble();
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
        System.out.print("Entrez le nom du projet : ");
        scanner.nextLine();
        String nomProjet = scanner.nextLine();
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
            System.out.print("Entrez la quantité de ce matériau (en m²) : ");
            double quantite = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire de ce matériau (€/m²) : ");
            double coutUnitaire = scanner.nextDouble();
            System.out.print("Entrez le coût de transport de ce matériau (€) : ");
            double coutTransport = scanner.nextDouble();
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
                if (coefficientQualite < 1.0) {
                    System.out.println("Le coefficient de qualité doit être supérieur ou égal à 1.0.");
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
            System.out.print("Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
            scanner.nextLine();
            String type = scanner.nextLine();
            System.out.print("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            double tauxHoraire = scanner.nextDouble();
            System.out.print("Entrez le nombre d'heures travaillées : ");
            double heuresTravaillees = scanner.nextDouble();
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
            System.out.print("Entrez le pourcentage de TVA (%) : ");
            tauxTVA = scanner.nextDouble();
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
            System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
            margeBeneficiaire = scanner.nextDouble();
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
        System.out.print("Entrez la date d'émission du devis (format : yyyy-mm-dd) : ");
        scanner.nextLine();
        LocalDate dateEmission = LocalDate.parse(scanner.nextLine());
        System.out.print("Entrez la date de validité du devis (format : yyyy-mm-dd) : ");
        LocalDate dateValidite = LocalDate.parse(scanner.nextLine());
        System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        if (scanner.next().equalsIgnoreCase("y")) {
            double montantEstime = coutTotal * (1 + margeBeneficiaire / 100);
            System.out.println("Shoisir devis accepté ou non (y/n) : ");
            boolean devisAccepte ;
            if (scanner.next().equalsIgnoreCase("y")) {
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
            System.out.println("====================================");
            showProjectDetails(project.getId());
            System.out.println("------------------------------------");
            showDevisDetails(project.getId());
            System.out.println("------------------------------------");
            System.out.println("====================================");
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
