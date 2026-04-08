package com.pollos.supermercado.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EntradaAlmacenRequestDTO(

        @NotNull(message = "El id del producto es obligatorio")
        Long productoId,

        @NotNull(message = "El id del proveedor es obligatorio")
        Long proveedorId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer cantidad
) {}
