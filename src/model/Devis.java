package model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Devis {
    private int id;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;
    private int projectId;

    public Devis() {
    }

    public Devis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite,int projectId) {
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.projectId = projectId;
    }

    public Devis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, int projectId) {
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = accepte;
        this.projectId = projectId;
    }

    public Devis(int id, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, int projectId) {
        this.id = id;
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = accepte;
        this.projectId = projectId;
    }

    public static List<Devis> fromResultSet(ResultSet resultSet) {
        List<Devis> devis = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Devis devi = new Devis(
                    resultSet.getInt("id"),
                    resultSet.getDouble("montant_estime"),
                    resultSet.getDate("date_emission").toLocalDate(),
                    resultSet.getDate("date_validite").toLocalDate(),
                    resultSet.getBoolean("accepte"),
                    resultSet.getInt("project_id")
                );
                devis.add(devi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devis;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean isAccepte() {
        return accepte;
    }

    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}