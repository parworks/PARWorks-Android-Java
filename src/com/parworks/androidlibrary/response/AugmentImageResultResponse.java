/*******************************************************************************
 * Copyright 2013 PAR Works, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
