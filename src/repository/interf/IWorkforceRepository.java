package repository.interf;

import model.Workforce;

import java.util.List;
import java.util.Optional;

public interface IWorkforceRepository {
    boolean addWorkforce(Workforce workforce);
    boolean updateWorkforce(Workforce workforce);
    boolean deleteWorkforce(int id);
    Optional<Workforce> getWorkforce(int id);
    Optional<List<Workforce>> getWorkforces();
    Optional<List<Workforce>> getWorkforcesByProject(int projectId);
}
