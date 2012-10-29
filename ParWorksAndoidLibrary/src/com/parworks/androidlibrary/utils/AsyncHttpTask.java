package com.parworks.androidlibrary.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpTask extends AsyncTask<HttpRequestInfo, Integer, HttpRequestInfo> {

	public AsyncHttpTask() {
		super();
	}

	protected HttpRequestInfo doInBackground(HttpRequestInfo... params) {
		HttpRequestInfo rinfo = params[0];
		try{
			HttpClient client = new DefaultHttpClient();
			HttpResponse resp = client.execute(rinfo.getRequest());
			rinfo.setResponse(resp);
		}
		catch (Exception e) {
			rinfo.setException(e);
		}
		return rinfo;
	}

	@Override
	protected void onPostExecute(HttpRequestInfo rinfo) {
		super.onPostExecute(rinfo);
		rinfo.requestFinished();
	}	
	
}