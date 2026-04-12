package com.pollos.supermercado.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.EntradaAlmacenRequestDTO;
import com.pollos.supermercado.dto.response.ProductoResponseDTO;
import com.pollos.supermercado.dto.response.ProveedorSimpleResponseDTO;
import com.pollos.supermercado.entity.Producto;
import com.pollos.supermercado.entity.Proveedor;
import com.pollos.supermercado.exception.BusinessRuleException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.ProductoRepository;
import com.pollos.supermercado.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbastecimientoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    @Transactional
    public ProductoResponseDTO registrarEntrada(EntradaAlmacenRequestDTO request) {
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + request.getProductoId()));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + request.getProveedorId()));

        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new BusinessRuleException("No se puede abastecer un producto inactivo");
        }

        producto.setStock(producto.getStock() + request.getCantidad());
        producto.getProveedores().add(proveedor);
        proveedor.getProductos().add(producto);

        Producto saved = productoRepository.save(producto);
        proveedorRepository.save(proveedor);

        return new ProductoResponseDTO(
                saved.getId(), saved.getNombre(), saved.getDescripcion(), saved.getCodigoBarras(),
                saved.getPrecioCompra(), saved.getPrecioVenta(), saved.getStock(), saved.getActivo(),
                saved.getCategoria().getId(), saved.getCategoria().getNombre(),
                saved.getProveedores().stream().map(ProveedorSimpleResponseDTO::of).toList()
        );
    }
}
