package com.parworks.androidlibrary.response;

public class SiteInfo {
	private String mName;
	private String mId;
	private String mChannel;
	private String mDescription;
	private String mGeoHash;
	private String mSiteState;
	private double mLatitude;
	private double mLongitude;
	private String mBimState;
	private String mFeatureType;
	private String mS3Bucket;
	private String mLastModificationTime;
	
	public SiteInfo() {}
	
	public SiteInfo(String name, String id, String channel, String description, String geoHash, String siteState,
			double lat, double lon, String bimState, String featureType, String s3Bucket, String lastModificationTime) {
		mName = name;
		mId = id;
		mChannel = channel;
		mDescription = description;
		mGeoHash = geoHash;
		mSiteState = siteState;
		mLatitude = lat;
		mLongitude = lon;
		mBimState = bimState;
		mFeatureType = featureType;
		mS3Bucket = s3Bucket;
		mLastModificationTime = lastModificationTime;
	}
		
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		this.mName = name;
	}
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		this.mId = id;
	}
	public String getChannel() {
		return mChannel;
	}
	public void setChannel(String channel) {
		this.mChannel = channel;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		this.mDescription = description;
	}
	public String getGeoHash() {
		return mGeoHash;
	}
	public void setGeoHash(String geoHash) {
		this.mGeoHash = geoHash;
	}
	public String getSiteState() {
		return mSiteState;
	}
	public void setSiteState(String siteState) {
		this.mSiteState = siteState;
	}
	public double getLat() {
		return mLatitude;
	}
	public void setLat(double lat) {
		this.mLatitude = lat;
	}
	public double getLon() {
		return mLongitude;
	}
	public void setLon(double lon) {
		this.mLongitude = lon;
	}
	public String getBimState() {
		return mBimState;
	}
	public void setBimState(String bimState) {
		this.mBimState = bimState;
	}
	public String getFeatureType() {
		return mFeatureType;
	}
	public void setFeatureType(String featureType) {
		this.mFeatureType = featureType;
	}
	public String getS3Bucket() {
		return mS3Bucket;
	}
	public void setS3Bucket(String s3Bucket) {
		this.mS3Bucket = s3Bucket;
	}
	public String getLastModificationTime() {
		return mLastModificationTime;
	}
	public void setLastModificationTime(String lastModificationTime) {
		this.mLastModificationTime = lastModificationTime;
	}

}
