package com.parworks.androidlibrary.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OverlayBoundary implements Serializable {
	
	private String type = "default";
	private String color;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}	

}
