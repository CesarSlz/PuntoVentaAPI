package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Usuario;

public interface UsuarioService {
	
	void createUsuario(Usuario usuario);
	List<Usuario> readUsuarios();
	Usuario readUsuarioById(Integer id);
	List<Usuario> readUsuarioByTipo(String tipo);
	Usuario updateUsuario(Integer id, Usuario usuario);
	void deleteUsuario(Integer id);

}
