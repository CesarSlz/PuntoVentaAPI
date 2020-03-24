package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import com.fresno.puntoventaapi.model.Saldo;

public interface SaldoService {

	void createSaldo(Saldo saldo);
	List<Saldo> readSaldos();
	Saldo readSaldoById(Long id);
	Saldo readSaldoByFecha(Date fechaCreacion);
	Saldo updateSaldo(Long id, Saldo saldo);
	void deleteSaldo(Long id);
}
