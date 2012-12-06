package com.parworks.androidlibrary.response;

import java.util.List;

public class GetSiteOverlaysResponse {
	
	private List<ImageOverlayInfo> overlays;
	private boolean success;

	public List<ImageOverlayInfo> getOverlays() {
		return overlays;
	}

	public void setOverlays(List<ImageOverlayInfo> overlays) {
		this.overlays = overlays;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
