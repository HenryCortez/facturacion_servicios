package com.servicios.facturacion.facturacion_servicios.sales.controller;

import org.springframework.web.bind.annotation.RestController;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleRequestDTO;
import com.servicios.facturacion.facturacion_servicios.sales.dto.UpdateSaleClientDTO;
import com.servicios.facturacion.facturacion_servicios.sales.exceptions.CustomBusinessException;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;
import com.servicios.facturacion.facturacion_servicios.sales.service.SalesService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping()
    public List<Sale> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        try{
            Optional<Sale> sale = salesService.getSaleById(id);
        if (sale.isPresent()) {
            return ResponseEntity.ok(sale.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta con id: " + id + " no encontrada");
        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getSalesByClientId(@PathVariable Long clientId) {
        try {
            List<Sale> sales = salesService.getSalesByClientId(clientId);
            return ResponseEntity.ok(sales);
        } catch (CustomBusinessException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getSalesByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999999);
            List<Sale> sales = salesService.getSalesByDate(startOfDay, endOfDay);
            return ResponseEntity.ok(sales);
        } catch (CustomBusinessException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> getSalesByDateRange(@RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            if (start.isAfter(end)) {
                return ResponseEntity.badRequest().body("La fecha de inicio debe ser anterior a la fecha de fin");
            }
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.atTime(23, 59, 59, 999999999); 
    
            List<Sale> sales = salesService.getSalesByDateRange(startDateTime, endDateTime);
            return ResponseEntity.ok(sales);
        } catch (CustomBusinessException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleRequestDTO saleRequestDTO) {
        try {
            Sale createdSale = salesService.createSale(saleRequestDTO);
            return ResponseEntity.ok(createdSale);
        } catch (CustomBusinessException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/update-client")
    public ResponseEntity<?> updateSaleClient(@RequestBody UpdateSaleClientDTO updateSaleClientDTO) {
        try {
            Sale updatedSale = salesService.updateSaleClient(updateSaleClientDTO.getSaleId(),
                    updateSaleClientDTO.getNewClientId());
            return ResponseEntity.ok(updatedSale);
        } catch (CustomBusinessException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

}