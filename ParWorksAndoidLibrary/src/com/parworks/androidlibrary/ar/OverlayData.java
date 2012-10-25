/* 
 **
 ** Copyright 2012, Jules White
 **
 ** 
 */
package com.parworks.androidlibrary.ar;

public class OverlayData {

	private float vertices_;

	private String name_;

	private String description_;

	private String baseImageId_;

	public OverlayData() {
	}

	public OverlayData(String name, String description, String baseImageId,
			float vertices) {
		super();
		name_ = name;
		description_ = description;
		baseImageId_ = baseImageId;
		vertices_ = vertices;
	}

	public float getVertices() {
		return vertices_;
	}

	public void setVertices(float vertices) {
		vertices_ = vertices;
	}

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}

	public String getDescription() {
		return description_;
	}

	public void setDescription(String description) {
		description_ = description;
	}

	public String getBaseImageId() {
		return baseImageId_;
	}

	public void setBaseImageId(String baseImageId) {
		baseImageId_ = baseImageId;
	}

}
