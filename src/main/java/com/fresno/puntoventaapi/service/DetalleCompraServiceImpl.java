package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresno.puntoventaapi.dao.AbonoRepository;
import com.fresno.puntoventaapi.dao.AdeudoRepository;
import com.fresno.puntoventaapi.dao.CompraRepository;
import com.fresno.puntoventaapi.dao.DetalleCompraRepository;
import com.fresno.puntoventaapi.dao.ProductoRepository;
import com.fresno.puntoventaapi.dao.SaldoRepository;
import com.fresno.puntoventaapi.model.Abono;
import com.fresno.puntoventaapi.model.Adeudo;
import com.fresno.puntoventaapi.model.Compra;
import com.fresno.puntoventaapi.model.DetalleCompra;
import com.fresno.puntoventaapi.model.DetalleCompraInfo;
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Saldo;

@Service
@Transactional
public class DetalleCompraServiceImpl implements DetalleCompraService {

	@Autowired
	private DetalleCompraRepository detalleCompraRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private AdeudoRepository adeudoRepository;

	@Autowired
	private AbonoRepository abonoRepository;
	
	@Autowired 
	private SaldoRepository saldoRepository;

	@Override
	public List<DetalleCompra> createDetalleCompra(DetalleCompraInfo detalleInfo) {
		Compra compraCrear = new Compra();
		Adeudo adeudoCrear = null;
		Saldo saldoActualizar = saldoRepository.findByFechaCreacion(new Date());
		int i  = 0;

		compraCrear.setEmpleado(detalleInfo.getCompra().getEmpleado());
		compraCrear.setProveedor(detalleInfo.getCompra().getProveedor());
		compraCrear.setEfectivo(detalleInfo.getCompra().getEfectivo());
		compraCrear.setTotal(detalleInfo.getCompra().getTotal());

		compraRepository.save(compraCrear);

		if (detalleInfo.getCompra().getEfectivo() < detalleInfo.getCompra().getTotal()) {
			adeudoCrear = new Adeudo();

			adeudoCrear.setCompra(compraCrear);
			adeudoCrear.setMonto(compraCrear.getTotal() - compraCrear.getEfectivo());
			adeudoCrear.setFechaCreacion(new Date());

			adeudoRepository.save(adeudoCrear);
		}

		for(Producto producto : detalleInfo.getProducto()) {
			DetalleCompra detalleCompraCrear = new DetalleCompra();
			Producto productoActualizar = productoRepository.findById(producto.getId()).get();
			
			productoActualizar.setExistencia(productoActualizar.getExistencia() + detalleInfo.getCantidad().get(i));
			productoActualizar.setFechaModificacion(new Date());

			detalleCompraCrear.setCompra(compraCrear);
			detalleCompraCrear.setProducto(productoActualizar);
			detalleCompraCrear.setCantidad(detalleInfo.getCantidad().get(i));
			detalleCompraCrear.setMonto(detalleInfo.getMonto().get(i));

			detalleCompraRepository.save(detalleCompraCrear);
			
			productoRepository.save(productoActualizar);
			
			i++;
		}
		
		// Actualizar la compra total del saldo una vez que el saldo ha sido creado
		if(saldoActualizar != null) {
			// Verificar si la compra genera un adeudo
			if(adeudoCrear == null) {
				// Sumar al total de compras el total de la compra ya que no tiene adeudo
				saldoActualizar.setCompraTotal(saldoActualizar.getCompraTotal() + compraCrear.getTotal());
				saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
						- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
			}else {
				// Sumar al total de compras el efectivo de la compra ya que tiene adeudo
				saldoActualizar.setCompraTotal(saldoActualizar.getCompraTotal() + compraCrear.getEfectivo());
				saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
						- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
			}
						
			saldoActualizar.setFechaModificacion(new Date());
			saldoRepository.save(saldoActualizar);
		}
		
		return readDetalleCompraByCompra(compraCrear);
	}

	@Override
	public List<DetalleCompra> readDetalleCompraByCompra(Compra compra) {
		return detalleCompraRepository.findAllByCompra(compra);
	}

	@Override
	public DetalleCompra readDetalleCompraById(Long id) {
		return detalleCompraRepository.findById(id).get();
	}

