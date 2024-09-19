package service;

import model.Workforce;
import repository.interf.IWorkforceRepository;

import java.util.List;

public class WorkforceService {
    private final IWorkforceRepository workforceRepository;

    public WorkforceService(IWorkforceRepository workforceRepository) {
        this.workforceRepository = workforceRepository;
    }

    public boolean addWorkforce(Workforce workforce) {
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

    public List<Workforce> getWorkforcesByProject(int projectId) {
        return workforceRepository.getWorkforcesByProject(projectId).orElse(null);
    }
}
