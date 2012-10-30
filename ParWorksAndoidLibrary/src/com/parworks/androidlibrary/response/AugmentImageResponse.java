package com.parworks.androidlibrary.response;
/**
 * Used to deserialize the response from the Augment Image endpoing
 * @author Adam Hickey
 *
 */
public class AugmentImageResponse {
	private boolean mSuccess;
	private String mImgId;
	
	public void setImgId(String imgId) {
		mImgId = imgId;
	}
	public String getImgId() {
		return mImgId;
	}
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	public boolean getSuccess() {
		return mSuccess;
	}
	
}
