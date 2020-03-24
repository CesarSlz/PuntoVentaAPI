package com.fresno.puntoventaapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Empleado;
import com.fresno.puntoventaapi.model.Venta;
import com.fresno.puntoventaapi.service.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {
	
	@Autowired
	private VentaService service;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readVentas() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Venta> ventas = null;
		
		try {
			ventas = new ArrayList<Venta>();
			ventas = service.readVentas();
			mensaje = "Exito al consultar las ventas";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las ventas";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", ventas);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readVentaById(@PathVariable ("id") Long id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Venta venta = null;
		
		try {
			venta = service.readVentaById(id);
			mensaje = "Exito al consultar la venta";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar la venta";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", venta);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/fecha")
	public ResponseEntity<Map<String, Object>> readVentaByFecha(
			@RequestParam("q") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCreacion) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Venta> ventas = null;
		
		try {
			ventas = service.readVentaByFecha(fechaCreacion);
			mensaje = "Exito al consultar las ventas";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las ventas";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", ventas);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/total")
	public ResponseEntity<Map<String, Object>> sumaVentas() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Double suma = 0.0;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			suma = service.sumaVentas();
			mensaje = "Exito al consultar la sumatoria de ventas del día " + formatter.format(new Date());
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar la sumatoria de ventas del día " + formatter.format(new Date());
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", suma);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/total/empleado/{empleado}")
	public ResponseEntity<Map<String, Object>> sumaVentasByEmpleado(@PathVariable("empleado") Empleado empleado) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Double suma = 0.0;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			suma = service.sumaVentasByEmpleado(empleado);
			mensaje = "Exito al consultar la sumatoria de ventas del  día " 
					+ formatter.format(new Date()) + " del empleado " 
					+ empleado.getNombre() + " " 
					+ empleado.getApellidoPaterno() + " " 
					+ empleado.getApellidoMaterno();
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar la sumatoria de ventas del  día " 
					+ formatter.format(new Date()) + " del empleado " 
					+ empleado.getNombre() + " " 
					+ empleado.getApellidoPaterno() + " " 
					+ empleado.getApellidoMaterno();
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", suma);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/ultimocliente")
	public ResponseEntity<Map<String, Object>> ultimoCliente() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Integer cliente = 0;
		
		try {
			cliente = service.ultimoCliente();
			
			if(cliente == null) {
				cliente = 0;
				mensaje = "No existen clientes registrados";
			}else {
				mensaje = "Exito al consultar el ultimo cliente";
			}
			
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", cliente);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deleteVenta(@PathVariable("id") Long id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deleteVenta(id);
			mensaje = "Exito al eliminar la venta";
			estatus = HttpStatus.OK;
		}catch (TransactionSystemException e) {
		    Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		    	e.printStackTrace();
				mensaje = "Error al eliminar la venta";
				estatus = HttpStatus.BAD_REQUEST;
		    }
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la venta";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la venta";
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
