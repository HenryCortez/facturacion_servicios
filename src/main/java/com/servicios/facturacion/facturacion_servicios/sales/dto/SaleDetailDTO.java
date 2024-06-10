package com.servicios.facturacion.facturacion_servicios.sales.dto;

import lombok.Data;

@Data
public class SaleDetailDTO {
    private Long productId;
    private int quantity;
    private float price;
}