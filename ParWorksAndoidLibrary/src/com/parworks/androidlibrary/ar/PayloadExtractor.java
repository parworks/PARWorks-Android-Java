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
