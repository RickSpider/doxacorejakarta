package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="Auditorias")
public class Auditoria extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6432592842934873814L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditoriaid;
	
	@Column(columnDefinition="text")
	private String json;
	private String usuario;
	private String modulo;
	private String sentencia;
		
	@Override
	public Object[] getArrayObjectDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAuditoriaid() {
		return auditoriaid;
	}

	public void setAuditoriaid(Long auditoriaid) {
		this.auditoriaid = auditoriaid;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSentencia() {
		return sentencia;
	}

	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}
