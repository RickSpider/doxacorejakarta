package com.doxacore.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.doxacore.modelo.Modulo;
import com.doxacore.modelo.Operacion;

public class UtilControlOperaciones {
	
	private UtilMetodos utilMetodos = new UtilMetodos(); 

	/*public List<Operacion> getOperacionesModulo(Register reg, Modulo modulo) {

		
		
		String [] campos = {"modulo"};
		Object [] valores = {modulo};
		
		List<Operacion> lOperacionesModulo = reg.getAllObjectsByColumns(Operacion.class,
				campos, valores);

		return lOperacionesModulo;
	}*/
	
	public List<Operacion> getOperacionesModulo(Register reg, Modulo modulo) {
		
		return reg.getAllObjectsByColumns(
				Operacion.class,
				new String[]{"modulo"},
	            new Object[]{modulo});
		
	}

	
	/*public List<Object[]> getUsuarioModuloOperacion(Register reg, String usuario, String modulo) {
		
		String sqlNativo = utilMetodos.getCoreSql("usuarioModuloOperaciones.sql").replace("?1", usuario).replace("?2", modulo);
		
		List<Object[]> lUsuarioModuloOperacion = reg.sqlNativo(sqlNativo);
		
		return lUsuarioModuloOperacion;

	}*/
	
	
	public List<Object[]> getUsuarioModuloOperacion(Register reg, String usuario, String modulo) {
		
		return reg.sqlNativo(utilMetodos.getCoreSql("usuarioModuloOperaciones.sql").replace("?1", usuario).replace("?2", modulo));

	}
	
	/*public boolean operacionHabilitada(String operacion, List<Operacion> lOperacionesModulo, List<Object[]> lUsuarioModuloOperaciones) {
		
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
	}*/
	
	public boolean operacionHabilitada(String operacion, List<Operacion> lOperacionesModulo, List<Object[]> lUsuarioModuloOperaciones) {
	    // Crear conjuntos para las operaciones de módulo y usuario para una búsqueda más rápida
	    Set<String> operacionesModulo = lOperacionesModulo.stream()
	            .map(Operacion::getOperacion)
	            .collect(Collectors.toSet());
	    
	    Set<String> operacionesUsuario = lUsuarioModuloOperaciones.stream()
	            .map(o -> o[0].toString())
	            .collect(Collectors.toSet());

	    // Verificar si la operación está habilitada en ambos conjuntos
	    return operacionesModulo.contains(operacion) && operacionesUsuario.contains(operacion);
	}


}
