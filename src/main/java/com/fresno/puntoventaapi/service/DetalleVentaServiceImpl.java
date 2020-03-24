package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.DetalleVentaRepository;
import com.fresno.puntoventaapi.dao.ProductoRepository;
import com.fresno.puntoventaapi.dao.SaldoRepository;
import com.fresno.puntoventaapi.dao.VentaRepository;
import com.fresno.puntoventaapi.model.DetalleVenta;
import com.fresno.puntoventaapi.model.DetalleVentaInfo;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Saldo;
import com.fresno.puntoventaapi.model.Venta;

@Service
@Transactional
public class DetalleVentaServiceImpl implements DetalleVentaService {

	@Autowired
	private DetalleVentaRepository detalleVentaRepository;

	@Autowired
	private VentaRepository ventaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private SaldoRepository saldoRepository;

	@Override
	public List<DetalleVenta> createDetalleVenta(DetalleVentaInfo detalleInfo) {
		if (detalleInfo.getVenta().getEfectivo() >= detalleInfo.getVenta().getTotal()) {
			Venta ventaCrear = new Venta();
			Saldo saldoActualizar = saldoRepository.findByFechaCreacion(new Date());
			int i = 0;

			ventaCrear.setEmpleado(detalleInfo.getVenta().getEmpleado());
			ventaCrear.setCliente(detalleInfo.getVenta().getCliente());
			ventaCrear.setEfectivo(detalleInfo.getVenta().getEfectivo());
			ventaCrear.setTotal(detalleInfo.getVenta().getTotal());

			ventaRepository.save(ventaCrear);

			for (Producto producto : detalleInfo.getProducto()) {
				DetalleVenta detalleVentaCrear = new DetalleVenta();
				Producto productoActualizar = productoRepository.findById(producto.getId()).get();

				productoActualizar.setExistencia(productoActualizar.getExistencia() - detalleInfo.getCantidad().get(i));
				productoActualizar.setFechaModificacion(new Date());

				detalleVentaCrear.setVenta(ventaCrear);
				detalleVentaCrear.setProducto(productoActualizar);
				detalleVentaCrear.setCantidad(detalleInfo.getCantidad().get(i));
				detalleVentaCrear.setMonto(detalleInfo.getMonto().get(i));

				detalleVentaRepository.save(detalleVentaCrear);

				productoRepository.save(productoActualizar);
				
				i++;
			}

			// Actualizar la venta total del saldo una vez que el saldo ha sido creado
			if (saldoActualizar != null) {
				// Sumar al total de ventas el total de la venta
				saldoActualizar.setVentaTotal(saldoActualizar.getVentaTotal() + ventaCrear.getTotal());
				saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
						- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
				saldoActualizar.setFechaModificacion(new Date());

				saldoRepository.save(saldoActualizar);
			}

			return readDetalleVentaByVenta(ventaCrear);
		}

		return null;
	}

	@Override
	public List<DetalleVenta> readDetalleVentaByVenta(Venta venta) {
		return detalleVentaRepository.findAllByVenta(venta);
	}

	@Override
	public DetalleVenta readDetalleVentaById(Long id) {
		return detalleVentaRepository.findById(id).get();
	}
	
	@Override
	public List<Producto> productosMasVendidos() {
		Pageable topTen = PageRequest.of(0, 7);
		return detalleVentaRepository.getProductosMasVendidos(topTen);
	}

