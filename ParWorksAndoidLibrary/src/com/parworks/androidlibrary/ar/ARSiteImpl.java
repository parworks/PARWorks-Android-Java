package com.parworks.androidlibrary.ar;

import java.io.InputStream;

import com.parworks.androidlibrary.ar.ARSite.State;

public class ARSiteImpl implements ARSite {
	
	private String mSiteId;
	
	public ARSiteImpl(String siteId) {
		mSiteId = siteId;
	}
	
	public String getSiteId() {
		return mSiteId;
	}

	
	
	/*
	 * 
	 *
	 * Async
	 * 
	 * 
	 */
	
	@Override
	public void addBaseImage(InputStream in, ARListener<BaseImageInfo> listener) {
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
	public BaseImageInfo addBaseImage(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State processBaseImages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OverlayResponse addOverlay(Overlay overlay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OverlayResponse updateOverlay(String id, OverlayData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOverlay(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ARData augmentImage(InputStream in) {
		// TODO Auto-generated method stub
		return null;
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
