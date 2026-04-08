package com.pollos.supermercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pollos.supermercado.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByNit(String nit);

    boolean existsByNitAndIdNot(String nit, Long id);

    @EntityGraph(attributePaths = {"productos"})
    Optional<Proveedor> findById(Long id);
}
