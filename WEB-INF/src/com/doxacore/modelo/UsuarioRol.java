package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="UsuariosRoles")
public class UsuarioRol extends Modelo implements Serializable{
	
	private static final long serialVersionUID = 4970231789783816888L;
	
	@EmbeddedId
	private UsuarioRolPK usuariorolpk = new UsuarioRolPK();

	public UsuarioRolPK getUsuariorolpk() {
		return usuariorolpk;
	}

	public void setUsuariorolpk(UsuarioRolPK usuariorolpk) {
		this.usuariorolpk = usuariorolpk;
	}
	
	public void setUsuario(Usuario usuario) {
		
		this.usuariorolpk.setUsuario(usuario);
		
	}
	
	public Usuario getUsuario() {
		
		return usuariorolpk.getUsuario();
		
	}
	
	public void setRol(Rol rol) {
		
		this.usuariorolpk.setRol(rol);
		
	}
	
	public Rol getRol() {
		
		return usuariorolpk.getRol();
		
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
