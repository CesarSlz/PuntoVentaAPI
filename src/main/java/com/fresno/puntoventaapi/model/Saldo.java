package com.fresno.puntoventaapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "saldo")
public class Saldo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado")
	private Empleado empleado;
	
	@Column(name = "fondo_caja", scale = 2, nullable = false)
	private Double fondoCaja;
	
	@Column(name = "abono_compra", scale = 2)
	private Double abonoCompra;
	
	@Column(name = "compra_total", scale = 2, nullable = false)
	private Double compraTotal;
	
	@Column(name = "venta_total", scale = 2, nullable = false)
	private Double ventaTotal;
	
	@Column(name = "total", scale = 2, nullable = false)
	private Double total;
	
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	
	@Column(name = "fecha_eliminacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEliminacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Double getFondoCaja() {
		return fondoCaja;
	}

	public void setFondoCaja(Double fondoCaja) {
		this.fondoCaja = fondoCaja;
	}

	public Double getAbonoCompra() {
		return abonoCompra;
	}

	public void setAbonoCompra(Double abonoCompra) {
		this.abonoCompra = abonoCompra;
	}

	public Double getCompraTotal() {
		return compraTotal;
	}

	public void setCompraTotal(Double compraTotal) {
		this.compraTotal = compraTotal;
	}

	public Double getVentaTotal() {
		return ventaTotal;
	}

	public void setVentaTotal(Double ventaTotal) {
		this.ventaTotal = ventaTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
	
}
