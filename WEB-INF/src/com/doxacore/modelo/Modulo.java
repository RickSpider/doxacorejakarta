package com.doxacore.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name ="modulos")
public class Modulo extends Modelo implements Serializable {
	
	private static final long serialVersionUID = -7888659498929785444L;

	@Id
	@Column(name ="moduloid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long moduloid;
	
	@Column(unique=true)
	private String modulo;
	
	private String titulo;
	
	private String descripcion;
	
	private String path;
	
	private String menu;
	
	@ColumnDefault("false")
	private boolean habilitado= false;
	
	@ColumnDefault("false")
	private boolean pagInit= false;
	
	@Override
	public String getStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getArrayObjectDatos() {
		
		Object[] o = {this.modulo, this.descripcion, this.path};
		
		return o;
	}

	public Long getModuloid() {
		return moduloid;
	}

	public void setModuloid(Long moduloid) {
		this.moduloid = moduloid;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isPagInit() {
		return pagInit;
	}

	public void setPagInit(boolean pagInit) {
		this.pagInit = pagInit;
	}

	
}
