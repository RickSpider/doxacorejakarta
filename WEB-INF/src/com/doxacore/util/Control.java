package com.doxacore.util;

import com.doxacore.modelo.SistemaPropiedad;

public class Control {
	
	protected Register reg;
	protected UtilMetodos um;
	
	public Control() {
		
		this.reg = new Register();
		this.um = new UtilMetodos();
		
	}
	
	public Control(Register reg) {
		
		this.reg = reg;
		this.um = new UtilMetodos();
	}
	
	protected SistemaPropiedad getSistemaPropiedad(String clave) {
		
		SistemaPropiedad sp = this.reg.getObjectByColumn(SistemaPropiedad.class, "clave", clave);
		
		return sp;
		
	}

}
