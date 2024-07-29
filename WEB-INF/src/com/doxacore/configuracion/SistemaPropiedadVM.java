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
import com.doxacore.modelo.SistemaPropiedad;
import com.doxacore.util.Params;
import com.doxacore.util.Register;

public class SistemaPropiedadVM extends TemplateViewModel{
	
	private List<SistemaPropiedad> lSistemaPropiedades; 
	private List<SistemaPropiedad> lSistemaPropiedadesOri;
	private SistemaPropiedad sistemaPropiedadSelected;
	
	private boolean opCrearSistemaPropiedad;
	private boolean opEditarSistemaPropiedad;
	private boolean opBorrarSistemaPropiedad;
	
	@Init(superclass = true)
	public void initSistemaPropiedadVM() {

		cargarSistemaPropiedades();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeSistemaPropiedadVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearSistemaPropiedad = this.operacionHabilitada(Params.OP_CREAR_SISTEMAPROPIEDAD);
		this.opEditarSistemaPropiedad = this.operacionHabilitada(Params.OP_EDITAR_SISTEMAPROPIEDAD);
		this.opBorrarSistemaPropiedad = this.operacionHabilitada(Params.OP_BORRAR_SISTEMAPROPIEDAD);
		
	}
	

	private void cargarSistemaPropiedades() {
	
		String []camposOrden = {"sistemapropiedadid"};
		String[] orden = {Register.ORDER_ASC};

		this.lSistemaPropiedades = this.reg.getAllObjectsByOrden(SistemaPropiedad.class,camposOrden, orden);
		this.lSistemaPropiedadesOri = this.lSistemaPropiedades;
	}
	
	//seccion filtro 
	
	private String filtroColumns[];
	
	private void inicializarFiltros(){
		
		this.filtroColumns = new String[2]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumns.length; i++) {
			
			this.filtroColumns[i] = "";
			
		}
		
	}
	
	@Command
	@NotifyChange("lSistemaPropiedades")
	public void filtrarSistemaPropiedad() {

		this.lSistemaPropiedades = this.filtrarLT(this.filtroColumns, this.lSistemaPropiedadesOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalSistemaPropiedadAgregar() {

		if(!this.opCrearSistemaPropiedad)
			return;

		this.editar = false;
		modalSistemaPropiedad(-1);

	}

	@Command
	public void modalSistemaPropiedad(@BindingParam("sistemapropiedadid") long sistemapropiedadid) {

		if (sistemapropiedadid != -1) {

			if(!this.opEditarSistemaPropiedad)
				return;
			
			this.sistemaPropiedadSelected = this.reg.getObjectById(SistemaPropiedad.class.getName(), sistemapropiedadid);
			this.editar = true;

		} else {
			
			sistemaPropiedadSelected = new SistemaPropiedad();

		}

		modal = (Window) Executions.createComponents("/doxacore/zul/configuracion/sistemaPropiedadModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lSistemaPropiedades")
	public void guardar() {
		
		if (!this.opCrearSistemaPropiedad) {
			
			this.mensajeInfo("No tienes permisos para crear Propiedades de Sistema");
			return;
		}

		this.reg.saveObject(sistemaPropiedadSelected, getCurrentUser().getAccount());

		this.sistemaPropiedadSelected = null;

		this.cargarSistemaPropiedades();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El SistemaPropiedad fue Actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El SistemaPropiedad fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarSistemaPropiedadConfirmacion(@BindingParam("sistemaPropiedad") SistemaPropiedad sistemaPropiedad) {
		
		if (!this.opBorrarSistemaPropiedad)
			return;
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarSistemaPropiedad(sistemaPropiedad);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El sistemaPropiedad sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarSistemaPropiedad (SistemaPropiedad sistemaPropiedad) {
		
		this.reg.deleteObject(sistemaPropiedad);
		
		this.cargarSistemaPropiedades();
		
		BindUtils.postNotifyChange(null,null,this,"lSistemaPropiedades");
		
	}
	
	
	
	

	public List<SistemaPropiedad> getlSistemaPropiedades() {
		return lSistemaPropiedades;
	}

	public void setlSistemaPropiedades(List<SistemaPropiedad> lSistemaPropiedades) {
		this.lSistemaPropiedades = lSistemaPropiedades;
	}

	public SistemaPropiedad getSistemaPropiedadSelected() {
		return sistemaPropiedadSelected;
	}

	public void setSistemaPropiedadSelected(SistemaPropiedad sistemaPropiedadSelected) {
		this.sistemaPropiedadSelected = sistemaPropiedadSelected;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public String[] getFiltroColumns() {
		return filtroColumns;
	}

	public void setFiltroColumns(String[] filtroColumns) {
		this.filtroColumns = filtroColumns;
	}
	
	public boolean isOpCrearSistemaPropiedad() {
		return opCrearSistemaPropiedad;
	}

	public void setOpCrearSistemaPropiedad(boolean opCrearSistemaPropiedad) {
		this.opCrearSistemaPropiedad = opCrearSistemaPropiedad;
	}

	public boolean isOpEditarSistemaPropiedad() {
		return opEditarSistemaPropiedad;
	}

	public void setOpEditarSistemaPropiedad(boolean opEditarSistemaPropiedad) {
		this.opEditarSistemaPropiedad = opEditarSistemaPropiedad;
	}

	public boolean isOpBorrarSistemaPropiedad() {
		return opBorrarSistemaPropiedad;
	}

	public void setOpBorrarSistemaPropiedad(boolean opBorrarSistemaPropiedad) {
		this.opBorrarSistemaPropiedad = opBorrarSistemaPropiedad;
	}
	
	
	
}
