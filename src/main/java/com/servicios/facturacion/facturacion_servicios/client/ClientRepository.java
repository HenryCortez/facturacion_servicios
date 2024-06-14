package com.servicios.facturacion.facturacion_servicios.client;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
    List<Client> findByStatus(boolean status);
    Optional<Client> findByDni(String dni);
    Optional<Client> findByPhone(String phone);
    Optional<Client> findByEmail(String email);
}
