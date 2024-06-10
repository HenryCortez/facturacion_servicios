package com.servicios.facturacion.facturacion_servicios.sales.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleDTO {
    private Long clientId;
    private LocalDateTime dateSale;
    private float subtotal;
    private float total;
    private List<SaleDetailDTO> saleDetails;
}