package com.servicios.facturacion.facturacion_servicios.sales.service;

import com.servicios.facturacion.facturacion_servicios.client.Client;
import com.servicios.facturacion.facturacion_servicios.client.ClientRepository;
import com.servicios.facturacion.facturacion_servicios.product.ProductRepository;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleRequestDTO;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;
import com.servicios.facturacion.facturacion_servicios.sales.model.SalesDetails;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesDetailsRepository;
import com.servicios.facturacion.facturacion_servicios.sales.repository.SalesRepository;
import java.util.List;
import java.time.LocalDateTime;
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
        Client client = clientRepository.findById(saleRequestDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        if (!client.isStatus()) {
            throw new RuntimeException("Client is not active");
        }
        // Create sale
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setDateSale(LocalDateTime.now());
        sale.setStatus(true);

        // Create and calculate sale details
        List<SalesDetails> saleDetailsList = SalesServiceHelper.createSaleDetails(sale, saleRequestDTO.getSaleDetails(),
                productRepository);
        float[] totals = SalesServiceHelper.calculateTotals(saleDetailsList);
        sale.setSubtotal(totals[0]);
        sale.setTotal(totals[1]);

        // Save sale to get the ID
        salesRepository.save(sale);

        // Save all sale details
        saleDetailsList.forEach(detail -> detail.setSales(sale));
        salesDetailsRepository.saveAll(saleDetailsList);

        // Set the sale details to the sale object
        sale.setSalesDetails(saleDetailsList);

        return sale;
    }

}
