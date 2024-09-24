package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Workforce extends Component {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public Workforce() {
    }

    public Workforce(int id, String nom, double tauxTVA, int projetId, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, "Workforce", tauxTVA, projetId);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public Workforce(String nom, double tauxHoraire, double heuresTravail, double productiviteOuvrier, int projetId) {
        super(nom, "Workforce", projetId);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public static List<Workforce> fromResultSet(ResultSet resultSet) throws SQLException {
        List<Workforce> workforces = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Workforce workforce = new Workforce();
                workforce.setId(resultSet.getInt("id"));
                workforce.setNom(resultSet.getString("nom"));
                workforce.setTypeComposant(resultSet.getString("type_composant"));
                workforce.setTauxTVA(resultSet.getDouble("taux_TVA"));
                workforce.setProjetId(resultSet.getInt("projet_id"));
                workforce.setTauxHoraire(resultSet.getDouble("taux_horaire"));
                workforce.setHeuresTravail(resultSet.getDouble("heures_travail"));
                workforce.setProductiviteOuvrier(resultSet.getDouble("productivite_ouvrier"));
                workforces.add(workforce);
            }
            return workforces;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public double getCoutTotal() {
        return tauxHoraire * heuresTravail * productiviteOuvrier;
    }


    public double getCoutTotalTVA() {
        return getCoutTotal() * (1 + getTauxTVA() / 100);
    }
}