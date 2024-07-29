package com.doxacore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class UtilMetodos {
	
	public String getSql(String fileName) {
		
		return this.getSql("/WEB-INF/sql/", fileName);
		
	}
	
	public String getCoreSql(String fileName) {
		
		return this.getSql("/doxacore/sql/", fileName);
		
	}
	
	public void ejecutarMetodo(String clase, String metodo){
		
		try {
			
			Class c = Class.forName(clase);
			Object o = c.newInstance();
			
			Method method = c.getDeclaredMethod(metodo);
			method.invoke(o);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String getSql(String folderPath, String fileName){
		
		String path = SystemInfo.SISTEMA_PATH_ABSOLUTO + folderPath + fileName;
		
		//System.out.println("Este el el getSql "+path);

		StringBuffer sb = new StringBuffer();

		String line = "";
		
		File file = new File(path);
		
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(file));
			
			while ((line = br.readLine()) != null) {

				sb.append(line).append("\n");

			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return sb.toString();

	}
	
	/**
	 * 
	 * Calcular fecha sumar, restar, hors, dias, semanas, meses, años.
	 * 
	 * @param fecha
	 * @param dato utilizar variables Estataicas de calendar Ej: Calendar.Calendar.HOUR
	 * @param cantidad
	 * @return
	 */
	public Date calcularFecha(Date fecha, int dato, int cantidad) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(dato, cantidad);

		return c.getTime();
	}
	
	public Date modificarHorasMinutosSegundos(Date fecha, int horas, int minutos, int segundos, int millisegundos) {
		
		Calendar cal = Calendar.getInstance();

		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, horas);
		cal.set(Calendar.MINUTE, minutos);
		cal.set(Calendar.SECOND, segundos);
		cal.set(Calendar.MILLISECOND, millisegundos);
		
		return cal.getTime();
		
	}

}
