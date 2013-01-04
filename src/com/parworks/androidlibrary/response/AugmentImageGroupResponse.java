package com.parworks.androidlibrary.response;

import java.util.List;

public class AugmentImageGroupResponse {

	private boolean mSuccess;
	private List<SiteImageBundle> candidates;
	
	public void setSuccess(boolean success) {
		mSuccess = success;
	}
	
	public boolean getSuccess() {
		return mSuccess;
	}

	public List<SiteImageBundle> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<SiteImageBundle> candidates) {
		this.candidates = candidates;
	}

}
