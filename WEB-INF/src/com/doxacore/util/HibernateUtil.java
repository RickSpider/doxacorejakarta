package com.doxacore.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	static {
		try {

			System.out.println("==========DENTRO DEL HIBERNATE=========");
			System.out.println("Datasource:" + getDataSource()+"\n");

			InputStream ds = new FileInputStream(getDataSource());

			Properties p = new Properties();
			p.load(ds);

			sessionFactory = new Configuration().setProperties(p).configure().buildSessionFactory();

		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);

		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static String getDataSource() {

		return SystemInfo.SISTEMA_PATH_ABSOLUTO + "/WEB-INF/datasource.properties";

	}

}
