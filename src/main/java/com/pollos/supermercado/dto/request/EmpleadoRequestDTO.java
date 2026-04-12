package com.pollos.supermercado.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pollos.supermercado.enums.CargoEmpleado;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank(message = "La cédula es obligatoria")
    @Size(max = 20, message = "La cédula no puede superar 20 caracteres")
    private String cedula;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @NotNull(message = "El cargo es obligatorio")
    private CargoEmpleado cargo;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor a 0")
    private BigDecimal salario;
}
