package com.parworks.androidlibrary.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class OverlayContent implements Serializable {
	
	public enum OverlayContentType {
		URL, IMAGE, TEXT, VIDEO, FORM, AUDIO
	}
	
	private String type = "text";
	private String size;
	private String provider;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@JsonIgnore
	public OverlayContentType getOverlayContentType() {
		return OverlayContentType.valueOf(this.getType().toUpperCase());
	}
}
