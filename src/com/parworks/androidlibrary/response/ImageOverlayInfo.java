package com.parworks.androidlibrary.response;
import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class ImageOverlayInfo implements Serializable {
	private String site;
	private String content;
	private String id;
	private String imageId;
	private String accuracy;
	private String name;
	private List<OverlayPoint> points;

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
}
