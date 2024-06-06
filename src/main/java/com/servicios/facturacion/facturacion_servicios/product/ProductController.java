package com.servicios.facturacion.facturacion_servicios.product;

import java.io.IOException;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;

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
    private IvaRepository ivaRepository;

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
    public ResponseEntity<Product> saveProduct(@RequestParam("name") String name,
            @RequestParam("category") Long category, @RequestParam("imagen") MultipartFile imagen,
            @RequestParam("stock") int stock, @RequestParam("price") float price) {
        try {
            System.out.println(imagen.getBytes());
            Product product = new Product();
            product.setName(name);
            product.setCategory(categoryRepository.findById(category).orElse(null));
            product.setImagen(Base64.getEncoder().encodeToString(imagen.getBytes()));
            product.setStock(stock);
            product.setPrice(price);
            product.setIva(ivaRepository.findById(2L).orElse(null));
            return ResponseEntity.ok(productService.saveProduct(product));
            // Ahora puedes usar el array de bytes...
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "category") Long category,
            @RequestParam(required = false, name = "imagen") MultipartFile imagen,
            @RequestParam(required = false, name = "stock") int stock,
            @RequestParam(required = false, name = "price") float price) throws IOException {
        Product entity = new Product();
        entity.setName(name);
        entity.setCategory(categoryRepository.findById(category).orElse(null));
        entity.setImagen(Base64.getEncoder().encodeToString(imagen.getBytes()));
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
