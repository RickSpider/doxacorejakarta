package com.doxacore.components;

public class Statbox {
	
	public static String styleWhite = "white";
	public static String styleSilverLighter = "silver-lighter";
	public static String styleSilver = "silver";
	public static String styleSilverDarker = "silver-darker";
	public static String styleBlack = "black";
	public static String styleBlackDarker = "black-darker";
	public static String styleBlackLighter = "black-lighter";
	public static String styleGrey = "grey";
	public static String styleGreyDarker = "grey-darker";
	public static String styleGreyLighter = "grey-lighter";
	public static String styleRed = "red";
	public static String styleRedDarker = "red-darker";
	public static String styleRedLighter = "red-lighter";
	public static String styleOrange = "orange";
	public static String styleOrangeDarker = "orange-darker";
	public static String styleOrangeLighter = "orange-lighter";
	public static String styleYellow = "yellow";
	public static String styleYellowDarker = "yellow-darker";
	public static String styleYellowLighter = "yellow-lighter";
	public static String styleGreen = "green";
	public static String styleGreenDarker = "green-darker";
	public static String styleGreenLighter = "green-lighter";
	public static String styleBlue = "blue";
	public static String styleBlueDarker = "blue-darker";
	public static String styleBlueLighter = "blue-lighter";
	public static String styleAqua = "aqua";
	public static String styleAquaDarker = "aqua-darker";
	public static String styleAquaLighter = "aqua-lighter";
	public static String stylePurple = "purple";
	public static String stylePurpleDarker = "purple-darker";
	public static String stylePurpleLighter = "purple-lighter";

	private String id;
	private String title;
	private String value;
	private String icon;
	private String style;
	
	private boolean showProgresbar = false;
	private double porcentaje;
	
	private boolean showButtonDetail = false;

	public Statbox(String id, String title, String value, String icon, String style) {
		super();
		this.id = id;
		this.title = title;
		this.value = value;
		this.icon = icon;
		this.style = style;
	}
	
	public Statbox(String id, String title, String value, String icon, String style, boolean showProgresbar, double porcentaje) {
		super();
		this.id = id;
		this.title = title;
		this.value = value;
		this.icon = icon;
		this.style = style;
		this.showProgresbar = showProgresbar;
		this.porcentaje = porcentaje;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getStyle() {
		return style;
	}

	public boolean isShowProgresbar() {
		return showProgresbar;
	}

	public void setShowProgresbar(boolean showProgresbar) {
		this.showProgresbar = showProgresbar;
	}

	public boolean isShowButtonDetail() {
		return showButtonDetail;
	}

	public void setShowButtonDetail(boolean showButtonDetail) {
		this.showButtonDetail = showButtonDetail;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
