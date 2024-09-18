package model;

public class Workforce extends Component {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public Workforce() {
    }

    public Workforce(int id, String nom, double coutUnitaire, double quantite, String typeComposant, double tauxTVA, int projetId, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA, projetId);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    // Getters and setters


    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }
}