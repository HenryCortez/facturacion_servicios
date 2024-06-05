package com.servicios.facturacion.facturacion_servicios.client;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Client> saveClient(@RequestParam String dni , @RequestParam String firstName, @RequestParam String lastName, @RequestParam(required = false) String secondName, @RequestParam(required = false) String secondLastName, @RequestParam String address, @RequestParam String phone, @RequestParam String email, @RequestParam String dniType) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setSecondName(secondName);
        client.setSecondLastName(secondLastName);
        client.setAddress(address);
        client.setPhone(phone);
        client.setEmail(email);
        client.setDniType(dniType);
        if (dniType.equals("cedula")) {
            if (dni.length() != 10) {
                return ResponseEntity.badRequest().build();
            }
            client.setDni(dni);
        } else if (dniType.equals("pasaporte")) {
            if (dni.length() != 9) {
                return ResponseEntity.badRequest().build();
            }
            client.setDni(dni);
        }else{
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> putMethodName(@PathVariable String id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String secondName, @RequestParam(required = false) String secondLastName, @RequestParam(required = false) String address, @RequestParam(required = false) String phone, @RequestParam(required = false) String email) {
        Client entity = new Client();
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setSecondName(secondName);
        entity.setSecondLastName(secondLastName);
        entity.setAddress(address);
        entity.setPhone(phone);
        entity.setEmail(email);
        return ResponseEntity.ok(clientService.updateClient(Long.parseLong(id), entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
