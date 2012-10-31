/* 
 **
 ** Copyright 2012, Jules White
 **
 ** 
 */
package com.parworks.androidlibrary.ar;

/**
 * Returned after adding a base image to an ARSite. Contains the id necessary to access that base image.
 * @author Jules White
 *
 */
public class BaseImageInfo {

	private final String baseImageId_;

	public BaseImageInfo(String baseImageId) {
		super();
		baseImageId_ = baseImageId;
	}

	public String getBaseImageId() {
		return baseImageId_;
	}

}
