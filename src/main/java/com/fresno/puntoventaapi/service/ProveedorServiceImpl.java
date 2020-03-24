package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.ProveedorRepository;
import com.fresno.puntoventaapi.model.Proveedor;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService{

	@Autowired
	private ProveedorRepository repository;

	@Override
	public void createProveedor(Proveedor proveedor) {
		repository.save(proveedor);
		
	}

	@Override
	public List<Proveedor> readProveedores() {
		return repository.findByNoEliminados();
	}

	@Override
	public Proveedor readProveedorById(Integer id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Proveedor> readProveedorByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	@Override
	public Proveedor updateProveedor(Integer id, Proveedor proveedor) {
		Proveedor proveedorActualizar = readProveedorById(id);
		
		proveedorActualizar.setNombre(proveedor.getNombre());
		proveedorActualizar.setTelefono(proveedor.getTelefono());
		proveedorActualizar.setFechaModificacion(new Date());
		
		return repository.save(proveedorActualizar);
	}

	@Override
	public void deleteProveedor(Integer id) {
		Proveedor proveedorEliminar = readProveedorById(id);
		
		proveedorEliminar.setTelefono(null);
		proveedorEliminar.setFechaEliminacion(new Date());
		
		repository.save(proveedorEliminar);
	}
	
	
}
