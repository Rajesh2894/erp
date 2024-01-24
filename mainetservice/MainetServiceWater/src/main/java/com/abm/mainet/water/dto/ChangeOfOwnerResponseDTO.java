package com.abm.mainet.water.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dto.ResponseDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ChangeOfOwnerResponseDTO extends ResponseDTO {

    private static final long serialVersionUID = -1152720384941849599L;

    private Long cooOtitle;

    private String cooOname;

    private String cooOomname;

    private String cooOolname;

    private Long cooUidNo;

    private String connectionNumber;

    private long conId;

    private Long conType;

    private Long conSize;

    private String conCategory;
    private Long codDwzid1;
    private Long codDwzid2;
    private Long codDwzid3;
    private Long codDwzid4;
    // tariff category
    private Long trmGroup1;
    private Long trmGroup2;
    private Long trmGroup3;
    private Long trmGroup4;
    private Long trmGroup5;

    private String oldOwnerFullName;
    private Long csOGender;

    private String canApplyOrNot;

    private Long meterType;
    private Long applicantType;

    private Long taxCategory;
    private String taxSubCategory;
    private String errorFlag;
    private String csAddress;
    private String errMsg;
    private String csOldNo;

    public Long getCooOtitle() {
        return cooOtitle;
    }

    public void setCooOtitle(final Long cooOtitle) {
        this.cooOtitle = cooOtitle;
    }

    public String getCooOname() {
        return cooOname;
    }

    public void setCooOname(final String cooOname) {
        this.cooOname = cooOname;
    }

    public String getCooOomname() {
        return cooOomname;
    }

    public void setCooOomname(final String cooOomname) {
        this.cooOomname = cooOomname;
    }

    public String getCooOolname() {
        return cooOolname;
    }

    public void setCooOolname(final String cooOolname) {
        this.cooOolname = cooOolname;
    }

    public String getConnectionNumber() {
        return connectionNumber;
    }

    public void setConnectionNumber(final String connectionNumber) {
        this.connectionNumber = connectionNumber;
    }

    public long getConId() {
        return conId;
    }

    public void setConId(final long conId) {
        this.conId = conId;
    }

    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(final String conCategory) {
        this.conCategory = conCategory;
    }

    public Long getConType() {
        return conType;
    }

    public void setConType(final Long conType) {
        this.conType = conType;
    }

    public Long getConSize() {
        return conSize;
    }

    public void setConSize(final Long conSize) {
        this.conSize = conSize;
    }

    public Long getCooUidNo() {
        return cooUidNo;
    }

    public void setCooUidNo(final Long cooUidNo) {
        this.cooUidNo = cooUidNo;
    }

    public Long getCodDwzid1() {
        return codDwzid1;
    }

    public void setCodDwzid1(final Long codDwzid1) {
        this.codDwzid1 = codDwzid1;
    }

    public Long getCodDwzid2() {
        return codDwzid2;
    }

    public void setCodDwzid2(final Long codDwzid2) {
        this.codDwzid2 = codDwzid2;
    }

    public Long getCodDwzid3() {
        return codDwzid3;
    }

    public void setCodDwzid3(final Long codDwzid3) {
        this.codDwzid3 = codDwzid3;
    }

    public Long getCodDwzid4() {
        return codDwzid4;
    }

    public void setCodDwzid4(final Long codDwzid4) {
        this.codDwzid4 = codDwzid4;
    }

    public Long getTrmGroup1() {
        return trmGroup1;
    }

    public void setTrmGroup1(final Long trmGroup1) {
        this.trmGroup1 = trmGroup1;
    }

    public Long getTrmGroup2() {
        return trmGroup2;
    }

    public void setTrmGroup2(final Long trmGroup2) {
        this.trmGroup2 = trmGroup2;
    }

    public Long getTrmGroup3() {
        return trmGroup3;
    }

    public void setTrmGroup3(final Long trmGroup3) {
        this.trmGroup3 = trmGroup3;
    }

    public Long getTrmGroup4() {
        return trmGroup4;
    }

    public void setTrmGroup4(final Long trmGroup4) {
        this.trmGroup4 = trmGroup4;
    }

    public Long getTrmGroup5() {
        return trmGroup5;
    }

    public void setTrmGroup5(final Long trmGroup5) {
        this.trmGroup5 = trmGroup5;
    }

    public String getOldOwnerFullName() {
        return oldOwnerFullName;
    }

    public void setOldOwnerFullName(final String oldOwnerFullName) {
        this.oldOwnerFullName = oldOwnerFullName;
    }

    public Long getCsOGender() {
        return csOGender;
    }

    public void setCsOGender(final Long csOGender) {
        this.csOGender = csOGender;
    }

    public String getCanApplyOrNot() {
        return canApplyOrNot;
    }

    public void setCanApplyOrNot(final String canApplyOrNot) {
        this.canApplyOrNot = canApplyOrNot;
    }

    public Long getMeterType() {
        return meterType;
    }

    public void setMeterType(final Long meterType) {
        this.meterType = meterType;
    }

    public Long getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(final Long applicantType) {
        this.applicantType = applicantType;
    }

    /**
     * @return the taxCategory
     */
    public Long getTaxCategory() {
        return taxCategory;
    }

    /**
     * @param taxCategory the taxCategory to set
     */
    public void setTaxCategory(final Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    /**
     * @return the taxSubCategory
     */
    public String getTaxSubCategory() {
        return taxSubCategory;
    }

    /**
     * @param taxSubCategory the taxSubCategory to set
     */
    public void setTaxSubCategory(final String taxSubCategory) {
        this.taxSubCategory = taxSubCategory;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(final String errorFlag) {
        this.errorFlag = errorFlag;
    }

	public String getCsAddress() {
		return csAddress;
	}

	public void setCsAddress(String csAddress) {
		this.csAddress = csAddress;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getCsOldNo() {
		return csOldNo;
	}

	public void setCsOldNo(String csOldNo) {
		this.csOldNo = csOldNo;
	}

	
}
