package com.servicios.facturacion.facturacion_servicios.client;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"dni"})})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String dni;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column( length = 20, nullable = false)
    private String lastName;

    @Column(length = 10)
    private String secondName;

    @Column(length = 10)
    private String secondLastName;

    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private boolean status = true;

    
}
