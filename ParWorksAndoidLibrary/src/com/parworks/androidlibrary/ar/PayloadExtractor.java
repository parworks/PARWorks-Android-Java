/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import org.apache.http.HttpResponse;

public interface PayloadExtractor<T> {

	public T extract(HttpResponse resp);
	
}
