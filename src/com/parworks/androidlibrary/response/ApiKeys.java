package com.parworks.androidlibrary.response;

public class ApiKeys {
	
	private String apikey;
	private String secretkey;
	
	public ApiKeys(String apikey, String secretkey) {
		this.apikey = apikey;
		this.secretkey = secretkey;
	}
	
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
}
