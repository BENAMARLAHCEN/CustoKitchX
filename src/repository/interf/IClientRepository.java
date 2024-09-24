package repository.interf;

import model.Client;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {

    Client createClient(Client client);
    Optional<Client> getClientById(int id);
    boolean updateClient(Client client);
    boolean deleteClient(int id);
    List<Client> getAllClients();
    Optional<Client> getClientByNom(String nom);
}
