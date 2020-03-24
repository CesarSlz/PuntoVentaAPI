package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import com.fresno.puntoventaapi.model.Adeudo;

public interface AdeudoService {
	
	List<Adeudo> readAdeudos();
	List<Adeudo> readAdeudosByEstatus(Boolean estatus);
	List<Adeudo> readAdeudoByFecha(Date fechaCreacion);
	Adeudo readAdeudoById(Long id);
}
