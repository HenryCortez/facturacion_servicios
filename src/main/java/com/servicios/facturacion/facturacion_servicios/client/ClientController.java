package com.servicios.facturacion.facturacion_servicios.client;

import org.springframework.web.bind.annotation.RestController;

import com.servicios.facturacion.facturacion_servicios.client.validator.Validator;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Client> getClientByDni(@PathVariable String dni) {
        return ResponseEntity.ok(clientService.findByDni(dni));
    }
    

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Client> getClientByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(clientService.findByPhone(phone));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.findByEmail(email));
    }

    @PostMapping()
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        
        if (client.getDniType().equals("cedula")) {
            if (client.getDni().length() != 10 || !Validator.isValidCedula(client.getDni())) {
                return ResponseEntity.badRequest().build();
            }
        } else if (client.getDniType().equals("pasaporte")) {
            if (client.getDni().length() != 8 || !Validator.isValidPasaporte(client.getDni())) {
                return ResponseEntity.badRequest().build();
            }
        }else if (client.getDniType().toLowerCase().equals("ruc")){
            if (client.getDni().length() != 13 || 
            !Validator.isValidPasaporte(client.getDni().substring(0, 10))
            || (!client.getDni().substring(10, 13).equals("001") && 
            !client.getDni().substring(10, 13).equals("002")) ) {
                return ResponseEntity.badRequest().build();
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> putMethodName(@PathVariable String id, @RequestBody ClientDto client) {
        Client entity = new Client();
        entity.setFirstName(client.getFirstName());
        entity.setLastName(client.getLastName());
        entity.setSecondName(client.getSecondName());
        entity.setSecondLastName(client.getSecondLastName());
        entity.setAddress(client.getAddress());
        entity.setPhone(client.getPhone());
        entity.setEmail(client.getEmail());
        return ResponseEntity.ok(clientService.updateClient(Long.parseLong(id), entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
