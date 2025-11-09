package com.doxacore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
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
			Object o = c.getDeclaredConstructor().newInstance();
			
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
	
	public String getSHA512(String input) {
	    String toReturn = null;
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-512");
	        digest.reset();
	        digest.update(input.getBytes("UTF-8"));
	        // SHA-512 produce 64 bytes -> 128 caracteres hexadecimales
	        toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return toReturn;
	}
	
	public String RandomStringGenerator() {

	    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
	    SecureRandom random = new SecureRandom();
	    int length = 35;

	    
	    StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
	   
        return sb.toString();
	   
	}

}
