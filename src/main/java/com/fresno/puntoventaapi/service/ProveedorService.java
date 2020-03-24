package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Proveedor;

public interface ProveedorService {

	void createProveedor(Proveedor proveedor);
	List<Proveedor> readProveedores();
	Proveedor readProveedorById(Integer id);
	List<Proveedor> readProveedorByNombre(String nombre);
	Proveedor updateProveedor(Integer id, Proveedor proveedor);
	void deleteProveedor(Integer id);
}
