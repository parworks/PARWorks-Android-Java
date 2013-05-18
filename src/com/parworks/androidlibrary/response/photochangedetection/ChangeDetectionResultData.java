package com.parworks.androidlibrary.response.photochangedetection;

import java.util.List;

public class ChangeDetectionResultData {
	private String imageId;
	private String imageWidth;
	private String imageHeight;
	private List<ChangeDetectionObject> objects;
	
	public ChangeDetectionResultData() {}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}
	public String getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}
	public List<ChangeDetectionObject> getObjects() {
		return objects;
	}
	public void setObjects(List<ChangeDetectionObject> objects) {
		this.objects = objects;
	}
	
	public String toString() {
		String returnString = "imageId : " + imageId
			+ "\n imageWidth : " + imageWidth
			+ "\n imageHeight : " + imageHeight
			+ "\n objects : [";
		for(ChangeDetectionObject obj : objects) {
			returnString += "\n { \n";
			returnString += obj.toString();
			returnString += "\n },";
		}
		returnString += "]";
		return returnString;
	}
	

}
