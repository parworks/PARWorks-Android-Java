/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.BasicResponse;
import com.parworks.androidlibrary.response.GetSiteInfoResponse;
import com.parworks.androidlibrary.response.NearbySitesResponse;
import com.parworks.androidlibrary.response.SiteInfo;
import com.parworks.androidlibrary.utils.AsyncHttpUtils;
import com.parworks.androidlibrary.utils.HttpCallback;
import com.parworks.androidlibrary.utils.HttpUtils;
/**
 * Used for Synchronously and Asynchronously finding, managing, and creating ARSites
 * @author Jules White, Adam Hickey
 *
 */
public class ARSites {
	
	private String mApiKey;
	private String mSalt;
	private String mSignature;
		
	public ARSites(String username, String password) {
		
		//TODO fix this!!! 
		
		mApiKey = "parTestUser";
		mSalt = "foo";
		mSignature = "6p/ip68gea//V5H+dkvbnPG/RIPjai9tb3DdfbkQMj0=";
	}
	
	
	/**
	 * Asynchronously create an ARSite
	 * @param id the id of the site. Will be used for all site accesses
	 * @param name the name of the site
	 * @param lon longitude of the site
	 * @param lat latitude of the site
	 * @param desc site description
	 * @param feature site feature
	 * @param channel site channel
	 * @param listener callback that provides an ARSite object when the call completes
	 */
	public void create(final String id, final String name, final double lon, final double lat, final String desc, final String feature, final String channel, final ARListener<ARSite> listener){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		parameterMap.put("name",name);
		parameterMap.put("lon",Double.toString(lon));
		parameterMap.put("lat",Double.toString(lat));
		parameterMap.put("description",desc);
		parameterMap.put("feature", feature);
		parameterMap.put("channel",channel);
		
		AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
		HttpCallback callback = new HttpCallback() {
			
			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<ARSite> extractor = new PayloadExtractor<ARSite>() {

					@Override
					public ARSite extract(HttpResponse httpResponseFromExtractor) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						BasicResponse addSiteResponse = responseHandler.handleResponse(httpResponseFromExtractor, BasicResponse.class);						
						
						if(addSiteResponse.getSuccess() == true ) {
							SiteInfo siteInfo = new SiteInfo();
							siteInfo.setId(id);
							siteInfo.setName(name);
							siteInfo.setLon(lon);
							siteInfo.setLat(lat);
							siteInfo.setDescription(desc);
							siteInfo.setFeatureType(feature);
							siteInfo.setChannel(channel);
							return new ARSiteImpl(siteInfo, mApiKey, mSalt, mSignature);
							
						} else {
							throw new ARException("Successfully communicated with the server, but the server was unsuccessful in handling the request.");
						}
					}
					
				};
				ARResponse<ARSite> addArSiteResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(addArSiteResponse);
			}			
			
			
			@Override
			public void onError(Exception e) {
				throw new ARException(e);
				
			}
		};
		asyncHttpUtils.doGet(mApiKey,mSalt,mSignature,HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.ADD_SITE_PATH, parameterMap, callback);
		
		
	}
	
	/**
	 * Asynchronously create an ARSite
	 * @param id the id of the site. Will be used for all site accesses
	 * @param desc site description
	 * @param channel site channel
	 * @param listener callback that provides an ARSite object when the call completes
	 */
	public void create(final String id, final String desc, final String channel, final ARListener<ARSite> listener){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		parameterMap.put("description",desc);
		parameterMap.put("channel",channel);
		
		AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
		HttpCallback callback = new HttpCallback() {
			
			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<ARSite> extractor = new PayloadExtractor<ARSite>() {

					@Override
					public ARSite extract(HttpResponse httpResponseFromExtractor) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						BasicResponse nearbySites = responseHandler.handleResponse(httpResponseFromExtractor, BasicResponse.class);						
						
						if(nearbySites.getSuccess() == true ) {
							SiteInfo siteInfo = new SiteInfo();
							siteInfo.setId(id);
							siteInfo.setDescription(desc);
							siteInfo.setChannel(channel);
							return new ARSiteImpl(siteInfo, mApiKey, mSalt, mSignature);
						} else {
							throw new ARException("Successfully communicated with the server, but the server was unsuccessful in handling the request.");
						}
					}
					
				};
				ARResponse<ARSite> addArSiteResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(addArSiteResponse);
			}			
			
			
			@Override
			public void onError(Exception e) {
				throw new ARException(e);
				
			}
		};
		asyncHttpUtils.doGet(mApiKey,mSalt,mSignature,HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.ADD_SITE_PATH, parameterMap, callback);
	}
	
	/**
	 * Asynchronously finds the site nearest a given latitude and longitude. This method does not specify the maximum number of sites, and so the default is one. Use near(double, double, int, double, ARListener)
	 * to retrieve multiple sites.
	 * @param lat latitude
	 * @param lon longitude
	 * @param sites the callback which provides a list of sites nearest the coordinates
	 */
	public void near(double lat, double lon, ARListener<List<ARSite>> sites) {
		near(Double.toString(lat),Double.toString(lon),"","",sites);
	}
	
	/**
	 * Asynchronously finds the sites nearest a set of coordinates
	 * @param lat latitude
	 * @param lon longitude
	 * @param max the maximum number of sites to return.
	 * @param radius the radius in which to search
	 * @param sites the callback which provides a list of the nearest ARSites
	 */
	public void near(double lat, double lon, int max, double radius,ARListener<List<ARSite>> sites){
		near(Double.toString(lat),Double.toString(lon),Integer.toString(max),Double.toString(radius),sites);
	}
	
	private void near(String lat, String lon, String max, String radius,final ARListener<List<ARSite>> sites){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("lat", lat);
		parameterMap.put("lon", lon);
		parameterMap.put("max", max);
		parameterMap.put("radius", radius);
		
		AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
		HttpCallback callback = new HttpCallback() {
			
			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<List<ARSite>> extractor = new PayloadExtractor<List<ARSite>>() {

					@Override
					public List<ARSite> extract(HttpResponse httpResponseFromExtractor) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						NearbySitesResponse nearbySites = responseHandler.handleResponse(httpResponseFromExtractor, NearbySitesResponse.class);						
						
						if(nearbySites.getSuccess() == true ) {
							List<SiteInfo> sitesInfo = nearbySites.getSites();
							List<ARSite> nearbySitesList = new ArrayList<ARSite>();
							for(SiteInfo info : sitesInfo) {
								nearbySitesList.add(new ARSiteImpl(info, mApiKey, mSalt, mSignature));				
							}
							return nearbySitesList;
						} else {
							throw new ARException("Successfully communicated with the server, but the server was unsuccessful in handling the request.");
						}
					}
					
				};
				ARResponse<List<ARSite>> nearbySitesARResponse = ARResponse.from(serverResponse, extractor);
				sites.handleResponse(nearbySitesARResponse);
			}			
			
			
			@Override
			public void onError(Exception e) {
				throw new ARException(e);
				
			}
		};
		asyncHttpUtils.doGet(mApiKey,mSalt,mSignature,HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.NEARBY_SITE_PATH, parameterMap, callback);
	}
	
	/**
	 * Asynchronously get a previously created site
	 * @param id the id of the site
	 * @param listener the callback which provides the ARSite once the call completes
	 */
	public void getExisting(String id, final ARListener<ARSite> listener){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("site", id);
		
		AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
		HttpCallback callback = new HttpCallback() {
			
			@Override
			public void onResponse(HttpResponse serverResponse) {
				HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
				PayloadExtractor<ARSite> extractor = new PayloadExtractor<ARSite>() {

					@Override
					public ARSite extract(HttpResponse httpResponseFromExtractor) {
						ARResponseHandler responseHandler = new ARResponseHandlerImpl();
						GetSiteInfoResponse siteInfoResponse = responseHandler.handleResponse(httpResponseFromExtractor, GetSiteInfoResponse.class);						
						
						if(siteInfoResponse.getSuccess() == true ) {
							return new ARSiteImpl(siteInfoResponse.getSite(), mApiKey, mSalt, mSignature);
						} else {
							throw new ARException("Successfully communicated with the server, but the server was unsuccessful in handling the request.");
						}
					}
					
				};
				ARResponse<ARSite> nearbySitesARResponse = ARResponse.from(serverResponse, extractor);
				listener.handleResponse(nearbySitesARResponse);
			}			
			
			
			@Override
			public void onError(Exception e) {
				throw new ARException(e);
				
			}
		};
		asyncHttpUtils.doGet(mApiKey,mSalt,mSignature,HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.GET_SITE_INFO_PATH, parameterMap, callback);
		
	}
	

	
	
	/*
	 * Please create synchronous equivalents of the above methods
	 * 
	 */
	
	/**
	 * Synchronously get a previously created site
	 * @param id site id
	 * @return the ARSite
	 */
	public ARSite getExisting(String id){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse;
		serverResponse = httpUtils.doGet(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.GET_SITE_INFO_PATH, parameterMap);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		GetSiteInfoResponse getSiteResponse = responseHandler.handleResponse(serverResponse, GetSiteInfoResponse.class);
		

		
		if(getSiteResponse.getSuccess() == true) {
			ARSite newSite = new ARSiteImpl(getSiteResponse.getSite(), mApiKey, mSalt, mSignature);
			return newSite;
		} else {
			throw new ARException("Successfully communicated with the server, but failed to get siteinfo. The most likely cause is that a site with the specificed ID does not exist.");
		}
	}

	
	/**
	 * Synchronously create an ARSite
	 * @param id the site id. Will be used for accessing the site.
	 * @param desc the site description
	 * @param channel the site channel
	 * @return the newly created ARSite
	 */
	public ARSite create(String id, String desc, String channel) {
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		parameterMap.put("description",desc);
		parameterMap.put("channel",channel);
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.ADD_SITE_PATH, parameterMap);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse addSiteResponse = responseHandler.handleResponse(serverResponse, BasicResponse.class);
		

		
		if(addSiteResponse.getSuccess() == true) {
			ARSite newSite = getExisting(id);
			return newSite;
		} else {
			throw new ARException("Successfully communicated with the server, but failed to create a new site. The site id could already be in use, or a problem occurred.");
		}
	}
	
	/**
	 * Synchronously create an ARSites
	 * @param id
	 * @param name
	 * @param lon
	 * @param lat
	 * @param desc
	 * @param feature
	 * @param channel
	 * @return
	 */
	public ARSite create(String id, String name, double lon, double lat, String desc, String feature, String channel){
		Map<String,String> parameterMap = new HashMap<String,String>();
		parameterMap.put("id", id);
		parameterMap.put("name",name);
		parameterMap.put("lon",Double.toString(lon));
		parameterMap.put("lat",Double.toString(lat));
		parameterMap.put("description",desc);
		parameterMap.put("feature", feature);
		parameterMap.put("channel",channel);
		
		HttpUtils httpUtils = new HttpUtils();
		HttpResponse serverResponse;
		serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL+HttpUtils.ADD_SITE_PATH, parameterMap);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse addSiteResponse = responseHandler.handleResponse(serverResponse, BasicResponse.class);
		

		
		if(addSiteResponse.getSuccess() == true) {
			ARSite newSite = getExisting(id);
			return newSite;
		} else {
			throw new ARException("Successfully communicated with the server, but failed to create a new site. The site id could already be in use, or a problem occurred.");
		}

		
	}
	
	public List<ARSite> near(double lat, double lon) {
		return near(Double.toString(lat),Double.toString(lon),"","");
	}
	public List<ARSite> near(double lat, double lon, int max, double radius ) {
		return near(Double.toString(lat),Double.toString(lon),Integer.toString(max),Double.toString(radius));
	}
	private List<ARSite> near(String lat, String lon, String max, String radius) {
		Map<String, String> parameterMap = new HashMap<String,String>();
		parameterMap.put("lat", lat);
		parameterMap.put("lon", lon);
		parameterMap.put("max",max);
		parameterMap.put("radius",radius);
		
		HttpResponse serverResponse;
		HttpUtils httpUtils = new HttpUtils();
		serverResponse = httpUtils.doGet(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.NEARBY_SITE_PATH, parameterMap);
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());

		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		NearbySitesResponse nearbySites = responseHandler.handleResponse(serverResponse, NearbySitesResponse.class);
		
		if(nearbySites.getSuccess() == true ) {
			List<SiteInfo> sitesInfo = nearbySites.getSites();
			List<ARSite> nearbySitesList = new ArrayList<ARSite>();
			for(SiteInfo info : sitesInfo) {
				nearbySitesList.add(new ARSiteImpl(info, mApiKey, mSalt, mSignature));				
			}
			return nearbySitesList;
		} else {
			throw new ARException("Successfully communicated with the server, but the server was unsuccessful in handling the request.");
		}	
		
	}
}
