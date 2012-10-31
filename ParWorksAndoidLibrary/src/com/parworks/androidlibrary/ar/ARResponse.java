/* 
 **
 ** Copyright 2012, Jules White
 **
 ** 
 */
package com.parworks.androidlibrary.ar;

import org.apache.http.HttpResponse;

/**
 * Used by the ARListener callback to generically provide the response to an asynchronous request.
 * 
 * 
 * Example:
 *     ARsites arsites = new ARSites("user","password");
 *     arSites.near(latitude,longitude,max,radius,new ARListener<List<ARSite>>() {
 *     		
 *         public void handleResponse(ARResponse<List<ARSite>> resp) {
 *             List<ARSite> nearbySites = resp.getPayload();
 *             //do something with the sites...
 *         }
 * @author Jules White
 *
 * @param <T>
 */
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

	/**
	 * Used to get the desired object after an asynchronous request.
	 * 
	 * Example:
     *     ARsites arsites = new ARSites("user","password");
     *     arSites.near(latitude,longitude,max,radius,new ARListener<List<ARSite>>() {
     *     		
     *         public void handleResponse(ARResponse<List<ARSite>> resp) {
     *             List<ARSite> nearbySites = resp.getPayload();
     *             //do something with the sites...
     *         }
	 * @return the specified response object of type T
	 */
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
