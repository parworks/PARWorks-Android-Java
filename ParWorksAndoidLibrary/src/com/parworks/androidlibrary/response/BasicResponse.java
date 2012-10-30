package com.parworks.androidlibrary.response;

/**
 * Used to deserialize the response from the Remove Overlay, Add Site, Remove Site, and Health Check Endpoints
 * @author Adam Hickey
 *
 */
public class BasicResponse {
	
	private boolean mSuccess;
	
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	public boolean getSuccess() {
		return mSuccess;
	}

}
