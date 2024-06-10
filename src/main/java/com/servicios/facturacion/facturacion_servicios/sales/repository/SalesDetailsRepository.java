package com.servicios.facturacion.facturacion_servicios.sales.repository;

import org.springframework.stereotype.Repository;
import com.servicios.facturacion.facturacion_servicios.sales.model.SalesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SalesDetailsRepository extends JpaRepository<SalesDetails, Long>{

    
}
