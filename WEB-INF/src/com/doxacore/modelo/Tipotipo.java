package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="tipotipos")
public class Tipotipo extends Modelo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1455281298223200338L;
	@Id
	@Column(name ="tipotipoid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipotipoid;
	private String tipotipo;
	private String descripcion;
	
	@Column(unique = true)
	private String sigla;
	
	public Long getTipotipoid() {
		return tipotipoid;
	}
	public void setTipotipoid(Long tipotipoid) {
		this.tipotipoid = tipotipoid;
	}
	public String getTipotipo() {
		return tipotipo;
	}
	public void setTipotipo(String tipotipo) {
		this.tipotipo = tipotipo;
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
	@Override
	public String getStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] getArrayObjectDatos() {
	
		Object[] o = {this.tipotipo, this.descripcion, this.sigla};
		
		return o;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
