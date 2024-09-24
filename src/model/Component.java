package model;

public abstract class Component {
    private int id;
    private String nom;
    private String typeComposant;
    private double tauxTVA;
    private int projetId;


    public Component() {
    }

    public Component(String nom, String typeComposant, int projetId) {
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.projetId = projetId;
    }

    public Component(String nom, String typeComposant, double tauxTVA, int projetId) {
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
        this.projetId = projetId;
    }


    public Component(int id, String nom, String typeComposant, double tauxTVA, int projetId) {
        this.id = id;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
        this.projetId = projetId;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public int getProjetId() {
        return projetId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }
}