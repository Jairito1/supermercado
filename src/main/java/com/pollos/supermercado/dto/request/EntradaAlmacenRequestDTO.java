package com.pollos.supermercado.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntradaAlmacenRequestDTO {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El id del proveedor es obligatorio")
    private Long proveedorId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
}
