package com.fresno.puntoventaapi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Empleado;
import com.fresno.puntoventaapi.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{

	@Query("SELECT v FROM Venta v WHERE v.fechaEliminacion IS NULL")
	List<Venta> findByNoEliminados();
	
	@Query("SELECT v FROM Venta v WHERE DATE(v.fechaCreacion) = :fechaCreacion AND v.fechaEliminacion IS NULL")
	List<Venta> findByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);
	
	@Query("SELECT COALESCE(SUM(v.total), 0) "
			+ "FROM Venta v "
			+ "WHERE DATE(v.fechaCreacion) = CURDATE() "
			+ "AND v.fechaEliminacion IS NULL")
	Double getSumatoriaVentasByFecha();
	
	@Query("SELECT COALESCE(SUM(v.total), 0) "
			+ "FROM Venta v "
			+ "WHERE v.empleado = :empleado "
			+ "AND DATE(v.fechaCreacion) = CURDATE() "
			+ "AND v.fechaEliminacion IS NULL")
	Double getSumatoriaVentasByEmpleadoFecha(@Param("empleado") Empleado empleado);
	
	@Query(value = "SELECT v.cliente FROM Venta v ORDER BY v.cliente DESC LIMIT 1", nativeQuery = true)
	Integer getUltimoCliente();
}
