package service;

import model.Workforce;
import repository.interf.IWorkforceRepository;

import java.util.List;
import java.util.Optional;

public class WorkforceService {
    private final IWorkforceRepository workforceRepository;

    public WorkforceService() {
        this.workforceRepository = new repository.WorkforceRepository();
    }

    public boolean addWorkforce(String nom, double tauxHoraire, double heuresTravail, double productiviteOuvrier, int projetId) {
        Workforce workforce = new Workforce(nom, tauxHoraire, heuresTravail, productiviteOuvrier, projetId);
        return workforceRepository.addWorkforce(workforce);
    }

    public boolean updateWorkforce(Workforce workforce) {
        return workforceRepository.updateWorkforce(workforce);
    }

    public boolean deleteWorkforce(int id) {
        return workforceRepository.deleteWorkforce(id);
    }

    public Workforce getWorkforceById(int project_id) {
        return workforceRepository.getWorkforce(project_id).orElse(null);
    }

    public List<Workforce> getWorkforces() {
        return workforceRepository.getWorkforces().orElse(null);
    }

    public Optional<List<Workforce>> getWorkforcesByProject(int projectId) {
        return workforceRepository.getWorkforcesByProject(projectId);
    }
}
