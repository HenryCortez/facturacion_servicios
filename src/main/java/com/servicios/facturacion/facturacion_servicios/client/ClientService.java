package com.servicios.facturacion.facturacion_servicios.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public boolean deleteClient(Long id) {
        Client clientToDelete = clientRepository.findById(id).orElse(null);
        if (clientToDelete != null) {
            clientToDelete.setStatus(false);
            clientRepository.save(clientToDelete);
            return true;
        }
        return false;
    }

    public Client updateClient(Long id, Client client) {
        Client clientToUpdate = clientRepository.findById(id).orElse(null);
        if (clientToUpdate != null) {
            if (client.getFirstName() != null) {
                clientToUpdate.setFirstName(client.getFirstName());
            }
            if (client.getLastName() != null) {
                clientToUpdate.setLastName(client.getLastName());
            }
            if (client.getSecondName() != null) {
                clientToUpdate.setSecondName(client.getSecondName());
            }
            if (client.getSecondLastName() != null) {
                clientToUpdate.setSecondLastName(client.getSecondLastName());
            }
            return clientRepository.save(clientToUpdate);
        }
        return null;
    }

    public List<Client> findAll() {
        return clientRepository.findByStatus(true);
    }

    public Client findByDni(String dni) {
        return clientRepository.findByDni(dni).orElse(null);
    }
}

