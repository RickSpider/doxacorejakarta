package com.doxacore;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.doxacore.login.UsuarioCredencial;
import com.doxacore.modelo.Modelo;
import com.doxacore.modelo.Modulo;
import com.doxacore.modelo.Operacion;
import com.doxacore.modelo.Rol;
import com.doxacore.modelo.SistemaPropiedad;
import com.doxacore.modelo.Usuario;
import com.doxacore.modelo.UsuarioRol;
import com.doxacore.util.Params;
import com.doxacore.util.Register;
import com.doxacore.util.UtilControlOperaciones;
import com.doxacore.util.UtilMetodos;

public abstract class TemplateViewModel {

	protected Register reg;
	protected Component mainComponent;
	protected UtilControlOperaciones uco;
	protected List<Operacion> lOperacionesModulo;
	protected List<Object[]> lUsuarioModuloOperaciones;
	protected UtilMetodos um;
	
	@Init
	public void initTemplateViewModel(@ContextParam(ContextType.VIEW) Component view, @ExecutionParam("arg") String arg) {

		this.reg = new Register();
		this.uco = new UtilControlOperaciones();
		this.um = new UtilMetodos();
		this.mainComponent = view;
		
		//System.out.println("EL CURRENT MODULO NAME ES "+currentModuloName);
		
		Modulo currentModulo = this.reg.getObjectByColumn(Modulo.class, "modulo", arg);
		
		this.lOperacionesModulo = uco.getOperacionesModulo(this.reg, currentModulo);
		this.lUsuarioModuloOperaciones = uco.getUsuarioModuloOperacion(reg, getCurrentUser().getAccount(), currentModulo.getModulo());
		this.inicializarOperaciones();
		
	}

	@AfterCompose
	public void afterComposeTemplateViewModel() {

	}
	
	protected abstract void inicializarOperaciones();
	
	protected boolean operacionHabilitada(String operacion) {
		
		return uco.operacionHabilitada(operacion, lOperacionesModulo, lUsuarioModuloOperaciones);
		
	}

	protected Usuario getCurrentUser() {

		UsuarioCredencial usuarioCredencial = (UsuarioCredencial) Sessions.getCurrent().getAttribute("userCredential");

		Usuario currentUser = this.reg.getObjectByColumn(Usuario.class, "account",
				usuarioCredencial.getAccount());

		return currentUser;

	}
	
	protected List<Rol> getRolesCurrentUser(){
		
		String campos[] = {"usuario"};
		Object[] valores = {this.getCurrentUser()};
		String []camposOrden = {"rolid"};
		String[] orden = {Register.ORDER_ASC};
		
		List<UsuarioRol> lur = this.reg.getAllObjectsByColumnsOrder(UsuarioRol.class, campos, valores, camposOrden, orden);
		
		List<Rol> lr = new ArrayList<Rol>();
		
		for (UsuarioRol x : lur) {
			
			lr.add(x.getRol());
			
		}
		
		return lr;
		
	}
	
	protected boolean isUserRolMaster() {
		
		Rol rol = this.reg.getObjectByColumn(Rol.class, "rol", Params.ROL_MASTER);
		
		String[] campos = {"usuariorolpk.usuario", "usuariorolpk.rol"};
		Object[] valores = {this.getCurrentUser(), rol};
		
		//UsuarioRol ur = this.reg.getObjectByCondicion(UsuarioRol.class.getName(), "usuarioid = "+this.getCurrentUser().getUsuarioid()+" AND rolid = "+rol.getRolid());
		
		UsuarioRol ur = this.reg.getObjectByColumns(UsuarioRol.class, campos, valores);
		
		if (ur == null) {
			
			return false;
		}
		
		return true;
		
	}

	protected <T extends Modelo> T save(T m) {

		return this.reg.saveObject(m, getCurrentUser().getAccount());

	}

	public void mensajeInfo(String texto) {
		org.zkoss.zul.Messagebox.Button b = Messagebox.show(texto, "Información",
				new Messagebox.Button[] { Messagebox.Button.OK }, Messagebox.INFORMATION, null);
	}

