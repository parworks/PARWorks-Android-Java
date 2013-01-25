package com.parworks.androidlibrary.response;

import java.io.Serializable;

import android.annotation.SuppressLint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class OverlayBoundary implements Serializable {
	
	public enum OverlayBoundaryType {
		DASHED, SOLID
	}
	
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

	@SuppressLint("DefaultLocale")
	@JsonIgnore
	public OverlayBoundaryType getOverlayBoundaryType() {
		OverlayBoundaryType res = null;
		try {
			res = OverlayBoundaryType.valueOf(this.getType().toUpperCase());
		} catch (Exception e) {
			// make sure to return default in unexpected error state
			res = OverlayBoundaryType.DASHED;
		}
		return res;
	}
}
