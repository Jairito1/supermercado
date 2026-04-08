package com.pollos.supermercado.dto.response;

import java.util.List;

public record CategoriaResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        List<ProductoSimpleResponseDTO> productos
) {}
