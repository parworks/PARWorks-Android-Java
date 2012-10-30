/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

/**
 * Specifies the state that an ARSite must be in for certain methods to function correctly
 * @author Jules White
 *
 */
public @interface RequiredState {

	public ARSite.State[] value();
	
}
