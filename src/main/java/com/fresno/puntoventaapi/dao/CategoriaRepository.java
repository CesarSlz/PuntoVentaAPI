package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	@Query("SELECT c FROM Categoria c WHERE c.fechaEliminacion IS NULL")
	List<Categoria> findByNoEliminados();
	
	@Query("SELECT c FROM Categoria c WHERE c.nombre LIKE %:nombre% AND c.fechaEliminacion IS NULL")
	List<Categoria> findByNombre(@Param("nombre") String nombre);
}
