package com.parworks.androidlibrary.ar;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;

import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.ApiKeys;
import com.parworks.androidlibrary.response.GetApiKeysResponse;
import com.parworks.androidlibrary.utils.HttpUtils;

/**
 * Used for Synchronously and Asynchronously get auth info.
 * 
 * @author Yu Sun
 */
public class ARAuth {
	
	/**
	 * Synchronously get the Api keys
	 * 
	 * @param email
	 *            the user's email
	 * @param password
	 *            the user's password
	 */
	public ApiKeys getApiKeys(String email, String password) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("email", email);
		parameterMap.put("password", password);
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse;
		serverResponse = httpUtils.doGet(HttpUtils.PARWORKS_AUTH_API_BASE_URL
				+ HttpUtils.RETRIEVE_KEY_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetApiKeysResponse getApiKeysResponse = responseHandler.handleResponse(
				serverResponse, GetApiKeysResponse.class);
		
		if (getApiKeysResponse.getSuccess() == true) {			
			return new ApiKeys(getApiKeysResponse.getApikey(), getApiKeysResponse.getSecretkey());
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to get the keys. The most likely cause is that the given credentails are invalid.");
		}
	}
	
	/**
	 * Asynchronously get the Api keys
	 * 
	 * @param email
	 *            the user's email
	 * @param password
	 *            the user's password
	 * @param listener
	 *            the callback which provides the ARSite once the call completes
	 */
	public void getApiKeys(final String email, final String password, final ARListener<ApiKeys> listener) {
		new AsyncTask<Void, Void, ApiKeys>() {
			@Override
			protected ApiKeys doInBackground(Void... params) {
				return getApiKeys(email, password);
			}
			@Override
			protected void onPostExecute(ApiKeys result) {
				listener.handleResponse(result);
			}
		}.execute();
	}
}
