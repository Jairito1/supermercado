package com.pollos.supermercado.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoBarras;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stock;
    private Boolean activo;
    private Long categoriaId;
    private String categoriaNombre;
    private List<ProveedorSimpleResponseDTO> proveedores;
}
