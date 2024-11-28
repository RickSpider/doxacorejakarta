package com.doxacore.configuracion;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.doxacore.TemplateViewModel;
import com.doxacore.modelo.Rol;
import com.doxacore.modelo.Usuario;
import com.doxacore.modelo.UsuarioRol;
import com.doxacore.util.Params;
import com.doxacore.util.Register;
import com.doxacore.util.UtilStaticMetodos;


public class UsuarioVM extends TemplateViewModel {

	private List<Usuario> lUsuarios;
	private List<Usuario> lUsuariosOri;
	private List<UsuarioRol> lRolesUsuarios;
	private Usuario usuarioSelected;
	private Usuario usuarioSelectedRol;
	private String filtroColumnsUsuario[];
	
	private boolean opCrearUsuario;
	private boolean opEditarUsuario;
	private boolean opBorrarUsuario;
	private boolean opAgregarUsuarioRol;
	private boolean opQuitarUsuarioRol;

	@Init(superclass = true)
	public void initUsuarioVM() {
		
		cargarUsuarios();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeUsuarioVM() {
		
	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearUsuario = this.operacionHabilitada(Params.OP_CREAR_USUARIO);
		this.opEditarUsuario = this.operacionHabilitada(Params.OP_EDITAR_USUARIO);
		this.opBorrarUsuario = this.operacionHabilitada(Params.OP_BORRAR_USUARIO);
		this.opAgregarUsuarioRol = this.operacionHabilitada(Params.OP_AGREGAR_USUARIO_ROL);
		this.opQuitarUsuarioRol = this.operacionHabilitada(Params.OP_QUITAR_USUARIO_ROL);
		
	}

	private void cargarUsuarios() {
 
		String []camposOrden = {"usuarioid"};
		String[] orden = {Register.ORDER_ASC};
		
		this.lUsuarios = this.reg.getAllObjectsByOrden(Usuario.class, camposOrden, orden);
		this.lUsuariosOri = this.lUsuarios;

	}
	
	private void inicializarFiltros(){
		
		this.filtroColumnsUsuario = new String[3]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumnsUsuario.length; i++) {
			
			this.filtroColumnsUsuario[i] = "";
			
		}
		
	}

	@Command
	@NotifyChange("lUsuarios")
	public void filtrarUsuario() {

		lUsuarios = this.filtrarLT(this.filtroColumnsUsuario, this.lUsuariosOri);

	}

	// Seccion modal

	private Window modal;
	private boolean editar = false;

	@Command
	public void modalUsuarioAgregar() {
		
		if(!this.opCrearUsuario)
			return;

		this.editar = false;
		modalUsuario(-1);

	}
	
