package com.parworks.androidlibrary.response.photochangedetection;

import java.util.List;

public class ChangeDetectionInstance {
	private List<String> boundingBox;
	private String result;
	private String comment;
	public List<String> getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(List<String> boundingBox) {
		this.boundingBox = boundingBox;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		String returnString = "boundingBox : [";
		for(String box : boundingBox) {
			returnString += box + ", ";
		}
		returnString += "],\n";
		returnString += "result : " + result;
		returnString += "\n comment : " + comment;
		return returnString;
		
	}

}
