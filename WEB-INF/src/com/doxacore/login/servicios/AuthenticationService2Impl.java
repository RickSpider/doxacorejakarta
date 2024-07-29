package com.doxacore.login.servicios;

import java.io.Serializable;


import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.doxacore.login.UsuarioCredencial;

public class AuthenticationService2Impl implements AuthenticationService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean login(String account, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}
	
	public UsuarioCredencial getUserCredential(){
		Session sess = Sessions.getCurrent();
		UsuarioCredencial cre = (UsuarioCredencial)sess.getAttribute("userCredential");
		if(cre==null){
			cre = new UsuarioCredencial();//new a anonymous user and set to session
			sess.setAttribute("userCredential",cre);
		}
		return cre;
	}

}