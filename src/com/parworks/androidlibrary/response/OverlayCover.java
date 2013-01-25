package com.parworks.androidlibrary.response;

import java.io.Serializable;

import android.annotation.SuppressLint;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class OverlayCover implements Serializable {
	
	public enum OverlayCoverType {
		REGULAR,IMAGE, HIDE
	}
	
	private String type = "default";
	private int transparency;
	private String color;
	private String provider;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getTransparency() {
		return transparency;
	}
	
	public void setTransparency(int transparency) {
		this.transparency = transparency;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	@JsonIgnore
	public boolean isDefault() {
		if (type != null && type.equalsIgnoreCase("default")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public boolean isImage() {
		if (type != null && type.equalsIgnoreCase("image")) {
			return true;
		}
		return false;
	}
	
	@SuppressLint("DefaultLocale")
	@JsonIgnore
	public OverlayCoverType getOverlayCoverType() {
		OverlayCoverType res = null;
		try {
			res = OverlayCoverType.valueOf(this.getType().toUpperCase());
		} catch (Exception e) {
			// make sure to return default in unexpected error state
			res = OverlayCoverType.REGULAR;
		}
		return res;
	}
}
