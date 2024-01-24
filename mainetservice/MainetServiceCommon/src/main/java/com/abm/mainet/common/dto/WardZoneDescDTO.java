package com.abm.mainet.common.dto;

import java.io.Serializable;

public class WardZoneDescDTO implements Serializable {

    private static final long serialVersionUID = 4344098497963131101L;

    private String wardZoneLabel;
    private String wardZoneDesc;

    public String getWardZoneLabel() {
        return wardZoneLabel;
    }

    public void setWardZoneLabel(String wardZoneLabel) {
        this.wardZoneLabel = wardZoneLabel;
    }

    public String getWardZoneDesc() {
        return wardZoneDesc;
    }

    public void setWardZoneDesc(String wardZoneDesc) {
        this.wardZoneDesc = wardZoneDesc;
    }

}
