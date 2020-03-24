package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

	@Query("SELECT p FROM Proveedor p WHERE p.fechaEliminacion IS NULL")
	List<Proveedor> findByNoEliminados();
	
	@Query("SELECT p FROM Proveedor p WHERE p.nombre LIKE %:nombre% AND p.fechaEliminacion IS NULL")
	List<Proveedor> findByNombre(@Param("nombre") String nombre);
}
