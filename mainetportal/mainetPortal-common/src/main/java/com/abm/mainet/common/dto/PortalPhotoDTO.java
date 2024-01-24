package com.abm.mainet.common.dto;

import java.io.Serializable;

public class PortalPhotoDTO implements Serializable {

    private static final long serialVersionUID = -2059692481646888764L;

    private String imagePath;
    private String caption;

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

}
