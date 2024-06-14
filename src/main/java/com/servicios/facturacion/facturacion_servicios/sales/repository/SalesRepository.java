package com.servicios.facturacion.facturacion_servicios.sales.repository;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByClientId(Long clientId);
    List<Sale> findByDateSale(LocalDateTime dateSale);
    List<Sale> findByDateSaleBetween(LocalDateTime startDate, LocalDateTime endDate);
    
}
