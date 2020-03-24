package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import com.fresno.puntoventaapi.model.Empleado;
import com.fresno.puntoventaapi.model.Venta;

public interface VentaService {
	
	List<Venta> readVentas();
	Venta readVentaById(Long id);
	List<Venta> readVentaByFecha(Date fechaCreacion);
	Double sumaVentas();
	Double sumaVentasByEmpleado(Empleado empleado);
	Integer ultimoCliente();
	void deleteVenta(Long id);
}
