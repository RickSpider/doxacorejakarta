package com.doxacore.login.servicios;

import com.doxacore.modelo.Usuario;

public interface UserInfoService {
	
	/** find user by account **/
	public Usuario findUser(String account);
	
	/** update user **/
	public Usuario updateUser(Usuario user);

}
