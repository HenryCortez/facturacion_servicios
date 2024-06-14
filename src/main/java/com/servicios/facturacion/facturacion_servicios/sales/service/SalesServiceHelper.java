package com.servicios.facturacion.facturacion_servicios.sales.service;

import com.servicios.facturacion.facturacion_servicios.product.Product;
import com.servicios.facturacion.facturacion_servicios.product.ProductRepository;
import com.servicios.facturacion.facturacion_servicios.sales.dto.SaleDetailDTO;
import com.servicios.facturacion.facturacion_servicios.sales.exceptions.CustomBusinessException;
import com.servicios.facturacion.facturacion_servicios.sales.model.Sale;
import com.servicios.facturacion.facturacion_servicios.sales.model.SalesDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalesServiceHelper {

    public static List<SalesDetails> createSaleDetails(Sale sale, List<SaleDetailDTO> saleDetailsDTO,  ProductRepository productRepository) {
        List<SalesDetails> saleDetailsList = new ArrayList<>();
        Map<Long, SaleDetailDTO> aggregatedDetails = aggregateSaleDetails(saleDetailsDTO);

        for (SaleDetailDTO detailDTO : aggregatedDetails.values()) {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (!product.isActive()) {
                throw CustomBusinessException.productInactive(detailDTO.getProductId());
            }
            if (product.getStock() < detailDTO.getQuantity()) {
                throw CustomBusinessException.insufficientStock(detailDTO.getProductId());
            }

            SalesDetails saleDetail = new SalesDetails();
            saleDetail.setSales(sale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(detailDTO.getQuantity());
            saleDetail.setPrice(product.getPrice());
            saleDetail.setSubtotal(product.getPrice() * detailDTO.getQuantity());

            saleDetailsList.add(saleDetail);
            /* product.setStock(product.getStock() - detailDTO.getQuantity());
            productRepository.save(product); */
        }

        return saleDetailsList;
    }

    public static float[] calculateTotals(List<SalesDetails> saleDetailsList) {
        float subtotal = 0;
        float total = 0;
        for (SalesDetails detail : saleDetailsList) {
            subtotal += detail.getSubtotal();
            total += detail.getSubtotal() * (1 + detail.getProduct().getIva().getValue());
        }
        return new float[] { subtotal, total };
    }

    private static Map<Long, SaleDetailDTO> aggregateSaleDetails(List<SaleDetailDTO> saleDetailsDTO) {
        Map<Long, SaleDetailDTO> aggregatedDetails = new HashMap<>();
        for (SaleDetailDTO detail : saleDetailsDTO) {
            aggregatedDetails.merge(detail.getProductId(), detail, (d1, d2) -> {
                d1.setQuantity(d1.getQuantity() + d2.getQuantity());
                return d1;
            });
        }
        return aggregatedDetails;
    }
}