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
