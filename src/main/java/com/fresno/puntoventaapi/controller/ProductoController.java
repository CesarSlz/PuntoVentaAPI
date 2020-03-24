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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.service.ProductoService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService service;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createProducto(@RequestBody Producto producto){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.createProducto(producto);
			mensaje = "Exito al guardar el producto";
			estatus = HttpStatus.OK;
		} catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al guardar el producto";
			estatus = HttpStatus.BAD_REQUEST;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", producto);
		
		return ResponseEntity.status(estatus).body(respuesta);
		
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> readProductos() {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Producto> productos = null;
		
		
		try {
			productos = new ArrayList<Producto>();
			productos = service.readProductos();
			mensaje = "Exito al consultar los productos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los productos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", productos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/codigo/{codigoBarra}")
	public ResponseEntity<Map<String, Object>> readProductoByCodigoBarra(
			@PathVariable ("codigoBarra") String codigoBarra) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		Producto producto = null;
		
		try {
			producto = service.readProductoByCodigoBarra(codigoBarra);
			mensaje = "Exito al consultar el producto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al consultar el producto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", producto);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<Map<String, Object>> readProductoByNombre(@RequestParam("q") String nombre) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		List<Producto> productos= null;
		
		try {
			productos = service.readProductoByNombre(nombre);
			mensaje = "Exito al consultar los productos";
			estatus = HttpStatus.OK;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al consultar los productos";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", productos);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@PutMapping("/codigo/{codigoBarra}")
	public ResponseEntity<Map<String, Object>> updateProducto(@PathVariable("codigoBarra") String codigoBarra,
			@RequestBody Producto producto) {
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.updateProducto(codigoBarra, producto);
			mensaje = "Exito al actualizar el producto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el producto";
			estatus = HttpStatus.BAD_REQUEST;
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al actualizar el producto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(Exception e) {
			e.printStackTrace();
			mensaje = "Error";
			estatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		respuesta = new HashMap<String, Object>();
		respuesta.put("estatus", estatus);
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", producto);
		
		return ResponseEntity.status(estatus).body(respuesta);
	}
	
	@DeleteMapping("/codigo/{codigoBarra}")
	public ResponseEntity<Map<String, Object>> deleteProducto(@PathVariable("codigoBarra") String codigoBarra){
		Map<String, Object> respuesta = null;
		String mensaje = null;
		HttpStatus estatus = null;
		
		try {
			service.deleteProducto(codigoBarra);
			mensaje = "Exito al eliminar el producto";
			estatus = HttpStatus.OK;
		}catch(DataAccessException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el producto";
			estatus = HttpStatus.NOT_FOUND;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			mensaje = "Error al eliminar el producto";
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
