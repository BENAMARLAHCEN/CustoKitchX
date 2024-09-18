package service;

import model.Client;
import repository.ClientRepository;
import repository.interf.IClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final IClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public Optional<Client> getClientById(int id) {
        return clientRepository.getClientById(id);
    }

    public Client createClient(String nom, String adresse, String telephone, boolean estProfessionnel, double remise) {
        Client client = new Client(nom, adresse, telephone, estProfessionnel, remise);
        return clientRepository.createClient(client);
    }

    public boolean updateClient(int id, String nom, String adresse, String telephone, boolean estProfessionnel, double remise) {
        Client client = new Client(id, nom, adresse, telephone, estProfessionnel, remise);
        return clientRepository.updateClient(client);
    }

    public boolean deleteClient(int id) {
        return clientRepository.deleteClient(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public Optional<Client> getClientByNom(String nom) {
        return clientRepository.getClientByNom(nom);
    }
}
