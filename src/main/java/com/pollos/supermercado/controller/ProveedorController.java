package com.pollos.supermercado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pollos.supermercado.dto.request.ProveedorRequestDTO;
import com.pollos.supermercado.dto.response.MessageResponseDTO;
import com.pollos.supermercado.dto.response.ProveedorResponseDTO;
import com.pollos.supermercado.service.ProveedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> create(@Valid @RequestBody ProveedorRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> findAll() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDTO request) {
        return ResponseEntity.ok(proveedorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.ok(new MessageResponseDTO("Proveedor eliminado correctamente"));
    }
}
