package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import com.fresno.puntoventaapi.model.Compra;

public interface CompraService {

	List<Compra> readCompras();
	Compra readCompraById(Long id);
	List<Compra> readCompraByFecha(Date fechaCreacion);
	Double sumaCompras();
	Compra deleteCompra(Long id);
}
