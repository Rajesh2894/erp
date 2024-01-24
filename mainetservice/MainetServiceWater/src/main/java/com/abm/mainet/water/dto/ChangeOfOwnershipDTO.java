package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Vivek.Kumar
 * @since 05-May-2016
 */
public class ChangeOfOwnershipDTO implements Serializable {
    private static final long serialVersionUID = 1969343458672817125L;

    private long chIdn;

    private Long csIdn;

    private Long apmApplicationId;

    private Date chAPLDate;

    private Long chNewTitle;

    private String chNewName;

    private String chNewMName;

    private String chNewLname;

    private Long chNewGender;

    private Long chNewUIDNo;

    private String chNewOrgName;

    private Long chOldTitle;

    private String chOldName;

    private String chOldMnNme;

    private String chOldLName;

    private Long chOldUIDNo;

    private Long chOldGender;

    private String chOldOrgName;

    private String chRemark;

    private String chGranted;

    private Long orgId;

    private Long createdBy;

    private Long langId;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String wtV1;

    private String wtV2;

    private String wtV3;

    private String wtV4;

    private String wtV5;

    private Long wtN1;

    private Long wtN2;

    private Long wtN3;

    private Long wtN4;

    private Long wtN5;

    private Date wtD1;

    private Date wtD2;

    private Date wtD3;

    private String wtLo1;

    private String wtLo2;

    private String wtLo3;

    // ward, zone, block ids
    private Long codDwzid1;
    private Long codDwzid2;
    private Long codDwzid3;
    private Long codDwzid4;

    // Tariff type Premise
    private Long trmGroup1;
    private Long trmGroup2;
    private Long trmGroup3;
    private Long trmGroup4;
    private Long trmGroup5;
    // additional owners info
    private List<AdditionalOwnerInfoDTO> additionalOwners;
    private Long gender;
    private String ownerFullName;

    // change of ownership execution info
    private String applicantFullName;
    private String serviceName;
    private String applicationDate;
    private String executeFlag;
    private Date ownerChangeDate;
    private String approvedBy;
    private String approvedDate;

    private TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();
    private String executionDate;
    private Date loiDate;
    private String loiNo;
    private Long receiptNo;
    private String receiptDate;
    private String name;
    private String address;
    private String connectionNo;
    private String city;
    private String connectionInch;
    private Date paymentDate;
    private Date startDate;
    private String customString;
    private String serviceCode;
    
    public long getChIdn() {
        return chIdn;
    }

