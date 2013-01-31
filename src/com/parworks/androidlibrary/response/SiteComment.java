package com.parworks.androidlibrary.response;

public class SiteComment {
	private String siteId;
	private long timeStamp;
	private String userId;
	private String userName;
	private String comment;

	public SiteComment() {		
	}

	public SiteComment(String siteId, long timeStamp, String userId,
			String userName, String comment) {
		this.siteId = siteId;
		this.timeStamp = timeStamp;
		this.userId = userId;
		this.userName = userName;
		this.comment = comment;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
