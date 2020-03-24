package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.DetalleVenta;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Venta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>{
	
	@Query("SELECT d FROM DetalleVenta d WHERE d.venta = :venta AND d.fechaEliminacion IS NULL")
	List<DetalleVenta> findAllByVenta(@Param("venta") Venta venta);
	
	@Query("SELECT p FROM DetalleVenta d "
			+ "INNER JOIN d.producto p "
			+ "WHERE p.fechaEliminacion IS NULL "
			+ "AND d.fechaEliminacion IS NULL "
			+ "GROUP BY p.id "
			+ "ORDER BY SUM(d.cantidad) DESC")
	List<Producto> getProductosMasVendidos(Pageable pageable);	
}
