package com.fresno.puntoventaapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Abono;
import com.fresno.puntoventaapi.model.Adeudo;
import com.fresno.puntoventaapi.service.AbonoService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE})
@RequestMapping("/abonos")
public class AbonoController {

	@Autowired
	private AbonoService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createAbono(@RequestBody Abono abono){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createAbono(abono);
			mensaje = "Exito al guardar el abono";
			estatus = HttpStatus.OK;
		} catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar el abono";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", abono);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{adeudo}")
	public ResponseEntity<Map<String, Object>> readAbonosByAdeudo(@PathVariable("adeudo") Adeudo adeudo) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Abono> abonos = null;
		
		try {
			abonos = new ArrayList<Abono>();
			abonos = service.readAbonosByAdeudo(adeudo);
			mensaje = "Exito al consultar los abonos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los abonos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", abonos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/total")
	public ResponseEntity<Map<String, Object>> sumaAbonos() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Double suma = 0.0;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			suma = service.sumaAbonos();
			mensaje = "Exito al consultar la sumatoria de abonos del día " + formatter.format(new Date());
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar la sumatoria de abonos del día " + formatter.format(new Date());
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", suma);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/total/{adeudo}")
	public ResponseEntity<Map<String, Object>> sumaAbonosByAdeudo(@PathVariable("adeudo") Adeudo adeudo) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Double suma = 0.0;
		
		try {
			suma = service.sumaAbonosByAdeudo(adeudo);
			mensaje = "Exito al consultar la sumatoria del abono";
			estatus = HttpStatus.OK;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error al consultar la sumatoria de abono";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", suma);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deleteAbono(@PathVariable("id") Long id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deleteAbono(id);
			mensaje = "Exito al eliminar el abono";
			estatus = HttpStatus.OK;
		}catch (DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el abono";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el abono";
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
