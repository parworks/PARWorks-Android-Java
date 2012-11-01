/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.parworks.androidlibrary.response;

/**
 * Used to deserialize the response from the Get Site Info endpoint
 * @author Adam Hickey
 *
 */
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
