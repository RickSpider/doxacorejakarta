package com.doxacore.login;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UsuarioCredencial implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String account;
	String name;
	String extra;
	
	Set<String> roles = new HashSet<String>();

	public UsuarioCredencial(String account, String name) {
		this.account = account;
		this.name = name;
	}

	public UsuarioCredencial() {
		this.account = "anonymous";
		this.name = "Anonymous";
		roles.add("anonymous");
	}

	public boolean isAnonymous() {
		return hasRole("anonymous") || "anonymous".equals(account);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasRole(String role){
		return roles.contains(role);
	}
	
	public void addRole(String role){
		roles.add(role);
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}



}