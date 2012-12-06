package com.parworks.androidlibrary.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OverlayPoint implements Serializable {		
	
	private float x;
	private float y;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}		
	
	public void scale(float scale) {
		x = x * scale;
		y = y * scale;
	}
}

