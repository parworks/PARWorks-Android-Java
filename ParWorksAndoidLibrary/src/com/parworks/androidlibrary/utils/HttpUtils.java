package com.parworks.androidlibrary.utils;

import java.io.IOException;
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


public class HttpUtils {
	
	public HttpResponse doGet(String apiKey, String salt,
			String signature, String url) throws ClientProtocolException, IOException {
		return doGet(apiKey,salt,signature,url, new HashMap<String,String>());
	}
	public HttpResponse doGet(String apiKey, String salt,
			String signature, String url, Map<String, String> queryString) throws ClientProtocolException, IOException {
		HttpResponse response = null;

		url = appendQueryStringToUrl(url, queryString);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		getRequest.setHeader("apikey", apiKey);
		getRequest.setHeader("salt", salt);
		getRequest.setHeader("signature", signature);

		response = httpClient.execute(getRequest);

		return response;

	}
	
	public HttpResponse doPost(String apiKey, String salt,
			String signature, String url, Map<String,String> queryString) throws ClientProtocolException, IOException {
		return doPost(apiKey, salt, signature, url,new MultipartEntity(), queryString);
	}
	public HttpResponse doPost(String apiKey, String salt,
			String signature, String url, MultipartEntity entity, Map<String,String> queryString) throws ClientProtocolException, IOException {
			
			url = appendQueryStringToUrl(url, queryString);		
			
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(url);
			
			
			postRequest.setHeader("apikey", apiKey);
			postRequest.setHeader("salt",salt);
			postRequest.setHeader("signature",signature);
			
			
			
			postRequest.setEntity(entity);
			HttpResponse response = null;
			
			response = httpClient.execute(postRequest);
			
			return response;

	}

	private String appendQueryStringToUrl(String url,
			Map<String, String> queryString) {
		url += "?";
		Iterator<Entry<String, String>> it = queryString.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			url += "&" + pairs.getKey() + "=" + pairs.getValue();
			it.remove();
		}

		return url;
	}

}
