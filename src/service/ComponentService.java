package service;

import repository.interf.IComponentRepository;

public class ComponentService {
    private final IComponentRepository componentRepository;

    public ComponentService() {
        this.componentRepository = new repository.ComponentRepository();
    }

    public boolean applyTVA(int projectId, double tva) {
        return componentRepository.applyTVA(projectId, tva);
    }
}
