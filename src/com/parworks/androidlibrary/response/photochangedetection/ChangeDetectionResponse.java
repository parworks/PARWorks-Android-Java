package com.parworks.androidlibrary.response.photochangedetection;

public class ChangeDetectionResponse {
	private boolean success;
	private String creationTime;
	private String imageId;
	private String site;
	private String resultData;
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	
	@Override
	public String toString() {
		String returnString = "success : " + success
				+ "\n creationTime : " + creationTime
				+ "\n site : " + site
				+ "\n imageId : " + imageId
				+ "\n resultData : " + resultData;
		
		return returnString;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

}
