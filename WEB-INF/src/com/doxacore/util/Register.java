package com.doxacore.util;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.zkoss.zk.ui.Executions;

import com.doxacore.modelo.Modelo;
import jakarta.persistence.NoResultException;


public class Register {
	
	public static String JOIN = "join";
	public static String LEFT_JOIN = "left join";
	public static String RIGHT_JOIN = "rigth join";
	public static String INNER_JOIN = "inner join";
	public static String FULL_OUTER_JOIN = "full outer join";
	
	public static String ORDER_ASC ="asc";
	public static String ORDER_DESC ="desc";
	
	/* private Session getSession() {
         return HibernateUtil.getSessionFactory().getCurrentSession();
     }*/
	
	private Session getSession() {
		if (Executions.getCurrent() != null) {
	        // Estamos en ZK
			//System.out.println("HIbernate Utilizando zK");
	        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
	        if (!s.getTransaction().isActive()) s.beginTransaction();
	        return s;
	    } else {
	    	//System.out.println("HIbernate Utilizando Externo");
	        // NO estamos en ZK (Quartz, hilos externos, etc)
	        Session s = HibernateUtil.getSessionFactory().openSession();
	        s.beginTransaction();
	        return s;
	    }
	}

	public <T extends Modelo> T saveObject(T m, String usuario) {
	    Session sess = getSession(); // obtiene getSession() o openSession()
	    
	    try {
	        // seteo de auditoría
	        if (m.getCreadoUser() == null || m.getCreadoUser().isEmpty()) {
	            m.setCreadoUser(usuario);
	        } else {
	            m.setModificacion(new Date());
	        }
	        m.setModificacionUser(usuario);

	        // merge y flush
	        m = (T) sess.merge(m);
	        sess.flush();
	        return m;

	    } finally {
	        // Si la sesión NO pertenece a un thread OSIV, hacemos commit + close
	        if (!isOSIVThread(sess)) {
	            commitAndClose(sess);
	        }
	    }
	}
	  
	// ================= Helpers =================

	    /** Determina si la sesión pertenece a un thread OSIV de ZK */
	private boolean isOSIVThread(Session sess) {
	    try {
	        // Si la sesión actual es la misma que getCurrentSession(), es OSIV
	        Session current = HibernateUtil.getSessionFactory().getCurrentSession();
	        return current == sess;
	    } catch (Exception e) {
	        // No hay getSession -> no es OSIV
	        return false;
	    }
	}

	    /** Commit + Close para sesiones externas a OSIV */
	private void commitAndClose(Session session) {
	    if (session.isOpen()) {
	        try {
	            if (session.getTransaction().isActive()) {
	                session.getTransaction().commit();
	            }
	        } finally {
	            session.close();
	        }
	    }
	}
	  
	/*public <T extends Modelo> T saveObject(T m, String Usuario) {
		
	
		 Session sess =  getSession();
		 
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
	}*/
	
	public <T extends Modelo> List<T> saveObject(List<T> lm, String Usuario) {
		
		
		 Session sess =  getSession();
		 
		 for (T m : lm) {
			 
			 if (m.getCreadoUser()== null || m.getCreadoUser().length() == 0) {
				 
				 m.setCreadoUser(Usuario);
				 
			 } else {
				 
				 m.setModificacion(new Date());

			 }
			 
			 m.setModificacionUser(Usuario);
			 
			 m = (T) sess.merge(m);
			 
			 sess.flush();
			 sess.clear();
			 
		 }

		// sess.saveOrUpdate(m);
		
		 //sess.getTransaction().commit();
		 
		 //sess.flush();
		 
		// sess.clear();
		 
		 return lm;
	}	
	
	@Deprecated
	public <T extends Modelo> T getObjectById(String entityName, long id) {
		
		 Session sess =  getSession();
		// sess.setDefaultReadOnly(true);
		 T entity = (T) sess.find(entityName, id);
		// sess.evict(entity);
		 return entity ;
		
	}
	
