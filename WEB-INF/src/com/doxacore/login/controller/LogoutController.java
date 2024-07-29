package com.doxacore.login.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

import com.doxacore.login.servicios.AuthenticationService;
import com.doxacore.login.servicios.AuthenticationService3Impl;

public class LogoutController extends SelectorComposer<Component> {

	// services
	AuthenticationService authService = new AuthenticationService3Impl();

	@Listen("onClick=#logout")
	public void doLogout() {
		authService.logout();
		Executions.sendRedirect("/doxacore/zul/main/");
	}
}
