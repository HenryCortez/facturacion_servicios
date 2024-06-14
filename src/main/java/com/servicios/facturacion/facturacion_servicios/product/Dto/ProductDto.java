package com.servicios.facturacion.facturacion_servicios.product.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
     String name;
     Long category;
     String image;
     int stock;
     float price;


}
