package com.pollos.supermercado.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.EmpleadoRequestDTO;
import com.pollos.supermercado.dto.response.EmpleadoResponseDTO;
import com.pollos.supermercado.entity.Empleado;
import com.pollos.supermercado.enums.CargoEmpleado;
import com.pollos.supermercado.exception.DuplicateResourceException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Transactional
    public EmpleadoResponseDTO create(EmpleadoRequestDTO request) {
        if (empleadoRepository.existsByCedula(request.cedula())) {
            throw new DuplicateResourceException("Ya existe un empleado con esa cédula");
        }

        Empleado empleado = new Empleado();
        mapFields(request, empleado);

        return toResponse(empleadoRepository.save(empleado));
    }

    public List<EmpleadoResponseDTO> findAll() {
        return empleadoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EmpleadoResponseDTO findById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con id: " + id));
        return toResponse(empleado);
    }

    @Transactional
    public EmpleadoResponseDTO update(Long id, EmpleadoRequestDTO request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));

        if (empleadoRepository.existsByCedulaAndIdNot(request.cedula(), id)) {
            throw new DuplicateResourceException("Ya existe un empleado con esa cédula");
        }

        mapFields(request, empleado);
        return toResponse(empleadoRepository.save(empleado));
    }

    public void delete(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        empleadoRepository.delete(empleado);
    }

    public List<EmpleadoResponseDTO> buscarPorCargoOCuandoIngreso(CargoEmpleado cargo, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Empleado> empleados;

        if (cargo != null) {
            empleados = empleadoRepository.findByCargo(cargo);
        } else if (fechaInicio != null && fechaFin != null) {
            empleados = empleadoRepository.findByFechaIngresoBetween(fechaInicio, fechaFin);
        } else {
            empleados = empleadoRepository.findAll();
        }

        return empleados.stream().map(this::toResponse).toList();
    }

    private void mapFields(EmpleadoRequestDTO dto, Empleado empleado) {
        empleado.setCedula(dto.cedula());
        empleado.setNombre(dto.nombre());
        empleado.setCargo(dto.cargo());
        empleado.setFechaIngreso(dto.fechaIngreso());
        empleado.setSalario(dto.salario());
    }

    private EmpleadoResponseDTO toResponse(Empleado emp) {
        return new EmpleadoResponseDTO(
                emp.getId(), emp.getCedula(), emp.getNombre(),
                emp.getCargo(), emp.getFechaIngreso(), emp.getSalario()
        );
    }
}
