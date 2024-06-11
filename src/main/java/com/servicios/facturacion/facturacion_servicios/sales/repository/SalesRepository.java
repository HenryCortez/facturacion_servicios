package com.servicios.facturacion.facturacion_servicios.sales.repository;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByStatusTrue();
    Optional<Sale> findByIdAndStatusTrue(Long id);
    List<Sale> findByClientIdAndStatusTrue(Long clientId);
    List<Sale> findByDateSale(LocalDate dateSale);
    List<Sale> findByDateSaleBetween(LocalDate startDate, LocalDate endDate);
    
}
