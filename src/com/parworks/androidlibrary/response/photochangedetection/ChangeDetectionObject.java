package com.parworks.androidlibrary.response.photochangedetection;

import java.util.List;

public class ChangeDetectionObject {
	private String objectId;
	private String objectLabel;
	private List<ChangeDetectionInstance> instances;
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getObjectLabel() {
		return objectLabel;
	}
	public void setObjectLabel(String objectLabel) {
		this.objectLabel = objectLabel;
	}
	public List<ChangeDetectionInstance> getInstances() {
		return instances;
	}
	public void setInstances(List<ChangeDetectionInstance> instances) {
		this.instances = instances;
	}
	
	@Override
	public String toString() {
		String returnString = "objectId : " + objectId
				+ "objectLabel : " + objectLabel
				+ "instances : [";
		for(ChangeDetectionInstance instance : instances) {
			returnString += "\n { \n";
			returnString += instance.toString();
			returnString += "\n },";
		}
		returnString += "]";
		return returnString;
	}

}
