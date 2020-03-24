package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Compra;
import com.fresno.puntoventaapi.model.DetalleCompra;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long>{

	@Query("SELECT d FROM DetalleCompra d WHERE d.compra = :compra AND d.fechaEliminacion IS NULL")
	List<DetalleCompra> findAllByCompra(@Param("compra") Compra compra);
}
