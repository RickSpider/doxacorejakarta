package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UsuarioRolPK implements Serializable {

	private static final long serialVersionUID = -2651519002670965545L;

	@ManyToOne
	@JoinColumn(name="usuarioid")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="rolid")
	private Rol rol;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
