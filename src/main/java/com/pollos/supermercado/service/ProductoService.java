package com.pollos.supermercado.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.ProductoRequestDTO;
import com.pollos.supermercado.dto.response.ProductoResponseDTO;
import com.pollos.supermercado.dto.response.ProveedorSimpleResponseDTO;
import com.pollos.supermercado.entity.Categoria;
import com.pollos.supermercado.entity.Producto;
import com.pollos.supermercado.exception.BusinessRuleException;
import com.pollos.supermercado.exception.DuplicateResourceException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.CategoriaRepository;
import com.pollos.supermercado.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ProductoResponseDTO create(ProductoRequestDTO request) {
        if (productoRepository.existsByCodigoBarras(request.getCodigoBarras())) {
            throw new DuplicateResourceException("El código de barras ya está registrado");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getCategoriaId()));

        if (request.getPrecioVenta().compareTo(request.getPrecioCompra()) < 0) {
            throw new BusinessRuleException("El precio de venta no puede ser menor al precio de compra");
        }

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecioCompra(request.getPrecioCompra());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStock(request.getStock());
        producto.setActivo(true);
        producto.setCategoria(categoria);

        return toResponse(productoRepository.save(producto));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll(Boolean incluirInactivos) {
        List<Producto> productos = Boolean.TRUE.equals(incluirInactivos)
                ? productoRepository.findAll()
                : productoRepository.findByActivoTrue();

        return productos.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return toResponse(producto);
    }

    @Transactional
    public ProductoResponseDTO update(Long id, ProductoRequestDTO request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        if (productoRepository.existsByCodigoBarrasAndIdNot(request.getCodigoBarras(), id)) {
            throw new DuplicateResourceException("Ese código de barras ya lo tiene otro producto");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getCategoriaId()));

        if (request.getPrecioVenta().compareTo(request.getPrecioCompra()) < 0) {
            throw new BusinessRuleException("El precio de venta no puede ser menor al precio de compra");
        }

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecioCompra(request.getPrecioCompra());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        Producto saved = productoRepository.save(producto);
        return findById(saved.getId());
    }

    @Transactional
    public ProductoResponseDTO toggleStatus(Long id, boolean activo) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id: " + id));
        producto.setActivo(activo);
        return toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void softDelete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con id: " + id));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoResponseDTO toResponse(Producto p) {
        return new ProductoResponseDTO(
                p.getId(), p.getNombre(), p.getDescripcion(), p.getCodigoBarras(),
                p.getPrecioCompra(), p.getPrecioVenta(), p.getStock(), p.getActivo(),
                p.getCategoria().getId(), p.getCategoria().getNombre(),
                p.getProveedores().stream().map(ProveedorSimpleResponseDTO::of).toList()
        );
    }
}
