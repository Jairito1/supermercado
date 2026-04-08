package com.pollos.supermercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pollos.supermercado.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @EntityGraph(attributePaths = {"empleado", "detalles", "detalles.producto"})
    Optional<Venta> findById(Long id);
}
