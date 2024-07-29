package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="RolesOperaciones")
public class RolOperacion extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4388354877545406068L;
	
	@EmbeddedId
	private RolOperacionPK roloperacionpk = new RolOperacionPK();
	
	public Rol getRol() {
		
		return this.roloperacionpk.getRol();
		
	}
	
	public void setRol(Rol rol) {
		
		this.roloperacionpk.setRol(rol);
		
	}
	
	public Operacion getOperacion() {
		
		return this.roloperacionpk.getOperacion();
		
	}
	
	public void setOperacion(Operacion operacion) {
		
		this.roloperacionpk.setOperacion(operacion);
		
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
