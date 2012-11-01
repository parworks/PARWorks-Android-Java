/*
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
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
package com.parworks.androidlibrary.ar;
/**
 * A callback used for returning the result of asynchronous server requests
 * @author Jules White
 *
 * @param <T>
 */
public interface ARListener<T> {

	/**
	 * Example:
	 *     ARsites arsites = new ARSites("user","password");
	 *     arSites.near(latitude,longitude,max,radius,new ARListener<List<ARSite>>() {
	 *     		
	 *         public void handleResponse(ARResponse<List<ARSite>> resp) {
	 *             List<ARSite> nearbySites = resp.getPayload();
	 *             //do something with the sites...
	 *         }
	 * @param resp the response from the asynchronous server request
	 */
	public void handleResponse(ARResponse<T> resp);
	
}