	@Override
	public List<DetalleVenta> updateDetalleVenta(Venta venta, DetalleVentaInfo detalleInfo) {
		if (detalleInfo.getVenta().getEfectivo() >= detalleInfo.getVenta().getTotal()) {
			Venta ventaActualizar = ventaRepository.findById(venta.getId()).get();
			List<DetalleVenta> detallesVenta = readDetalleVentaByVenta(ventaActualizar);
			Saldo saldoActualizar = saldoRepository.findByFechaCreacion(ventaActualizar.getFechaCreacion());
			double total = ventaActualizar.getTotal();
			int i = 0;
			
			// Actualizar venta
			ventaActualizar.setEmpleado(detalleInfo.getVenta().getEmpleado());
			ventaActualizar.setEfectivo(detalleInfo.getVenta().getEfectivo());
			ventaActualizar.setTotal(detalleInfo.getVenta().getTotal());
			ventaActualizar.setFechaModificacion(new Date());

			ventaRepository.save(ventaActualizar);
			
			// Actualizar detalles de venta
			for (Long id : detalleInfo.getId()) {
				try {
					DetalleVenta detalleVentaActualizar = readDetalleVentaById(id);
					Producto productoActualizar = productoRepository.findById(
							detalleInfo.getProducto().get(i).getId()).get();
					
					if (detalleInfo.getCantidad().get(i) != detalleVentaActualizar.getCantidad()) {
						productoActualizar.setExistencia(productoActualizar.getExistencia() 
								+ detalleVentaActualizar.getCantidad());
						productoActualizar.setExistencia(productoActualizar.getExistencia() 
								- detalleInfo.getCantidad().get(i));
						productoActualizar.setFechaModificacion(new Date());

						productoRepository.save(productoActualizar);
					}

					detalleVentaActualizar.setProducto(productoActualizar);
					detalleVentaActualizar.setCantidad(detalleInfo.getCantidad().get(i));
					detalleVentaActualizar.setMonto(detalleInfo.getMonto().get(i));
					detalleVentaActualizar.setFechaModificacion(new Date());

					detalleVentaRepository.save(detalleVentaActualizar);
				}catch (NoSuchElementException e) {
					// Si no existe el detalle de venta se crea uno nuevo
					DetalleVenta detalleVentaCrear = new DetalleVenta();
					Producto productoActualizar = productoRepository.findById(
							detalleInfo.getProducto().get(i).getId()).get();

					productoActualizar.setExistencia(productoActualizar.getExistencia() - detalleInfo.getCantidad().get(i));
					productoActualizar.setFechaModificacion(new Date());

					detalleVentaCrear.setVenta(ventaActualizar);
					detalleVentaCrear.setProducto(productoActualizar);
					detalleVentaCrear.setCantidad(detalleInfo.getCantidad().get(i));
					detalleVentaCrear.setMonto(detalleInfo.getMonto().get(i));

					detalleVentaRepository.save(detalleVentaCrear);

					productoRepository.save(productoActualizar);
				}
				
				i++;
			}
			
			// Eliminar los detalles de venta que no existen al actualizar
			if(detallesVenta.size() > detalleInfo.getId().size()) {
				// Obtener los detalles que se desean eliminar, removiendo de la lista lo que aun existen
				for(int j= 0; j<detallesVenta.size(); j++){
		            for(int k = 0; k<detalleInfo.getId().size(); k++){
		                if(detallesVenta.get(j).getId() == detalleInfo.getId().get(k)){
		                   detallesVenta.remove(j);
		                }
		            }
		        }
				
				// Iterar sobre los detalles para eliminarlos
				for(DetalleVenta detalleEliminar: detallesVenta) {
					Producto productoActualizar = productoRepository.findByCodigoBarra(
							detalleEliminar.getProducto().getCodigoBarra());
					productoActualizar.setExistencia(productoActualizar.getExistencia() 
							+ detalleEliminar.getCantidad());
					productoActualizar.setFechaModificacion(new Date());

					productoRepository.save(productoActualizar);
					
					detalleEliminar.setFechaEliminacion(new Date());
					
					detalleVentaRepository.save(detalleEliminar);
				}
			}

			// Actualizar la venta total del saldo una vez que el saldo ha sido creado
			if (saldoActualizar != null) {
				// Sumar al total de ventas el total de la venta
				saldoActualizar.setVentaTotal(
						(saldoActualizar.getVentaTotal() - total) + detalleInfo.getVenta().getTotal());
				saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
						- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
				saldoActualizar.setFechaModificacion(new Date());

				saldoRepository.save(saldoActualizar);
			}

			return readDetalleVentaByVenta(venta);
		}
		return null;
	}

}
