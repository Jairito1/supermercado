package com.pollos.supermercado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pollos.supermercado.dto.request.ProductoRequestDTO;
import com.pollos.supermercado.dto.response.MessageResponseDTO;
import com.pollos.supermercado.dto.response.ProductoResponseDTO;
import com.pollos.supermercado.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll(
            @RequestParam(required = false, defaultValue = "false") Boolean incluirInactivos) {
        return ResponseEntity.ok(productoService.findAll(incluirInactivos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProductoResponseDTO> changeStatus(@PathVariable Long id, @RequestParam boolean activo) {
        return ResponseEntity.ok(productoService.toggleStatus(id, activo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
        productoService.softDelete(id);
        return ResponseEntity.ok(new MessageResponseDTO("Producto desactivado correctamente"));
    }
}
