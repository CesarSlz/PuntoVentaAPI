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
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "venta")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado")
	private Empleado empleado;
	
	@Column(name = "cliente", nullable = false, unique = true)
	private Long cliente;
	
	@Min(value = 1)
	@Column(name = "efectivo", scale = 2, nullable = false)
	private Double efectivo;
	
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

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Double getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Double efectivo) {
		this.efectivo = efectivo;
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
