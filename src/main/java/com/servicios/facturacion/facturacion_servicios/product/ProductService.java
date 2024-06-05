package com.servicios.facturacion.facturacion_servicios.product;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicios.facturacion.facturacion_servicios.product.iva.Iva;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product productToUpdate = productRepository.findById(id).orElse(null);
        
        if (productToUpdate == null) {
            return null;
        }
        if (product.getName() != null) {
            productToUpdate.setName(product.getName());
        }
        if (product.getCategory() != null) {
            productToUpdate.setCategory(product.getCategory());
        }
        if (product.getImagen() != null) {
            productToUpdate.setImagen(product.getImagen());
        }
        if (product.getStock() != 0) {
            productToUpdate.setStock(product.getStock());
        }
        if (product.getPrice() != 0) {
            productToUpdate.setPrice(product.getPrice());
        }
        return productRepository.save(productToUpdate);
        
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    public boolean activation(Long id) {
        Product productToDelete = productRepository.findById(id).orElse(null);
        if(productToDelete != null){
            if(productToDelete.isActive()){
                productToDelete.setActive(false);
            }else{
                productToDelete.setActive(true);
            }
            productRepository.save(productToDelete);
            return true;
        }
        return false;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> changeAllIva( Iva idIva) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            product.setIva(idIva);
        }
        return productRepository.saveAll(products);

    }

   
    
}
