package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Producto;

public interface ProductoService {
	
	void createProducto(Producto producto);
	List<Producto> readProductos();
	Producto readProductoByCodigoBarra(String codigoBarra);
	List<Producto> readProductoByNombre(String nombre);
	Producto updateProducto(String codigoBarra, Producto producto);
	void deleteProducto(String codigoBarra);
}
