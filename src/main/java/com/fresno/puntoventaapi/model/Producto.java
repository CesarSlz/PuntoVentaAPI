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
@Table(name = "producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@Column(name = "codigo_barra", length = 20, unique = true)
	private String codigoBarra;
	
	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;
	
	@Column(name = "marca", length = 100, nullable = false)
	private String marca;
	
	@Column(name = "precio_compra", scale = 2, nullable = false)
	private Double precioCompra;
	
	@Column(name = "precio_venta", scale = 2, nullable = false)
	private Double precioVenta;
	
	@Min(value = 0)
	@Column(name = "existencia", nullable = false)
	private Integer existencia;
	
	@Column(name = "tamano", length = 10)
	private String tamano;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Integer getExistencia() {
		return existencia;
	}

	public void setExistencia(Integer existencia) {
		this.existencia = existencia;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
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