	public <T extends Modelo> T findObjectById(Class<T> clase, long id) {
	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);
	    try {
	        return sess.find(clase, id); // moderno y no deprecado
	    } finally {
	        if (external) {
	            commitAndClose(sess);
	        }
	    }
	}
	
	public <T extends Modelo> T getObjectByColumns(Class<T> clase, String[] campos, Object[] valores) {
	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);

	    try {
	        StringBuilder hql = new StringBuilder("from ").append(clase.getName()).append(" e");

	        if (campos != null && campos.length > 0) {
	            hql.append(" where ");
	            for (int i = 0; i < campos.length; i++) {
	                if (i > 0) hql.append(" and ");
	                String col = campos[i].contains(".") ? campos[i].split("\\.")[1] : campos[i];
	                hql.append("e.").append(col).append(" = :").append(col);
	            }
	        }

	        Query<T> query = sess.createQuery(hql.toString(), clase);

	        if (campos != null && campos.length > 0) {
	            for (int i = 0; i < campos.length; i++) {
	                String col = campos[i].contains(".") ? campos[i].split("\\.")[1] : campos[i];
	                query.setParameter(col, valores[i]);
	            }
	        }

	        T entity = null;
	        try {
	            entity = query.getSingleResult();
	            if (external) sess.evict(entity);
	        } catch (NoResultException e) {
	            System.out.println("No result found for query");
	        }

	        return entity;

	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
	public <T extends Modelo> T getObjectByColumn(Class<T> clase, String campo, Object valor) {
		
		String[] campos = {campo};
		Object[] valores = {valor};
		
		return this.getObjectByColumns(clase ,campos , valores);
		
	}
	
	public <T extends Modelo> T getObjectBySigla(Class<T> clase, Object valor) {
		
		String[] campos = {"sigla"};
		Object[] valores = {valor};
		
		return this.getObjectByColumns(clase ,campos , valores);
	}
	
	/*public <T extends Modelo> T getObjectByCondicion(Class clase, String condicion) {
		
		Session sess =  getSession();
		
		Query<List<T>> query = sess.createQuery("from " + clase.getName() + " where " + condicion, clase );
		
		T entity = null;
	    try {
	        entity = (T) query.getSingleResult();
	        sess.evict(entity);
	    } catch (NoResultException e) {
	        // Manejar el caso donde no se encuentran resultados, devolviendo null o un valor por defecto
	        System.out.println("No result found for query");
	        return null;
	    }
	    
		return entity;
		
		//return (T) sess.createQuery("from " + entityName + " where " + condicion ).getSingleResult();
	}*/
	
	public <T extends Modelo> T getObjectByCondicion(Class<T> clase, String condicion) {
	    Session sess = getSession();                  // sesión segura OSIV o externa
	    boolean external = !isOSIVThread(sess);      // detecta sesión externa
	    try {
	        Query<T> query = sess.createQuery(
	            "from " + clase.getName() + " where " + condicion, clase
	        );

	        T entity = null;
	        try {
	            entity = query.getSingleResult();
	            if (external && entity != null) sess.evict(entity); // limpiar solo para threads externos
	        } catch (NoResultException e) {
	            System.out.println("No result found for query");
	        }

	        return entity;

	    } finally {
	        if (external) commitAndClose(sess); // cerramos solo si es sesión externa
	    }
	}
	
	public <T extends Modelo> List<T> getAllObjectsByCondicion(Class<T> clase, String condicion) {
		
		Session sess =  getSession();
		
		return  sess.createQuery("from " + clase.getName() + " where " + condicion, clase ).getResultList();
	
	}
	
	/*public synchronized <T extends Modelo> T getObjectByColumn(Class clase, String column, Object value) {
		
		Session sess = getSession();
		
		Query<T> query = sess.createQuery("from "+ clase.getName() +" where " +column+ "=:value", clase ).setParameter("value", value);
		
		return query.uniqueResult();
	}*/
	
	
	
	/*public synchronized <T extends Modelo> T getObjectBySigla(String entityName, String sigla) {
		
		Session sess = getSession();
		
		Query<T> query = sess.createQuery("from "+ entityName +" where sigla=:value" ).setParameter("value", sigla);
		
		return query.uniqueResult();
	}*/
	
	public <T extends Modelo> List<T> getAllObjects(Class<T> clase) {
		
		Session sess =  getSession();

		return sess.createQuery("from " + clase.getName(), clase ).list();
			
	}
	
	public <T extends Modelo> List<T> getAllObjectsByColumns(Class<T> clase, String[] campos, Object[] valores){
		
		return getAllObjectsByColumnsOrder(clase, campos, valores, null, null);
		
	}
	
	public <T extends Modelo> List<T> getAllObjectsByOrden(Class<T> clase, String[] camposOrden, String[] orden){
		
		return getAllObjectsByColumnsOrder(clase, null, null, camposOrden, orden);
		
	}
	
	
	public <T extends Modelo> List<T> getAllObjectsByColumnsOrder(
	        Class<T> clase, String[] campos, Object[] valores, String[] camposOrden, String[] orden) {

	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);

	    try {
	        StringBuilder hql = new StringBuilder("from ").append(clase.getName()).append(" e");

	       /* if (campos != null && campos.length > 0) {
	            hql.append(" where ");
	            for (int i = 0; i < campos.length; i++) {
	                if (i > 0) hql.append(" and ");
	                String col = campos[i].contains(".") ? campos[i].split("\\.")[1] : campos[i];
	                hql.append("e.").append(col).append(" :").append(col);
	            }
	        }*/
	        
	        if (campos != null && campos.length > 0) {
	            hql.append(" where ");
	            for (int i = 0; i < campos.length; i++) {
	                if (i > 0) hql.append(" and ");
	                String col = campos[i];
	                String param = col.replace(".", "_");
	                hql.append("e.").append(col).append(" = :").append(param); 
	            }
	        }

	        if (camposOrden != null && camposOrden.length > 0) {
	            hql.append(" order by ");
	            for (int i = 0; i < camposOrden.length; i++) {
	                if (i > 0) hql.append(",");
	                hql.append(" e.").append(camposOrden[i]).append(" ").append(orden[i]);
	            }
	        }

	        Query<T> query = sess.createQuery(hql.toString(), clase);

	        if (campos != null && campos.length > 0) {
	            for (int i = 0; i < campos.length; i++) {
	            	String col = campos[i].replace(".", "_");
	                query.setParameter(col, valores[i]);
	            }
	        }

	        List<T> results = query.getResultList();

	        if (external) sess.clear(); // limpieza solo para threads externos

	        return results;
	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
	public <T extends Modelo> void deleteObject(T entity) {
	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);
	    try {
	        sess.remove(entity);
	        sess.flush();
	        if (external) sess.evict(entity);
	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
	public List<Object[]> sqlNativo(String sql){
	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);
	  //  System.out.println("sqlNativo external = " + external);
	    try {
	        return sess.createNativeQuery(sql, Object[].class).getResultList();
	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
	public int sqlNativoIUD(String sql) {
	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);
	  //  System.out.println("sqlNativoIUD external = " + external);
	    try {
	        MutationQuery query = sess.createNativeMutationQuery(sql);
	        return query.executeUpdate();
	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
	public int[] sqlNativoIUDBatch(List<String> sqls) {
	    if (sqls == null || sqls.isEmpty()) return new int[0];

	    Session sess = getSession();
	    boolean external = !isOSIVThread(sess);

	    int batchSize = 50; // Ajusta según tu configuración de Hibernate y memoria
	    int[] resultados = new int[sqls.size()];

	    try {
	        for (int i = 0; i < sqls.size(); i++) {
	            MutationQuery query = sess.createNativeMutationQuery(sqls.get(i));
	            resultados[i] = query.executeUpdate();

	            // Flush y clear cada batchSize para mejorar performance
	            if ((i + 1) % batchSize == 0) {
	                sess.flush();
	                sess.clear();
	            }
	        }

	        // Flush final para asegurarnos de que todo se ejecute
	        sess.flush();
	        sess.clear();

	        return resultados;
	    } finally {
	        if (external) commitAndClose(sess);
	    }
	}
	
}
