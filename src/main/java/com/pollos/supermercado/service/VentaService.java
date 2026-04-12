package com.pollos.supermercado.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollos.supermercado.dto.request.DetalleVentaRequestDTO;
import com.pollos.supermercado.dto.request.VentaRequestDTO;
import com.pollos.supermercado.dto.response.DetalleVentaResponseDTO;
import com.pollos.supermercado.dto.response.VentaResponseDTO;
import com.pollos.supermercado.entity.DetalleVenta;
import com.pollos.supermercado.entity.Empleado;
import com.pollos.supermercado.entity.Producto;
import com.pollos.supermercado.entity.Venta;
import com.pollos.supermercado.exception.BusinessRuleException;
import com.pollos.supermercado.exception.InsufficientStockException;
import com.pollos.supermercado.exception.ResourceNotFoundException;
import com.pollos.supermercado.repository.EmpleadoRepository;
import com.pollos.supermercado.repository.ProductoRepository;
import com.pollos.supermercado.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private static final BigDecimal PORCENTAJE_IVA = new BigDecimal("0.19");

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional
    public VentaResponseDTO procesarVenta(VentaRequestDTO request) {
        Empleado empleado = empleadoRepository.findById(request.empleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con id: " + request.empleadoId()));

        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEmpleado(empleado);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVentaRequestDTO detalleReq : request.detalles()) {
            Producto producto = productoRepository.findById(detalleReq.productoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + detalleReq.productoId()));

            if (!Boolean.TRUE.equals(producto.getActivo())) {
                throw new BusinessRuleException("El producto " + producto.getNombre() + " está inactivo y no puede venderse");
            }

            if (producto.getStock() < detalleReq.cantidad()) {
                throw new InsufficientStockException("No hay suficiente stock de: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalleReq.cantidad());
            productoRepository.save(producto);

            BigDecimal subtotalLinea = producto.getPrecioVenta()
                    .multiply(BigDecimal.valueOf(detalleReq.cantidad()))
                    .setScale(2, RoundingMode.HALF_UP);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleReq.cantidad());
            detalle.setPrecioUnitario(producto.getPrecioVenta());
            detalle.setSubtotalLinea(subtotalLinea);

            venta.getDetalles().add(detalle);
            subtotal = subtotal.add(subtotalLinea);
        }

        BigDecimal iva = subtotal.multiply(PORCENTAJE_IVA).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        venta.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        venta.setIva(iva);
        venta.setTotal(total);

        Venta saved = ventaRepository.save(venta);
        return findById(saved.getId());
    }

    public VentaResponseDTO findById(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la venta con id: " + id));
        return toResponse(venta);
    }

    private VentaResponseDTO toResponse(Venta venta) {
        return new VentaResponseDTO(
                venta.getId(), venta.getFechaVenta(),
                venta.getEmpleado().getId(), venta.getEmpleado().getNombre(),
                venta.getSubtotal(), venta.getIva(), venta.getTotal(),
                venta.getDetalles().stream().map(this::toDetalleResponse).toList()
        );
    }

    private DetalleVentaResponseDTO toDetalleResponse(DetalleVenta d) {
        return new DetalleVentaResponseDTO(
                d.getId(), d.getProducto().getId(), d.getProducto().getNombre(),
                d.getCantidad(), d.getPrecioUnitario(), d.getSubtotalLinea()
        );
    }
}
