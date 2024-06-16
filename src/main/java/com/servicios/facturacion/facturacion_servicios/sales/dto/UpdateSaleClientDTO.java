package com.servicios.facturacion.facturacion_servicios.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaleClientDTO {
    private Long saleId;
    private String newClientDni;

}
