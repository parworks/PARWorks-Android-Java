package com.parworks.androidlibrary.response;

import java.util.List;
/**
 * Used to deserialize the server response from the Nearby Sites endpoint
 * @author Adam hickey
 *
 */
public class NearbySitesResponse {
	
	List<SiteInfo> mSites;
	boolean mSuccess;
	
	public void setSites(List<SiteInfo> sites) {
		mSites = sites;
	}
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	
	public List<SiteInfo> getSites() {
		return mSites;
	}
	
	public boolean getSuccess() {
		return mSuccess;
	}

}
