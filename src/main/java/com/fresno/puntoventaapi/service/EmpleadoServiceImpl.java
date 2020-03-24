package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.EmpleadoRepository;
import com.fresno.puntoventaapi.dao.PuestoRepository;
import com.fresno.puntoventaapi.model.Empleado;
import com.fresno.puntoventaapi.model.Puesto;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepository repository;
	
	@Autowired
	private PuestoRepository puestoRepository;
	
	@Override
	public void createEmpleado(Empleado empleado) {
		Puesto puesto = empleado.getPuesto();
		
		if(puesto.getId() == null) {
			puesto.setNombre(empleado.getPuesto().getNombre());
			puesto.setSalario(empleado.getPuesto().getSalario());
			
			puestoRepository.save(puesto);
		}
		
		repository.save(empleado);
	}

	@Override
	public List<Empleado> readEmpleados() {
		return repository.findByNoEliminados();
	}

	@Override
	public Empleado readEmpleadoById(Integer id) {
		return repository.findById(id).get();
	}
	
	@Override
	public List<Empleado> readEmpleadoByNombreCompleto(String nombreCompleto) {
		return repository.findByNombreCompleto(nombreCompleto);
	}
	
	@Override
	public Empleado readEmpleadoByTelefono(String telefono) {
		return repository.findByTelefono(telefono);
	}

	@Override
	public Empleado updateEmpleado(Integer id, Empleado empleado) {
		Empleado empleadoActualizar = readEmpleadoById(id);
		
		Puesto puesto = empleado.getPuesto();
		
		if(puesto.getId() == null) {
			puesto.setNombre(empleado.getPuesto().getNombre());
			puesto.setSalario(empleado.getPuesto().getSalario());
			
			puestoRepository.save(puesto);
		}
		
		empleadoActualizar.setPuesto(empleado.getPuesto());
		empleadoActualizar.setUsuario(empleado.getUsuario());
		empleadoActualizar.setNombre(empleado.getNombre());
		empleadoActualizar.setApellidoPaterno(empleado.getApellidoPaterno());
		empleadoActualizar.setApellidoMaterno(empleado.getApellidoMaterno());
		empleadoActualizar.setTelefono(empleado.getTelefono());
		empleadoActualizar.setContrasena(empleado.getContrasena());
		empleadoActualizar.setFechaModificacion(new Date());
		
		return repository.save(empleadoActualizar);
	}

	@Override
	public void deleteEmpleado(Integer id) {
		Empleado empleadoEliminar = readEmpleadoById(id);
		
		empleadoEliminar.setTelefono(null);
		empleadoEliminar.setFechaEliminacion(new Date());
		
		repository.save(empleadoEliminar);
	}

}
