package com.doxacore.login.servicios;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.doxacore.modelo.Departamento;
import com.doxacore.modelo.Pais;
import com.doxacore.modelo.Usuario;
import com.doxacore.util.CargarPaisDepartamentoCiudad;
import com.doxacore.util.Register;
import com.doxacore.util.UtilStaticMetodos;


public class UserInfoService2Impl implements UserInfoService,Serializable{
	private static final long serialVersionUID = 1L;
	
	protected List<Usuario> userList = new ArrayList<Usuario>();  
	Register r = new Register();
		
	// algun dia cambiar este metodo por algo mas directo @Rick
	private void cargarListaUsuarios() {
		
		String[] campos =  {"activo"};
		Object[] valores = {true};
		
		userList = r.getAllObjectsByColumns(Usuario.class, campos, valores);
		
		if (userList.size() == 0 ) {
			
			UtilStaticMetodos.generarDatosInicio(r);
			//
			this.cargarListaUsuarios();
			
		}
		
		/*List<Pais> paises = r.getAllObjects(Pais.class.getName());
		
		Pais p;
		
		if (paises.size() == 0) {
			
			p = CargarPaisDepartamentoCiudad.cargarPais(r);
			
		}else {
			
			p = r.getObjectById(Pais.class.getName(), 1);
			
		}
		
		
		List<Departamento> departamentos = r.getAllObjects(Departamento.class.getName());
		
		if (departamentos.size() == 0) {
			
			CargarPaisDepartamentoCiudad.cargarDC(r,p);
			
		}*/
		
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized Usuario findUser(String account){
		
		cargarListaUsuarios();
		
		int s = userList.size();
		for(int i=0;i<s;i++){
			Usuario u = userList.get(i);
			if(account.equals(u.getAccount())){
				return Usuario.clone(u);
			}
		}
		return null;
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized Usuario updateUser(Usuario user){
		
		cargarListaUsuarios();
		
		int s = userList.size();
		for(int i=0;i<s;i++){
			Usuario u = userList.get(i);
			if(user.getAccount().equals(u.getAccount())){
				userList.set(i,u = Usuario.clone(user));
				return u;
			}
		}
		throw new RuntimeException("user not found "+user.getAccount());
	}
}