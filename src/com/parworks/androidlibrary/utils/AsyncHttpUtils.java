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
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;

import com.parworks.androidlibrary.ar.ARException;

/**
 * Class for doing Asynchronous HTTP methods
 * @author Adam Hickey
 *
 */
public class AsyncHttpUtils {
	
	String mTime;
	String mApiKey;
	String mSignature;
	
	public AsyncHttpUtils(String apiKey, String time, String signature) {
		mTime = time;
		mApiKey = apiKey;
		mSignature = signature;
	}
	

	/**
	 * Does an asynchronous HTTP get
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url absolute url to endpoint
	 * @param params the parameters that will make up the query string
	 * @param callback the callback to receive the http response
	 */
	public void doGet(String url, Map<String,String> params, HttpCallback callback) {
		url = HttpUtils.appendQueryStringToUrl(url, params);
		HttpGet get = new HttpGet(url);
		get.setHeader("apikey", mApiKey);
		get.setHeader("salt",mTime);
		get.setHeader("signature",mSignature);
		HttpRequestInfo rinfo = new HttpRequestInfo(get, callback);
		AsyncHttpTask task = new AsyncHttpTask();
		task.execute(rinfo);
	}

	/**
	 * Does an asynchronous HTTP post 
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url absolute url to endpoing
	 * @param params the parameters that will make up the query string
	 * @param entity a multipart entity. Can be used to sending images to endpoints
	 * @param callback the callback to receive the http response
	 */
	public void doPost(String url, Map<String, String> params, MultipartEntity entity,
			HttpCallback callback) {
		try {

			url = HttpUtils.appendQueryStringToUrl(url, params);
			
			HttpPost post = new HttpPost(url);
			post.setHeader("apikey", mApiKey);
			post.setHeader("salt",mTime);
			post.setHeader("signature",mSignature);
			
			post.setEntity(entity);
			
			

			HttpRequestInfo rinfo = new HttpRequestInfo(post, callback);
			AsyncHttpTask task = new AsyncHttpTask();
			task.execute(rinfo);
		} catch (Exception e) {
			throw new ARException(e);
		}
	}
}
