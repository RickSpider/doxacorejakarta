package com.doxacore.main;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.MatchMedia;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.doxacore.TemplateViewModel;
import com.doxacore.modelo.Usuario;
import com.doxacore.util.Register;
import com.doxacore.util.UtilStaticMetodos;

public class TopBarVM {

	private String currentUser;
	private String currentPass;
	private String newPass;
	private String newPassControl;
	private boolean collapsed = false;
	private String includeSclass ="content";
	protected Component mainComponent;
	private boolean visibleResponsive = true;

	@Init(superclass = true)
	public void initTopBarVM(@ContextParam(ContextType.VIEW) Component view) {

		this.mainComponent = view;

	}

	@AfterCompose(superclass = true)
	public void afterComposeTopBarVM() {

	}

	private Window modal;

	@Command
	public void cambiarPass(@BindingParam("user") String user) {

		this.currentUser = user;

		this.currentPass = "";
		this.newPass = "";
		this.newPassControl = "";

		modal = (Window) Executions.createComponents("/doxacore/zul/main/cambiarPassModal.zul", this.mainComponent,
				null);

		Selectors.wireComponents(modal, this, false);
		modal.doModal();
	}

	@Command
	@NotifyChange({"currentPass","newPass","newPassControl"})
	public void guardarPass(@BindingParam("btnGuardar") Button btnGuardar) {

		Register reg = new Register();
		
		Usuario user = reg.getObjectByColumn(Usuario.class, "account", this.currentUser);

		this.currentPass = UtilStaticMetodos.getSHA256(this.currentPass);

		if (this.currentPass.compareTo(user.getPassword()) != 0) {

			Notification.show("Contraseña actual incorrecta.", "error", btnGuardar, "after_start", 2000, false);

			this.currentPass = "";
			this.newPass = "";
			this.newPassControl = "";

			return;

		}
		
		if (this.newPass.length() == 0) {

			Notification.show("La contraseña no debe estar vacia.", "error", btnGuardar, "after_start", 2000, false);

			this.currentPass = "";
			this.newPass = "";
			this.newPassControl = "";
			return;
		}

		if (this.newPass.compareTo(this.newPassControl) != 0) {

			Notification.show("Contraseñas no coinciden.", "error", btnGuardar, "after_start", 2000, false);

			this.newPass = "";
			this.newPassControl = "";
			return;
		}
		
		user.setPassword(UtilStaticMetodos.getSHA256(this.newPass)); 
		
		reg.saveObject(user, this.currentUser);
		
		this.modal.detach();
		
		Notification.show("Contraseña Actualizada con exito.");

	}
	
	@Command
	@NotifyChange({"includeSclass", "collapsed"})
	public void collapsedAll() {
		
		this.collapsed = !this.collapsed;
		
		this.includeSclass="content";
		
		if (this.collapsed) {
			
			this.includeSclass="content collapsed";
			
		}
		
	}
	
	 @MatchMedia("all and (min-width: 958px)")
	 @NotifyChange({"collapsed", "includeSclass", "visibleResponsive"})
	 public void beWide(){
		 	
		 this.visibleResponsive = true;
		 this.collapsed = false;
		 this.includeSclass="content";
		
	    
	 }

	
	 @MatchMedia("all and (max-width: 957px)")
	 @NotifyChange({"collapsed", "includeSclass", "visibleResponsive"})
	 public void beNarrow(){
		 
		 this.visibleResponsive = false;
		 this.collapsed = true;
		 this.includeSclass="content collapsed";
		 
		 
	 }
	
	

	public String getCurrentPass() {
		return currentPass;
	}

	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewPassControl() {
		return newPassControl;
	}

	public void setNewPassControl(String newPassControl) {
		this.newPassControl = newPassControl;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getIncludeSclass() {
		return includeSclass;
	}

	public void setIncludeSclass(String includeSclass) {
		this.includeSclass = includeSclass;
	}

	public boolean isVisibleResponsive() {
		return visibleResponsive;
	}

	public void setVisibleResponsive(boolean visibleResponsive) {
		this.visibleResponsive = visibleResponsive;
	}

}
