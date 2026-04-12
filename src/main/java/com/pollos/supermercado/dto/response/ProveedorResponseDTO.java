package com.pollos.supermercado.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseDTO {

    private Long id;
    private String nombre;
    private String nit;
    private String telefono;
    private String correo;
    private String direccion;
    private List<ProductoSimpleResponseDTO> productos;
}
