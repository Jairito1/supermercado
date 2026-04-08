package com.pollos.supermercado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pollos.supermercado.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);

    List<Producto> findByActivoTrue();

    @EntityGraph(attributePaths = {"categoria", "proveedores"})
    Optional<Producto> findById(Long id);
}
