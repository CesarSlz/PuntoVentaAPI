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

import com.fresno.puntoventaapi.model.Categoria;
import com.fresno.puntoventaapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createCategoria(@RequestBody Categoria categoria){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createCategoria(categoria);
			mensaje = "Exito al guardar la categoría";
			estatus = HttpStatus.OK;
		} catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar la categoría";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", categoria);
		
		return ResponseEntity.status(estatus).body(respuesta);
		
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readCategorias() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Categoria> categorias = null;
		
		
		try {
			categorias = new ArrayList<Categoria>();
			categorias = service.readCategorias();
			mensaje = "Exito al consultar las categorías";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las categorías";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", categorias);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> readCategoriaById(@PathVariable ("id") Integer id) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Categoria categoria = null;
		
		try {
			categoria = service.readCategoriaById(id);
			mensaje = "Exito al consultar la categoría";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar la categoría";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", categoria);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<Map<String, Object>> readCategoriaByNombre(@RequestParam("q") String nombre) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Categoria> categorias= null;
		
		try {
			categorias = service.readCategoriaByNombre(nombre);
			mensaje = "Exito al consultar las categorías";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar las categorías";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", categorias);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> updateCategoria(@PathVariable("id") Integer id,
			@RequestBody Categoria categoria) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.updateCategoria(id, categoria);
			mensaje = "Exito al actualizar la categoría";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la categoría";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar la categoría";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", categoria);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{id}")
	public ResponseEntity<Map<String, Object>> deleteCategoria(@PathVariable("id") Integer id){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deleteCategoria(id);
			mensaje = "Exito al eliminar la categoría";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la categoría";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar la categoría";
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
