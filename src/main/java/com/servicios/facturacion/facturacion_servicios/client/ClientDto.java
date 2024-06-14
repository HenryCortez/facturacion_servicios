package com.servicios.facturacion.facturacion_servicios.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    String firstName;
    String lastName;
    String secondName;
    String secondLastName;
    String address;
    String phone;
    String email;
}
