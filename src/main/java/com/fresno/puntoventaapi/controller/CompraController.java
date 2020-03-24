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

import com.fresno.puntoventaapi.model.Compra;
import com.fresno.puntoventaapi.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {

	@Autowired
	private CompraService service;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readCompras() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Compra> compras = null;
		
		try {
			compras = new ArrayList<Compra>();
			compras = service.readCompras();
			mensaje = "Exito al consultar las compras";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las compras";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", compras);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readCompraById(@PathVariable ("id") Long id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Compra compra = null;
		
		try {
			compra = service.readCompraById(id);
			mensaje = "Exito al consultar la compra";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar la compra";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", compra);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/fecha")
	public ResponseEntity<Map<String, Object>> readCompraByFecha(
			@RequestParam("q") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCreacion) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Compra> compras = null;
		
		try {
			compras = service.readCompraByFecha(fechaCreacion);
			mensaje = "Exito al consultar las compras";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las compras";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", compras);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/total")
	public ResponseEntity<Map<String, Object>> sumaCompras() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Double suma = 0.0;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			suma = service.sumaCompras();
			mensaje = "Exito al consultar la sumatoria de compras del día " + formatter.format(new Date());
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar la sumatoria de compras del día " + formatter.format(new Date());
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", suma);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deleteCompra(@PathVariable("id") Long id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Compra compraActualizada = null;
		
		try {
			compraActualizada = service.deleteCompra(id);
			
			if(compraActualizada == null) {
				mensaje = "Error al eliminar la compra debido a que esta en proceso de liquidación";
				estatus = HttpStatus.BAD_REQUEST;
			}else {
				mensaje = "Exito al eliminar la compra";
				estatus = HttpStatus.OK;
			}
		}catch (TransactionSystemException e) {
		    Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		    	e.printStackTrace();
				mensaje = "Error al eliminar la compra";
				estatus = HttpStatus.BAD_REQUEST;
		    }
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la compra";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la compra";
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
