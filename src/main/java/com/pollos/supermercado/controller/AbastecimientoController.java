package com.pollos.supermercado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pollos.supermercado.dto.request.EntradaAlmacenRequestDTO;
import com.pollos.supermercado.dto.response.ProductoResponseDTO;
import com.pollos.supermercado.service.AbastecimientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/abastecimiento")
@RequiredArgsConstructor
public class AbastecimientoController {

    private final AbastecimientoService abastecimientoService;

    @PostMapping("/entrada-almacen")
    public ResponseEntity<ProductoResponseDTO> registrarEntrada(@Valid @RequestBody EntradaAlmacenRequestDTO request) {
        return ResponseEntity.ok(abastecimientoService.registrarEntrada(request));
    }
}
