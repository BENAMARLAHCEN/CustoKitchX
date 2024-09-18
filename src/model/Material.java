package model;

public class Material extends Component {
    private double coutTransport;
    private double coefficientQualite;

    public Material() {
    }

    public Material(int id, String nom, double coutUnitaire, double quantite, String typeComposant, double tauxTVA, int projetId, double coutTransport, double coefficientQualite) {
        super(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA, projetId);
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    // Getters and setters

    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }
}