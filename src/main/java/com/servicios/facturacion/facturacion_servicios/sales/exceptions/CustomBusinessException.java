package com.servicios.facturacion.facturacion_servicios.sales.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomBusinessException extends ResponseStatusException {

    public CustomBusinessException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static CustomBusinessException productInactive(Long productId) {
        return new CustomBusinessException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " is inactive or deleted");
    }

    public static CustomBusinessException insufficientStock(Long productId) {
        return new CustomBusinessException(HttpStatus.BAD_REQUEST, "Insufficient stock for product with ID " + productId);
    }
}