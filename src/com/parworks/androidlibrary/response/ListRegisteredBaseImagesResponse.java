package com.parworks.androidlibrary.response;

import java.util.List;

public class ListRegisteredBaseImagesResponse {
	
	private boolean mSuccess;
	private List<String> mImages;
	
	public void setImages(List<String> images) {
		mImages = images;
	}
	public List<String> getImages() {
		return mImages;
	}
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	public boolean getSuccess() {
		return mSuccess;
	}

}
