package com.parworks.androidlibrary.response;

public class SiteInfoSummary {
	
	private String id;
	private String siteState;
	private int numImages;
	private int numOverlays;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSiteState() {
		return siteState;
	}
	
	public void setSiteState(String siteState) {
		this.siteState = siteState;
	}
	
	public int getNumImages() {
		return numImages;
	}
	
	public void setNumImages(int numImages) {
		this.numImages = numImages;
	}
	
	public int getNumOverlays() {
		return numOverlays;
	}
	
	public void setNumOverlays(int numOverlays) {
		this.numOverlays = numOverlays;
	}
}
