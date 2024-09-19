package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private int clientId;

    public Project() {
    }

    public Project(int id, String nomProjet, double margeBeneficiaire, double coutTotal, EtatProjet etatProjet) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
    }

    public Project(int id, String nomProjet, double margeBeneficiaire, double coutTotal, EtatProjet etatProjet, int clientId) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.clientId = clientId;
    }

    public Project(String nomProjet, double margeBeneficiaire, double coutTotal, EtatProjet etatProjet) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
    }

    public static List<Project> fromResultSet(ResultSet resultSet) {
        List<Project> projects = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Project project = new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("nom_projet"),
                    resultSet.getDouble("marge_beneficiaire"),
                    resultSet.getDouble("cout_total"),
                    EtatProjet.valueOf(resultSet.getString("etat_projet")),
                    resultSet.getInt("client_id")
                );
                projects.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}