package com.doxacore.util;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.zkoss.zul.South;

import com.doxacore.modelo.Modelo;
import com.doxacore.modelo.Usuario;


public class Register {
	
	public static String JOIN = "join";
	public static String LEFT_JOIN = "left join";
	public static String RIGHT_JOIN = "rigth join";
	public static String INNER_JOIN = "inner join";
	public static String FULL_OUTER_JOIN = "full outer join";
	
	public static String ORDER_ASC ="asc";
	public static String ORDER_DESC ="desc";
	
	 private Session currentSession() {
         return HibernateUtil.getSessionFactory().getCurrentSession();
     }

	
	public synchronized <T extends Modelo> T saveObject(T m, String Usuario) {
		
	
		 Session sess =  currentSession();
		 
		 if (m.getCreadoUser()== null || m.getCreadoUser().length() == 0) {
			 
			 m.setCreadoUser(Usuario);
			 
		 } else {
			 
			 m.setModificacion(new Date());

		 }
		 
		 m.setModificacionUser(Usuario);
		
		 //sess.saveOrUpdate(m);
		 m = (T) sess.merge(m);
		 
		 sess.flush();
		 
		 sess.clear();
		 
		 return m;
	}
	
	public synchronized <T extends Modelo> T getObjectById(String entityName, long id) {
		
		 Session sess =  currentSession();
		 
		 return (T) sess.get(entityName, id);
		
	}
	
	public synchronized <T extends Modelo> T getObjectByColumns(Class clase, String[] campos, Object[] valores) {
		
		Session sess =  currentSession();
		
		Query<List<T>> query;
		
		StringBuffer wo = new StringBuffer();
		
		if (campos != null && campos.length > 0) {
			
			wo.append(" where ");
			
			for (String x : campos) {
				
				if (wo.toString().contains(":")) {
					
					wo.append(" and ");
					
				}
				
				wo.append(" e."+x+" = :");
				if (!x.contains(".")) {
					
					wo.append(x);
					
				}else {
					
					String[] partes = x.split("\\.");
					String despuesDelPunto = partes.length > 1 ? partes[1] : null;
					wo.append(despuesDelPunto);
					
				}
				
			}
		}

		query = sess.createQuery("from " + clase.getName() +" e" + wo, clase);
		
		
		if (campos != null && campos.length > 0) {
			for (int i = 0; i<campos.length ; i++) {
				
				System.out.println(campos[i]);
				
				if (!campos[i].contains(".")) {
					
					query.setParameter(campos[i], valores[i]);
					
				}else {
					System.out.println("contiene");
					String[] partes = campos[i].split("\\.");
					String despuesDelPunto = partes.length > 1 ? partes[1] : null;
					query.setParameter(despuesDelPunto, valores[i]);
					
				}
				
				
				
			}
		}
		
		//System.out.println(query.getQueryString());
		
		return (T) query.getSingleResult();
		
	}
	
	public synchronized <T extends Modelo> T getObjectByColumn(Class clase, String campo, Object valor) {
		
		String[] campos = {campo};
		Object[] valores = {valor};
		
		return this.getObjectByColumns(clase ,campos , valores);
		
	}
	
	public synchronized <T extends Modelo> T getObjectBySigla(Class clase, Object valor) {
		
		String[] campos = {"sigla"};
		Object[] valores = {valor};
		
		return this.getObjectByColumns(clase ,campos , valores);
	}
	
	/*public synchronized <T extends Modelo> T getObjectByCondicion(String entityName, String condicion) {
		
		Session sess =  currentSession();
		
		return (T) sess.createQuery("from " + entityName + " where " + condicion ).uniqueResult();
	}*/
	
	/*public synchronized <T extends Modelo> T getObjectByColumn(Class clase, String column, Object value) {
		
		Session sess = currentSession();
		
		Query<T> query = sess.createQuery("from "+ clase.getName() +" where " +column+ "=:value", clase ).setParameter("value", value);
		
		return query.uniqueResult();
	}*/
	
	
	
	/*public synchronized <T extends Modelo> T getObjectBySigla(String entityName, String sigla) {
		
		Session sess = currentSession();
		
		Query<T> query = sess.createQuery("from "+ entityName +" where sigla=:value" ).setParameter("value", sigla);
		
		return query.uniqueResult();
	}*/
	
	public synchronized <T extends Modelo> List<T> getAllObjects(String entityName) {
		
		Session sess =  currentSession();

		return sess.createQuery("from " + entityName ).list();
			
	}
	
	public synchronized <T extends Modelo> List<T> getAllObjectsByCondicion(Class clase, String[] campos, Object[] valores){
		
		return getAllObjectsByCondicionOrden(clase, campos, valores, null, null);
		
	}
	
	public synchronized <T extends Modelo> List<T> getAllObjectsByOrden(Class clase, String[] camposOrden, String[] orden){
		
		return getAllObjectsByCondicionOrden(clase, null, null, camposOrden, orden);
		
	}
	
	
	public synchronized <T extends Modelo> List<T> getAllObjectsByCondicionOrden(Class clase, String[] campos, Object[] valores, String[] camposOrden, String[] orden) {
		
		Session sess =  currentSession();
		
		Query<List<T>> query;
		
		StringBuffer wo = new StringBuffer();
		
		if (campos != null && campos.length > 0) {
			
			wo.append(" where ");
			
			for (String x : campos) {
				
				if (wo.toString().contains(":")) {
					
					wo.append(" and ");
					
				}
				
				wo.append("e."+x+" = :");
				if (!x.contains(".")) {
					
					wo.append(x);
					
				}else {
					
					String[] partes = x.split("\\.");
					String despuesDelPunto = partes.length > 1 ? partes[1] : null;
					wo.append(despuesDelPunto);
					
				}
				
			}
		}
		
		if (camposOrden != null && camposOrden.length > 0) {
			
			wo.append(" order by ");
			
			for (int i = 0; i<camposOrden.length ; i++) {
				
				if (i!=0) {
					
					wo.append(",");
					
				}
				
				wo.append(" e."+camposOrden[i]+" "+orden[i]);
				
			}
			
		}

		query = sess.createQuery("from " + clase.getName() +" e" + wo, clase);
		
		
		if (campos != null && campos.length > 0) {
			for (int i = 0; i<campos.length ; i++) {
				
				System.out.println(campos[i]);
				
				if (!campos[i].contains(".")) {
					
					query.setParameter(campos[i], valores[i]);
					
				}else {
					System.out.println("contiene");
					String[] partes = campos[i].split("\\.");
					String despuesDelPunto = partes.length > 1 ? partes[1] : null;
					query.setParameter(despuesDelPunto, valores[i]);
					
				}
				
				
				
			}
		}
		
		//System.out.println(query.getQueryString());
		
		return (List<T>) query.getResultList();
			
	}
	
	public synchronized <T extends Modelo> void deleteObject(T m) {
		
		Session sess = currentSession();
		
		//sess.delete(m);
		sess.remove(m);
	}
	
	public synchronized List<Object[]> sqlNativo(String sql){
		
		Session sess = currentSession();
		
		return sess.createNativeQuery(sql, Object[].class).getResultList();
		
		//return sess.createSQLQuery(sql).list();
	}
	
}
