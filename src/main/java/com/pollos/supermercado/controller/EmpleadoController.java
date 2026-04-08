package com.pollos.supermercado.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pollos.supermercado.dto.request.EmpleadoRequestDTO;
import com.pollos.supermercado.dto.response.EmpleadoResponseDTO;
import com.pollos.supermercado.dto.response.MessageResponseDTO;
import com.pollos.supermercado.enums.CargoEmpleado;
import com.pollos.supermercado.service.EmpleadoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> create(@Valid @RequestBody EmpleadoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.ok(new MessageResponseDTO("Empleado eliminado correctamente"));
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarPorFiltro(
            @RequestParam(required = false) CargoEmpleado cargo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        return ResponseEntity.ok(empleadoService.buscarPorCargoOCuandoIngreso(cargo, fechaInicio, fechaFin));
    }
}
