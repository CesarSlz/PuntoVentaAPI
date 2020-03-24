package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.PuestoRepository;
import com.fresno.puntoventaapi.model.Puesto;

@Service
@Transactional
public class PuestoServiceImpl implements PuestoService{
	
	@Autowired
	private PuestoRepository repository;
	
	@Override
	public void createPuesto(Puesto puesto) {
		repository.save(puesto);
	}

	@Override
	public List<Puesto> readPuestos() {
		return repository.findByNoEliminados();
	}

	@Override
	public Puesto readPuestoById(Integer id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Puesto> readPuestoByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	@Override
	public Puesto updatePuesto(Integer id, Puesto puesto) {
		Puesto puestoActualizar = readPuestoById(id);
		
		puestoActualizar.setNombre(puesto.getNombre());
		puestoActualizar.setSalario(puesto.getSalario());
		puestoActualizar.setFechaModificacion(new Date());
		
		return repository.save(puestoActualizar);
	}

	@Override
	public void deletePuesto(Integer id) {
		Puesto puestoEliminar = readPuestoById(id);
		
		puestoEliminar.setFechaEliminacion(new Date());
		
		repository.save(puestoEliminar);
		
	}

}
