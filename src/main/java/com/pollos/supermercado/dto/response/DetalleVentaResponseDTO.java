package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;

public record DetalleVentaResponseDTO(
        Long id,
        Long productoId,
        String productoNombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotalLinea
) {}
