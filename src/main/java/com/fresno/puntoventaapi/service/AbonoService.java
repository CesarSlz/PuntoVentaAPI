package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Abono;
import com.fresno.puntoventaapi.model.Adeudo;

public interface AbonoService {
	
	void createAbono(Abono abono);
	List<Abono> readAbonosByAdeudo(Adeudo adeudo);
	Abono readAbonoById(Long id);
	Double sumaAbonos();
	Double sumaAbonosByAdeudo(Adeudo adeudo);
	void deleteAbono(Long id);
}
