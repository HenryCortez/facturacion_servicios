package com.servicios.facturacion.facturacion_servicios.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String name);
    Optional<List<Product>>  findByActive(boolean active);
}
