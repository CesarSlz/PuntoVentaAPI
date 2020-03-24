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

import com.fresno.puntoventaapi.model.Proveedor;
import com.fresno.puntoventaapi.service.ProveedorService;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

	@Autowired
	private ProveedorService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createProveedor(@RequestBody Proveedor proveedor) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createProveedor(proveedor);
			mensaje = "Exito al guardar el proveedor";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar el proveedor";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", proveedor);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readProveedores() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Proveedor> proveedores = null;
		
		
		try {
			proveedores = new ArrayList<Proveedor>();
			proveedores = service.readProveedores();
			mensaje = "Exito al consultar los proveedores";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los proveedores";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", proveedores);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readProveedorById(@PathVariable ("id") Integer id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Proveedor proveedor = null;
		
		try {
			proveedor = service.readProveedorById(id);
			mensaje = "Exito al consultar el proveedor";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar el proveedor";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", proveedor);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<Map<String, Object>> readProveedorByNombre(@RequestParam("q") String nombre) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Proveedor> proveedores= null;
		
		try {
			proveedores = service.readProveedorByNombre(nombre);
			mensaje = "Exito al consultar los proveedores";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los proveedores";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", proveedores);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> updateProveedor(@PathVariable("id") Integer id,
			@RequestBody Proveedor proveedor) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.updateProveedor(id, proveedor);
			mensaje = "Exito al actualizar el proveedor";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el proveedor";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el proveedor";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", proveedor);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deleteProveedor(@PathVariable("id") Integer id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deleteProveedor(id);
			mensaje = "Exito al eliminar el proveedor";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el proveedor";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el proveedor";
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
