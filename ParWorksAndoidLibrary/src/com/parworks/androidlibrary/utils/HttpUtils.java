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
package com.parworks.androidlibrary.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.parworks.androidlibrary.ar.ARException;





/**
 * Class used for synchronous HTTP calls. Also contains the url and paths for all endpoints.
 * @author Adam Hickey
 *
 */
public class HttpUtils {
	
	public final static String PARWORKS_API_BASE_URL = "http://dev.parworksapi.com"; 

	
	public final static String BASE_IMAGE_PROCESSING_STATE_PATH = "/ar/site/process/state";
	public final static String INITIATE_BASE_IMAGE_PROCESSING_PATH = "/ar/site/process";
	public final static String GET_SITE_OVERLAYS_PATH = "ar/site/overlay";
	public final static String ADD_OVERLAY_PATH = "/ar/site/overlay/add";
	public final static String SAVE_OVERLAY_PATH = "/ar/site/overlay/save";
	public final static String REMOVE_OVERLAY_PATH = "/ar/site/overlay/remove";
	public final static String LIST_BASE_IMAGES_PATH = "/ar/site/image";
	public final static String ADD_BASE_IMAGE_PATH = "/ar/site/image/add";
	public final static String ADD_SITE_PATH = "/ar/site/add";
	public final static String AUGMENT_IMAGE_RESULT_PATH = "/ar/image/augment/result";
	public final static String AUGMENT_IMAGE_WITH_PROXIMITY_SEARCH_PATH = "/ar/image/augment/geo";
	public final static String AUGMENT_IMAGE_PATH = "/ar/image/augment";
	public final static String GET_SITE_INFO_PATH = "/ar/site/info";
	public final static String REMOVE_SITE_PATH = "/ar/site/remove";
	public final static String NEARBY_SITE_PATH = "/ar/site/nearby";
	public final static String HEALTH_CHECK_PATH = "/ar/ping";
	
	
	/**
	 * Synchronous HTTP get to the specified url. Sets the apikey, salt, and signature as headers.
	 * @param apiKey the user's api key.
	 * @param salt 
	 * @param signature
	 * @param url the absolute url or the endpoint
	 * @return the http response
	 */
	public HttpResponse doGet(String apiKey,
			String signature, String url) {
		return doGet(apiKey,signature,url, new HashMap<String,String>());
	}
	/**
	 * Synchronous HTTP get to the specified url. Sets the apikey, salt, and signature as headers.
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url absolute url to endpoing
	 * @param queryString 
	 * @return the http response
	 */
	public HttpResponse doGet(String apiKey,
			String signature, String url, Map<String, String> queryString) {
		HttpResponse response = null;

		url = appendQueryStringToUrl(url, queryString);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		getRequest.setHeader("apikey", apiKey);
		getRequest.setHeader("salt", ""+System.currentTimeMillis());
		getRequest.setHeader("signature", signature);

		try {
			response = httpClient.execute(getRequest);
		} catch (ClientProtocolException e) {
			throw new ARException("Couldn't create site: The HTTP response from the server was invalid.",e);
		} catch (IOException e) {
			throw new ARException("Couldn't create site: The HTTP connection was aborted or a problem occurred.",e);
		}

		return response;

	}
	
	/**
	 * Synchronous HTTP post to the specified url. Set's apikey, salt, and signature as headers.
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url absolute url to endpoing
	 * @param queryString
	 * @return the HTTP response
	 */
	
	public HttpResponse doPost(String apiKey, String signature, String url, Map<String,String> queryString) {
		return doPost(apiKey, signature, url,new MultipartEntity(), queryString);
	}
	/**
	 * Synchronous HTTP post to the specified url. Set's apikey, salt, and signature as headers.
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url
	 * @param entity a multipart entity that can be used for sending images to api endpoints
	 * @param queryString
	 * @return the http response
	 */
	public HttpResponse doPost(String apiKey, String signature, String url, MultipartEntity entity, Map<String,String> queryString) {
			
			url = appendQueryStringToUrl(url, queryString);		
			
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(url);
			
			
			postRequest.setHeader("apikey", apiKey);
			postRequest.setHeader("salt",""+System.currentTimeMillis());
			postRequest.setHeader("signature",signature);
			
			
			
			postRequest.setEntity(entity);
			HttpResponse response = null;
			
			try {
				response = httpClient.execute(postRequest);
			} catch (ClientProtocolException e) {
				throw new ARException("Couldn't create site: The HTTP response from the server was invalid.",e);
			} catch (IOException e) {
				throw new ARException("Couldn't create site: The HTTP connection was aborted or a problem occurred.",e);
			}
			
			return response;

	}

	public static String appendQueryStringToUrl(String url,
			Map<String, String> queryString) {
		url += "?";
		Iterator<Entry<String, String>> it = queryString.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			try {
				url += "&" + pairs.getKey() + "=" + URLEncoder.encode(pairs.getValue(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new ARException(e);
			}
			it.remove();
		}

		return url;
	}
	
	/**
	 * Returns if 226 >= statusCode >= 200
	 * Otherwise, throws an ARException.
	 * @param statusCode
	 */
	public static void handleStatusCode(int statusCode ) {
		if( (226 >= statusCode) && (statusCode >= 200) ) {
			return;
		} else {
			switch (statusCode) {
				
				case 400: throw new ARException("The server responsed with 400 bad request. There was probably a problem with the input parameters.");
				case 401: throw new ARException("The server responsed with 401 authentication failed. The credentials were incorrect.");
				case 404: throw new ARException("The server responsed with 404 problem accessing path. There was an error in the path.");
				
				default: throw new ARException("The server responded with status code: " + statusCode);
			
			}
		}
	}

}
