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

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parworks.androidlibrary.ar.ARException;

/**
 * 
 * @author Adam Hickey
 *
 */
public class ARResponseHandlerImpl implements ARResponseHandler {

	@Override
	public <T> T handleResponse(HttpResponse serverResponse, Class<T> typeOfResponse) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T responseObject = null;
		
		try {
			responseObject = mapper.readValue(serverResponse.getEntity().getContent(),typeOfResponse);
		} catch (JsonParseException e) {
			throw new ARException("Couldn't handle the response because the http response contained malformed json.",e);
		} catch (JsonMappingException e) {
			throw new ARException("Mapping the json response to the response object " + typeOfResponse + " failed.",e);
		} catch (IllegalStateException e) {
			throw new ARException("Couldn't convert the http response to an inputstream because of illegal state.",e);
		} catch (IOException e) {
			throw new ARException("Couldn't convert the http response to an inputstream.",e);
		}
		return responseObject;
	}
}
