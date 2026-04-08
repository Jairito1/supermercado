package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        String codigoBarras,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        Integer stock,
        Boolean activo,
        Long categoriaId,
        String categoriaNombre,
        List<ProveedorSimpleResponseDTO> proveedores
) {}
