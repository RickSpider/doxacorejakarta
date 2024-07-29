package com.doxacore.main.menu;

public class NavigationTitle {

	private String title;
	private String icon;
	
	public NavigationTitle(String title, String icon) {
		super();
		this.title = title;
		this.icon = icon;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
