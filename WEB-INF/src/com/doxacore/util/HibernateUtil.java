package com.doxacore.util;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            System.out.println("==========INICIALIZANDO HIBERNATE==========");
            System.out.println("Datasource: " + getDataSource());

            // Cargar propiedades externas
            Properties props = new Properties();
            try (InputStream in = new FileInputStream(getDataSource())) {
                props.load(in);
            }

            // Construir registry de Hibernate
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()      // carga hibernate.cfg.xml
                    .applySettings(props) // sobrescribe propiedades si es necesario
                    .build();

            // Crear SessionFactory a partir de MetadataSources
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            System.out.println("SessionFactory creada correctamente.");
            
          /*  ConnectionProvider cp = sessionFactory.getSessionFactoryOptions()
            	    .getServiceRegistry()
            	    .getService(ConnectionProvider.class);*/

            	//System.out.println("Clase del ConnectionProvider: " + cp.getClass().getName());

        } catch (Throwable ex) {
            System.err.println("Error al inicializar Hibernate: " + ex.getMessage());
            ex.printStackTrace();
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