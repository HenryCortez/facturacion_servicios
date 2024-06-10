package com.servicios.facturacion.facturacion_servicios.sales.service;

import com.servicios.facturacion.facturacion_servicios.client.Client;
import com.servicios.facturacion.facturacion_servicios.client.ClientRepository;
import com.servicios.facturacion.facturacion_servicios.product.Product;
import com.servicios.facturacion.facturacion_servicios.product.ProductRepository;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleDTO;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleDetailDTO;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;
import com.servicios.facturacion.facturacion_servicios.sales.model.SalesDetails;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesDetailsRepository;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesRepository;

import java.util.List;
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

    public Sale createSale(SaleDTO saleDTO) {
        // Find client
        Client client = clientRepository.findById(saleDTO.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));

        // Create sale
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setDateSale(saleDTO.getDateSale());
        sale.setSubtotal(saleDTO.getSubtotal());
        sale.setTotal(saleDTO.getTotal());
        sale.setStatus(true);

        // Save sale to get the ID
        salesRepository.save(sale);

        // Create sale details
        List<SalesDetails> saleDetailsList = new ArrayList<>();
        for (SaleDetailDTO detailDTO : saleDTO.getSaleDetails()) {
            Product product = productRepository.findById(detailDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            SalesDetails saleDetail = new SalesDetails();
            saleDetail.setSales(sale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(detailDTO.getQuantity());
            saleDetail.setPrice(detailDTO.getPrice());
            saleDetail.setSubtotal(detailDTO.getSubtotal());

            saleDetailsList.add(saleDetail);
        }

        // Save all sale details
        salesDetailsRepository.saveAll(saleDetailsList);

        // Set the sale details to the sale object
        sale.setSalesDetails(saleDetailsList);

        return sale;
    }
}
