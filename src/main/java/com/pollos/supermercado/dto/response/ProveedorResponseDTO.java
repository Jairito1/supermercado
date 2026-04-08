package com.pollos.supermercado.dto.response;

import java.util.List;

public record ProveedorResponseDTO(
        Long id,
        String nombre,
        String nit,
        String telefono,
        String correo,
        String direccion,
        List<ProductoSimpleResponseDTO> productos
) {}
