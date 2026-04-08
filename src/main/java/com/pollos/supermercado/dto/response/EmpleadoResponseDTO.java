package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pollos.supermercado.enums.CargoEmpleado;

public record EmpleadoResponseDTO(
        Long id,
        String cedula,
        String nombre,
        CargoEmpleado cargo,
        LocalDate fechaIngreso,
        BigDecimal salario
) {}
