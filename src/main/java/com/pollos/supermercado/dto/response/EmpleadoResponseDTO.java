package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pollos.supermercado.enums.CargoEmpleado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO {

    private Long id;
    private String cedula;
    private String nombre;
    private CargoEmpleado cargo;
    private LocalDate fechaIngreso;
    private BigDecimal salario;
}
