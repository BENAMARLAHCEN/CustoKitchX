package repository.interf;

import model.Devis;

import java.util.Optional;

public interface IDevisRepository {
    void createDevis(Devis devis);
    boolean updateDevis(Devis devis);
    boolean deleteDevis(int id);
    Optional<Devis> getDevisById(int id);
    Optional<Devis> getDevisByProjectId(int projectId);

}
