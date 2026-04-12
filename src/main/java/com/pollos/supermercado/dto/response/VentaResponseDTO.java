package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {

    private Long id;
    private LocalDateTime fechaVenta;
    private Long empleadoId;
    private String empleadoNombre;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles;
}
