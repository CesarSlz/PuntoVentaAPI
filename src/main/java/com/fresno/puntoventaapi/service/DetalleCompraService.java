package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.Compra;
import com.fresno.puntoventaapi.model.DetalleCompra;
import com.fresno.puntoventaapi.model.DetalleCompraInfo;

public interface DetalleCompraService {
	
	List<DetalleCompra> createDetalleCompra(DetalleCompraInfo detalleInfo);
	List<DetalleCompra> readDetalleCompraByCompra(Compra compra);
	DetalleCompra readDetalleCompraById(Long id);
	List<DetalleCompra> updateDetalleCompra(Compra compra, DetalleCompraInfo detalleInfo);
}
