package com.servicios.facturacion.facturacion_servicios.sales.controller;

import org.springframework.web.bind.annotation.RestController;

import com.servicios.facturacion.facturacion_servicios.product.Product;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleRequestDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping()
    public List<Sale> getAllActiveSales() {
        return salesService.getAllActiveSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = salesService.getSaleById(id);
        if (sale.isPresent()) {
            return ResponseEntity.ok(sale.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getSalesByClientId(@PathVariable Long clientId) {
        try {
            List<Sale> sales = salesService.getSalesByClientId(clientId);
            return ResponseEntity.ok(sales);
        } catch (CustomBusinessException e) {
            // Captura excepciones de negocio personalizadas
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otra excepción no manejada específicamente
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

}