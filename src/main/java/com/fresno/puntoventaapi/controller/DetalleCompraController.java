package com.fresno.puntoventaapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Compra;
import com.fresno.puntoventaapi.model.DetalleCompra;
import com.fresno.puntoventaapi.model.DetalleCompraInfo;
import com.fresno.puntoventaapi.service.DetalleCompraService;

@RestController
@RequestMapping("/detallecompra")
public class DetalleCompraController {
	
	@Autowired
	private DetalleCompraService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createDatelleCompra(@RequestBody DetalleCompraInfo detalleInfo){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createDetalleCompra(detalleInfo);
			mensaje = "Exito al guardar la compra";
			estatus = HttpStatus.OK;
		} catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar la compra";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (TransactionSystemException e) {
		    Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		    	e.printStackTrace();
				mensaje = "Error al guardar la compra";
				estatus = HttpStatus.BAD_REQUEST;
		    }
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", detalleInfo);
		
		return ResponseEntity.status(estatus).body(respuesta);
		
	}
	
	@GetMapping("/codigo/{compra}")
	public ResponseEntity<Map<String, Object>> readDetalleCompraByCompra(Compra compra) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<DetalleCompra> detalleCompra = null;
		
		
		try {
			detalleCompra = new ArrayList<DetalleCompra>();
			detalleCompra = service.readDetalleCompraByCompra(compra);
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
		respuesta.put("datos", detalleCompra);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{compra}")
	public ResponseEntity<Map<String, Object>> updateDetalleCompraById(@PathVariable("compra") Compra compra,
			@RequestBody DetalleCompraInfo detalleInfo) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<DetalleCompra> detalleCompraActualizado = null;
		
		try {
			detalleCompraActualizado = service.updateDetalleCompra(compra, detalleInfo);
			
			if(detalleCompraActualizado == null) {
				mensaje = "Error al actualizar la compra debido a que esta en proceso de liquidación";
				estatus = HttpStatus.BAD_REQUEST;
			}else {
				mensaje = "Exito al actualizar la compra";
				estatus = HttpStatus.OK;
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la compra";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la venta";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la compra";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", detalleCompraActualizado);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}

}