	public void mensajeSiNo(String texto, String titulo, EventListener event) {

		Messagebox.show(texto, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event);

	}

	public void mensajeAgregar(String texto, EventListener event) {

		mensajeSiNo(texto, "Agregar", event);

	}

	public void mensajeEliminar(String texto, EventListener event) {

		mensajeSiNo(texto, "Eliminar", event);

	}

	public void mensajeError(String texto) {
		org.zkoss.zul.Messagebox.Button b = Messagebox.show(texto, "Error",
				new Messagebox.Button[] { Messagebox.Button.OK }, Messagebox.ERROR, null);
	}

	protected List<Object[]> filtrarListaObject(String filtro, List<Object[]> listOri) {

		//System.out.println("VOY A FILTRAR " + listOri.size());

		List<Object[]> aux = new ArrayList<Object[]>();

		if (filtro.length() > 0) {

			for (Object[] x : listOri) {

				StringBuffer sbConcat = new StringBuffer();

				for (int i = 0; i < x.length; i++) {

					sbConcat.append(x[i]);
					sbConcat.append(" ");

				}

				if (sbConcat.toString().toUpperCase().contains(filtro.toUpperCase())) {

					aux.add(x);
					// System.out.println("FILTRANDO "+sbConcat.toString().toUpperCase());
					// System.out.println("FILTRO "+filtro.toUpperCase());

				}

			}

		} else {

			//System.out.println("NO FILTRE NADA");
			aux = listOri;

		}

		return aux;
	}
	
	protected List<Object[]> filtrarListaObject(String[] filtro, List<Object[]> listOri){
		
		List<Object[]> aux = new ArrayList<Object[]>();

		StringBuffer sbContent = new StringBuffer();

		for (int i = 0; i < filtro.length; i++) {

			sbContent.append(filtro[i]);

		}

		if (sbContent.toString().length() == 0) {

			return listOri;

		}

		for (Object[] x : listOri) {

			boolean[] existe = new boolean[filtro.length];
			
			for (int i = 0; i < filtro.length; i++) {

				if (filtro[i].length() > 0) {

					if (x[i] != null && x[i].toString().toUpperCase().contains(filtro[i].toUpperCase())) {

						existe[i] = true;

					} else {
						
						existe[i] = false;

					}


				}else {
					
					existe[i]=true;
					
				}
			}
			
			boolean filtrar = true;
			
			for (int i = 0; i<filtro.length;i++) {
				
				if (!existe[i]) {
					
					filtrar = false;
					break;
				}
				
			}
			
			if (filtrar) {
				
				aux.add(x);
				
			}

		}

		return aux;
		
	}
	
	protected <T extends Modelo> List<T> filtrarLT(String[] filtro, List<T> lmOri) {

		List<T> aux = new ArrayList<T>();

		StringBuffer sbContent = new StringBuffer();

		for (int i = 0; i < filtro.length; i++) {

			sbContent.append(filtro[i]);

		}

		if (sbContent.toString().length() == 0) {

			return lmOri;

		}

		for (T x : lmOri) {

			Object[] filtroModelo = x.getArrayObjectDatos();

			boolean[] existe = new boolean[filtro.length];
			
			for (int i = 0; i < filtro.length; i++) {

				if (filtro[i].length() > 0) {

					if (filtroModelo[i].toString().toUpperCase().contains(filtro[i].toUpperCase())) {

						existe[i] = true;

					} else {
						
						existe[i] = false;

					}


				}else {
					
					existe[i]=true;
					
				}
			}
			
			boolean filtrar = true;
			
			for (int i = 0; i<filtro.length;i++) {
				
				if (!existe[i]) {
					
					filtrar = false;
					break;
				}
				
			}
			
			if (filtrar) {
				
				aux.add(x);
				
			}

		}

		return aux;
	}
	
	
	protected void lastPageListBox(Listbox listbox) {

		listbox.setActivePage(listbox.getPageCount() - 1);

	}
	
	protected SistemaPropiedad getSistemaPropiedad(String clave) {
		
		SistemaPropiedad sp = this.reg.getObjectByColumn(SistemaPropiedad.class, "clave", clave);
		
		return sp;
		
	}
	

}
