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

public class AugmentedImage {
	
	private String siteId;
	private String imgId;
	private String userId;
	private int fullSizeWidth;
	private int fullSizeHeight;
	private String imgPath;	
	private String imgGalleryPath;
	private String imgContentPath;
	private long time;	
	private String output;
	
	public String getSiteId() {
		return siteId;
	}
	
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public String getImgId() {
		return imgId;
	}
	
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getImgGalleryPath() {
		return imgGalleryPath;
	}
	
	public void setImgGalleryPath(String imgGalleryPath) {
		this.imgGalleryPath = imgGalleryPath;
	}
	
	public String getImgContentPath() {
		return imgContentPath;
	}
	
	public void setImgContentPath(String imgContentPath) {
		this.imgContentPath = imgContentPath;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}

	public int getFullSizeWidth() {
		return fullSizeWidth;
	}

	public void setFullSizeWidth(int fullSizeWidth) {
		this.fullSizeWidth = fullSizeWidth;
	}

	public int getFullSizeHeight() {
		return fullSizeHeight;
	}

	public void setFullSizeHeight(int fullSizeHeight) {
		this.fullSizeHeight = fullSizeHeight;
	}
	
}
