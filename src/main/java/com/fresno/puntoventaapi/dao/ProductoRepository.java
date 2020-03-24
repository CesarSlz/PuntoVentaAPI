package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
	@Query("SELECT p FROM Producto p WHERE p.fechaEliminacion IS NULL")
	List<Producto> findByNoEliminados();
	
	@Query("SELECT p FROM Producto p WHERE p.codigoBarra = :codigoBarra AND p.fechaEliminacion IS NULL")
	Producto findByCodigoBarra(@Param("codigoBarra") String codigoBarra);
	
	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre% AND p.fechaEliminacion IS NULL")
	List<Producto> findByNombre(@Param("nombre") String nombre);
}
