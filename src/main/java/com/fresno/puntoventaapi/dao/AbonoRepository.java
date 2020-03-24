package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Abono;
import com.fresno.puntoventaapi.model.Adeudo;

@Repository
public interface AbonoRepository extends JpaRepository<Abono, Long>{

	@Query("SELECT a FROM Abono a WHERE a.adeudo = :adeudo AND a.fechaEliminacion IS NULL")
	List<Abono> findAllByAdeudo(@Param("adeudo") Adeudo adeudo);
	
	@Query("SELECT "
			+ "COALESCE(SUM(a.monto), 0) "
			+ "FROM Abono a "
			+ "WHERE DATE(a.fechaCreacion) = CURDATE() "
			+ "AND a.fechaEliminacion IS NULL")
	Double getSumatoriaAbonosByFecha();
	
	@Query("SELECT "
			+ "COALESCE(SUM(a.monto), 0) "
			+ "FROM Abono a "
			+ "WHERE a.adeudo = :adeudo "
			+ "AND a.fechaEliminacion IS NULL")
	Double getSumatoriaAbonosByAdeudo(@Param("adeudo") Adeudo adeudo);
}
