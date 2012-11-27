package com.parworks.androidlibrary.response;

public class GetApiKeysResponse {
	
	private String apikey;
	private String secretkey;
	private boolean success;
	
	public String getApikey() {
		return apikey;
	}
	
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	
	public String getSecretkey() {
		return secretkey;
	}
	
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
