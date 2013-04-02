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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class OverlayContent implements Serializable {
	
	public enum OverlayContentType {
		URL, IMAGE, TEXT, VIDEO, FORM, AUDIO
	}
	
	public enum OverlaySize {
		SMALL, MEDIUM, LARGE, FULL_SCREEN
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
	
	@JsonIgnore
	public OverlaySize getOverlayContentSize() {
		OverlaySize res = null;
		try {
			res = OverlaySize.valueOf(this.getSize().toUpperCase());
		} catch (Exception e) {
			res = OverlaySize.LARGE;
		}
		return res;
	}
}
