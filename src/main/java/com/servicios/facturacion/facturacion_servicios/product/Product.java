package com.servicios.facturacion.facturacion_servicios.product;

import com.servicios.facturacion.facturacion_servicios.product.category.Category;
import com.servicios.facturacion.facturacion_servicios.product.iva.Iva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_Id", nullable = false)
    private Category category;
    @Lob
    @Column(nullable = false)
    private byte[] imagen;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private float price;
    @ManyToOne(targetEntity = Iva.class)
    @JoinColumn(name = "iva_Id", nullable = true)
    private Iva iva;
    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private boolean active = true;

}
