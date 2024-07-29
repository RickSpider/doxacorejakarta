package com.doxacore.components.finder;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

public interface FinderInterface {
	
	public void inicializarFinders();
	
	
	/**
	 * 
	 * @param finder
	 */
	public void generarFinders(@BindingParam("finder") String finder);
	
	/**
	 * 
	 * @param filter
	 * @param finder
	 */
	@Command	
	public void finderFilter(@BindingParam("filter") String filter, @BindingParam("finder") String finder);
	
	/**
	 * 
	 * @param id
	 * @param finder
	 */
	@Command
	public void onSelectetItemFinder(@BindingParam("id") Long id, @BindingParam("finder") String finder);
	
	

	
}
