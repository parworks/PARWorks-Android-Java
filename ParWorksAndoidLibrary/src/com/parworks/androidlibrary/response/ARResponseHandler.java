package com.parworks.androidlibrary.response;

import org.apache.http.HttpResponse;

/**
 * Parses the http response from an AR endpoint and creates the desired object.
 * @author Adam Hickey
 *
 */
public interface ARResponseHandler {
	
	/**
	 * The handleResponse method takes an HttpResponse from an AR endpoint and creates the appropriate response object from the com.parworks.androidlibary.response package.
	 * 
	 * Example:
	 *    HttpResponse responseFromGetSiteInfoEndpoint;
	 *    GetSiteInfoResponse siteInfo = responseHandler.handleResponse(responseFromGetSiteInfoEndpoint, GetSiteInfoResponse.class);
	 * @param serverResponse the http response returned from an AR endpoint
	 * @param typeOfResponse the type of response object corresponding to the endpoint
	 * @return the deserialized response object
	 */
	public <T> T handleResponse(HttpResponse serverResponse, Class<T> typeOfResponse );

}
