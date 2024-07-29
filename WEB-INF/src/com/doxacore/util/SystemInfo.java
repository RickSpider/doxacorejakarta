package com.doxacore.util;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.select.SelectorComposer;

public class SystemInfo{

	public static String SISTEMA_PATH_ABSOLUTO;
	public static String SISTEMA_PATH_NOMBRE;
	public static String SISTEMA_PATH_DIRECTORIO;
	public static String SISTEMA_PAQUETE;

	//public static void init (WebApp webApp) {

	static {
	
		// DIRECTORIO_BASE_WEB = Executions.getCurrent().getDesktop().getCurrentDirectory();
		SISTEMA_PATH_ABSOLUTO = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		
		//SISTEMA_PATH_ABSOLUTO = webApp.getRealPath("/");
		
		try {
			Wini ini = new Wini(new File(SystemInfo.SISTEMA_PATH_ABSOLUTO + "/WEB-INF/sistema.ini"));
			SISTEMA_PATH_NOMBRE = ini.get("Sistema", "Nombre");
			SISTEMA_PATH_DIRECTORIO = "/" + ini.get("Sistema", "Directorio") + "/menu.zul";
			SISTEMA_PAQUETE = ini.get("Sistema", "Paquete");
			Params.CLASE_INICIO = Params.CLASE_INICIO.replace("?", SystemInfo.SISTEMA_PAQUETE);
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getSistemaNombre() {
		
		return SISTEMA_PATH_NOMBRE;
		
	}
	
}
