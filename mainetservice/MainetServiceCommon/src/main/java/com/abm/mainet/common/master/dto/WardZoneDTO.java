package com.abm.mainet.common.master.dto;

import java.io.Serializable;

public class WardZoneDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long wordId;
    private Long zoneId;
    private Long blockId;
    private String wardName;
    private String zoneName;
    private Long locationId;
    private String blockName;
    private String locationName;

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(final Long wordId) {
        this.wordId = wordId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(final Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(final String wardName) {
        this.wardName = wardName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(final String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(final Long blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(final String blockName) {
        this.blockName = blockName;
    }

}
