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
