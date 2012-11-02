package com.parworks.androidlibrary.response;

import java.util.List;

public class ListBaseImagesResponse {
	
	private boolean mSuccess;
	private List<BaseImageInfo> mImages;
	
	public void setImages(List<BaseImageInfo> images) {
		mImages = images;
	}
	public List<BaseImageInfo> getImages() {
		return mImages;
	}
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	public boolean getSuccess() {
		return mSuccess;
	}

}
