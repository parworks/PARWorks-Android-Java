package com.parworks.androidlibrary.utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;

import android.util.Log;

public class AsyncHttpUtils {

	public void doGet(String apiKey,String salt, String signature,String url, Map<String,String> params, HttpCallback callback) {
		url = HttpUtils.appendQueryStringToUrl(url, params);
		HttpGet get = new HttpGet(url);
		get.setHeader("apikey", apiKey);
		get.setHeader("salt",salt);
		get.setHeader("signature",signature);
		HttpRequestInfo rinfo = new HttpRequestInfo(get, callback);
		AsyncHttpTask task = new AsyncHttpTask();
		task.execute(rinfo);
	}

	public void doPost(String apiKey, String salt, String signature, String url, Map<String, String> params, MultipartEntity entity,
			HttpCallback callback) {
		try {

			url = HttpUtils.appendQueryStringToUrl(url, params);
			
			HttpPost post = new HttpPost(url);
			post.setHeader("apikey", apiKey);
			post.setHeader("salt",salt);
			post.setHeader("signature",signature);
			
			post.setEntity(entity);
			
			

			HttpRequestInfo rinfo = new HttpRequestInfo(post, callback);
			AsyncHttpTask task = new AsyncHttpTask();
			task.execute(rinfo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String responseToString(HttpResponse response) throws IOException{
		InputStream in = response.getEntity().getContent();
		InputStreamReader ir = new InputStreamReader(in);
		BufferedReader bin = new BufferedReader(ir);
		String line = null;
		StringBuffer buff = new StringBuffer();
		while((line = bin.readLine())!=null){
			buff.append(line+"\n");
		}
		bin.close();
		return buff.toString();
	}
}
