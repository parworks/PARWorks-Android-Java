package com.parworks.androidlibrary.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OverlayConfiguration implements Serializable {
	
	private String title;
	private OverlayBoundary boundary = new OverlayBoundary();
	private OverlayCover cover = new OverlayCover();
	private OverlayContent content = new OverlayContent();
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public OverlayBoundary getBoundary() {
		return boundary;
	}
	
	public void setBoundary(OverlayBoundary boundary) {
		this.boundary = boundary;
	}
	
	public OverlayContent getContent() {
		return content;
	}
	
	public void setContent(OverlayContent content) {
		this.content = content;
	}
	
	public OverlayCover getCover() {
		return cover;
	}
	
	public void setCover(OverlayCover cover) {
		this.cover = cover;
	}
}