    public void setChIdn(final long chIdn) {
        this.chIdn = chIdn;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getChAPLDate() {
        return chAPLDate;
    }

    public void setChAPLDate(final Date chAPLDate) {
        this.chAPLDate = chAPLDate;
    }

    public Long getChNewTitle() {
        return chNewTitle;
    }

    public void setChNewTitle(final Long chNewTitle) {
        this.chNewTitle = chNewTitle;
    }

    public String getChNewName() {
        return chNewName;
    }

    public void setChNewName(final String chNewName) {
        this.chNewName = chNewName;
    }

    public String getChNewMName() {
        return chNewMName;
    }

    public void setChNewMName(final String chNewMName) {
        this.chNewMName = chNewMName;
    }

    public String getChNewLname() {
        return chNewLname;
    }

    public void setChNewLname(final String chNewLname) {
        this.chNewLname = chNewLname;
    }

    public Long getChNewGender() {
        return chNewGender;
    }

    public void setChNewGender(final Long chNewGender) {
        this.chNewGender = chNewGender;
    }

    public Long getChNewUIDNo() {
        return chNewUIDNo;
    }

    public void setChNewUIDNo(final Long chNewUIDNo) {
        this.chNewUIDNo = chNewUIDNo;
    }

    public String getChNewOrgName() {
        return chNewOrgName;
    }

    public void setChNewOrgName(final String chNewOrgName) {
        this.chNewOrgName = chNewOrgName;
    }

    public Long getChOldTitle() {
        return chOldTitle;
    }

    public void setChOldTitle(final Long chOldTitle) {
        this.chOldTitle = chOldTitle;
    }

    public String getChOldName() {
        return chOldName;
    }

    public void setChOldName(final String chOldName) {
        this.chOldName = chOldName;
    }

    public String getChOldMnNme() {
        return chOldMnNme;
    }

    public void setChOldMnNme(final String chOldMnNme) {
        this.chOldMnNme = chOldMnNme;
    }

    public String getChOldLName() {
        return chOldLName;
    }

    public void setChOldLName(final String chOldLName) {
        this.chOldLName = chOldLName;
    }

    public Long getChOldUIDNo() {
        return chOldUIDNo;
    }

    public void setChOldUIDNo(final Long chOldUIDNo) {
        this.chOldUIDNo = chOldUIDNo;
    }

    public Long getChOldGender() {
        return chOldGender;
    }

    public void setChOldGender(final Long chOldGender) {
        this.chOldGender = chOldGender;
    }

    public String getChOldOrgName() {
        return chOldOrgName;
    }

    public void setChOldOrgName(final String chOldOrgName) {
        this.chOldOrgName = chOldOrgName;
    }

    public String getChRemark() {
        return chRemark;
    }

    public void setChRemark(final String chRemark) {
        this.chRemark = chRemark;
    }

    public String getChGranted() {
        return chGranted;
    }

    public void setChGranted(final String chGranted) {
        this.chGranted = chGranted;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWtN1() {
        return wtN1;
    }

    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
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

    public List<AdditionalOwnerInfoDTO> getAdditionalOwners() {
        return additionalOwners;
    }

    public void setAdditionalOwners(final List<AdditionalOwnerInfoDTO> additionalOwners) {
        this.additionalOwners = additionalOwners;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(final Long gender) {
        this.gender = gender;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(final String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(final String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getExecuteFlag() {
        return executeFlag;
    }

    public void setExecuteFlag(final String executeFlag) {
        this.executeFlag = executeFlag;
    }

    public Date getOwnerChangeDate() {
        return ownerChangeDate;
    }

    public void setOwnerChangeDate(final Date ownerChangeDate) {
        this.ownerChangeDate = ownerChangeDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(final String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(final String approvedDate) {
        this.approvedDate = approvedDate;
    }

	public TbCsmrInfoDTO getCsmrInfoDTO() {
		return csmrInfoDTO;
	}

	public void setCsmrInfoDTO(TbCsmrInfoDTO csmrInfoDTO) {
		this.csmrInfoDTO = csmrInfoDTO;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public Date getLoiDate() {
		return loiDate;
	}

	public void setLoiDate(Date loiDate) {
		this.loiDate = loiDate;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public Long getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConnectionInch() {
		return connectionInch;
	}

	public void setConnectionInch(String connectionInch) {
		this.connectionInch = connectionInch;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getCustomString() {
		return customString;
	}

	public void setCustomString(String customString) {
		this.customString = customString;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Override
	public String toString() {
		return "ChangeOfOwnershipDTO [chIdn=" + chIdn + ", csIdn=" + csIdn + ", apmApplicationId=" + apmApplicationId
				+ ", chAPLDate=" + chAPLDate + ", chNewTitle=" + chNewTitle + ", chNewName=" + chNewName
				+ ", chNewMName=" + chNewMName + ", chNewLname=" + chNewLname + ", chNewGender=" + chNewGender
				+ ", chNewUIDNo=" + chNewUIDNo + ", chNewOrgName=" + chNewOrgName + ", chOldTitle=" + chOldTitle
				+ ", chOldName=" + chOldName + ", chOldMnNme=" + chOldMnNme + ", chOldLName=" + chOldLName
				+ ", chOldUIDNo=" + chOldUIDNo + ", chOldGender=" + chOldGender + ", chOldOrgName=" + chOldOrgName
				+ ", chRemark=" + chRemark + ", chGranted=" + chGranted + ", orgId=" + orgId + ", createdBy="
				+ createdBy + ", langId=" + langId + ", lmodDate=" + lmodDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", wtV1="
				+ wtV1 + ", wtV2=" + wtV2 + ", wtV3=" + wtV3 + ", wtV4=" + wtV4 + ", wtV5=" + wtV5 + ", wtN1=" + wtN1
				+ ", wtN2=" + wtN2 + ", wtN3=" + wtN3 + ", wtN4=" + wtN4 + ", wtN5=" + wtN5 + ", wtD1=" + wtD1
				+ ", wtD2=" + wtD2 + ", wtD3=" + wtD3 + ", wtLo1=" + wtLo1 + ", wtLo2=" + wtLo2 + ", wtLo3=" + wtLo3
				+ ", codDwzid1=" + codDwzid1 + ", codDwzid2=" + codDwzid2 + ", codDwzid3=" + codDwzid3 + ", codDwzid4="
				+ codDwzid4 + ", trmGroup1=" + trmGroup1 + ", trmGroup2=" + trmGroup2 + ", trmGroup3=" + trmGroup3
				+ ", trmGroup4=" + trmGroup4 + ", trmGroup5=" + trmGroup5 + ", additionalOwners=" + additionalOwners
				+ ", gender=" + gender + ", ownerFullName=" + ownerFullName + ", applicantFullName=" + applicantFullName
				+ ", serviceName=" + serviceName + ", applicationDate=" + applicationDate + ", executeFlag="
				+ executeFlag + ", ownerChangeDate=" + ownerChangeDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", csmrInfoDTO=" + csmrInfoDTO + ", executionDate=" + executionDate
				+ ", loiDate=" + loiDate + ", loiNo=" + loiNo + ", receiptNo=" + receiptNo + ", receiptDate="
				+ receiptDate + ", name=" + name + ", address=" + address + ", connectionNo=" + connectionNo + ", city="
				+ city + ", connectionInch=" + connectionInch + ", paymentDate=" + paymentDate + ", startDate="
				+ startDate + ", customString=" + customString + "]";
	}

}
