package com.doxacore.report;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CustomDataSource implements JRDataSource{
	
	private List<Object[]> data;
	private String[] columns;
	private int index = -1;
	
	public CustomDataSource(List<Object[]> data, String[] columns) {
		
		this.data = data;
		this.columns = columns;
		
	}


	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		
		Object value = null;
		String fieldName = arg0.getName();
		
		for (int i = 0; i<columns.length ; i++) {
			
			
			
			if (columns[i].compareTo(fieldName)==0) {
				
				System.out.println("column: "+columns[i]);
				System.out.println("fieldName: "+fieldName);
				
				
				value = data.get(index)[i];
				
				System.out.println("cargue value: "+value.toString());
				
				break;
			}
			
		}	
		
		return value;
	}

	@Override
	public boolean next() throws JRException {
		index++;
		return (index<data.size());
	}

}
