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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class ImageOverlayInfo implements Serializable {

	private String site;
	private String content;
	private String id;
	private String imageId;
	private String accuracy;
	private String name;
	private List<OverlayPoint> points;

	/** 
	 * The configuration object by parsing the content value.
	 * 
	 * The reason to not replace content String with this object
	 * is to better handle old overlay content without the JSON
	 * format.
	 */
	private OverlayConfiguration configuration;
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		
		// parse the content whenever this is set
		this.configuration = parseOverlayContent();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OverlayPoint> getPoints() {
		return points;
	}

	public void setPoints(List<OverlayPoint> points) {
		this.points = points;
	}

	public OverlayConfiguration getConfiguration() {
		return configuration;
	}
	
	public ImageOverlayInfo clone() {
		ImageOverlayInfo info = new ImageOverlayInfo();
		info.configuration = parseOverlayContent();
		info.points = this.points;
		info.name = this.name;
		info.accuracy = this.accuracy;
		info.imageId = this.imageId;
		info.id = this.id;
		info.content = this.content;
		info.site = this.site;
		return info;
	}

	private OverlayConfiguration parseOverlayContent() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OverlayConfiguration conf = null;
		try {
			conf = content == null ? new OverlayConfiguration()
					: mapper.readValue(content, OverlayConfiguration.class);
		} catch (IOException e) {
			// when failing to parse the overlay content,
			// generate an empty object and use default for everything
			conf = new OverlayConfiguration();			
		}
		return conf;
	}
}

