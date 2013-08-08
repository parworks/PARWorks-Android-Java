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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parworks.androidlibrary.ar.ARException;
import com.parworks.androidlibrary.utils.BitmapUtils;

/**
 * 
 * @author Adam Hickey
 *
 */
public class ARResponseHandlerImpl implements ARResponseHandler {

	public static final String TAG = ARResponseHandlerImpl.class.getName();

	@Override
	public <T> T handleResponse(HttpResponse serverResponse, Class<T> typeOfResponse) {
		String contentString = ARResponseUtils.convertHttpResponseToString(serverResponse);
		return handleResponse(contentString,typeOfResponse);
	}

	@Override
	public <T> T handleResponse(String contentString, Class<T> typeOfResponse) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T responseObject = null;
		try {
			responseObject = mapper.readValue(contentString,typeOfResponse);
		} catch (JsonParseException e) {
			throw new ARException("Couldn't create the object " + typeOfResponse + " because the json was malformed : " + contentString,e);
		} catch (JsonMappingException e) {
			throw new ARException("Mapping the json response to the response object " + typeOfResponse + " failed.  Json was : " + contentString ,e);
		} catch (IllegalStateException e) {
			throw new ARException("Couldn't convert the json to object " + typeOfResponse + " because of illegal state. Json was : " + contentString,e);
		} catch (IOException e) {
			throw new ARException("Couldn't convert the json to object " + typeOfResponse + " because of an ioexception. Json was : " + contentString,e);
		}
		return responseObject;
	}
	
	@Override
	public <T> T handleResponse(JsonParser jp, Class<T> typeOfResponse) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T responseObject = null;
		try {
			responseObject = mapper.readValue(jp,typeOfResponse);
		} catch (JsonParseException e) {
			throw new ARException("Couldn't create the object " + typeOfResponse + " because the json was malformed : " + jp.toString(),e);
		} catch (JsonMappingException e) {
			throw new ARException("Mapping the json response to the response object " + typeOfResponse + " failed.  Json was : " + jp.toString() ,e);
		} catch (IllegalStateException e) {
			throw new ARException("Couldn't convert the json to object " + typeOfResponse + " because of illegal state. Json was : " + jp.toString(),e);
		} catch (IOException e) {
			throw new ARException("Couldn't convert the json to object " + typeOfResponse + " because of an ioexception. Json was : " + jp.toString(),e);
		}
		return responseObject;
	}
}
