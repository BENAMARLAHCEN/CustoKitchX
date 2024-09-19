package service;

import model.Project;
import repository.ProjectRepository;
import repository.interf.IProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectService {
    private IProjectRepository projectRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository();
    }

    public Project createProject(Project project) {
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
}
