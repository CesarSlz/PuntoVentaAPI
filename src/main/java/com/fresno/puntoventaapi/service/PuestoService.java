package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Puesto;

public interface PuestoService {
	
	void createPuesto(Puesto puesto);
	List<Puesto> readPuestos();
	Puesto readPuestoById(Integer id);
	List<Puesto> readPuestoByNombre(String nombre);
	Puesto updatePuesto(Integer id, Puesto puesto);
	void deletePuesto(Integer id);
	
}
