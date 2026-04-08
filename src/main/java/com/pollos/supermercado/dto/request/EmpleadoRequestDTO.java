package com.pollos.supermercado.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pollos.supermercado.enums.CargoEmpleado;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record EmpleadoRequestDTO(

        @NotBlank(message = "La cédula es obligatoria")
        @Size(max = 20, message = "La cédula no puede superar 20 caracteres")
        String cedula,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
        String nombre,

        @NotNull(message = "El cargo es obligatorio")
        CargoEmpleado cargo,

        @NotNull(message = "La fecha de ingreso es obligatoria")
        @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
        LocalDate fechaIngreso,

        @NotNull(message = "El salario es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor a 0")
        BigDecimal salario
) {}
