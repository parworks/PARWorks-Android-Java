/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
