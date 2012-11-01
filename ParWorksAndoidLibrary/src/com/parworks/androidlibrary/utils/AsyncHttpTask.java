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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

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