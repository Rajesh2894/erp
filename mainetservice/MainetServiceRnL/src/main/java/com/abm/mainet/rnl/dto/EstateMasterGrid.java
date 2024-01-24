package com.abm.mainet.rnl.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class EstateMasterGrid implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long esId;
    private String code;
    private String nameEng;
    private String nameReg;
    private String locationEng;
    private String locationReg;
    private String address;
    private Character cat;
    private String category;
    private Integer type;
    private Integer subType;
    private String categoryName;
    private String typeName;
    private String subTypeName;
    private Long purpose;// EPR
    private String purposeDesc;
    private Long acquisitionType;
    private String acquisitionTypeName;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(final String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(final String nameReg) {
        this.nameReg = nameReg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Integer getType() {
        return type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(final Integer subType) {
        this.subType = subType;
    }

    public String getLocationEng() {
        return locationEng;
    }

    public void setLocationEng(final String locationEng) {
        this.locationEng = locationEng;
    }

    public String getLocationReg() {
        return locationReg;
    }

    public void setLocationReg(final String locationReg) {
        this.locationReg = locationReg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(final String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Long getEsId() {
        return esId;
    }

    public void setEsId(final Long esId) {
        this.esId = esId;
    }

    public Character getCat() {
        return cat;
    }

    public void setCat(final Character cat) {
        this.cat = cat;
    }

	public Long getPurpose() {
		return purpose;
	}

	public void setPurpose(Long purpose) {
		this.purpose = purpose;
	}

	public String getPurposeDesc() {
		return purposeDesc;
	}

	public void setPurposeDesc(String purposeDesc) {
		this.purposeDesc = purposeDesc;
	}

	public Long getAcquisitionType() {
		return acquisitionType;
	}

	public void setAcquisitionType(Long acquisitionType) {
		this.acquisitionType = acquisitionType;
	}

	public String getAcquisitionTypeName() {
		return acquisitionTypeName;
	}

	public void setAcquisitionTypeName(String acquisitionTypeName) {
		this.acquisitionTypeName = acquisitionTypeName;
	}

}
