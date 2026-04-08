package com.pollos.supermercado.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pollos.supermercado.entity.Empleado;
import com.pollos.supermercado.enums.CargoEmpleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByCedula(String cedula);

    boolean existsByCedulaAndIdNot(String cedula, Long id);

    List<Empleado> findByCargo(CargoEmpleado cargo);

    List<Empleado> findByFechaIngresoBetween(LocalDate start, LocalDate end);
}
