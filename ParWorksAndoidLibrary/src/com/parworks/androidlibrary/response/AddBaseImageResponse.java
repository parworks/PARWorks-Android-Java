package com.parworks.androidlibrary.response;

public class AddBaseImageResponse {
	private String mId;
	private boolean mSuccess;
	
	public void setId(String id) {
		mId = id;
	}
	public String getId() {
		return mId;
	}
	
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	
	public boolean getSuccess() {
		return mSuccess;
	}

}
