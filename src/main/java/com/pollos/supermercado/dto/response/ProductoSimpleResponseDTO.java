package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;

import com.pollos.supermercado.entity.Producto;

public record ProductoSimpleResponseDTO(
        Long id,
        String nombre,
        String codigoBarras,
        BigDecimal precioVenta,
        Integer stock,
        Boolean activo
) {
    public static ProductoSimpleResponseDTO fromEntity(Producto p) {
        return new ProductoSimpleResponseDTO(
                p.getId(), p.getNombre(), p.getCodigoBarras(),
                p.getPrecioVenta(), p.getStock(), p.getActivo()
        );
    }
}
