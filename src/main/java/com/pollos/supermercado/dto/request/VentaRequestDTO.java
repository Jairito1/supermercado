package com.pollos.supermercado.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VentaRequestDTO(

        @NotNull(message = "El empleado es obligatorio")
        Long empleadoId,

        @NotEmpty(message = "La venta debe tener al menos un detalle")
        @Valid
        List<DetalleVentaRequestDTO> detalles
) {}
