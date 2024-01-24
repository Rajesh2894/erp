package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * super class DTO made for WS call, to hold request processing related common request data, this class must be inherit by every
 * module specific sub class Request DTO.
 *
 * @author Vivek.Kumar
 * @since 26-Feb-2016
 */
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 6934757961818795322L;

    private String fName;
    private String mName;
    private String lName;
    private String mobileNo;
    private String phone;
    private String email;
    private Long orgId;
    private Long deptId;
    // might used for both empId and userId
    private Long empId;
    private Long applicationId;
    private String challanNo;
    // Transaction No or Transaction Id
    private Long txnId;
    private String licenseNo;
    private Long serviceId;
    private Long userId;
    private Long langId;
    // status of Payment
    private String payStatus;
    // payable amount or paid amount or charges
    private Double payAmount;
    // Machine IP Address
    private String macId;
    private Long updatedBy;
    // service short code to identify Service
    private String serviceShortCode;

    // database-client from configuration.properties from Portal
    private String tenant;

    // being used to upload document
    private List<DocumentDetailsVO> documentList;

    // directory path where document will be stored
    private String dirPath;

    // Application Address Related Fields
    private Long titleId;
    private String blockNo;
    private String floor;
    private String wing;
    private String bldgName;
    private String houseComplexName;
    private String roadName;
    private String areaName;
    private Long pincodeNo;
    private Long applicationType;
    private String phone1;
    private String phone2;
    private Long wardNo;
    private String bplNo;
    private String gender;
    private String aadhaarNo;
    private Long zoneNo;
    private String blockName;
    private String flatBuildingNo;
    private String cityName;
    private Long uid;
    private boolean free;
    private String idfId;
    private String status;
    private String departmentName;
    private String referenceId;
    private String isBPL;
    private Long yearOfIssue;
    private String bplIssuingAuthority;
    private String apmOrgnName;
    private String apmMode;

    // added For Supplimentry Bill
    private String ccnNumber;
    private Long binder;
    private Long folio;
    private Long meterSize;
    private Long ccnSize;
    private Long ownership;
    private Date applicationDate;
    private Long locId;

    // for custom sequence generate
    private String tableName;
    private String columnName;
    private String customField;
    private String otpPass;
    private String pincodeNum;
    private String serviceShortCodeMar;
    private String departmentNameMar;
    private Long assWard1;
    private Long assWard2;
    private Long assWard3;

    public Long getAssWard1() {
		return assWard1;
	}

	public void setAssWard1(Long assWard1) {
		this.assWard1 = assWard1;
	}

	public Long getAssWard2() {
		return assWard2;
	}

	public void setAssWard2(Long assWard2) {
		this.assWard2 = assWard2;
	}

	public Long getAssWard3() {
		return assWard3;
	}

	public void setAssWard3(Long assWard3) {
		this.assWard3 = assWard3;
	}

	public String getIdfId() {
        return idfId;
    }

    public void setIdfId(String idfId) {
        this.idfId = idfId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(final boolean free) {
        this.free = free;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(final String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(final String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(final String lName) {
        this.lName = lName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final String challanNo) {
        this.challanNo = challanNo;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(final Long txnId) {
        this.txnId = txnId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(final String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the tenant
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * @param tenant the tenant to set
     */
    public void setTenant(final String tenant) {
        this.tenant = tenant;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(final List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    /**
     * @return the dirPath
     */
    public String getDirPath() {
        return dirPath;
    }

    /**
     * @param dirPath the dirPath to set
     */
    public void setDirPath(final String dirPath) {
        this.dirPath = dirPath;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(final String payStatus) {
        this.payStatus = payStatus;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(final Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(final String macId) {
        this.macId = macId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getServiceShortCode() {
        return serviceShortCode;
    }

    public void setServiceShortCode(final String serviceShortCode) {
        this.serviceShortCode = serviceShortCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("RequestDTO [fName=");
        builder.append(fName);
        builder.append(", mName=");
        builder.append(mName);
        builder.append(", lName=");
        builder.append(lName);
        builder.append(", mobileNo=");
        builder.append(mobileNo);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", email=");
        builder.append(email);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", deptId=");
        builder.append(deptId);
        builder.append(", empId=");
        builder.append(empId);
        builder.append(", applicationId=");
        builder.append(applicationId);
        builder.append(", challanNo=");
        builder.append(challanNo);
        builder.append(", licenseNo=");
        builder.append(licenseNo);
        builder.append(", tenant=");
        builder.append(tenant);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

    /**
     * @return the titleId
     */
    public Long getTitleId() {
        return titleId;
    }

    /**
     * @param titleId the titleId to set
     */
    public void setTitleId(final Long titleId) {
        this.titleId = titleId;
    }

    /**
     * @return the blockNo
     */
    public String getBlockNo() {
        return blockNo;
    }

    /**
     * @param blockNo the blockNo to set
     */
    public void setBlockNo(final String blockNo) {
        this.blockNo = blockNo;
    }

    /**
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(final String floor) {
        this.floor = floor;
    }

    /**
     * @return the wing
     */
    public String getWing() {
        return wing;
    }

    /**
     * @param wing the wing to set
     */
    public void setWing(final String wing) {
        this.wing = wing;
    }

    /**
     * @return the bldgName
     */
    public String getBldgName() {
        return bldgName;
    }

    /**
     * @param bldgName the bldgName to set
     */
    public void setBldgName(final String bldgName) {
        this.bldgName = bldgName;
    }

    /**
     * @return the houseComplexName
     */
    public String getHouseComplexName() {
        return houseComplexName;
    }

    /**
     * @param houseComplexName the houseComplexName to set
     */
    public void setHouseComplexName(final String houseComplexName) {
        this.houseComplexName = houseComplexName;
    }

    /**
     * @return the roadName
     */
    public String getRoadName() {
        return roadName;
    }

    /**
     * @param roadName the roadName to set
     */
    public void setRoadName(final String roadName) {
        this.roadName = roadName;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the pincodeNo
     */
    public Long getPincodeNo() {
        return pincodeNo;
    }

    /**
     * @param pincodeNo the pincodeNo to set
     */
    public void setPincodeNo(final Long pincodeNo) {
        this.pincodeNo = pincodeNo;
    }

    /**
     * @return the applicationType
     */
    public Long getApplicationType() {
        return applicationType;
    }

    /**
     * @param applicationType the applicationType to set
     */
    public void setApplicationType(final Long applicationType) {
        this.applicationType = applicationType;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(final String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(final String phone2) {
        this.phone2 = phone2;
    }

    public Long getWardNo() {
        return wardNo;
    }

    public void setWardNo(final Long wardNo) {
        this.wardNo = wardNo;
    }

    public String getBplNo() {
        return bplNo;
    }

    public void setBplNo(final String bplNo) {
        this.bplNo = bplNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(final String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public Long getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(final Long zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(final String blockName) {
        this.blockName = blockName;
    }

    public String getFlatBuildingNo() {
        return flatBuildingNo;
    }

    public void setFlatBuildingNo(final String flatBuildingNo) {
        this.flatBuildingNo = flatBuildingNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(final Long uid) {
        this.uid = uid;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getIsBPL() {
        return isBPL;
    }

    public void setIsBPL(String isBPL) {
        this.isBPL = isBPL;
    }

    public Long getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(Long yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public String getBplIssuingAuthority() {
        return bplIssuingAuthority;
    }

    public void setBplIssuingAuthority(String bplIssuingAuthority) {
        this.bplIssuingAuthority = bplIssuingAuthority;
    }

    public String getApmOrgnName() {
        return apmOrgnName;
    }

    public void setApmOrgnName(String apmOrgnName) {
        this.apmOrgnName = apmOrgnName;
    }

    public String getApmMode() {
        return apmMode;
    }

    public void setApmMode(String apmMode) {
        this.apmMode = apmMode;
    }

    public String getCcnNumber() {
        return ccnNumber;
    }

    public void setCcnNumber(String ccnNumber) {
        this.ccnNumber = ccnNumber;
    }

    public Long getBinder() {
        return binder;
    }

    public void setBinder(Long binder) {
        this.binder = binder;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Long getMeterSize() {
        return meterSize;
    }

    public void setMeterSize(Long meterSize) {
        this.meterSize = meterSize;
    }

    public Long getCcnSize() {
        return ccnSize;
    }

    public void setCcnSize(Long ccnSize) {
        this.ccnSize = ccnSize;
    }

    public Long getOwnership() {
        return ownership;
    }

    public void setOwnership(Long ownership) {
        this.ownership = ownership;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getOtpPass() {
        return otpPass;
    }

    public void setOtpPass(String otpPass) {
        this.otpPass = otpPass;
    }

	public String getPincodeNum() {
		return pincodeNum;
	}

	public void setPincodeNum(String pincodeNum) {
		this.pincodeNum = pincodeNum;
	}

	public String getServiceShortCodeMar() {
		return serviceShortCodeMar;
	}

	public void setServiceShortCodeMar(String serviceShortCodeMar) {
		this.serviceShortCodeMar = serviceShortCodeMar;
	}

	public String getDepartmentNameMar() {
		return departmentNameMar;
	}

	public void setDepartmentNameMar(String departmentNameMar) {
		this.departmentNameMar = departmentNameMar;
	}

}
