package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.SaldoRepository;
import com.fresno.puntoventaapi.model.Saldo;

@Service
@Transactional
public class SaldoServiceImpl implements SaldoService{

	@Autowired
	private SaldoRepository repository;

	@Override
	public void createSaldo(Saldo saldo) {
		repository.save(saldo);
	}

	@Override
	public List<Saldo> readSaldos() {
		return repository.findByNoEliminados();
	}

	@Override
	public Saldo readSaldoById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Saldo readSaldoByFecha(Date fechaCreacion) {
		return repository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public Saldo updateSaldo(Long id, Saldo saldo) {
		Saldo saldoActualizar = readSaldoById(id);
		
		if(saldo.getFondoCaja() != saldoActualizar.getFondoCaja()) {
			saldoActualizar.setTotal((saldoActualizar.getTotal() - saldoActualizar.getFondoCaja()) 
					+ saldo.getFondoCaja());
		}
			
		saldoActualizar.setFondoCaja(saldo.getFondoCaja());
		saldoActualizar.setFechaModificacion(new Date());
		
		return repository.save(saldoActualizar);
	}

	@Override
	public void deleteSaldo(Long id) {
		Saldo saldoEliminar = readSaldoById(id);
		
		saldoEliminar.setFechaEliminacion(new Date());
		
		repository.save(saldoEliminar);
	}

}
