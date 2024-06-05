package com.servicios.facturacion.facturacion_servicios.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.facturacion.facturacion_servicios.product.category.CategoryRepository;
import com.servicios.facturacion.facturacion_servicios.product.iva.Iva;
import com.servicios.facturacion.facturacion_servicios.product.iva.IvaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IvaRepository  ivaRepository;

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @PostMapping()
    public ResponseEntity<Product> saveProduct(@RequestParam("name") String name, @RequestParam("category") Long category, @RequestParam("imagen") byte[] imagen, @RequestParam("stock") int stock, @RequestParam("price") float price, @RequestParam("iva") Long iva) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(categoryRepository.findById(category).orElse(null));
        product.setImagen(imagen);
        product.setStock(stock);
        product.setPrice(price);
        product.setIva(ivaRepository.findById(iva).orElse(null));
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("category") Long category, @RequestParam("imagen") byte[] imagen, @RequestParam("stock") int stock, @RequestParam("price") float price) {
        Product entity = new Product();
        entity.setName(name);
        entity.setCategory(categoryRepository.findById(category).orElse(null));
        entity.setImagen(imagen);
        entity.setStock(stock);
        entity.setPrice(price);
        return ResponseEntity.ok(productService.updateProduct(id, entity));
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<Boolean> activation(@PathVariable Long id) {
        return ResponseEntity.ok(productService.activation(id));
    }

    @PutMapping("/iva")
    public ResponseEntity<List<Product>> updateIva(@RequestParam("iva") Long iva) {
        Iva ivaEntity = ivaRepository.findById(iva).orElse(null);
        return ResponseEntity.ok(productService.changeAllIva(ivaEntity));
    }
}
