package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.AdeudoRepository;

import com.fresno.puntoventaapi.model.Adeudo;

@Service
@Transactional
public class AdeudoServiceImpl implements AdeudoService{

	@Autowired
	private AdeudoRepository repository;
	
	@Override
	public List<Adeudo> readAdeudos() {
		return repository.findByNoEliminados();
	}

	@Override
	public List<Adeudo> readAdeudosByEstatus(Boolean estatus) {
		return repository.findByEstatus(estatus);
	}

	@Override
	public List<Adeudo> readAdeudoByFecha(Date fechaCreacion) {
		return repository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public Adeudo readAdeudoById(Long id) {
		return repository.findById(id).get();
	}

}
