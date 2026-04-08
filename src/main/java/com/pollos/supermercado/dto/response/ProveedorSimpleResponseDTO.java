package com.pollos.supermercado.dto.response;

import com.pollos.supermercado.entity.Proveedor;

public record ProveedorSimpleResponseDTO(
        Long id,
        String nombre,
        String nit
) {
    public static ProveedorSimpleResponseDTO of(Proveedor p) {
        return new ProveedorSimpleResponseDTO(p.getId(), p.getNombre(), p.getNit());
    }
}
