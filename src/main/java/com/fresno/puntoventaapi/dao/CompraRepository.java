package com.fresno.puntoventaapi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>{
	
	@Query("SELECT c FROM Compra c WHERE c.fechaEliminacion IS NULL")
	List<Compra> findByNoEliminados();
	
	@Query("SELECT c FROM Compra c WHERE DATE(c.fechaCreacion) = :fechaCreacion AND c.fechaEliminacion IS NUL")
	List<Compra> findByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);
	
	@Query("SELECT COALESCE(SUM("
			+ "CASE WHEN c.efectivo >= c.total "
			+ "AND DATE(c.fechaCreacion) = CURDATE() "
			+ "AND c.fechaEliminacion IS NULL "
			+ "THEN total "
			+ "WHEN c.efectivo < c.total "
			+ "AND DATE(c.fechaCreacion) = CURDATE() "
			+ "AND c.fechaEliminacion IS NULL "
			+ "THEN efectivo "
			+ "ELSE 0 "
			+ "END), 0) from Compra c")
	Double getSumatoriaComprasByFecha();
}
