package com.doxacore.login;

import java.util.Map;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import com.doxacore.login.servicios.AuthenticationService;
import com.doxacore.login.servicios.AuthenticationService3Impl;


public class AuthenticationInit implements Initiator {
	
	//services
    AuthenticationService authService = new AuthenticationService3Impl();

    public void doInit(Page page, Map<String, Object> args) throws Exception {

        UsuarioCredencial cre = authService.getUserCredential();
        if(cre==null || cre.isAnonymous()){
            Executions.sendRedirect("/doxacore/zul/login/login.zul");
            return;
        }
    }

}
