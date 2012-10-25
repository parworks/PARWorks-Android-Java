/* 
**
** Copyright 2012, Jules White
**
** 
*/
package com.parworks.androidlibrary.ar;

import java.io.InputStream;

public interface ARSite {
	
	public enum State {
		READY_TO_AUGMENT_IMAGES, 
		NEEDS_MORE_BASE_IMAGES,
		NEEDS_BASE_IMAGE_PROCESSING,
		NEEDS_OVERLAYS
	}
	
	
	public String getSiteId();

	/*
	 * Asynchronous Methods
	 * 
	 */
	
	@RequiredState({State.NEEDS_MORE_BASE_IMAGES,State.NEEDS_BASE_IMAGE_PROCESSING})
	public void addBaseImage(InputStream in, ARListener<BaseImageInfo> listener);
	
	@RequiredState({State.NEEDS_BASE_IMAGE_PROCESSING})
	public void processBaseImages(ARListener<State> listener);
	
	public void getState(ARListener<State> listener);
	
	@RequiredState({State.NEEDS_OVERLAYS,State.READY_TO_AUGMENT_IMAGES})
	public void addOverlay(Overlay overlay, ARListener<OverlayResponse> listener);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void updateOverlay(String id, OverlayData data, ARListener<OverlayResponse> listener);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void deleteOverlay(String id, ARListener<OverlayResponse> listener);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void augmentImage(InputStream in, ARListener<ARData> listener);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void augmentImage(InputStream in, long lat, long lon, double compass, ARListener<ARData> listener);
	
	public void delete(ARListener<Boolean> listener);
	
	/*
	 * Synchronous Methods
	 * 
	 * 
	 */
	@RequiredState({State.NEEDS_MORE_BASE_IMAGES,State.NEEDS_BASE_IMAGE_PROCESSING})
	public BaseImageInfo addBaseImage(InputStream in);
	
	@RequiredState({State.NEEDS_BASE_IMAGE_PROCESSING})
	public State processBaseImages();
	
	public State getState();
		
	@RequiredState({State.NEEDS_OVERLAYS,State.READY_TO_AUGMENT_IMAGES})
	public OverlayResponse addOverlay(Overlay overlay);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public OverlayResponse updateOverlay(String id, OverlayData data);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void deleteOverlay(String id);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public ARData augmentImage(InputStream in);
	
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public ARData augmentImage(InputStream in, long lat, long lon);
	
	public void delete();
}
