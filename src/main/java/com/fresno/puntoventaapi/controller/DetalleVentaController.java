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

import com.fresno.puntoventaapi.model.DetalleVenta;
import com.fresno.puntoventaapi.model.DetalleVentaInfo;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Venta;
import com.fresno.puntoventaapi.service.DetalleVentaService;

@RestController
@RequestMapping("/detalleventa")
public class DetalleVentaController {
	
	@Autowired 
	private DetalleVentaService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createDatelleVenta(@RequestBody DetalleVentaInfo detalleInfo){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<DetalleVenta> detalleVentasCreado = null;
		
		try {
			detalleVentasCreado = service.createDetalleVenta(detalleInfo);
			
			if(detalleVentasCreado == null) {
				mensaje = "Error al crear la venta debido a que el efectivo no puede ser menor que el total";
				estatus = HttpStatus.BAD_REQUEST;
			}else {
				mensaje = "Exito al guardar la venta";
				estatus = HttpStatus.OK;
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar la venta";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (TransactionSystemException e) {
		    Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		    	e.printStackTrace();
				mensaje = "Error al guardar la venta";
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
		respuesta.put("datos", detalleVentasCreado);
		
		return ResponseEntity.status(estatus).body(respuesta);
		
	}
	
	@GetMapping("/codigo/{venta}")
	public ResponseEntity<Map<String, Object>> readDetalleVentaByVenta(Venta venta) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<DetalleVenta> detalleVenta = null;
		
		
		try {
			detalleVenta = new ArrayList<DetalleVenta>();
			detalleVenta = service.readDetalleVentaByVenta(venta);
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
		respuesta.put("datos", detalleVenta);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/masvendidos")
	public ResponseEntity<Map<String, Object>> productosMasVendidos() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Producto> productos = null;
		
		try {
			productos = service.productosMasVendidos();
			mensaje = "Exito al consultar los productos más vendidos";
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar los productos más vendidos";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", productos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{venta}")
	public ResponseEntity<Map<String, Object>> updateDetalleVentaById(@PathVariable("venta") Venta venta,
			@RequestBody DetalleVentaInfo detalleInfo) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<DetalleVenta> detalleVentaActualizado = null;
		
		try {
			detalleVentaActualizado = service.updateDetalleVenta(venta, detalleInfo);
			
			if(detalleVentaActualizado == null) {
				mensaje = "Error al crear la venta debido a que el efectivo no puede ser menor que el total";
				estatus = HttpStatus.OK;
			}else {
				mensaje = "Exito al actualizar la venta";
				estatus = HttpStatus.OK;
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la venta";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la venta";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la venta";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", detalleVentaActualizado);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}

}
