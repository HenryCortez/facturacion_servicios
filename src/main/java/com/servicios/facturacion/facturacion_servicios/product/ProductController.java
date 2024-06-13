package com.servicios.facturacion.facturacion_servicios.product;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.servicios.facturacion.facturacion_servicios.aws.S3Service;
import com.servicios.facturacion.facturacion_servicios.product.Dto.ProductDto;
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
    @Autowired
    private S3Service s3Service;

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
    @GetMapping("/deactivate")
    public ResponseEntity<List<Product>> findDeactivate() {
        return ResponseEntity.ok(productService.findDeactivate());
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
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productRequest) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(productRequest.getImage());
            // Crea un MultipartFile a partir del array de bytes
            MultipartFile imagen = new MockMultipartFile(
                    productRequest.getName(),
                    productRequest.getName() + ".jpg",
                    "image/jpeg",
                    imageBytes);
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setCategory(categoryRepository.findById(productRequest.getCategory()).orElse(null));
            product.setImagen(s3Service.uploadFile(imagen));
            product.setStock(productRequest.getStock());
            product.setPrice(productRequest.getPrice());
            product.setIva(ivaRepository.findById(2L).orElse(null));
            return ResponseEntity.ok(productService.saveProduct(product));
            // Ahora puedes usar el array de bytes...
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
            @RequestBody() ProductDto productRequest) throws IOException {
        byte[] imageBytes;
        MultipartFile imagen = null;
        if (productRequest.getImage() != null) {
            imageBytes = Base64.getDecoder().decode(productRequest.getImage());
            // Crea un MultipartFile a partir del array de bytes
            imagen = new MockMultipartFile(
                    productRequest.getName(),
                    productRequest.getName() + ".jpg",
                    "image/jpeg",
                    imageBytes);
        }

        Product product = new Product();
        product.setName(productRequest.getName());
        if (productRequest.getCategory() != null) {
            product.setCategory(categoryRepository.findById(productRequest.getCategory()).orElse(null));
        }
        if (productRequest.getImage() != null && imagen != null) {
            product.setImagen(s3Service.uploadFile(imagen));
        }
        product.setStock(productRequest.getStock());
        product.setPrice(productRequest.getPrice());
        product.setIva(ivaRepository.findById(2L).orElse(null));
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<Boolean> activation(@PathVariable Long id) {
        return ResponseEntity.ok(productService.activation(id));
    }

    @PutMapping("/iva")
    public ResponseEntity<List<Product>> updateIva(@RequestBody() Long iva) {
        Iva ivaEntity = ivaRepository.findById(iva).orElse(null);
        return ResponseEntity.ok(productService.changeAllIva(ivaEntity));
    }
}
