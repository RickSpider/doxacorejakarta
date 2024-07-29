package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="tipos")
public class Tipo extends Modelo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5708926064943615784L;

	@Id
	@Column(name ="tipoid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoid;
	
	private String tipo;
	
	private String descripcion;

	@Column(unique = true)
	private String sigla;
	
	@ManyToOne
	@JoinColumn(name = "tipotipoid")
	private Tipotipo tipotipo;
	
	public Long getTipoid() {
		return tipoid;
	}

	public void setTipoid(Long tipoid) {
		this.tipoid = tipoid;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Tipotipo getTipotipo() {
		return tipotipo;
	}

	public void setTipotipo(Tipotipo tipotipo) {
		this.tipotipo = tipotipo;
	}
	
	@Override
	public String getStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getArrayObjectDatos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
