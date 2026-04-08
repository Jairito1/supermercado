package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VentaResponseDTO(
        Long id,
        LocalDateTime fechaVenta,
        Long empleadoId,
        String empleadoNombre,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total,
        List<DetalleVentaResponseDTO> detalles
) {}
