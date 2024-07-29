package com.doxacore.components.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.doxacore.util.Register;

public class FinderModel {

	private Register register = new Register();

	private String nameFinder;
	private String sql;
	private String[] columns;
	private List<Object[]> listFinder;
	private List<Object[]> listFinderOri;

	public FinderModel(String nameFinder, String sql) {

		this.nameFinder = nameFinder;
		this.sql = sql;
		this.generarColumans();

	}

	private void generarColumans() {
	
		Pattern pattern = Pattern.compile("Select\\s+(.*?)\\s+from", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(this.sql);

		
		if (matcher.find()) {
			String columnsString = matcher.group(1);
			this.columns = columnsString.split(",(?![^()]*\\))");
			//this.columns = columnsString.split("\\s*,\\s*");

		}
		
		for (int i = 0; i<this.columns.length; i++) {
			
			this.columns[i] = this.columns[i].replaceAll(".*\\bas\\s+", "").toUpperCase();
			
		}

	}
		
	public void generarListFinder() {
		
		this.listFinderOri = this.register.sqlNativo(this.sql);
		this.listFinder = this.listFinderOri;
		
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String getNameFinder() {
		return nameFinder;
	}

	public void setNameFinder(String nameFinder) {
		this.nameFinder = nameFinder;
	}

	public List<Object[]> getListFinder() {
		
		return listFinder;
	}

	public void setListFinder(List<Object[]> listFinder) {
		this.listFinder = listFinder;
	}

	public List<Object[]> getListFinderOri() {
		return listFinderOri;
	}

	public void setListFinderOri(List<Object[]> listFinderOri) {
		this.listFinderOri = listFinderOri;
	}

}
