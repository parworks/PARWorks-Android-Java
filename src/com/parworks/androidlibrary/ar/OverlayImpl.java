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

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class OverlayImpl implements Overlay, Serializable {
	
	private String mImageId;
	private String mName;
	private String mDescription;
	private List<Vertex> mVertices;
	
	public OverlayImpl(BaseImage imageId, String name, String description, List<Vertex> vertices) {
		mImageId = imageId.getBaseImageId();
		mName = name;
		mDescription = description;
		mVertices = vertices;
	}
	
	public OverlayImpl(String imageId, String name, String description, List<Vertex> vertices) {
		mImageId = imageId;
		mName = name;
		mDescription = description;
		mVertices = vertices;
	}

	@Override
	public String getImageId() {
		return mImageId;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}

	@Override
	public List<Vertex> getVertices() {
		return mVertices;
	}

}
