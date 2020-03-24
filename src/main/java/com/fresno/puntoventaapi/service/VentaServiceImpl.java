package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.DetalleVentaRepository;
import com.fresno.puntoventaapi.dao.ProductoRepository;
import com.fresno.puntoventaapi.dao.SaldoRepository;
import com.fresno.puntoventaapi.dao.VentaRepository;
import com.fresno.puntoventaapi.model.DetalleVenta;
import com.fresno.puntoventaapi.model.Empleado;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Saldo;
import com.fresno.puntoventaapi.model.Venta;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Autowired
	private DetalleVentaRepository detalleVentaRepository;

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private SaldoRepository saldoRepository;
	
	@Override
	public List<Venta> readVentas() {
		return ventaRepository.findByNoEliminados();
	}

	@Override
	public Venta readVentaById(Long id) {
		return ventaRepository.findById(id).get();
	}

	@Override
	public List<Venta> readVentaByFecha(Date fechaCreacion) {
		return ventaRepository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public Double sumaVentas() {
		return ventaRepository.getSumatoriaVentasByFecha();
	}

	@Override
	public Double sumaVentasByEmpleado(Empleado empleado) {
		return ventaRepository.getSumatoriaVentasByEmpleadoFecha(empleado);
	}

	@Override
	public Integer ultimoCliente() {
		return ventaRepository.getUltimoCliente();
	}

	@Override
	public void deleteVenta(Long id) {
		Venta ventaEliminar = readVentaById(id);
		List<DetalleVenta> detalleVenta = detalleVentaRepository.findAllByVenta(ventaEliminar);
		Saldo saldoActualizar = saldoRepository.findByFechaCreacion(ventaEliminar.getFechaCreacion());

		ventaEliminar.setFechaEliminacion(new Date());

		for (DetalleVenta detalles : detalleVenta) {
			DetalleVenta detalleVentaEliminar = detalleVentaRepository.findById(detalles.getId()).get();
			Producto productoActualizar = productoRepository.findById(detalles.getProducto().getId()).get();

			productoActualizar.setExistencia(productoActualizar.getExistencia() + detalles.getCantidad());
			productoActualizar.setFechaModificacion(new Date());

			detalleVentaEliminar.setFechaEliminacion(new Date());
		}

		ventaRepository.save(ventaEliminar);

		// Actualizar la venta total del saldo una vez que el saldo ha sido creado
		if (saldoActualizar != null) {
			// Sumar al total de ventas el total de la venta
			saldoActualizar.setVentaTotal(saldoActualizar.getVentaTotal() - ventaEliminar.getTotal());
			saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
					- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
			saldoActualizar.setFechaModificacion(new Date());

			saldoRepository.save(saldoActualizar);
		}
	}

}
