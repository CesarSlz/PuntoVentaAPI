package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.CategoriaRepository;
import com.fresno.puntoventaapi.model.Categoria;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaRepository repository;
	
	@Override
	public void createCategoria(Categoria categoria) {
		repository.save(categoria);
	}

	@Override
	public List<Categoria> readCategorias() {
		return repository.findByNoEliminados();
	}

	@Override
	public Categoria readCategoriaById(Integer id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Categoria> readCategoriaByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	@Override
	public Categoria updateCategoria(Integer id, Categoria categoria) {
		Categoria categoriaActualizar = readCategoriaById(id);
		
		categoriaActualizar.setNombre(categoria.getNombre());
		categoriaActualizar.setFechaModificacion(new Date());
		
		return repository.save(categoriaActualizar);
	}

	@Override
	public void deleteCategoria(Integer id) {
		Categoria categoriaEliminar = readCategoriaById(id);
		
		categoriaEliminar.setFechaEliminacion(new Date());
		
		repository.save(categoriaEliminar);
	}

}
