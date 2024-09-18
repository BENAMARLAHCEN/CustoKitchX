package service;

import model.Material;
import repository.MaterialRepository;
import repository.interf.IMaterialRepository;

import java.util.List;
import java.util.Optional;

public class MaterialService {
    private IMaterialRepository materialRepository;

    public MaterialService() {
        this.materialRepository = new MaterialRepository();
    }

    public boolean addMaterial(String nom, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite, int projetId) {
        Material material = new Material(nom, coutUnitaire, quantite, coutTransport, coefficientQualite, projetId);
        return materialRepository.addMaterial(material);
    }

    public boolean updateMaterial(int id, String nom, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite, int projetId) {
        Material material = new Material(id, nom, coutUnitaire, quantite, "MATERIEL", 0, projetId, coutTransport, coefficientQualite);
        return materialRepository.updateMaterial(material);
    }

    public boolean deleteMaterial(int id) {
        return materialRepository.deleteMaterial(id);
    }

    public Optional<Material> getMaterial(int id) {
        return materialRepository.getMaterial(id);
    }

    public Optional<List<Material>> getMaterials() {
        return materialRepository.getMaterials();
    }

    public Optional<List<Material>> getMaterialsByProject(int projectId) {
        return materialRepository.getMaterialsByProject(projectId);
    }
}
