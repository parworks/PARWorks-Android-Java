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


/**
 * Returned after adding a base image to an ARSite. Contains the id necessary to access that base image.
 * @author Jules White
 *
 */
public class BaseImage {

	private final String baseImageId_;
	private String contentSizeImagePath;
	private String fullSizeImagePath;
	private String contentSizeUrl;
	private int width;
	private int height;

	public BaseImage(String baseImageId) {
		super();
		baseImageId_ = baseImageId;
	}

	public String getBaseImageId() {
		return baseImageId_;
	}

	public String getContentSizeImagePath() {
		return contentSizeImagePath;
	}

	public void setContentSizeImagePath(String contentSizeImagePath) {
		this.contentSizeImagePath = contentSizeImagePath;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFullSizeImagePath() {
		return fullSizeImagePath;
	}

	public void setFullSizeImagePath(String fullSizeImagePath) {
		this.fullSizeImagePath = fullSizeImagePath;
	}

	public String getContentSizeUrl() {
		return contentSizeUrl;
	}

	public void setContentSizeUrl(String contentSizeUrl) {
		this.contentSizeUrl = contentSizeUrl;
	}
}
