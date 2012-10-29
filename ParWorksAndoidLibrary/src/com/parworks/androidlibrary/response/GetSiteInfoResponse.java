package com.parworks.androidlibrary.response;


public class GetSiteInfoResponse {
	
	private SiteInfo site;
	boolean success;
	
	public void setSite(SiteInfo mySite) {
		site = mySite;
	}
	public void setSuccess(boolean s) {
		success = s;
	}
	public boolean getSuccess() {
		return success;
	}
	public SiteInfo getSite() {
		return site;
	}


}
