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
package com.parworks.androidlibrary.ar;

import java.io.InputStream;
import java.util.List;

import com.parworks.androidlibrary.response.BaseImageInfo;
import com.parworks.androidlibrary.response.SiteInfo;

/**
 * An interface representing an ARSite
 * 
 * The state of an ARSite. Begins at NEEDS_MORE_BASE_IMAGES, then changes to
 * NEEDS_BASE_IMAGE_PROCESSING when the required number of base images have been
 * uploaded. After the base images have been processed, the state of an ARSite
 * changes to NEEDS_OVERLAYS and finally to READY_TO_AUGMENT_IMAGES
 * 
 * @author Jules White
 * 
 */
public interface ARSite {

	/**
	 * An enum representing ARSite state
	 * 
	 * @author Jules White
	 * 
	 */
	public enum State {
		READY_TO_AUGMENT_IMAGES, NEEDS_MORE_BASE_IMAGES, NEEDS_BASE_IMAGE_PROCESSING, NEEDS_OVERLAYS, PROCESSING
	}

	/**
	 * Returns the site id connected with this site. Does not do any networking.
	 * 
	 * @return site id
	 */
	public String getSiteId();

	/**
	 * Asynchronously get the site's base images
	 */
	public void getBaseImages(ARListener<List<BaseImageInfo>> listener);

	/**
	 * Makes an asynchronous server request to get site info
	 * 
	 * @param listener
	 *            the callback to be used when the call completes. Will contain
	 *            a SiteInfo object.
	 */
	public void getSiteInfo(ARListener<SiteInfo> listener);

	/**
	 * Asynchronously add a base image. Throws an ARException if the state is
	 * not NEEDS_MORE_BASE_IMAGES or NEEDS_BASE_IMAGE_PROCESSING
	 * 
	 * @param filename
	 *            the name of the base image
	 * @param image
	 *            an image as an InputStream
	 * @param listener
	 *            the callback to be used when the call completes containing a
	 *            BaseImageInfo object with the id of the new base image.
	 */
	@RequiredState({ State.NEEDS_MORE_BASE_IMAGES,
			State.NEEDS_BASE_IMAGE_PROCESSING })
	public void addBaseImage(String filename, InputStream image,
			ARListener<BaseImage> listener);

	/**
	 * Asynchronously begin processing the base images. Throws an ARException if
	 * the state is not NEEDS_BASE_IMAGE_PROCESSING
	 * 
	 * @param listener
	 *            the callback to be used when the call completes providing the
	 *            state of the site
	 */
	@RequiredState({ State.NEEDS_BASE_IMAGE_PROCESSING })
	public void processBaseImages(ARListener<State> listener);

	/**
	 * Makes an asynchronous server request to get the current state of the
	 * ARSite
	 * 
	 * @param listener
	 *            the callback to be used when the call completes providing the
	 *            state of the site
	 */
	public void getState(ARListener<State> listener);

	/**
	 * Asynchronously add an overlay. Throws an ARException if the state is not
	 * NEEDS_OVERLAYS or READY_TO_AUGMENT_IMAGES
	 * 
	 * @param overlay
	 *            the overlay to add
	 * @param listener
	 *            the callback to be used when the call completes providing an
	 *            overlay response which contains the new overlay id
	 */
	@RequiredState({ State.NEEDS_OVERLAYS, State.READY_TO_AUGMENT_IMAGES })
	public void addOverlay(Overlay overlay, ARListener<OverlayResponse> listener);

	/**
	 * Asynchronously update an overlay. Throws an ARException if the state is
	 * not READY_TO_AUGMENT_IMAGES
	 * 
	 * @param id
	 *            the overlay id
	 * @param data
	 *            the overlay data
	 * @param listener
	 *            the callback to be used when the call completes providing an
	 *            overlay response which contains the new overlay id
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void updateOverlay(String id, Overlay overlay,
			ARListener<OverlayResponse> listener);

	/**
	 * Asynchronously remove an overlay from the site. Throws an ARException if
	 * the state is not READY_TO_AUGMENT_IMAGES
	 * 
	 * @param id
	 *            the overlay id
	 * @param listener
	 *            the callback to be used when the call completes providing an
	 *            overlay response which contains the new overlay id
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void deleteOverlay(String id, ARListener<Boolean> listener);

	/**
	 * Asynchronously augment an image.
	 * 
	 * @param image
	 *            an inputstream containing the image to be augmented
	 * @param listener
	 *            a callback to get the augmented data when the call is complete
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void augmentImage(InputStream image,
			ARListener<AugmentedData> listener);

	/**
	 * Asynchronously delete the site.
	 * 
	 * @param listener
	 *            callback to be used when the call completes providing a
	 *            boolean indicating success or failure
	 */
	public void delete(ARListener<Boolean> listener);

	/**
	 * Synchronously add a base image. Throws an ARException if the state is not
	 * NEEDS_MORE_BASE_IMAGES or NEEDS_BASE_IMAGE_PROCESSING
	 * 
	 * @param filename
	 *            the name of the image
	 * @param image
	 *            the image as an InputStream
	 * @return a BaseImageInfo object containing the id of the new base image
	 */
	@RequiredState({ State.NEEDS_MORE_BASE_IMAGES,
			State.NEEDS_BASE_IMAGE_PROCESSING })
	public BaseImage addBaseImage(String filename, InputStream image);

	/**
	 * Synchronously process base images. Throws an ARException if the state is
	 * not NEEDS_BASE_IMAGE_PROCESSING
	 * 
	 * @return the state of the site
	 */
	@RequiredState({ State.NEEDS_BASE_IMAGE_PROCESSING })
	public State processBaseImages();

	/**
	 * This make a sychronous server request to get the state of the site
	 * 
	 * @return the state of the site
	 */
	public State getState();

	/**
	 * Synchronously add an overlay. Throws an ARException if the state is not
	 * NEEDS_OVERLAYS or READY_TO_AUGMENT_IMAGES
	 * 
	 * @param overlay
	 *            the overlay to add
	 * @return an OverlayResponse containing the id of the new overlay
	 */
	@RequiredState({ State.NEEDS_OVERLAYS, State.READY_TO_AUGMENT_IMAGES })
	public OverlayResponse addOverlay(Overlay overlay);

	/**
	 * Synchronously update an overlay. Throws an ARException if the state is
	 * not READY_TO_AUGMENT_IMAGES
	 * 
	 * @param id
	 *            the id of the overlay to update
	 * @param data
	 *            the data with which to update the specified overlay
	 * @return an OverlayResponse containing the id of the new overlya
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public OverlayResponse updateOverlay(OverlayResponse overlayToUpdate,
			Overlay newOverlay);

	/**
	 * Synchronously delete an overlay. Throws an ARException if the state is
	 * not READY_TO_AUGMENT_IMAGES
	 * 
	 * @param id
	 *            the id of the overlay to delete
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public void deleteOverlay(String id);

	/**
	 * Synchronously augment an image
	 * 
	 * @param image
	 *            an inputstream containing the image
	 * @return the augmented data
	 */
	@RequiredState(State.READY_TO_AUGMENT_IMAGES)
	public AugmentedData augmentImage(InputStream image);

	/**
	 * Synchronously delete the site
	 */
	public void delete();

	/**
	 * Makes a synchronous server request to get the site info
	 * 
	 * @return
	 */
	public SiteInfo getSiteInfo();

	/**
	 * Synchronously get the site's base images
	 * 
	 * @return
	 */
	public List<BaseImageInfo> getBaseImages();

}
