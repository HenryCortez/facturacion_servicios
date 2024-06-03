package com.servicios.facturacion.facturacion_servicios.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.servicios.facturacion.facturacion_servicios.product.iva.IvaRepository;

public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IvaRepository ivaRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
