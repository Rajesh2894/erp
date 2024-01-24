package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;

/**
 * This class use for to set and get all look up related data for application.
 * 
 * 
 * @author Jeetendra.Pal
 */
public class LookUp implements Serializable {
    private static final long serialVersionUID = -3818986783455402310L;

    private long lookUpId;
    private String lookUpCode;
    private String lookUpDesc;
    private String lookUpType;
    private long lookUpParentId;
    private String descLangFirst;
    private String descLangSecond;
    private String defaultVal = "N";
    private String extraStringField1;
    private String otherField;
    private long lookUpExtraLongOne;
    private long lookUpExtraLongTwo;
    private String dmsDocId;

    public long getLookUpId() {
        return lookUpId;
    }

    public void setLookUpId(long lookUpId) {
        this.lookUpId = lookUpId;
    }

    public String getLookUpCode() {
        return lookUpCode;
    }

    public void setLookUpCode(String lookUpCode) {
        this.lookUpCode = lookUpCode;
    }

    public String getLookUpDesc() {
        return lookUpDesc;
    }

    public void setLookUpDesc(String lookUpDesc) {
        this.lookUpDesc = lookUpDesc;
    }

    public String getLookUpType() {
        return lookUpType;
    }

    public void setLookUpType(String lookUpType) {
        this.lookUpType = lookUpType;
    }

    public long getLookUpParentId() {
        return lookUpParentId;
    }

    public void setLookUpParentId(long lookUpParentId) {
        this.lookUpParentId = lookUpParentId;
    }

    public String getDescLangFirst() {
        return descLangFirst;
    }

    public void setDescLangFirst(String descLangFirst) {
        this.descLangFirst = descLangFirst;
    }

    public String getDescLangSecond() {
        return descLangSecond;
    }

    public void setDescLangSecond(String descLangSecond) {
        this.descLangSecond = descLangSecond;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getExtraStringField1() {
        return extraStringField1;
    }

    public void setExtraStringField1(String extraStringField1) {
        this.extraStringField1 = extraStringField1;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }

    public long getLookUpExtraLongOne() {
        return lookUpExtraLongOne;
    }

    public void setLookUpExtraLongOne(long lookUpExtraLongOne) {
        this.lookUpExtraLongOne = lookUpExtraLongOne;
    }

    public long getLookUpExtraLongTwo() {
        return lookUpExtraLongTwo;
    }

    public void setLookUpExtraLongTwo(long lookUpExtraLongTwo) {
        this.lookUpExtraLongTwo = lookUpExtraLongTwo;
    }

	public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

}
