package repository.interf;

import model.Material;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepository {
    boolean addMaterial(Material material);
    boolean updateMaterial(Material material);
    boolean deleteMaterial(int id);
    Optional<Material> getMaterial(int id);
    Optional<List<Material>> getMaterials();
    Optional<List<Material>> getMaterialsByProject(int projectId);
}
