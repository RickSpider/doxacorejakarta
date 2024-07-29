package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name="SistemaPropiedades"
	,indexes = {
        @Index(name="clave_index", columnList="clave", unique=true)
    })
public class SistemaPropiedad extends Modelo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7595484616798263671L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sistemapropiedadid;
	private String clave;
	private String valor;
	
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

	public Long getSistemapropiedadid() {
		return sistemapropiedadid;
	}

	public void setSistemapropiedadid(Long sistemapropiedadid) {
		this.sistemapropiedadid = sistemapropiedadid;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
