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
 * 
 * @author Jules White
 *
 */
public class OverlayData {

	private float vertices_;

	private String name_;

	private String description_;

	private String baseImageId_;

	public OverlayData() {
	}

	public OverlayData(String name, String description, String baseImageId,
			float vertices) {
		super();
		name_ = name;
		description_ = description;
		baseImageId_ = baseImageId;
		vertices_ = vertices;
	}

	public float getVertices() {
		return vertices_;
	}

	public void setVertices(float vertices) {
		vertices_ = vertices;
	}

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}

	public String getDescription() {
		return description_;
	}

	public void setDescription(String description) {
		description_ = description;
	}

	public String getBaseImageId() {
		return baseImageId_;
	}

	public void setBaseImageId(String baseImageId) {
		baseImageId_ = baseImageId;
	}

}
