package com.pollos.supermercado.dto.response;

import com.pollos.supermercado.entity.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorSimpleResponseDTO {

    private Long id;
    private String nombre;
    private String nit;

    public static ProveedorSimpleResponseDTO of(Proveedor p) {
        return new ProveedorSimpleResponseDTO(p.getId(), p.getNombre(), p.getNit());
    }
}
