package com.doxacore.report;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;

import com.doxacore.util.Register;
import com.doxacore.util.UtilMetodos;

public abstract class TemplateReportViewModel {

	protected Long id;
	protected String source = "/reportTemplate/";
	protected Map<String, Object> parameters;
	protected CustomDataSource dataSource;
	protected Register reg;
	protected UtilMetodos um;
	
	@Init
	public void initTemplateReportViewModel(@QueryParam("id") Long id) {
		
		this.reg = new Register();
		this.um = new UtilMetodos();
		
		this.id = id;
		this.parameters = cargarParametros();
		cargarDataSource();

	}

	@AfterCompose
	public void afterComposeTemplateReportViewModel() {

	}
	
	
	protected abstract Map<String, Object> cargarParametros();
	
	protected abstract String[] cargarColumas();
	
	protected abstract List<Object[]> cargarDatos();
	
	private void cargarDataSource() {
		
		dataSource = new CustomDataSource(cargarDatos(), cargarColumas());
		
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CustomDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(CustomDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
}
