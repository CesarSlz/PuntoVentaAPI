package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.CategoriaRepository;
import com.fresno.puntoventaapi.dao.ProductoRepository;
import com.fresno.puntoventaapi.model.Categoria;
import com.fresno.puntoventaapi.model.Producto;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public void createProducto(Producto producto) {
		Categoria categoria = producto.getCategoria();
		
		if(categoria.getId() == null) {
			categoria.setNombre(producto.getCategoria().getNombre());
			
			categoriaRepository.save(categoria);
		}
		
		productoRepository.save(producto);
	}

	@Override
	public List<Producto> readProductos() {
		return productoRepository.findByNoEliminados();
	}

	@Override
	public Producto readProductoByCodigoBarra(String codigoBarra) {
		return productoRepository.findByCodigoBarra(codigoBarra);
	}
	
	@Override
	public List<Producto> readProductoByNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	@Override
	public Producto updateProducto(String codigoBarra, Producto producto) {
		Producto productoActualizar = readProductoByCodigoBarra(codigoBarra);
		
		Categoria categoria = producto.getCategoria();
		
		if(categoria.getId() == null) {
			categoria.setNombre(producto.getCategoria().getNombre());
			
			categoriaRepository.save(categoria);
		}
		
		productoActualizar.setCategoria(producto.getCategoria());
		productoActualizar.setNombre(producto.getNombre());
		productoActualizar.setMarca(producto.getMarca());
		productoActualizar.setPrecioCompra(producto.getPrecioCompra());
		productoActualizar.setPrecioVenta(producto.getPrecioVenta());
		productoActualizar.setExistencia(producto.getExistencia());
		productoActualizar.setTamano(producto.getTamano());
		productoActualizar.setFechaModificacion(new Date());
		
		return productoRepository.save(productoActualizar);
	}

	@Override
	public void deleteProducto(String codigoBarra) {
		Producto productoEliminar = readProductoByCodigoBarra(codigoBarra);
		
		productoEliminar.setCodigoBarra(null);
		productoEliminar.setFechaEliminacion(new Date());
		
		productoRepository.save(productoEliminar);
	}

}
