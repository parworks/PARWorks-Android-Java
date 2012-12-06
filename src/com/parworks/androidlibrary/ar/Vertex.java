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

@SuppressWarnings("serial")
public class Vertex implements Serializable {

	private float xCoord;
	private float yCoord;
	private float zCoord;

	public Vertex(float x, float y, float z) {
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	public float getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public float getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public float getzCoord() {
		return zCoord;
	}

	public void setzCoord(float zCoord) {
		this.zCoord = zCoord;
	}
	
	public String toString() {
		return "(" + xCoord + "," + yCoord + "," + zCoord + ")";
	}

}
