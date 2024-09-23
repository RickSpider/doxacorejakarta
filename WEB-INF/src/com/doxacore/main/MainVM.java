package com.doxacore.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ini4j.InvalidFileFormatException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;

import com.doxacore.login.UsuarioCredencial;
import com.doxacore.main.menu.NavigationPage;
import com.doxacore.main.menu.NavigationTitle;
import com.doxacore.modelo.Modulo;
import com.doxacore.modelo.Operacion;
import com.doxacore.modelo.UsuarioRol;
import com.doxacore.util.Register;
import com.doxacore.util.SystemInfo;
import com.doxacore.util.UtilMetodos;

public class MainVM {

	private List<NavigationTitle> menus = new ArrayList<NavigationTitle>();
	private NavigationPage currentPage;
	private Map<String, Map<String, NavigationPage>> pageMap;
	private List<Modulo> lModulos = new ArrayList<Modulo>();
	private UtilMetodos utilMetodos = new UtilMetodos();

	@Init
	public void init() throws InvalidFileFormatException, IOException {
		
		initListaModulos();
		initPageMap();
		// currentPage = pageMap.get("Main").get("Blank");

		// pageMap.get("Main").values().size();
	}
	
	@AfterCompose
	public void afterCompose() {

	}

	private void initListaModulos() {

		Register reg = new Register();

		UsuarioCredencial usuarioCredencial = (UsuarioCredencial) Sessions.getCurrent().getAttribute("userCredential");

		String UsuarioOperacionesSQL = utilMetodos.getCoreSql("usuarioOperaciones.sql").replace("?1", "true")
				.replace("?2", usuarioCredencial.getAccount());

		List<Object[]> lUsuariosOperaciones = reg.sqlNativo(UsuarioOperacionesSQL);

		/*List<Operacion> lOperaciones = reg.getAllObjectsByCondicionOrder(Operacion.class.getName(), "abreModulo = true",
				null);*/
		
		String campos[] = {"abreModulo"};
		Object[] valores = {true};
				
		List<Operacion> lOperaciones = reg.getAllObjectsByCondicion(Operacion.class, campos,valores);

		for (Operacion o : lOperaciones) {

			if (o.getModulo().isHabilitado()) {

				for (Object[] obj : lUsuariosOperaciones) {

					if (o.getOperacion().compareTo(obj[0].toString()) == 0) {

						lModulos.add(o.getModulo());

					}

				}

			}

		}

	}

	@Command
	public void navigatePage(@BindingParam("target") NavigationPage targetPage) {

		BindUtils.postNotifyChange(null, null, currentPage, "selected");
		this.currentPage = targetPage;
		BindUtils.postNotifyChange(null, null, this, "currentPage");

	}

	public NavigationPage getCurrentPage() {
		return currentPage;
	}

	public Map<String, Map<String, NavigationPage>> getPageMap() {
		return pageMap;
	}

	public String getMenuSistema() {
		
		return SystemInfo.SISTEMA_PATH_DIRECTORIO+"/menu.zul";

	}

	private void initPageMap() throws InvalidFileFormatException, IOException {
		pageMap = new LinkedHashMap<String, Map<String, NavigationPage>>();

		// addPage("Main", "Blank", "/doxacore/zul/blank.zul");

		for (Modulo m : lModulos) {

			// System.out.println("cargado Modulo " + m.getModulo());
			addPage(m.getMenu(), m.getTitulo(), m.getPath(), m.getModulo());
			
			if (m.isPagInit()) {
				this.currentPage = pageMap.get(m.getMenu()).get(m.getTitulo());
			}

		}

	}

	public boolean menuVisible(@BindingParam("size") int size) {

		if (size == 0)
			return false;

		return true;
	}

	private void addPage(String title, String subTitle, String includeUri) {
		addPage(title, subTitle, includeUri, null);
	}

	private void addPage(String title, String subTitle, String includeUri, String data) {
		String folder = "/";
		Map<String, NavigationPage> subPageMap = pageMap.get(title);
		if (subPageMap == null) {
			subPageMap = new LinkedHashMap<String, NavigationPage>();
			pageMap.put(title, subPageMap);
		}
		NavigationPage navigationPage = new NavigationPage(title, subTitle,
				folder + includeUri + "?random=" + Math.random(), data) {
			@Override
			public boolean isSelected() {

				return currentPage == this;
			}
		};
		subPageMap.put(subTitle, navigationPage);
	}

	public List<NavigationTitle> getMenus() {
		return menus;
	}

	public void setMenus(List<NavigationTitle> menus) {
		this.menus = menus;
	}

}
