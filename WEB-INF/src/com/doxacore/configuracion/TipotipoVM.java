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
import com.doxacore.modelo.Tipo;
import com.doxacore.modelo.Tipotipo;
import com.doxacore.util.Params;
import com.doxacore.util.Register;

public class TipotipoVM extends TemplateViewModel{

	private List<Tipotipo> lTipotipos;
	private List<Tipotipo> lTipotiposOri;
	private Tipotipo tipotipoSelected;
	private Tipotipo tipotipoSelectedTipo;
	private List<Tipo> lTiposTipotipos;
	private Tipo tipoSelected;
	
	private boolean opCrearTipotipo;
	private boolean opEditarTipotipo;
	private boolean opBorrarTipotipo;
	
	private boolean opCrearTipo;
	private boolean opEditarTipo;
	private boolean opBorrarTipo;
	
	@Init(superclass = true)
	public void initTipotipoVM() {

		cargarTipotipos();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeTipotipoVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {

		this.opCrearTipotipo = this.operacionHabilitada(Params.OP_CREAR_TIPOTIPO);
		this.opEditarTipotipo = this.operacionHabilitada(Params.OP_EDITAR_TIPOTIPO);
		this.opBorrarTipotipo = this.operacionHabilitada(Params.OP_BORRAR_TIPOTIPO);
		

		this.opCrearTipo = this.operacionHabilitada(Params.OP_CREAR_TIPO);
		this.opEditarTipo = this.operacionHabilitada(Params.OP_EDITAR_TIPO);
		this.opBorrarTipo = this.operacionHabilitada(Params.OP_BORRAR_TIPO);
		
	}
	
	private void cargarTipotipos() {
		
		String [] camposOrden = {"tipotipoid"};
		String [] orden = {Register.ORDER_ASC};
		

		this.lTipotipos = this.reg.getAllObjectsByOrden(Tipotipo.class, camposOrden, orden);
		this.lTipotiposOri = this.lTipotipos;
	}

	// seccion filtro

	private String filtroColumns[];

	private void inicializarFiltros() {

		this.filtroColumns = new String[3]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el
											// modelo

		for (int i = 0; i < this.filtroColumns.length; i++) {

			this.filtroColumns[i] = "";

		}

	}

	@Command
	@NotifyChange("lTipotipos")
	public void filtrarTipotipo() {

		this.lTipotipos = this.filtrarLT(this.filtroColumns, this.lTipotiposOri);

	}
	
	// seccion modal

		private Window modal;
		private boolean editar = false;

		@Command
		public void modalTipotipoAgregar() {
			
			if (!this.opCrearTipotipo) 
				return;

			this.editar = false;
			modalTipotipo(-1);

		}

		@Command
		public void modalTipotipo(@BindingParam("tipotipoid") long tipotipoid) {

			if (tipotipoid != -1) {
				
				if (!this.opEditarTipotipo)
					return;

				this.tipotipoSelected = this.reg.getObjectById(Tipotipo.class.getName(), tipotipoid);
				this.editar = true;

			} else {
				
				tipotipoSelected = new Tipotipo();

			}

			modal = (Window) Executions.createComponents("/doxacore/zul/configuracion/tipotipoModal.zul", this.mainComponent,
					null);
			Selectors.wireComponents(modal, this, false);
			modal.doModal();

		}

		@Command
		@NotifyChange("lTipotipos")
		public void guardar() {
			
			tipotipoSelected.setSigla(tipotipoSelected.getSigla().toUpperCase());

			this.save(tipotipoSelected);

			this.tipotipoSelected = null;

			this.cargarTipotipos();

			this.modal.detach();

			if (editar) {

				Notification.show("El Tipotipo fue Actualizado.");
				this.editar = false;
			} else {

				Notification.show("El Tipotipo fue agregado.");
			}

		}
		
		// fin modal
		
		@Command
		public void borrarTipotipoConfirmacion(@BindingParam("tipotipo") Tipotipo Tipotipo) {
			
			if(!this.opBorrarTipotipo)
				return;

			EventListener event = new EventListener() {

				@Override
				public void onEvent(Event evt) throws Exception {

					if (evt.getName().equals(Messagebox.ON_YES)) {

						borrarTipotipo(Tipotipo);

					}

				}

			};

			this.mensajeEliminar("El Tipotipo sera eliminado. \n Continuar?", event);
		}
		
		private void borrarTipotipo(Tipotipo tipotipo) {

			this.reg.deleteObject(tipotipo);

			this.cargarTipotipos();

			BindUtils.postNotifyChange(null, null, this, "lTipotipos");

		}
		
		// Seccion Tipo

		@Command
		@NotifyChange("lTiposTipotipos")
		public void refrescarTipos(@BindingParam("tipotipo") Tipotipo tipotipo) {

			String campos[] = {"tipotipo"};
			Object[] valores = {tipotipo};
			String []camposOrden = {"tipoid"};
			String[] orden = {Register.ORDER_ASC};
			
			this.tipotipoSelectedTipo = tipotipo;
			this.lTiposTipotipos = this.reg.getAllObjectsByCondicionOrden(Tipo.class, campos, valores, camposOrden, orden);

		}
		
		@Command
		public void modalTipoAgregar() {
			
			if (!this.opCrearTipo) {
				
				this.mensajeError("No tienes permiso para Crear un Tipo.");
				return;
				
			}

			if (this.tipotipoSelectedTipo == null) {
				
				this.mensajeInfo("Seleccione un tipotipo.");
				return;
			}
			
			this.editar = false;			
			modalTipo(-1);

		}
		
		@Command
		public void modalTipo(@BindingParam("tipoid") long tipoid) {

			if (tipoid != -1) {
				
				if (!this.opEditarTipo) {
					
					this.mensajeError("No tienes permiso para editar el Tipo.");
					return;
					
				}

				this.tipoSelected = this.reg.getObjectById(Tipo.class.getName(), tipoid);
				this.editar = true;

			} else {

				this.tipoSelected = new Tipo();
				this.tipoSelected.setTipotipo(this.tipotipoSelectedTipo);

			}

			modal = (Window) Executions.createComponents("/doxacore/zul/configuracion/tipoModal.zul", this.mainComponent,
					null);
			Selectors.wireComponents(modal, this, false);
			modal.doModal();

		}
		
		@Command
		@NotifyChange("lTiposTipotipos")
		public void guardarTipo() {
			
			this.tipoSelected.setSigla(this.tipoSelected.getSigla().toUpperCase());
			
			this.save(tipoSelected);

			this.tipoSelected = null;

			this.refrescarTipos(this.tipotipoSelectedTipo);

			this.modal.detach();

			if (editar) {

				Notification.show("El Tipo fue Actualizada.");
				this.editar = false;
			} else {

				Notification.show("El Tipo fue agregada.");
			}

		}
		
		@Command
		@NotifyChange("lTiposTipotipos")
		public void borrarTipoConfirmacion(@BindingParam("tipo") Tipo tipo) {
			
			if (!this.opBorrarTipo) {
				
				this.mensajeError("No tienes permiso para borrar el Tipo.");
				return;
				
			}

			this.mensajeEliminar("El Tipo sera eliminado. \n Continuar?", new EventListener() {

				@Override
				public void onEvent(Event evt) throws Exception {

					if (evt.getName().equals(Messagebox.ON_YES)) {

						borrarTipo(tipo);

					}

				}

			});
			
		}
		
		private void borrarTipo(Tipo tipo) {
			
			this.reg.deleteObject(tipo);

			this.refrescarTipos(this.tipotipoSelectedTipo);;

			BindUtils.postNotifyChange(null, null, this, "lTiposTipotipos");
			
		}

		// fin seccion Tipo


		public List<Tipotipo> getlTipotipos() {
			return lTipotipos;
		}

		public void setlTipotipos(List<Tipotipo> lTipotipos) {
			this.lTipotipos = lTipotipos;
		}

		public Tipotipo getTipotipoSelected() {
			return tipotipoSelected;
		}

		public void setTipotipoSelected(Tipotipo tipoTipoSelected) {
			this.tipotipoSelected = tipoTipoSelected;
		}

		public Tipotipo getTipotipoSelectedTipo() {
			return tipotipoSelectedTipo;
		}

		public void setTipotipoSelectedTipo(Tipotipo tipoTipoSelectedTipo) {
			this.tipotipoSelectedTipo = tipoTipoSelectedTipo;
		}

		public List<Tipo> getlTiposTipotipos() {
			return lTiposTipotipos;
		}

		public void setlTiposTipotipos(List<Tipo> lTiposTipotipos) {
			this.lTiposTipotipos = lTiposTipotipos;
		}

		public boolean isOpCrearTipotipo() {
			return opCrearTipotipo;
		}

		public void setOpCrearTipotipo(boolean opCrearTipotipo) {
			this.opCrearTipotipo = opCrearTipotipo;
		}

		public boolean isOpEditarTipotipo() {
			return opEditarTipotipo;
		}

		public void setOpEditarTipotipo(boolean opEditarTipotipo) {
			this.opEditarTipotipo = opEditarTipotipo;
		}

		public boolean isOpBorrarTipotipo() {
			return opBorrarTipotipo;
		}

		public void setOpBorrarTipotipo(boolean opBorrarTipotipo) {
			this.opBorrarTipotipo = opBorrarTipotipo;
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

		public Tipo getTipoSelected() {
			return tipoSelected;
		}

		public void setTipoSelected(Tipo tipoSelected) {
			this.tipoSelected = tipoSelected;
		}

		public boolean isOpCrearTipo() {
			return opCrearTipo;
		}

		public void setOpCrearTipo(boolean opCrearTipo) {
			this.opCrearTipo = opCrearTipo;
		}

		public boolean isOpEditarTipo() {
			return opEditarTipo;
		}

		public void setOpEditarTipo(boolean opEditarTipo) {
			this.opEditarTipo = opEditarTipo;
		}

		public boolean isOpBorrarTipo() {
			return opBorrarTipo;
		}

		public void setOpBorrarTipo(boolean opBorrarTipo) {
			this.opBorrarTipo = opBorrarTipo;
		}
		
		
}
