package com.fresno.puntoventaapi.service;

import java.util.List;

import com.fresno.puntoventaapi.model.DetalleVenta;
import com.fresno.puntoventaapi.model.DetalleVentaInfo;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Venta;

public interface DetalleVentaService {

	List<DetalleVenta> createDetalleVenta(DetalleVentaInfo detalleInfo);
	List<DetalleVenta> readDetalleVentaByVenta(Venta venta);
	DetalleVenta readDetalleVentaById(Long id);
	List<Producto> productosMasVendidos();
	List<DetalleVenta> updateDetalleVenta(Venta venta, DetalleVentaInfo detalleInfo);
}
