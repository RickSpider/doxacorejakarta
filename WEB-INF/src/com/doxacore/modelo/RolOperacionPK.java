package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class RolOperacionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4027905914324051222L;

	@ManyToOne
	@JoinColumn(name="rolid")
	private Rol rol = new Rol();
	
	@ManyToOne
	@JoinColumn(name="operacionid")
	private Operacion operacion= new Operacion();

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
