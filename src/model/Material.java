package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Material extends Component {
    private double coutUnitaire;
    private double quantite;
    private double coutTransport;
    private double coefficientQualite;

    public Material() {
    }

    public Material(String nom, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite, int projetId) {
        super(nom, "Matériel", projetId);
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    public Material(int id, String nom, double coutUnitaire, double quantite, String typeComposant, double tauxTVA, int projetId, double coutTransport, double coefficientQualite) {
        super(id, nom, typeComposant, tauxTVA, projetId);
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
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

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public static List<Material> fromResultSet(ResultSet resultSet) throws SQLException {
        List<Material> materials = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNom(resultSet.getString("nom"));
                material.setTypeComposant(resultSet.getString("type_composant"));
                material.setTauxTVA(resultSet.getDouble("taux_tva"));
                material.setProjetId(resultSet.getInt("projet_id"));
                material.setCoutUnitaire(resultSet.getDouble("cout_unitaire"));
                material.setQuantite(resultSet.getDouble("quantite"));
                material.setCoutTransport(resultSet.getDouble("cout_transport"));
                material.setCoefficientQualite(resultSet.getDouble("coefficient_qualite"));
                materials.add(material);
            }
            return materials;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getCoutTotal() {
        return coutUnitaire * quantite + coutTransport;
    }

    public double getCoutTotalTVA() {
        return getCoutTotal() * (1 + getTauxTVA() / 100);
    }
}