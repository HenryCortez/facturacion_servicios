package com.servicios.facturacion.facturacion_servicios.product.category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long>{
  
    Optional<Category> findByName(String name);
    List<Category> findByStatus(boolean status);
}
