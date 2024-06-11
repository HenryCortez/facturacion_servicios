package com.servicios.facturacion.facturacion_servicios.sales.dto;

import lombok.Data;
import java.util.List;

@Data
public class SaleRequestDTO {
    private Long clientId;
    private List<SaleDetailDTO> saleDetails;
}