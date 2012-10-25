package com.parworks.androidlibrary.response;

import java.util.Map;

public class GetSiteInfoResponse {
	
	private Map<String,Object> site;
	boolean success;
	
	public void setSite(Map<String,Object> mySite) {
		site = mySite;
	}
	public void setSuccess(boolean s) {
		success = s;
	}
	public boolean getSuccess() {
		return success;
	}
	public Map<String,Object> getSite() {
		return site;
	}


}
