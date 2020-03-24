package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Empleado;

public interface EmpleadoService {
	
	void createEmpleado(Empleado empleado);
	List<Empleado> readEmpleados();
	Empleado readEmpleadoById(Integer id);
	List<Empleado> readEmpleadoByNombreCompleto(String nombreCompleto);
	Empleado readEmpleadoByTelefono(String telefono);
	Empleado updateEmpleado(Integer id, Empleado empleado);
	void deleteEmpleado(Integer id);
	
}
