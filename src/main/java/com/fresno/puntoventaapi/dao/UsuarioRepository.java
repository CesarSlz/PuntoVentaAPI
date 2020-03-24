package com.fresno.puntoventaapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fresno.puntoventaapi.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@Query("SELECT u FROM Usuario u WHERE u.fechaEliminacion IS NULL")
	List<Usuario> findByNoEliminados();
	
	@Query("SELECT u FROM Usuario u WHERE u.tipo LIKE %:tipo% AND u.fechaEliminacion IS NULL")
	List<Usuario> findByTipo(@Param("tipo") String tipo);
}
