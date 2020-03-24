package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.UsuarioRepository;
import com.fresno.puntoventaapi.model.Usuario;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public void createUsuario(Usuario usuario) {
		repository.save(usuario);
	}

	@Override
	public List<Usuario> readUsuarios() {
		return repository.findByNoEliminados();
	}

	@Override
	public Usuario readUsuarioById(Integer id) {
		return repository.findById(id).get();
	}
	
	@Override
	public List<Usuario> readUsuarioByTipo(String tipo) {
		return repository.findByTipo(tipo);
	}

	@Override
	public Usuario updateUsuario(Integer id, Usuario usuario) {
		Usuario usuarioActualizar = readUsuarioById(id);
		
		usuarioActualizar.setTipo(usuario.getTipo());
		usuarioActualizar.setFechaModificacion(new Date());
		
		return repository.save(usuarioActualizar);
	}

	@Override
	public void deleteUsuario(Integer id) {
		Usuario usuarioEliminar = readUsuarioById(id);
		
		usuarioEliminar.setFechaEliminacion(new Date());
		
		repository.save(usuarioEliminar);
	}

}
