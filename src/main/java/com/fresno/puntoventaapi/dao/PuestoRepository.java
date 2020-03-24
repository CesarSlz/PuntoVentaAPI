package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Puesto;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Integer>{
	
	@Query("SELECT p FROM Puesto p WHERE p.fechaEliminacion IS NULL")
	List<Puesto> findByNoEliminados();
	
	@Query("SELECT p FROM Puesto p WHERE p.nombre LIKE %:nombre% AND p.fechaEliminacion IS NULL")
	List<Puesto> findByNombre(@Param("nombre") String nombre);

}
