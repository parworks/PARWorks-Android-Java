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

public class BaseImageInfo {
	private String mId;
	private String mWidth;
	private String mHeight;
	private String mFullSize;
	private String mGallerySize;
	private String mContentSize;
	
	public void setContent_size(String contentSize) {
		mContentSize = contentSize;
	}
	public String getContentSize() {
		return mContentSize;
	}
	public void setGallery_size(String gallerySize) {
		mGallerySize = gallerySize;
	}
	public String getGallerySize() {
		return mGallerySize;
	}
	
	public void setFull_size(String fullSize) {
		mFullSize = fullSize;
	}
	public String getFullSize() {
		return mFullSize;
	}
	public void setHeight (String height) {
		mHeight = height;
	}
	public String getHeight() {
		return mHeight;
	}
	public void setWidth(String width) {
		mWidth = width;
	}
	public String getWidth() {
		return mWidth;
	}
	public void setId(String id) {
		mId = id;
	}
	public String getId() {
		return mId;
	}
	public String toString() {
		return mId+" , " +mWidth+" , " +mHeight+" , " +mFullSize+" , " +mGallerySize+" , " +mContentSize;
	}
	
	

}