	@Override
	public List<DetalleCompra> updateDetalleCompra(Compra compra, DetalleCompraInfo detalleInfo) {
		// Obtener compra a actualizar
		Compra compraActualizar = compraRepository.findById(compra.getId()).get();
		
		// Obtener los detalles de compra
		List<DetalleCompra> detallesCompra = readDetalleCompraByCompra(compraActualizar);
		
		// Variables para actualizar el saldo ya que guardan el monto previo a la modificacion de la compra
		double total = compraActualizar.getTotal();
		double efectivo = compraActualizar.getEfectivo();

		// Obtener adeudo de la compra
		Adeudo adeudo = adeudoRepository.findByCompra(compraActualizar);

		// Obtener abonos del adeudo de la compra
		List<Abono> abonos = abonoRepository.findAllByAdeudo(adeudo);
		
		// Variable para obtener el saldo
		Saldo saldoActualizar = null;
		
		int i = 0;
		
		// Verificacion para poder editar la compra
		// Puede modificar cuando no hay adeudo o
		// cuando hay adeudo, no esta pagado y no hay abonos o
		// cuando hay adeudo, esta pagado y hay abonos
		if((adeudo == null) || (adeudo != null && !adeudo.getEstatus() && abonos.isEmpty()) || 
				(adeudo != null && adeudo.getEstatus())) {

			// Actualizar compra
			compraActualizar.setEmpleado(detalleInfo.getCompra().getEmpleado());
			compraActualizar.setEfectivo(detalleInfo.getCompra().getEfectivo());
			compraActualizar.setTotal(detalleInfo.getCompra().getTotal());
			compraActualizar.setFechaModificacion(new Date());

			// Guardar compra actualizada
			compraRepository.save(compraActualizar);
			
			// Verificar si el adeudo existe
			if(adeudo != null) {
				// Verificar si el total de la compra es mayor al efectivo
				if (compraActualizar.getTotal() > compraActualizar.getEfectivo()) {
					// Actualizar monto de adeudo
					adeudo.setEstatus(false);
					adeudo.setMonto(compraActualizar.getTotal() - compraActualizar.getEfectivo());
					adeudo.setFechaModificacion(new Date());
					adeudo.setFechaEliminacion(null);
				}else {
					// Eliminar adeudo
					adeudo.setFechaEliminacion(new Date());
					
					if(!abonos.isEmpty()) {
						// Iterar cada abono para eliminarlo
						for(Abono abono : abonos) {
							abono.setFechaEliminacion(new Date());
							abonoRepository.save(abono);
							
							// Obtener el saldo al que pertenece cada abono
							saldoActualizar = saldoRepository.findByFechaCreacion(abono.getFechaCreacion());
							
							if(saldoActualizar != null) {
								// Restar al abono a compras del saldo el monto del abono
								saldoActualizar.setAbonoCompra(saldoActualizar.getAbonoCompra() - abono.getMonto());
								saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
										- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
								saldoActualizar.setFechaModificacion(new Date());

								saldoRepository.save(saldoActualizar);
							}
						}
					}
				}
				
				// Guardar adeudo
				adeudoRepository.save(adeudo);
			}
			// Verificar si la compra sin adeudo al ser actualizada genera un adeudo
			else if(adeudo == null && compraActualizar.getTotal() > compraActualizar.getEfectivo()) {
				adeudo = new Adeudo();
				
				// Colocar valores al nuevo adeudo
				adeudo.setCompra(compraActualizar);
				adeudo.setMonto(compraActualizar.getTotal() - compraActualizar.getEfectivo());
				
				// Guardar adeudo
				adeudoRepository.save(adeudo);
			}
			
			// Actualizar detalles de compra
			// Recorrer el detalle de compra
			for (Long id : detalleInfo.getId()) {
				try {
					// Obtener detalle de compra a actualizar
					DetalleCompra detalleCompraActualizar = readDetalleCompraById(id);
					
					// Obtener producto a actualizar su existencia
					Producto productoActualizar = productoRepository.findById(
							detalleInfo.getProducto().get(i).getId()).get();
					
					// Verificar si se quiere actualizar la existencia del producto
					if (detalleInfo.getCantidad().get(i) != detalleCompraActualizar.getCantidad()) {
						// Actualizar existencia del producto
						productoActualizar.setExistencia(productoActualizar.getExistencia() 
								- detalleCompraActualizar.getCantidad());
						productoActualizar.setExistencia(productoActualizar.getExistencia() 
								+ detalleInfo.getCantidad().get(i));
						productoActualizar.setFechaModificacion(new Date());

						// Guardar producto actualizado
						productoRepository.save(productoActualizar);
					}
					
					// Actualizar detalle de compra
					detalleCompraActualizar.setProducto(productoActualizar);
					detalleCompraActualizar.setCantidad(detalleInfo.getCantidad().get(i));
					detalleCompraActualizar.setMonto(detalleInfo.getMonto().get(i));
					detalleCompraActualizar.setFechaModificacion(new Date());

					detalleCompraRepository.save(detalleCompraActualizar);
				} catch (NoSuchElementException e) {
					// Si no existe el detalle de compra se crea uno nuevo
					DetalleCompra detalleCompraCrear = new DetalleCompra();
					Producto productoActualizar = productoRepository.findById(
							detalleInfo.getProducto().get(i).getId()).get();

					productoActualizar.setExistencia(productoActualizar.getExistencia() + detalleInfo.getCantidad().get(i));
					productoActualizar.setFechaModificacion(new Date());

					detalleCompraCrear.setCompra(compraActualizar);
					detalleCompraCrear.setProducto(productoActualizar);
					detalleCompraCrear.setCantidad(detalleInfo.getCantidad().get(i));
					detalleCompraCrear.setMonto(detalleInfo.getMonto().get(i));

					detalleCompraRepository.save(detalleCompraCrear);

					productoRepository.save(productoActualizar);
				}
				
				i++;
			}
			
			// Eliminar los detalles de compra que no existen al actualizar
			if(detallesCompra.size() > detalleInfo.getId().size()) {
				// Obtener los detalles que se desean eliminar, removiendo de la lista lo que aun existen
				for(int j= 0; j<detallesCompra.size(); j++){
					for(int k = 0; k<detalleInfo.getId().size(); k++){
						if(detallesCompra.get(j).getId() == detalleInfo.getId().get(k)){
							detallesCompra.remove(j);
					    }
					}
				}
			
				// Iterar sobre los detalles para eliminarlos
				for(DetalleCompra detalleEliminar: detallesCompra) {
					Producto productoActualizar = productoRepository.findByCodigoBarra(
							detalleEliminar.getProducto().getCodigoBarra());
					productoActualizar.setExistencia(productoActualizar.getExistencia() 
										- detalleEliminar.getCantidad());
					productoActualizar.setFechaModificacion(new Date());

					productoRepository.save(productoActualizar);
								
					detalleEliminar.setFechaEliminacion(new Date());
								
					detalleCompraRepository.save(detalleEliminar);
				}
			}
			
			// Obtener el saldo al que pertenece la compra
			saldoActualizar = saldoRepository.findByFechaCreacion(compraActualizar.getFechaCreacion());
			
			// Actualizar la compra total del saldo una vez que el saldo ha sido creado
			if(saldoActualizar != null) {
				// Verificar si la compra tiene un adeudo
				if(adeudo == null) {
					// Sumar al total de compras el total de la compra ya que no tiene adeudo
					saldoActualizar.setCompraTotal((saldoActualizar.getCompraTotal() - total) 
							+ compraActualizar.getTotal());
					saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
							- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
				}else {
					// Verificar que cantidad se le restara a la compra total
					if(total > efectivo) {
						// Verificar que cantidad se le sumara a la compra total
						if(compraActualizar.getTotal() > compraActualizar.getEfectivo()) {
							// A la compra total del saldo restar efectivo y sumar el nuevo efectivo
							saldoActualizar.setCompraTotal((saldoActualizar.getCompraTotal() - efectivo)
									+ compraActualizar.getEfectivo());
							saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
									- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
						}else {
							// A la compra total del saldo restar el efectivo y sumar el nuevo total
							saldoActualizar.setCompraTotal((saldoActualizar.getCompraTotal() - efectivo)
									+ compraActualizar.getTotal());
							saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
									- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
						}
					}else {
						if(compraActualizar.getTotal() > compraActualizar.getEfectivo()) {
							// A la compra total del saldo restar el total de la compra y sumar el nuevo efectivo
							saldoActualizar.setCompraTotal((saldoActualizar.getCompraTotal() - total)
									+ compraActualizar.getEfectivo());
							saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
									- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
						}else {
							// A la compra total del saldo restar el total de la compra y sumar el nuevo total
							saldoActualizar.setCompraTotal((saldoActualizar.getCompraTotal() - total)
									+ compraActualizar.getTotal());
							saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
									- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
						}
					}
				}
							
				saldoActualizar.setFechaModificacion(new Date());
				saldoRepository.save(saldoActualizar);
			}

			return readDetalleCompraByCompra(compra);
		}
		
		return null;
	}
	
}
