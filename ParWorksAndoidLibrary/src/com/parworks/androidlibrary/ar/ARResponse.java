/* 
 **
 ** Copyright 2012, Jules White
 **
 ** 
 */
package com.parworks.androidlibrary.ar;

import org.apache.http.HttpResponse;


public class ARResponse<T> {

	public static <T> ARResponse<T> from(HttpResponse resp, PayloadExtractor<T> extractor){
		ARResponse<T> arresp = null;
		try{
			T payload = extractor.extract(resp);
			int code = resp.getStatusLine().getStatusCode();
			boolean success = (code == 200 || code == 204);
			arresp = new ARResponse<T>(payload, code, success);
		}catch(Exception e){
			throw new ARException(e);
		}
		return arresp;
	}
	
	private final T payload_;

	private final boolean successful_;

	private final int responseCode_;

	private ARResponse(T payload, int responsecode, boolean success) {
		super();
		successful_ = success;
		payload_ = payload;
		responseCode_ = responsecode;
	}

	public T getPayload() {
		return payload_;
	}

	public boolean success() {
		return successful_;
	}

	public int getResponseCode() {
		return responseCode_;
	}

}
