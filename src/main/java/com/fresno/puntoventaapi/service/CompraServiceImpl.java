package com.fresno.puntoventaapi.service;

import java.util.Date;
import java.util.List;

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
import com.fresno.puntoventaapi.model.Producto;
import com.fresno.puntoventaapi.model.Saldo;

@Service
@Transactional
public class CompraServiceImpl implements CompraService{
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private DetalleCompraRepository detalleCompraRepository;
	
	@Autowired 
	private ProductoRepository productoRepository;
	
	@Autowired
	private AdeudoRepository adeudoRepository;
	
	@Autowired
	private AbonoRepository abonoRepository;
	
	@Autowired
	private SaldoRepository saldoRepository;
	
	@Override
	public List<Compra> readCompras() {
		return compraRepository.findByNoEliminados();
	}

	@Override
	public Compra readCompraById(Long id) {
		return compraRepository.findById(id).get();
	}

	@Override
	public List<Compra> readCompraByFecha(Date fechaCreacion) {
		return compraRepository.findByFechaCreacion(fechaCreacion);
	}
	
	@Override
	public Double sumaCompras() {
		return compraRepository.getSumatoriaComprasByFecha();
	}

	@Override
	public Compra deleteCompra(Long id) {
		Compra compraEliminar = readCompraById(id);
		List<DetalleCompra> detalleCompra = detalleCompraRepository.findAllByCompra(compraEliminar);
		Adeudo adeudo = adeudoRepository.findByCompra(compraEliminar);
		List<Abono> abonos = abonoRepository.findAllByAdeudo(adeudo);
		Saldo saldoActualizar = null;
		
		// Puede eliminar cuando no hay adeudo o
		// cuando hay adeudo, no esta pagado y no hay abonos o
		// cuando hay adeudo, esta pagado y hay abonos
		//System.out.println(!abonos.isEmpty());
		if((adeudo == null) || (adeudo != null && !adeudo.getEstatus() && abonos.isEmpty()) ||
				(adeudo != null && adeudo.getEstatus())) {
		
			compraEliminar.setFechaEliminacion(new Date());
			
			if(adeudo != null) {
				adeudo.setFechaEliminacion(new Date());
				
				adeudoRepository.save(adeudo);
			}
			
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
			
			for(DetalleCompra detalles: detalleCompra) {
				DetalleCompra detalleCompraEliminar = detalleCompraRepository.findById(detalles.getId()).get();
				Producto productoActualizar = productoRepository.findById(detalles.getProducto().getId()).get();
				
				productoActualizar.setExistencia(productoActualizar.getExistencia() - detalles.getCantidad());
				productoActualizar.setFechaModificacion(new Date());
				
				detalleCompraEliminar.setFechaEliminacion(new Date());
			}
			
			// Obtener el saldo al que pertenece la compra
			saldoActualizar = saldoRepository.findByFechaCreacion(compraEliminar.getFechaCreacion());
			
			// Actualizar la compra total del saldo una vez que el saldo ha sido creado
			if(saldoActualizar != null) {
				// Verificar si la compra tiene un adeudo
				if(adeudo == null) {
					// Restar al total de compras el total de la compra ya que no tiene adeudo
					saldoActualizar.setCompraTotal(saldoActualizar.getCompraTotal() - compraEliminar.getTotal());
					saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
							- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
				}else {
					if(compraEliminar.getTotal() > compraEliminar.getEfectivo()) {
						// Restar al total de compras el efectivo de la compra
						saldoActualizar.setCompraTotal(saldoActualizar.getCompraTotal() - compraEliminar.getEfectivo());
						saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
								- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
					}else {
						// Restar al total de compras el total de la compra
						saldoActualizar.setCompraTotal(saldoActualizar.getCompraTotal() - compraEliminar.getTotal());
						saldoActualizar.setTotal((saldoActualizar.getVentaTotal() + saldoActualizar.getFondoCaja())
								- (saldoActualizar.getCompraTotal() + saldoActualizar.getAbonoCompra()));
					}
				}
				
				saldoActualizar.setFechaModificacion(new Date());
				saldoRepository.save(saldoActualizar);
			}
			
			return compraRepository.save(compraEliminar);
		}
		
		return null;
	}
}
