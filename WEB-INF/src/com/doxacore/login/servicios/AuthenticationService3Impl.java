package com.doxacore.login.servicios;


import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.doxacore.login.UsuarioCredencial;
import com.doxacore.modelo.Usuario;

public class AuthenticationService3Impl extends AuthenticationService2Impl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	UserInfoService userInfoService = new UserInfoService2Impl(); 
	
	@Override
	public boolean login(String nm, String pd) {
		Usuario user = userInfoService.findUser(nm);
		//a simple plan text password verification
		if(user==null || !user.getPassword().equals(pd)){
			return false;
		}
		
		Session sess = Sessions.getCurrent();
		UsuarioCredencial cre = new UsuarioCredencial(user.getAccount(), user.getFullName());
		//just in case for this demo.
		if(cre.isAnonymous()){
			return false;
		}
		sess.setAttribute("userCredential",cre);
		
		//TODO handle the role here for authorization
		return true;
	}

	@Override
	public void logout() {
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("userCredential");
	}

}
