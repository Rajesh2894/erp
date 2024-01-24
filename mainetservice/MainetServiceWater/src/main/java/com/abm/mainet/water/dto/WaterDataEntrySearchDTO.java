package com.abm.mainet.water.dto;

import java.io.Serializable;

public class WaterDataEntrySearchDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1185749671314552734L;

    private String csCcn;
    private String csOldccn;
    private String propertyNo;
    private String csName;
    private String csAdd;
    private String csContactno;
    private Long codDwzid1;
    private Long codDwzid2;
    private Long codDwzid3;
    private Long codDwzid4;
    private Long codDwzid5;
    private String rowId;
    private Long locId;
    private Long orgId;
    private String gardianOwnerName;
    private String errorMsg;
    private String status;
    private String ward;
    private String zone;
    private String payableAmount;

    public String getCsCcn() {
        return csCcn;
    }

    public void setCsCcn(String csCcn) {
        this.csCcn = csCcn;
    }

    public String getCsOldccn() {
        return csOldccn;
    }

    public void setCsOldccn(String csOldccn) {
        this.csOldccn = csOldccn;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getCsAdd() {
        return csAdd;
    }

    public void setCsAdd(String csAdd) {
        this.csAdd = csAdd;
    }

    public String getCsContactno() {
        return csContactno;
    }

    public void setCsContactno(String csContactno) {
        this.csContactno = csContactno;
    }

    public Long getCodDwzid1() {
        return codDwzid1;
    }

    public void setCodDwzid1(Long codDwzid1) {
        this.codDwzid1 = codDwzid1;
    }

    public Long getCodDwzid2() {
        return codDwzid2;
    }

    public void setCodDwzid2(Long codDwzid2) {
        this.codDwzid2 = codDwzid2;
    }

    public Long getCodDwzid3() {
        return codDwzid3;
    }

    public void setCodDwzid3(Long codDwzid3) {
        this.codDwzid3 = codDwzid3;
    }

    public Long getCodDwzid4() {
        return codDwzid4;
    }

    public void setCodDwzid4(Long codDwzid4) {
        this.codDwzid4 = codDwzid4;
    }

    public Long getCodDwzid5() {
        return codDwzid5;
    }

    public void setCodDwzid5(Long codDwzid5) {
        this.codDwzid5 = codDwzid5;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getEditFlag() {
        return getRowId();
    }

    public void setEditFlag(String l) {

    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getGardianOwnerName() {
        return gardianOwnerName;
    }

    public void setGardianOwnerName(String gardianOwnerName) {
        this.gardianOwnerName = gardianOwnerName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}

	@Override
	public String toString() {
		return "WaterDataEntrySearchDTO [csCcn=" + csCcn + ", csOldccn=" + csOldccn + ", propertyNo=" + propertyNo
				+ ", csName=" + csName + ", csAdd=" + csAdd + ", csContactno=" + csContactno + ", codDwzid1="
				+ codDwzid1 + ", codDwzid2=" + codDwzid2 + ", codDwzid3=" + codDwzid3 + ", codDwzid4=" + codDwzid4
				+ ", codDwzid5=" + codDwzid5 + ", rowId=" + rowId + ", locId=" + locId + ", orgId=" + orgId
				+ ", gardianOwnerName=" + gardianOwnerName + ", errorMsg=" + errorMsg + ", status=" + status + ", ward="
				+ ward + ", zone=" + zone + ", payableAmount=" + payableAmount + "]";
	}

}
