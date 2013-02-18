/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.parworks.androidlibrary.ar;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Returned after augmenting an image. Contains overlay information for a
 * particular augmented image.
 * 
 * @author Jules White
 * 
 */
@SuppressWarnings("serial")
public class AugmentedData implements Serializable {
	
	private static final ObjectMapper mapper = new ObjectMapper();

	private String mFov;
	@JsonProperty("focallength")
	private String mFocalLength;
	private String mScore;
	private List<Overlay> mOverlays;
	private boolean mLocalization;
	
	public AugmentedData() {
		
	}

	public AugmentedData(String fov, String focalLength, String score,
			boolean localization, List<Overlay> overlays) {
		mFov = fov;
		mFocalLength = focalLength;
		mScore = score;
		mLocalization = localization;
		mOverlays = overlays;
	}

	public String getFov() {
		return mFov;
	}

	public void setFov(String fov) {
		this.mFov = fov;
	}

	public String getFocalLength() {
		return mFocalLength;
	}

	public void setFocalLength(String focalLength) {
		this.mFocalLength = focalLength;
	}

	public String getScore() {
		return mScore;
	}

	public void setScore(String score) {
		this.mScore = score;
	}

	public List<Overlay> getOverlays() {
		return mOverlays;
	}

	public void setOverlays(List<Overlay> overlays) {
		this.mOverlays = overlays;
	}

	public boolean isLocalization() {
		return mLocalization;
	}

	public void setLocalization(boolean localization) {
		this.mLocalization = localization;
	}

	public String toString() {
		String res = null;
		try {
			res = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static AugmentedData readDataFromString(String input) {
		AugmentedData data = null;
		try {
			data = mapper.readValue(input, AugmentedData.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
