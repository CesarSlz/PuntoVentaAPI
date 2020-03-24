package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Categoria;

public interface CategoriaService {
	
	void createCategoria(Categoria categoria);
	List<Categoria> readCategorias();
	Categoria readCategoriaById(Integer id);
	List<Categoria> readCategoriaByNombre(String nombre);
	Categoria updateCategoria(Integer id, Categoria categoria);
	void deleteCategoria(Integer id);

}
