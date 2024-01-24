package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SWMComplaintFilesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> images=new ArrayList<>();
	private List<String> videos=new ArrayList<>();
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<String> getVideos() {
		return videos;
	}
	public void setVideos(List<String> videos) {
		this.videos = videos;
	}
	
	
	
	
}
