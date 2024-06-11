package com.servicios.facturacion.facturacion_servicios.sales.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomBusinessException extends ResponseStatusException {

    public CustomBusinessException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static CustomBusinessException productInactive(Long productId) {
        return new CustomBusinessException(HttpStatus.BAD_REQUEST, "Producto con ID: " + productId + " esta inactivo o no existe");
    }

    public static CustomBusinessException insufficientStock(Long productId) {
        return new CustomBusinessException(HttpStatus.BAD_REQUEST, "Insuficiente stock para el producto con ID: " + productId);
    }
    public static CustomBusinessException invalidDateFormat() {
        return new CustomBusinessException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido.");
    }
    
    public static CustomBusinessException errorFetchingSalesByDate() {
        return new CustomBusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener las ventas por la fecha especificada.");
    }
    
    public static CustomBusinessException errorFetchingSalesByDateRange() {
        return new CustomBusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener las ventas por el rango de fechas especificado.");
    }
}