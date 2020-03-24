package com.fresno.puntoventaapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Puesto;
import com.fresno.puntoventaapi.service.PuestoService;

@RestController
@RequestMapping("/puestos")
public class PuestoController {
	
	@Autowired
	private PuestoService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createPuesto(@RequestBody Puesto puesto) {
		Map<String, Object > respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createPuesto(puesto);
			mensaje = "Exito al guardar el puesto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar el puesto";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", puesto);
		
		return ResponseEntity.status(estatus).body(respuesta);
		
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readPuestos() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Puesto> puestos= null;
		
		
		try {
			puestos = new ArrayList<Puesto>();
			puestos = service.readPuestos();
			mensaje = "Exito al consultar los puestos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los puestos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", puestos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readPuestoById(@PathVariable ("id") Integer id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Puesto puesto = null;
		
		try {
			puesto = service.readPuestoById(id);
			mensaje = "Exito al consultar el puesto";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar el puesto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", puesto);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<Map<String, Object>> readPuestoByNombre(@RequestParam("q") String nombre) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Puesto> puestos = null;
		
		try {
			puestos = service.readPuestoByNombre(nombre);
			mensaje = "Exito al consultar los puestos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los puestos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", puestos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> updatePuesto(@PathVariable("id") Integer id,
			@RequestBody Puesto puesto) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.updatePuesto(id, puesto);
			mensaje = "Exito al actualizar el puesto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el puesto";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el puesto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", puesto);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deletePuesto(@PathVariable("id") Integer id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deletePuesto(id);
			mensaje = "Exito al eliminar el puesto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el puesto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el puesto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}

}
