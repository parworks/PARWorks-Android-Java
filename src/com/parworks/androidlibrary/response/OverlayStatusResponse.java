package com.parworks.androidlibrary.response;


public class OverlayStatusResponse {
	
	private OverlayStatus overlay;
	private boolean success;

	public OverlayStatus getOverlay() {
		return overlay;
	}

	public void setOverlay(OverlayStatus overlay) {
		this.overlay = overlay;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}	
}
