package com.servicios.facturacion.facturacion_servicios.product.iva;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/iva")
@RequiredArgsConstructor
public class IvaController {
    @Autowired
    private IvaRepository ivaService;

    @GetMapping()
    public ResponseEntity<List<Iva>> findAll() {
        return ResponseEntity.ok(ivaService.findAll());
    }
}
