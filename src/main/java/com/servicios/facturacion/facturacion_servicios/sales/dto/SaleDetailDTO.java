package com.servicios.facturacion.facturacion_servicios.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailDTO {
    private Long productId;
    private int quantity;
}