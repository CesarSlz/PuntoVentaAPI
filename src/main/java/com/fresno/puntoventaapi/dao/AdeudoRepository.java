package com.fresno.puntoventaapi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Adeudo;
import com.fresno.puntoventaapi.model.Compra;

@Repository
public interface AdeudoRepository extends JpaRepository<Adeudo, Long>{
	
	@Query("SELECT a FROM Adeudo a WHERE a.fechaEliminacion IS NULL")
	List<Adeudo> findByNoEliminados();
	
	@Query("SELECT a FROM Adeudo a WHERE a.estatus = :estatus AND a.fechaEliminacion IS NULL")
	List<Adeudo> findByEstatus(@Param("estatus") Boolean estatus);
	
	@Query("SELECT a FROM Adeudo a WHERE DATE(a.fechaCreacion) = :fechaCreacion AND a.fechaEliminacion IS NULL")
	List<Adeudo> findByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);
	
	Adeudo findByCompra(Compra compra);
}
