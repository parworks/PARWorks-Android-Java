package com.parworks.androidlibrary.ar;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;
	
import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.ApiKeys;
import com.parworks.androidlibrary.response.GetApiKeysResponse;
import com.parworks.androidlibrary.utils.GenericAsyncTask;
import com.parworks.androidlibrary.utils.GenericAsyncTask.GenericCallback;
import com.parworks.androidlibrary.utils.HttpUtils;

/**
 * Used for Synchronously and Asynchronously get auth info.
 * 
 * @author Yu Sun
 */
public class ARAuth {
	
	/**
	 * Synchronously create an user account
	 * 
	 * @param email
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param listener
	 */
	public ApiKeys createAccount(String email, String password,
			String firstname, String lastname, String address1, String address2,
			String city, String state, String country, String zipcode) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("email", email);
		parameterMap.put("password", password);
		parameterMap.put("firstname", firstname);
		parameterMap.put("lastname", lastname);
		parameterMap.put("address1", address1);
		parameterMap.put("address2", address2);
		parameterMap.put("city", city);
		parameterMap.put("state", state);
		parameterMap.put("country", country);
		parameterMap.put("zipcode", zipcode);
		
		return createAccount(parameterMap);
	}

	private ApiKeys createAccount(Map<String, String> parameterMap) {
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(HttpUtils.PARWORKS_AUTH_API_BASE_URL
				+ HttpUtils.CREATE_USER_PATH, parameterMap);

		HttpUtils.handleStatusCode(serverResponse.getStatusLine()
				.getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		// the return of create user is the same as the return of get Api keys
		GetApiKeysResponse createUserResponse = responseHandler.handleResponse(
				serverResponse, GetApiKeysResponse.class);
		
		if (createUserResponse.getSuccess() == true) {			
			return new ApiKeys(createUserResponse.getApikey(), createUserResponse.getSecretkey());
		} else {
			throw new ARException(
					"Successfully communicated with the server, but failed to create the user. The most likely cause is that email/password are missing or already exist.");
		}
	}
	
	/**
	 * Asynchronously create an user account
	 * 
	 * @param email
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param listener
	 */
	public void createAccount(final String email, final String password,
			final String firstname, final String lastname, final String address1, final String address2,
			final String city, final String state, final String country, 
			final String zipcode, final ARListener<ApiKeys> listener, final ARErrorListener onErrorListener) {
		
		
		GenericCallback<ApiKeys> genericCallback = new GenericCallback<ApiKeys>() {

			@Override
			public ApiKeys toCall() {
				return createAccount(email, password, firstname, lastname, address1, address2, city, state, country, zipcode);
			}

			@Override
			public void onComplete(ApiKeys keys) {
				listener.handleResponse(keys);
				
			}

			@Override
			public void onError(Exception error) {
				onErrorListener.handleError(error);
				
			}
			
		};
		GenericAsyncTask<ApiKeys> asyncTask = new GenericAsyncTask<ApiKeys>(genericCallback);
		asyncTask.execute();
	}
	
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
	public void getApiKeys(final String email, final String password, final ARListener<ApiKeys> listener, final ARErrorListener onErrorListener) {
		
		GenericCallback<ApiKeys> genericCallback = new GenericCallback<ApiKeys>() {

			@Override
			public ApiKeys toCall() {
				return getApiKeys(email, password);
			}

			@Override
			public void onComplete(ApiKeys keys) {
				listener.handleResponse(keys);
				
			}

			@Override
			public void onError(Exception error) {
				onErrorListener.handleError(error);
				
			}
			
		};
		GenericAsyncTask<ApiKeys> asyncTask = new GenericAsyncTask<ApiKeys>(genericCallback);
		asyncTask.execute();
	}
}
