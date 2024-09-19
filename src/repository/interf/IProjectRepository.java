package repository.interf;

import model.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectRepository {
    Project createProject(Project project);
    Optional<Project> getProject(int id);
    List<Project> getProjects();
    boolean updateProject(Project project);
    boolean deleteProject(int id);
    boolean applyMargeBeneficiaire(int id, double margeBeneficiaire);
}