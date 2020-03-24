package com.fresno.puntoventaapi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Saldo;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long>{
	
	@Query("SELECT s FROM Saldo s WHERE s.fechaEliminacion IS NULL")
	List<Saldo> findByNoEliminados();
	
	@Query("SELECT s FROM Saldo s WHERE DATE(s.fechaCreacion) = :fechaCreacion AND s.fechaEliminacion IS NULL")
	Saldo findByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);
}
