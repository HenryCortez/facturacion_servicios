package com.servicios.facturacion.facturacion_servicios.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.servicios.facturacion.facturacion_servicios.product.category.Category;
import com.servicios.facturacion.facturacion_servicios.product.iva.Iva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

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
    @ManyToOne()
    @JoinColumn(name = "category_Id", nullable = false)
    @JsonBackReference
    private Category category;
    @Column(name = "imagen")
    private String imagen;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private float price;
    @ManyToOne()
    @JoinColumn(name = "iva_Id", columnDefinition = "bigint default 2")
    private Iva iva;
    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private boolean active = true;

}
