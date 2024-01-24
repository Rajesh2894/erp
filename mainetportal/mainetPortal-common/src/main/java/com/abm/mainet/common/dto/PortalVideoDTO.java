package com.abm.mainet.common.dto;

import java.io.Serializable;

public class PortalVideoDTO implements Serializable {

    private static final long serialVersionUID = -2059692481646888764L;

    private String imagePath;
    private String caption;
    private String fileSize;
    private String fileType;
    private String subtitlePath;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSubtitlePath() {
		return subtitlePath;
	}

	public void setSubtitlePath(String subtitlePath) {
		this.subtitlePath = subtitlePath;
	}

}
