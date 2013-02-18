package com.parworks.androidlibrary.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AugmentImageResultResponse {

	private String fov;
	@JsonProperty("focallength")
	private String focalLength;
	private String score;
	private List<OverlayAugmentResponse> overlays;
	private boolean localization;
	public String getFov() {
		return fov;
	}
	public void setFov(String fov) {
		this.fov = fov;
	}
	public String getFocalLength() {
		return focalLength;
	}
	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public List<OverlayAugmentResponse> getOverlays() {
		return overlays;
	}
	public void setOverlays(List<OverlayAugmentResponse> overlays) {
		this.overlays = overlays;
	}
	public boolean isLocalization() {
		return localization;
	}
	public void setLocalization(boolean localization) {
		this.localization = localization;
	}
}
