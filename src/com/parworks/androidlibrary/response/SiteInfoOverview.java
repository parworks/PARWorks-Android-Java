/*******************************************************************************
 * Copyright 2013 PAR Works, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.parworks.androidlibrary.response;

import java.util.ArrayList;
import java.util.List;

public class SiteInfoOverview {
	private String id;
	private String name;
	private String address;	
	private String description;
	private String siteState;
	private Double lon;
	private Double lat;
	private String posterImageURL;
	private String posterImageOverlayContent;
	private String posterImageBlurredURL;
	private AugmentedImage augmentedPosterImage;
	private int numAugmentedImages;
	private List<AugmentedImage> recentlyAugmentedImages = new ArrayList<AugmentedImage>();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the siteState
	 */
	public String getSiteState() {
		return siteState;
	}

	/**
	 * @param siteState the siteState to set
	 */
	public void setSiteState(String siteState) {
		this.siteState = siteState;
	}

	/**
	 * @return the posterImageURL
	 */
	public String getPosterImageURL() {
		return posterImageURL;
	}

	/**
	 * @param posterImageURL the posterImageURL to set
	 */
	public void setPosterImageURL(String posterImageURL) {
		this.posterImageURL = posterImageURL;
	}

	/**
	 * @return the numAugmentedImages
	 */
	public int getNumAugmentedImages() {
		return numAugmentedImages;
	}

	/**
	 * @param numAugmentedImages the numAugmentedImages to set
	 */
	public void setNumAugmentedImages(int numAugmentedImages) {
		this.numAugmentedImages = numAugmentedImages;
	}

	/**
	 * @return the posterImageOverlayContent
	 */
	public String getPosterImageOverlayContent() {
		return posterImageOverlayContent;
	}

	/**
	 * @param posterImageOverlayContent the posterImageOverlayContent to set
	 */
	public void setPosterImageOverlayContent(String posterImageOverlayContent) {
		this.posterImageOverlayContent = posterImageOverlayContent;
	}

	/**
	 * @return the lon
	 */
	public Double getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}

	/**
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	public List<AugmentedImage> getRecentlyAugmentedImages() {
		return recentlyAugmentedImages;
	}

	public void setRecentlyAugmentedImages(List<AugmentedImage> recentlyAugmentedImages) {
		this.recentlyAugmentedImages = recentlyAugmentedImages;
	}

	public String getPosterImageBlurredURL() {
		return posterImageBlurredURL;
	}

	public void setPosterImageBlurredURL(String posterImageBlurredURL) {
		this.posterImageBlurredURL = posterImageBlurredURL;
	}

	public AugmentedImage getAugmentedPosterImage() {
		return augmentedPosterImage;
	}

	public void setAugmentedPosterImage(AugmentedImage augmentedPosterImage) {
		this.augmentedPosterImage = augmentedPosterImage;
	}
}
