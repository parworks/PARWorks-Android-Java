/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

public interface ARListener<T> {

	public void handleResponse(ARResponse<T> resp);
	
}
