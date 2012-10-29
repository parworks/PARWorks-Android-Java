package com.parworks.androidlibrary.ar;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;

import com.parworks.androidlibrary.response.ARResponseHandler;
import com.parworks.androidlibrary.response.ARResponseHandlerImpl;
import com.parworks.androidlibrary.response.AddBaseImageResponse;
import com.parworks.androidlibrary.response.AugmentImageResponse;
import com.parworks.androidlibrary.response.BasicResponse;
import com.parworks.androidlibrary.response.InitiateBaseImageProcessingResponse;
import com.parworks.androidlibrary.response.SiteInfo;
import com.parworks.androidlibrary.utils.HttpUtils;

public class ARSiteImpl implements ARSite {
	
	private SiteInfo mSiteInfo;
	private String mApiKey;
	private String mSalt;
	private String mSignature;
	
	private State mSiteState;
	
	public ARSiteImpl(SiteInfo info, String apiKey, String salt, String signature) {
		mSiteInfo = info;
		mApiKey = apiKey;
		mSalt = salt;
		mSignature = signature;
	}
	
	public SiteInfo getSiteInfo() {
		return mSiteInfo;
	}

	
	
	/*
	 * 
	 *
	 * Async
	 * 
	 * 
	 */
	
	@Override
	public void addBaseImage(String filename, InputStream in, ARListener<BaseImageInfo> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processBaseImages(ARListener<State> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getState(ARListener<State> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOverlay(Overlay overlay, ARListener<OverlayResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOverlay(String id, OverlayData data,
			ARListener<OverlayResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOverlay(String id, ARListener<OverlayResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void augmentImage(InputStream in, ARListener<ARData> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void augmentImage(InputStream in, long lat, long lon,
			double compass, ARListener<ARData> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ARListener<Boolean> listener) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/*
	 * 
	 * 
	 * Sync
	 * 
	 * 
	 */

	@Override
	public BaseImageInfo addBaseImage(String filename, InputStream image) {
		//make httputils
		HttpUtils httpUtils = new HttpUtils();
		
		//make query string
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mSiteInfo.getId());
		params.put("filename", filename);
		
		//make entity
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(image,filename);
		imageEntity.addPart("image", imageInputStreamBody);
		
		//do post
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.ADD_BASE_IMAGE_PATH, imageEntity, params);
		
		//handle status code
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		//parse response
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AddBaseImageResponse addSiteResponse = responseHandler.handleResponse(serverResponse, AddBaseImageResponse.class);
		
		//return baseimageinfo
		if(addSiteResponse.getSuccess() == true) {
			return new BaseImageInfo(addSiteResponse.getId());
		} else {
			throw new ARException("Successfully communicated with the server but failed to add the base image. Perhaps the site does not exist, or there is a problem with the image.");
		}
			
	}

	@Override
	public State processBaseImages() {
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mSiteInfo.getId());
				
		HttpResponse serverResponse = httpUtils.doGet(mApiKey, mSalt, mSignature, , params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		InitiateBaseImageProcessingResponse addSiteResponse = responseHandler.handleResponse(serverResponse, InitiateBaseImageProcessingResponse.class);
		
	}

	@Override
	public State getState() {
		return null;
	}

	@Override
	public OverlayResponse addOverlay(Overlay overlay) {
		return null;
	}

	@Override
	public OverlayResponse updateOverlay(String id, OverlayData data) {
		return null;
	}

	@Override
	public void deleteOverlay(String id) {
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mSiteInfo.getId());
		params.put("id", id);
		
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.REMOVE_OVERLAY_PATH, params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		BasicResponse deleteOverlayResponse = responseHandler.handleResponse(serverResponse, BasicResponse.class);
		
		if(deleteOverlayResponse.getSuccess() == false) {
			throw new ARException("Successfully communicated with the server, but the overlay was not deleted. Perhaps it does not exist.");
		} 
		
	}

	@Override
	public ARData augmentImage(InputStream in) {
		HttpUtils httpUtils = new HttpUtils();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("site", mSiteInfo.getId());
		
		MultipartEntity imageEntity = new MultipartEntity();
		InputStreamBody imageInputStreamBody = new InputStreamBody(in,"image");
		imageEntity.addPart("image", imageInputStreamBody);
		
		HttpResponse serverResponse = httpUtils.doPost(mApiKey, mSalt, mSignature, HttpUtils.PARWORKS_API_BASE_URL + HttpUtils.AUGMENT_IMAGE_PATH, imageEntity, params);
		
		HttpUtils.handleStatusCode(serverResponse.getStatusLine().getStatusCode());
		
		ARResponseHandler responseHandler = new ARResponseHandlerImpl();
		AugmentImageResponse augmentImageResponse = responseHandler.handleResponse(serverResponse, AugmentImageResponse.class);
		
		if(augmentImageResponse.getSuccess() == false) {
			throw new ARException("Successfully communicated with the server but failed to augment the image.");
		}
		
		String imgId = augmentImageResponse.getImgId();
		
		
		
		
	}

	@Override
	public ARData augmentImage(InputStream in, long lat, long lon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	

	
	
	



	

}
