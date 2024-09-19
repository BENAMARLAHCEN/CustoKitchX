package service;

import model.Devis;
import repository.DevisRepository;
import repository.interf.IDevisRepository;

public class DevisService {
    private final IDevisRepository devisRepository;

    public DevisService() {
        this.devisRepository = new DevisRepository();
    }

    public void createDevis(Devis devis) {
        devisRepository.createDevis(devis);
    }

    public boolean updateDevis(Devis devis) {
        return devisRepository.updateDevis(devis);
    }

    public boolean deleteDevis(int id) {
        return devisRepository.deleteDevis(id);
    }

    public Devis getDevisById(int id) {
        return devisRepository.getDevisById(id).orElse(null);
    }

    public Devis getDevisByProjectId(int projectId) {
        return devisRepository.getDevisByProjectId(projectId).orElse(null);
    }
}
