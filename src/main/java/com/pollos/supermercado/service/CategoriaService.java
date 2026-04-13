package com.pollos.supermercado.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.CategoriaRequestDTO;
import com.pollos.supermercado.dto.response.CategoriaResponseDTO;
import com.pollos.supermercado.dto.response.ProductoSimpleResponseDTO;
import com.pollos.supermercado.entity.Categoria;
import com.pollos.supermercado.exception.DuplicateResourceException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO create(CategoriaRequestDTO request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        return toResponse(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la categoría con id: " + id));
        return toResponse(categoria);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la categoría con id: " + id));

        if (categoriaRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        return toResponse(categoriaRepository.save(categoria));
    }

    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la categoría con id: " + id));
        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO toResponse(Categoria c) {
        return new CategoriaResponseDTO(
                c.getId(), c.getNombre(), c.getDescripcion(),
                c.getProductos().stream().map(ProductoSimpleResponseDTO::fromEntity).toList()
        );
    }
}
