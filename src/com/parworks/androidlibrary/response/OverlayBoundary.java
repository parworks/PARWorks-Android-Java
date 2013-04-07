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

import android.annotation.SuppressLint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class OverlayBoundary implements Serializable {
	
	public enum OverlayBoundaryType {
		DASHED, SOLID, HIDE
	}
	
	private String type = "DASHED";
	private String color;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}	

	@SuppressLint("DefaultLocale")
	@JsonIgnore
	public OverlayBoundaryType getOverlayBoundaryType() {
		OverlayBoundaryType res = null;
		try {
			res = OverlayBoundaryType.valueOf(this.getType().toUpperCase());
		} catch (Exception e) {
			// make sure to return default in unexpected error state
			res = OverlayBoundaryType.DASHED;
		}
		return res;
	}
}
