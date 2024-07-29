package com.doxacore.modelo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
public abstract class Modelo{

	@CreationTimestamp 
    @Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creado;
	@Column(updatable = false)
	private String creadoUser;
	
	@CreationTimestamp 
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;
	private String modificacionUser;
	
	@Column(nullable=true)
	private Long orden; 
	
	public abstract String getStringDatos(); 
	public abstract Object[] getArrayObjectDatos();
	
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
	public Date getCreado() {
		return creado;
	}
	public void setCreado(Date creado) {
		this.creado = creado;
	}
	public String getCreadoUser() {
		return creadoUser;
	}
	public void setCreadoUser(String creadoUser) {
		this.creadoUser = creadoUser;
	}
	public Date getModificacion() {
		return modificacion;
	}
	public void setModificacion(Date modificacion) {
		this.modificacion = modificacion;
	}
	public String getModificacionUser() {
		return modificacionUser;
	}
	public void setModificacionUser(String modificacionUser) {
		this.modificacionUser = modificacionUser;
	}
	
}
