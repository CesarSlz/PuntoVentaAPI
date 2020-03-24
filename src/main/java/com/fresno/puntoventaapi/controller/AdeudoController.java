package com.fresno.puntoventaapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Adeudo;
import com.fresno.puntoventaapi.service.AdeudoService;

@RestController
@RequestMapping("/adeudos")
public class AdeudoController {

	@Autowired
	private AdeudoService service;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readAdeudos() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Adeudo> adeudos = null;
		
		try {
			adeudos = service.readAdeudos();
			mensaje = "Exito al consultar los adeudos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los adeudos";
			estatus = HttpStatus.NOT_FOUND;
		}catch (Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", adeudos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/estatus")
	public ResponseEntity<Map<String, Object>> readAdeudosByEstatus(@RequestParam("q") Boolean estatusAdeudo) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Adeudo> adeudos= null;
		
		try {
			adeudos = service.readAdeudosByEstatus(estatusAdeudo);
			mensaje = "Exito al consultar los adeudos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los adeudos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", adeudos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/fecha")
	public ResponseEntity<Map<String, Object>> readAdeudoByFecha(
			@RequestParam("q") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCreacion) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Adeudo> adeudos = null;
		
		try {
			adeudos = service.readAdeudoByFecha(fechaCreacion);
			mensaje = "Exito al consultar las adeudos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las adeudos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", adeudos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readProductoById(@PathVariable ("id") Long id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Adeudo adeudo = null;
		
		try {
			adeudo = service.readAdeudoById(id);
			mensaje = "Exito al consultar el adeudo";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar el adeudo";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", adeudo);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}

}
