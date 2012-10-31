/* 
**
** Copyright 2012, Jules White
**
** 
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
