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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parworks.androidlibrary.ar.ARException;

@SuppressWarnings("serial")
public class OverlayConfiguration implements Serializable {
	
	private String title;
	private OverlayBoundary boundary = new OverlayBoundary();
	private OverlayCover cover = new OverlayCover();
	private OverlayContent content = new OverlayContent();
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public OverlayBoundary getBoundary() {
		return boundary;
	}
	
	public void setBoundary(OverlayBoundary boundary) {
		this.boundary = boundary;
	}
	
	public OverlayContent getContent() {
		return content;
	}
	
	public void setContent(OverlayContent content) {
		this.content = content;
	}
	
	public OverlayCover getCover() {
		return cover;
	}
	
	public void setCover(OverlayCover cover) {
		this.cover = cover;
	}
	
	public String toJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			objectMapper.writeValue(outputStream, OverlayConfiguration.this);
		} catch (JsonGenerationException e) {
			throw new ARException(e.getMessage(),e);
		} catch (JsonMappingException e) {
			throw new ARException(e.getMessage(),e);
		} catch (IOException e) {
			throw new ARException(e.getMessage(),e);
		}
		return outputStream.toString();
		
	}
}
