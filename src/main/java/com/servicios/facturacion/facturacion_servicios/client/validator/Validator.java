package com.servicios.facturacion.facturacion_servicios.client.validator;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }
        
        int[] coefficients = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int province = Integer.parseInt(cedula.substring(0, 2));
        int verifierDigit = Integer.parseInt(cedula.substring(9, 10));
        int sum = 0;

        if (province < 1 || province > 24) {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            int digit = Integer.parseInt(cedula.substring(i, i + 1));
            int product = digit * coefficients[i];
            sum += product > 9 ? product - 9 : product;
        }

        int computedVerifierDigit = (10 - (sum % 10)) % 10;
        System.out.println(computedVerifierDigit);
        return verifierDigit == computedVerifierDigit;
    }

    private static final Pattern PASAPORTE_PATTERN = Pattern.compile("^[A-Z]{1}[0-9]{7}$");

    public static boolean isValidPasaporte(String pasaporte) {
        return PASAPORTE_PATTERN.matcher(pasaporte).matches();
    }
}
