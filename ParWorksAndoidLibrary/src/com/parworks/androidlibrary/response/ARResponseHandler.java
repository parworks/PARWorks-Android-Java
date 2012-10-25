package com.parworks.androidlibrary.response;

import org.apache.http.HttpResponse;

public interface ARResponseHandler {
	
	public <T> T handleResponse(HttpResponse serverResponse, Class<T> typeOfResponse );

}
