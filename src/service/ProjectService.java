package service;

import model.Devis;
import model.Material;
import model.Project;
import model.Workforce;
import repository.DevisRepository;
import repository.ProjectRepository;
import repository.interf.IDevisRepository;
import repository.interf.IProjectRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProjectService {
    private IProjectRepository projectRepository;
    private IDevisRepository devisRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository();
        this.devisRepository = new DevisRepository();
    }

    public Project createProject(String nom,int clientId) {
        Project project = new Project(nom,clientId);
        return projectRepository.createProject(project);
    }

    public Project getProject(int id) {
        Optional<Project> project = projectRepository.getProject(id);
        return project.orElse(null);
    }

    public boolean updateProject(Project project) {
        return projectRepository.updateProject(project);
    }

    public boolean deleteProject(int id) {
        return projectRepository.deleteProject(id);
    }

    public List<Project> getProjects() {
        return projectRepository.getProjects();
    }

    public double calculateProjectCost(int id) {
        Optional<Project> project = projectRepository.getProject(id);
        List< Material> materials = new MaterialService().getMaterialsByProject(id).orElse(null);
        List<Workforce> workforces = new WorkforceService().getWorkforcesByProject(id).orElse(null);
        if (project.isPresent()) {
            assert materials != null;
            double totalMaterialCost = materials.stream().mapToDouble((m) -> m.getCoutTotalTVA()).sum();
            double totalWorkforceCost = workforces.stream().mapToDouble((w) -> w.getCoutTotalTVA()).sum();
            double totalCost = totalMaterialCost + totalWorkforceCost;
            if (project.get().getCoutTotal() != totalCost) {
                project.get().setCoutTotal(totalCost);
                projectRepository.updateProject(project.get());
            }
            return totalCost;
        }
        return 0;
    }

    public boolean applyMargeBeneficiaire(int id, double margeBeneficiaire) {
        return projectRepository.applyMargeBeneficiaire(id, margeBeneficiaire);
    }

    public boolean createDevis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, int projectId) {
        Devis devis = new Devis(montantEstime, dateEmission, dateValidite, accepte, projectId);
        return devisRepository.createDevis(devis);
    }
}
