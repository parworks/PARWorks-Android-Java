/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.AddSiteResponse;
import com.parworks.androidlibrary.utils.HttpUtils;

public class ARSites {
	
	private String apiKey;
	private String salt;
	private String signature;
	private HttpUtils mHttpUtils = new HttpUtils();
	
	private final String BASE_URL = "http://dev.parworksapi.com"; 
	private final String CREATE_SITE_PATH = "/ar/site/add";
	
	
	public ARSites(String username, String password) {
		
		//TODO fix this!!! 
		
		apiKey = "parTestUser";
		salt = "foo";
		signature = "6p/ip68gea//V5H+dkvbnPG/RIPjai9tb3DdfbkQMj0=";
	}
	
	
	/**
	 * Async interfaces
	 * 
	 */
	public void create(String id, String name, double lon, double lat, String desc, String feature, String channel, ARListener<ARSite> listener){

		
		
	}
	
	public void create(String id, String desc, String channel, ARListener<ARSite> listener){
		
	}
	
	public List<ARSite> near(long lat, long lon, double heading, ARListener<List<ARSite>> sites){
		return null;
	}
	
	public void near(long lat, long lon, ARListener<List<ARSite>> sites){
	}
	
	public ARSite getExisting(String id){
		return null;
	}
	
	
	/*
	 * Please create synchronous equivalents of the above methods
	 * 
	 */
	
	public ARSite create(String id, String name, double lon, double lat, String desc, String feature, String channel){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		parameterMap.put("name",name);
		parameterMap.put("lon",Double.toString(lon));
		parameterMap.put("lat",Double.toString(lat));
		parameterMap.put("description",desc);
		parameterMap.put("feature", feature);
		parameterMap.put("channel",channel);
		HttpResponse serverResponse;
		try {
			serverResponse = mHttpUtils.doPost(apiKey, salt, signature, BASE_URL+CREATE_SITE_PATH, parameterMap);
		} catch (ClientProtocolException e) {
			throw new ARException("Couldn't create site: The HTTP response from the server was invalid.",e);
		} catch (IOException e) {
			throw new ARException("Couldn't create site: The HTTP connection was aborted or a problem occurred.",e);
		}
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AddSiteResponse addSiteResponse = responseHandler.handleResponse(serverResponse, AddSiteResponse.class);
		
		if(addSiteResponse.getSuccess() == true) {
			ARSite newSite = new ARSiteImpl(id);
			return newSite;
		} else {
			throw new ARException("Failed to create a new site.");
		}
		
	}
	
}
