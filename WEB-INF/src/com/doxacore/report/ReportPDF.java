package com.doxacore.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.doxacore.util.Register;
import com.doxacore.util.SystemInfo;
import com.doxacore.util.UtilMetodos;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public abstract class ReportPDF {

	protected Long id;
	protected String source;
	protected Map<String, Object> parameters;
	protected CustomDataSource dataSource;
	protected Register reg;
	protected UtilMetodos um;
	protected String fileName;
	
	public ReportPDF(Long id, String fileName) {
		
		this.source = SystemInfo.SISTEMA_PATH_ABSOLUTO+"/reportTemplate/";
		
		this.reg = new Register();
		this.um = new UtilMetodos();
		
		this.id = id;
		this.fileName = fileName;
		this.parameters = cargarParametros();
		cargarDataSource();
		
	}
	
	protected abstract Map<String, Object> cargarParametros();
	
	protected abstract String[] cargarColumas();
	
	protected abstract List<Object[]> cargarDatos();
	
	private void cargarDataSource() {
		
		dataSource = new CustomDataSource(cargarDatos(), cargarColumas());
		
	}
	
	public File getPDF() throws JRException, IOException {
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(this.source);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, this.parameters, dataSource);
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	    
	    byte[] pdfBytes = outputStream.toByteArray();
	    File fileInMemory = createFileInMemory(pdfBytes, fileName);
	    
	    return fileInMemory;
		
	}
	
	
	private static File createFileInMemory(byte[] content, String fileName) throws IOException {
        // Crear un archivo en memoria usando FileOutputStream
        File tempFile = File.createTempFile(fileName, ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content);
        }
        return tempFile;
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
