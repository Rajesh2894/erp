/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO class for Asset Management Specification
 * 
 * @author sarojkumar.yadav
 *
 */
public class AssetSpecificationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7647441880275409872L;

    private Long assetId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private BigDecimal length;
    private Long lengthValue;
    private BigDecimal breadth;
    private Long breadthValue;
    private BigDecimal height;
    private Long heightValue;
    private BigDecimal width;
    private Long widthValue;
    private BigDecimal weight;
    private Long weightValue;
    private BigDecimal area;
    private Long areaValue;
    private BigDecimal volume;
    private Long volumeValue;
    private BigDecimal carpet;
    private Long carpetValue;
    private Long noOfFloor;

    /**
     * @return the assetId
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the length
     */
    public BigDecimal getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(BigDecimal length) {
        this.length = length;
    }

    /**
     * @return the lengthValue
     */
    public Long getLengthValue() {
        return lengthValue;
    }

    /**
     * @param lengthValue the lengthValue to set
     */
    public void setLengthValue(Long lengthValue) {
        this.lengthValue = lengthValue;
    }

    /**
     * @return the breadth
     */
    public BigDecimal getBreadth() {
        return breadth;
    }

    /**
     * @param breadth the breadth to set
     */
    public void setBreadth(BigDecimal breadth) {
        this.breadth = breadth;
    }

    /**
     * @return the breadthValue
     */
    public Long getBreadthValue() {
        return breadthValue;
    }

    /**
     * @param breadthValue the breadthValue to set
     */
    public void setBreadthValue(Long breadthValue) {
        this.breadthValue = breadthValue;
    }

    /**
     * @return the height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * @return the heightValue
     */
    public Long getHeightValue() {
        return heightValue;
    }

    /**
     * @param heightValue the heightValue to set
     */
    public void setHeightValue(Long heightValue) {
        this.heightValue = heightValue;
    }

    /**
     * @return the width
     */
    public BigDecimal getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    /**
     * @return the widthValue
     */
    public Long getWidthValue() {
        return widthValue;
    }

    /**
     * @param widthValue the widthValue to set
     */
    public void setWidthValue(Long widthValue) {
        this.widthValue = widthValue;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the weightValue
     */
    public Long getWeightValue() {
        return weightValue;
    }

    /**
     * @param weightValue the weightValue to set
     */
    public void setWeightValue(Long weightValue) {
        this.weightValue = weightValue;
    }

    /**
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * @return the areaValue
     */
    public Long getAreaValue() {
        return areaValue;
    }

    /**
     * @param areaValue the areaValue to set
     */
    public void setAreaValue(Long areaValue) {
        this.areaValue = areaValue;
    }

    /**
     * @return the volume
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * @return the volumeValue
     */
    public Long getVolumeValue() {
        return volumeValue;
    }

    /**
     * @param volumeValue the volumeValue to set
     */
    public void setVolumeValue(Long volumeValue) {
        this.volumeValue = volumeValue;
    }

    /**
     * @return the carpet
     */
    public BigDecimal getCarpet() {
        return carpet;
    }

    /**
     * @param carpet the carpet to set
     */
    public void setCarpet(BigDecimal carpet) {
        this.carpet = carpet;
    }

    /**
     * @return the carpetValue
     */
    public Long getCarpetValue() {
        return carpetValue;
    }

    /**
     * @param carpetValue the carpetValue to set
     */
    public void setCarpetValue(Long carpetValue) {
        this.carpetValue = carpetValue;
    }

    /**
     * @return the noOfFloor
     */
    public Long getNoOfFloor() {
        return noOfFloor;
    }

    /**
     * @param noOfFloor the noOfFloor to set
     */
    public void setNoOfFloor(Long noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AssetSpecificationDTO [assetId=" + assetId + ", creationDate=" + creationDate + ", createdBy=" + createdBy
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", length=" + length + ", lengthValue=" + lengthValue + ", breadth=" + breadth + ", breadthValue="
                + breadthValue + ", height=" + height + ", heightValue=" + heightValue + ", width=" + width + ", widthValue="
                + widthValue + ", weight=" + weight + ", weightValue=" + weightValue + ", area=" + area + ", areaValue="
                + areaValue + ", volume=" + volume + ", volumeValue=" + volumeValue + ", carpet=" + carpet + ", carpetValue="
                + carpetValue + ", noOfFloor=" + noOfFloor + "]";
    }

}
