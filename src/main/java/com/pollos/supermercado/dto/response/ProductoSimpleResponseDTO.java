package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;

import com.pollos.supermercado.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSimpleResponseDTO {

    private Long id;
    private String nombre;
    private String codigoBarras;
    private BigDecimal precioVenta;
    private Integer stock;
    private Boolean activo;

    public static ProductoSimpleResponseDTO fromEntity(Producto p) {
        return new ProductoSimpleResponseDTO(
                p.getId(), p.getNombre(), p.getCodigoBarras(),
                p.getPrecioVenta(), p.getStock(), p.getActivo()
        );
    }
}
