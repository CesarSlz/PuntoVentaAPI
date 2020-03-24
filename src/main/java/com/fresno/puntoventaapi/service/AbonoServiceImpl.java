package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.AbonoRepository;
import com.fresno.puntoventaapi.dao.AdeudoRepository;
import com.fresno.puntoventaapi.dao.SaldoRepository;
import com.fresno.puntoventaapi.model.Abono;
import com.fresno.puntoventaapi.model.Adeudo;
import com.fresno.puntoventaapi.model.Saldo;

@Service
@Transactional
public class AbonoServiceImpl implements AbonoService {

	@Autowired
	private AbonoRepository abonoRepository;

	@Autowired
	private AdeudoRepository adeudoRepository;

	@Autowired
	private SaldoRepository saldoRepository;

	@Override
	public void createAbono(Abono abono) {
		Adeudo adeudo = null;
		Saldo saldoActualizar = saldoRepository.findByFechaCreacion(new Date());
		double sumatoria = 0;

		abonoRepository.save(abono);

		// Obtener adeudo
		adeudo = adeudoRepository.findById(abono.getAdeudo().getId()).get();

		// Obtener sumatoria de abonos
		sumatoria = sumaAbonosByAdeudo(adeudo);

		// Verificar si el adeudo ha sido pagado
		if (sumatoria == adeudo.getMonto()) {
			adeudo.setEstatus(true);
			adeudo.setFechaModificacion(new Date());

			adeudoRepository.save(adeudo);
		}

		// Actualizar el abono a compras del saldo una vez que el saldo ha sido creado
		if (saldoActualizar != null) {
			// Sumar al abono a compras el monto del abono
			saldoActualizar.setAbonoCompra(saldoActualizar.getAbonoCompra() + abono.getMonto());
			saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
					- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
			saldoActualizar.setFechaModificacion(new Date());

			saldoRepository.save(saldoActualizar);
		}
	}

	@Override
	public List<Abono> readAbonosByAdeudo(Adeudo adeudo) {
		return abonoRepository.findAllByAdeudo(adeudo);
	}

	@Override
	public Abono readAbonoById(Long id) {
		return abonoRepository.findById(id).get();
	}

	@Override
	public Double sumaAbonos() {
		return abonoRepository.getSumatoriaAbonosByFecha();
	}

	@Override
	public Double sumaAbonosByAdeudo(Adeudo adeudo) {
		return abonoRepository.getSumatoriaAbonosByAdeudo(adeudo);
	}

	@Override
	public void deleteAbono(Long id) {
		Abono abonoEliminar = readAbonoById(id);
		Adeudo adeudo = null;
		Saldo saldoActualizar = saldoRepository.findByFechaCreacion(abonoEliminar.getFechaCreacion());
		double sumatoria = 0;

		abonoEliminar.setFechaEliminacion(new Date());

		// Obtener adeudo
		adeudo = adeudoRepository.findById(abonoEliminar.getAdeudo().getId()).get();

		// Obtener sumatoria de abonos
		sumatoria = sumaAbonosByAdeudo(adeudo);

		// Verificar si el adeudo ha sido pagado
		if (sumatoria < adeudo.getMonto()) {
			adeudo.setEstatus(false);
			adeudo.setFechaModificacion(new Date());

			adeudoRepository.save(adeudo);
		}

		// Actualizar el abono a compras del saldo una vez que el saldo ha sido creado
		if (saldoActualizar != null) {
			// Restar al abono a compras el monto del abono
			saldoActualizar.setAbonoCompra(saldoActualizar.getAbonoCompra() - abonoEliminar.getMonto());
			saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
					- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
			saldoActualizar.setFechaModificacion(new Date());

			saldoRepository.save(saldoActualizar);
		}
	}

}
