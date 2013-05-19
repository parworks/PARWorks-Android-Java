package com.parworks.androidlibrary.response.photochangedetection;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parworks.androidlibrary.ar.Vertex;
import com.parworks.androidlibrary.response.OverlayPoint;

public class ChangeDetectionInstance {
	private List<String> boundingBox;
	private String result;
	private String comment;
	public static final String TAG = ChangeDetectionInstance.class.getName();
	public List<String> getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(List<String> boundingBox) {
		this.boundingBox = boundingBox;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<Vertex> getVertices() {
		List<Vertex> vertices = new ArrayList<Vertex>();
		for(String boundingBoxString : boundingBox) {
			Vertex vertex = convertBoxPointToVertex(boundingBoxString);
			vertices.add(vertex);
		}
		return vertices;
		
	}
	
	public boolean isCorrect() {
		if(result.equals("CORRECT")) {
			return true;
		} else if(result.equals("INCORRECT")) {
			return false;
		} else {
			Log.e(TAG,"Result should be either CORRECT or INCORRECT. Instead it was: " + result);
			return false;
		}
	}
	
	private Vertex convertBoxPointToVertex(String boundingBoxString) {
		float[] xyCoords = parseBoundingBoxString(boundingBoxString);
		
		Vertex vertex = new Vertex(xyCoords[0],xyCoords[1],1.0f);
		return vertex;
	}
	private float[] parseBoundingBoxString(String boundingBoxString) {
		String[] xyStringCoords = boundingBoxString.split(",");
		float[] xyFloatCoords = new float[2];
		xyFloatCoords[0] = Float.parseFloat(xyStringCoords[0]);
		xyFloatCoords[1] = Float.parseFloat(xyStringCoords[1]);
		return xyFloatCoords;
	}
	@Override
	public String toString() {
		String returnString = "boundingBox : [";
		for(String box : boundingBox) {
			returnString += box + ", ";
		}
		returnString += "],\n";
		returnString += "result : " + result;
		returnString += "\n comment : " + comment;
		return returnString;
		
	}

}
