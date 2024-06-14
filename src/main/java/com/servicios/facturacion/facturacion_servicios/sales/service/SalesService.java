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
import java.time.LocalDate;
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

    public List<Sale> getAllSales() {
        return salesRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return salesRepository.findById(id);
    }

    public List<Sale> getSalesByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!client.isStatus()) {
            throw new RuntimeException("Client no está activo");
        }

        return salesRepository.findByClientId(clientId);
    }

    public List<Sale> getSalesByDate(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return salesRepository.findByDateSaleBetween(startOfDay, endOfDay);
    }

    public List<Sale> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime startOfDay = startDate;
        LocalDateTime endOfDay = endDate.plusDays(1).minusNanos(1);

        return salesRepository.findByDateSaleBetween(startOfDay, endOfDay);
    }

    public Sale updateSaleClient(Long saleId, Long newClientId) {
        Sale sale = salesRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        Client newClient = clientRepository.findById(newClientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!newClient.isStatus()) {
            throw new RuntimeException("Cliente no está activo");
        }

        sale.setClient(newClient);
        return salesRepository.save(sale);
    }

    public Sale createSale(SaleRequestDTO saleRequestDTO) {

        Client client;
        if (saleRequestDTO.getClientId() == 0) {
            client = clientRepository.findById(0L)
                    .orElseThrow(() -> new RuntimeException("Consumidor final no encontrado"));
        } else {
            client = clientRepository.findById(saleRequestDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            if (!client.isStatus()) {
                throw new RuntimeException("Cliente no está activo");
            }
        }

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setDateSale(LocalDateTime.now());
        sale.setStatus(true);

        List<SalesDetails> saleDetailsList = SalesServiceHelper.createSaleDetails(sale, saleRequestDTO.getSaleDetails(),
                productRepository);
        float[] totals = SalesServiceHelper.calculateTotals(saleDetailsList);
        sale.setSubtotal(totals[0]);
        sale.setTotal(totals[1]);

        salesRepository.save(sale);

        saleDetailsList.forEach(detail -> detail.setSales(sale));
        salesDetailsRepository.saveAll(saleDetailsList);

        sale.setSalesDetails(saleDetailsList);

        return sale;
    }

}
