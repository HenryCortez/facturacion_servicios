package com.servicios.facturacion.facturacion_servicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.servicios.facturacion.facturacion_servicios"})
public class FacturacionServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturacionServiciosApplication.class, args);
	}

}
