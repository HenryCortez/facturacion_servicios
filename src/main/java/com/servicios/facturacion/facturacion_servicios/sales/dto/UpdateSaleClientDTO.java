package com.servicios.facturacion.facturacion_servicios.sales.dto;

public class UpdateSaleClientDTO {
    private Long saleId;
    private Long newClientId;

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getNewClientId() {
        return newClientId;
    }

    public void setNewClientId(Long newClientId) {
        this.newClientId = newClientId;
    }
}
