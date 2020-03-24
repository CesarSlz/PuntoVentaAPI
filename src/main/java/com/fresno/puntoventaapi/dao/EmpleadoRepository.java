package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{

	@Query("SELECT e FROM Empleado e WHERE e.fechaEliminacion IS NULL")
	List<Empleado> findByNoEliminados();
	
	@Query("SELECT e FROM Empleado e WHERE CONCAT(e.nombre, ' ', e.apellidoPaterno, ' ', e.apellidoMaterno) "
			+ "LIKE %:nombreCompleto% AND e.fechaEliminacion IS NULL")
	List<Empleado> findByNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
	
	@Query("SELECT e FROM Empleado e "
			+ "WHERE e.telefono = :telefono "
			+ "AND e.fechaEliminacion IS NULL")
	Empleado findByTelefono(@Param("telefono") String telefono);
}