	@Command
	public void modalUsuario(@BindingParam("usuarioid") long usuarioid) {

		if (usuarioid != -1) {
			
			if(!this.opEditarUsuario)
				return;

			this.usuarioSelected = this.reg.getObjectById(Usuario.class.getName(), usuarioid);
			this.editar = true;

		} else {

			this.usuarioSelected = new Usuario();

		}

		modal = (Window) Executions.createComponents("/doxacore/zul/configuracion/usuarioModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lUsuarios")
	public void guardar() {
		
		if (this.usuarioSelected.getPassword().length() != 64) {
			
			this.usuarioSelected.setPassword((UtilStaticMetodos.getSHA256(this.usuarioSelected.getPassword())));		
			
		}
				
		this.save(usuarioSelected);

		this.usuarioSelected = null;

		this.cargarUsuarios();

		this.modal.detach();

		if (editar) {

			Notification.show("El Usuario fue Actualizado.");
			this.editar = false;

		} else {

			Notification.show("El Usuario fue agregado.");
		}

	}

	// Fin Seccion Modal

	@Command
	public void borrarUsuarioConfirmacion(@BindingParam("usuario") Usuario u) {
		
		if(!this.opBorrarUsuario)
			return;

		EventListener event = new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarUsuario(u);

				}

			}

		};

		this.mensajeEliminar("El usuario sera eliminado. \n Continuar?", event);
	}

	private void borrarUsuario(Usuario u) {

		this.reg.deleteObject(u);

		this.cargarUsuarios();

		BindUtils.postNotifyChange(null, null, this, "lUsuarios");

	}

	// Seccion roles usuario

	@Command
	@NotifyChange({"lRolesUsuarios","buscarRol"})
	public void refrescarRoles(@BindingParam("usuario") Usuario usuario) {

		String campos[] = {"usuariorolpk.usuario"};
		Object[] valores = {usuario};
		String []camposOrden = {"usuariorolpk.rol.rolid"};
		String[] orden = {Register.ORDER_ASC};
		
		this.usuarioSelectedRol = usuario;
		this.lRolesUsuarios = this.reg.getAllObjectsByColumnsOrder(UsuarioRol.class, campos, valores, camposOrden, orden);
		
		this.buscarSelectedRol = null;
		this.buscarRol="";

	}

	@Command
	public void borrarRolConfirmacion(@BindingParam("usuariorol") UsuarioRol ru) {
		
		if (!this.opQuitarUsuarioRol) {
			
			this.mensajeError("No tienes permisos para Borrar Roles a un Usuario.");
			
			return;
			
		}
		
		this.mensajeEliminar("El Rol "+ru.getRol().getRol()+" sera removido del Usuario "+ru.getUsuario().getAccount()+" \n Continuar?",  
				new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarUsuarioRol(ru);

				}

			}

		});

	}
	
	private void borrarUsuarioRol(UsuarioRol ru) {
		
		this.reg.deleteObject(ru);

		this.refrescarRoles(this.usuarioSelectedRol);

		BindUtils.postNotifyChange(null, null, this, "lRolesUsuarios");
		
	}

	// fin roles usuario

	// seccion buscador

	private List<Object[]> lRolesbuscarOri;
	private List<Object[]> lRolesBuscar;
	private Rol buscarSelectedRol;
	private String buscarRol = "";

	@Command
	@NotifyChange("lRolesBuscar")
	public void filtrarRolBuscar() {

		this.lRolesBuscar = this.filtrarListaObject(buscarRol, this.lRolesbuscarOri);

	}

	@Command
	@NotifyChange("lRolesBuscar")
	public void generarListaBuscarRol() {

		if (this.usuarioSelectedRol == null) {

			this.mensajeInfo("Selecciona un Usuario.");

			return;
		}

		this.lRolesBuscar = this.reg.sqlNativo("select r.rolid, r.rol, r.descripcion from roles r order by rolid;");
		this.lRolesbuscarOri = this.lRolesBuscar;
	}
	
	@Command
	@NotifyChange("buscarRol")
	public void onSelectRol(@BindingParam("id") long id) {
		
		this.buscarSelectedRol = this.reg.getObjectById(Rol.class.getName(), id);
		this.buscarRol = this.buscarSelectedRol.getRol();
		
	}

	@Command
	@NotifyChange({"lRolesUsuarios","buscarRol"})
	public void agregarRol() {
		
		if (!this.opAgregarUsuarioRol) {
			
			this.mensajeError("No tienes permiso para agregar un Rol al Usuario. ");
			return;
		}
		
		if (this.buscarSelectedRol == null) {
			
			this.mensajeInfo("Selecciona un Rol para agregar.");
			
			return;
			
		}
		
		for(UsuarioRol x : this.lRolesUsuarios) {
			
			if (this.buscarSelectedRol.getRolid() == x.getRol().getRolid()) {
				
				this.mensajeError("El Usuario ya tiene el rol "+x.getRol().getRol());
				
				return;
				
			}
			
		}
		
		UsuarioRol ur = new UsuarioRol();
		ur.setRol(this.buscarSelectedRol);
		ur.setUsuario(this.usuarioSelectedRol);
		this.save(ur);

		this.refrescarRoles(this.usuarioSelectedRol);

	}
	
	public List<Usuario> getlUsuarios() {
		return lUsuarios;
	}

	public void setlUsuarios(List<Usuario> lUsuarios) {
		this.lUsuarios = lUsuarios;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public List<UsuarioRol> getlRolesUsuarios() {
		return lRolesUsuarios;
	}

	public void setlRolesUsuarios(List<UsuarioRol> lRolesUsuarios) {
		this.lRolesUsuarios = lRolesUsuarios;
	}

	public List<Object[]> getlRolesBuscar() {
		return lRolesBuscar;
	}

	public void setlRolesBuscar(List<Object[]> lRolesBuscar) {
		this.lRolesBuscar = lRolesBuscar;
	}

	public String getBuscarRol() {
		return buscarRol;
	}

	public void setBuscarRol(String buscarRol) {
		this.buscarRol = buscarRol;
	}

	public String[] getFiltroColumnsUsuario() {
		return filtroColumnsUsuario;
	}

	public void setFiltroColumnsUsuario(String[] filtroColumnsUsuario) {
		this.filtroColumnsUsuario = filtroColumnsUsuario;
	}

	public boolean isOpCrearUsuario() {
		return opCrearUsuario;
	}

	public void setOpCrearUsuario(boolean opCrearUsuario) {
		this.opCrearUsuario = opCrearUsuario;
	}

	public boolean isOpEditarUsuario() {
		return opEditarUsuario;
	}

	public void setOpEditarUsuario(boolean opEditarUsuario) {
		this.opEditarUsuario = opEditarUsuario;
	}

	public boolean isOpBorrarUsuario() {
		return opBorrarUsuario;
	}

	public void setOpBorrarUsuario(boolean opBorrarUsuario) {
		this.opBorrarUsuario = opBorrarUsuario;
	}

	public boolean isOpAgregarUsuarioRol() {
		return opAgregarUsuarioRol;
	}

	public void setOpAgregarUsuarioRol(boolean opAgregarUsuarioRol) {
		this.opAgregarUsuarioRol = opAgregarUsuarioRol;
	}

	public boolean isOpQuitarUsuarioRol() {
		return opQuitarUsuarioRol;
	}

	public void setOpQuitarUsuarioRol(boolean opQuitarUsuarioRol) {
		this.opQuitarUsuarioRol = opQuitarUsuarioRol;
	}
	
	

}
