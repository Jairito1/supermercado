package com.pollos.supermercado.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pollos.supermercado.dto.request.VentaRequestDTO;
import com.pollos.supermercado.dto.response.VentaResponseDTO;
import com.pollos.supermercado.service.VentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> procesarVenta(@Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.procesarVenta(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }
}
