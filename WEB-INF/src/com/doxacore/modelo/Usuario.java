package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

/**
 * User entity
 */

@Entity
@Table(name = "usuarios")
public class Usuario extends Modelo implements Serializable, Cloneable {

	private static final long serialVersionUID = 5250682956698973491L;

	@Id
	@Column(name = "USUARIOID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioid;

	@Column(name = "account", unique = true)
	private String account;
	private String fullName;
	private String password;
	private String email;
	
	@ColumnDefault("false")
	private boolean activo = false;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String account, String password, String fullName, String email) {
		super();
		this.account = account;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
	}
	
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {

		this.account = account;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}

	public static Usuario clone(Usuario user) {
		try {
			return (Usuario) user.clone();
		} catch (CloneNotSupportedException e) {
			// not possible
		}
		return null;
	}

	public Long getUsuarioid() {
		return usuarioid;
	}

	public void setUsuarioid(Long usuarioid) {
		this.usuarioid = usuarioid;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String getStringDatos() {
		
		String aux = this.usuarioid + " " +this.account + " " + this.fullName ;
		
		return aux;
	}

	@Override
	public Object[] getArrayObjectDatos() {
		
		Object[] o = {this.account, this.fullName, this.email};
		
		return o;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
