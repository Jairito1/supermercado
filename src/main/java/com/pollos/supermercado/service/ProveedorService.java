package com.pollos.supermercado.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.ProveedorRequestDTO;
import com.pollos.supermercado.dto.response.ProductoSimpleResponseDTO;
import com.pollos.supermercado.dto.response.ProveedorResponseDTO;
import com.pollos.supermercado.entity.Proveedor;
import com.pollos.supermercado.exception.DuplicateResourceException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Transactional
    public ProveedorResponseDTO create(ProveedorRequestDTO request) {
        if (proveedorRepository.existsByNit(request.getNit())) {
            throw new DuplicateResourceException("El NIT ya está registrado en el sistema");
        }

        Proveedor proveedor = new Proveedor();
        mapFields(request, proveedor);
        return toResponse(proveedorRepository.save(proveedor));
    }

    public List<ProveedorResponseDTO> findAll() {
        return proveedorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProveedorResponseDTO findById(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el proveedor con id: " + id));
        return toResponse(proveedor);
    }

    @Transactional
    public ProveedorResponseDTO update(Long id, ProveedorRequestDTO request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + id));

        if (proveedorRepository.existsByNitAndIdNot(request.getNit(), id)) {
            throw new DuplicateResourceException("Ese NIT ya pertenece a otro proveedor");
        }

        mapFields(request, proveedor);
        return toResponse(proveedorRepository.save(proveedor));
    }

    public void delete(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el proveedor con id: " + id));
        proveedorRepository.delete(proveedor);
    }

    private void mapFields(ProveedorRequestDTO request, Proveedor proveedor) {
        proveedor.setNombre(request.getNombre());
        proveedor.setNit(request.getNit());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setCorreo(request.getCorreo());
        proveedor.setDireccion(request.getDireccion());
    }

    private ProveedorResponseDTO toResponse(Proveedor p) {
        return new ProveedorResponseDTO(
                p.getId(), p.getNombre(), p.getNit(), p.getTelefono(),
                p.getCorreo(), p.getDireccion(),
                p.getProductos().stream().map(ProductoSimpleResponseDTO::fromEntity).toList()
        );
    }
}
