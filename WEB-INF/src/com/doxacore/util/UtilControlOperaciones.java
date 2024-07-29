package com.doxacore.util;

import java.util.List;

import com.doxacore.modelo.Modulo;
import com.doxacore.modelo.Operacion;

public class UtilControlOperaciones {
	
	private UtilMetodos utilMetodos = new UtilMetodos(); 

	public List<Operacion> getOperacionesModulo(Register reg, Modulo modulo) {

		/*List<Operacion> lOperacionesModulo = reg.getAllObjectsByCondicionOrder(Operacion.class.getName(),
				"moduloid = " + modulo.getModuloid(), null);*/
		
		String [] campos = {"modulo"};
		Object [] valores = {modulo};
		
		List<Operacion> lOperacionesModulo = reg.getAllObjectsByCondicion(Operacion.class,
				campos, valores);

		return lOperacionesModulo;
	}

	
	public List<Object[]> getUsuarioModuloOperacion(Register reg, String usuario, String modulo) {
		
		String sqlNativo = utilMetodos.getCoreSql("usuarioModuloOperaciones.sql").replace("?1", usuario).replace("?2", modulo);
		
		List<Object[]> lUsuarioModuloOperacion = reg.sqlNativo(sqlNativo);
		
		return lUsuarioModuloOperacion;

	}
	
	public boolean operacionHabilitada(String operacion, List<Operacion> lOperacionesModulo, List<Object[]> lUsuarioModuloOperaciones) {
		
		boolean existeOperacionModulo = false;
		boolean existeOperacionUsuario = false;
		
		for (Operacion o : lOperacionesModulo) {
			
			if (o.getOperacion().compareTo(operacion) == 0) {
				
				existeOperacionModulo = true;
				break;
				
			}
			
		}
		
		for (Object [] o :lUsuarioModuloOperaciones ) {
			
			if (o[0].toString().compareTo(operacion)==0) {
				
				existeOperacionUsuario = true;
				break;
				
			}
			
		}
		
		if (existeOperacionModulo && existeOperacionUsuario) {
			
			return true;
			
		}
		
		return false;
	}

}
