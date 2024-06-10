package com.servicios.facturacion.facturacion_servicios.sales.service;

import com.servicios.facturacion.facturacion_servicios.client.Client;
import com.servicios.facturacion.facturacion_servicios.client.ClientRepository;
import com.servicios.facturacion.facturacion_servicios.product.Product;
import com.servicios.facturacion.facturacion_servicios.product.ProductRepository;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleDetailDTO;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleRequestDTO;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;
import com.servicios.facturacion.facturacion_servicios.sales.model.SalesDetails;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesDetailsRepository;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesRepository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
    @Autowired
    private SalesRepository salesRepository;
    
    @Autowired
    private SalesDetailsRepository salesDetailsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Sale> getAllActiveSales() {
        return salesRepository.findByStatusTrue();
    }

    public Optional<Sale> getSaleById(Long id) {
        return salesRepository.findByIdAndStatusTrue(id);
    }

    public Sale createSale(SaleRequestDTO saleRequestDTO) {
        // Find client
        Client client = clientRepository.findById(saleRequestDTO.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));

        // Create sale
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setDateSale(LocalDateTime.now());
        sale.setStatus(true);

        // Calculate totals
        float subtotal = 0;
        float total = 0;
        List<SalesDetails> saleDetailsList = new ArrayList<>();

        for (SaleDetailDTO detailDTO : saleRequestDTO.getSaleDetails()) {
            Product product = productRepository.findById(detailDTO.getProductId()).orElseThrow(() -> new RuntimeException("El producto no existe"));

            if (product.getStock() < detailDTO.getQuantity()) {
                throw new RuntimeException("No hay stock para el producto: " + product.getName());
            }

            // Calculate subtotal and total
            float productSubtotal = detailDTO.getPrice() * detailDTO.getQuantity();
            float productTotal = productSubtotal * (1 + (product.getIva().getValue() / 100));

            // Create sale detail
            SalesDetails saleDetail = new SalesDetails();
            saleDetail.setSales(sale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(detailDTO.getQuantity());
            saleDetail.setPrice(detailDTO.getPrice());
            saleDetail.setSubtotal(productSubtotal);

            saleDetailsList.add(saleDetail);

            subtotal += productSubtotal;
            total += productTotal;

            // Update product stock
            product.setStock(product.getStock() - detailDTO.getQuantity());
            productRepository.save(product);
        }

        sale.setSubtotal(subtotal);
        sale.setTotal(total);

        // Save sale to get the ID
        salesRepository.save(sale);

        // Save all sale details
        for (SalesDetails saleDetail : saleDetailsList) {
            saleDetail.setSales(sale);
        }
        salesDetailsRepository.saveAll(saleDetailsList);

        // Set the sale details to the sale object
        sale.setSalesDetails(saleDetailsList);

        return sale;
    }

}
