/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import org.apache.http.HttpResponse;

/**
 * Used by ARResponse to create the appropriate object to return to the ARListener callback
 * @author Jules White
 *
 * @param <T>
 */
public interface PayloadExtractor<T> {
	
	/**
	 * Used to extract an object of type T from an http response
	 * @param resp the server http response
	 * @return an object of type T
	 */
	public T extract(HttpResponse resp);
	
}
