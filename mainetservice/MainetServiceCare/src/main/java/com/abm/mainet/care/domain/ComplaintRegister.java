package com.abm.mainet.care.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMPLAINTREGISTER")
public class ComplaintRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "COMPLAINT_NO")
    private String complaintId;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "CARE_REQ_ID")
    private Long complaintReqId;

    @Column(name = "DIVISION")
    private Long divisionId;

    @Column(name = "DIVISION_ENG")
    private String divisionNameEng;

    @Column(name = "DIVISION_REG")
    private String divisionNameReg;

    @Column(name = "ORG_CPD_ID_DIS")
    private Long districtId;

    @Column(name = "DISTRICT_ENG")
    private String districtNameEng;

    @Column(name = "DISTRICT_REG")
    private String districtNameReg;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "ULB_NAME_ENG")
    private String orgNameEng;

    @Column(name = "ULB_NAME_REG")
    private String orgNameReg;

    @Column(name = "DEPT_COMP_ID")
    private Long compId;

    @Column(name = "COMPLAINT_SUB_TYPE")
    private String compNameEng;

    @Column(name = "COMPLAINT_SUB_TYPE_REG")
    private String compNameReg;

    @Column(name = "COMPLAINTDESC")
    private String complaintDesc;

    @Column(name = "DEPARTMENT_ID")
    private Long deptId;

    @Column(name = "DEPARTMENT_ENG")
    private String deptNameEng;

    @Column(name = "DEPARTMENT_REG")
    private String deptNameReg;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "LOCATIONNAME_ENG")
    private String locNameEng;

    @Column(name = "LOCATIONNAME_REG")
    private String locNameReg;

    @Column(name = "PINCODE")
    private String pincode;

    @Column(name = "SM_SERVICE_ID")
    private Long serviceId;

    @Column(name = "SERVICE_TYPE")
    private String serviceNameEng;

    @Column(name = "CARE_MODE")
    private String modeType;

    @Column(name = "CARE_MODE_REG")
    private String modeTypeReg;

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    /*
     * @Column(name = "COD_ID_OPER_LEVEL1") private Long codIdOperLevel1;
     * @Column(name = "COD_ID_OPER_LEVEL2") private Long codIdOperLevel2;
     * @Column(name = "COD_ID_OPER_LEVEL3") private Long codIdOperLevel3;
     * @Column(name = "COD_ID_OPER_LEVEL4") private Long codIdOperLevel4;
     * @Column(name = "COD_ID_OPER_LEVEL5") private Long codIdOperLevel5;
     */
    @Column(name = "DATEOFREQUEST")
    private Date dateOfRequest;

    @Column(name = "LASTDAYACTION")
    private Date lastDateOfAction;

    @Column(name = "LAST_DECISION")
    private String lastDecision;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "EMPL_TYPE")
    private Long empType;

    @Column(name = "CREATED_DATE")
    private Date createdDate = new Date();

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    @Column(name = "APPLICANT_NAME")
    private String apmName;

    @Column(name = "APA_MOBILNO")
    private String apaMobilno;

    @Column(name = "APA_EMAIL")
    private String apaEmail;

    @Column(name = "SLA")
    private String slaStatus;

    @Column(name = "APPLICATION_SLA_DURATION")
    private Long applicationSlaDurationInMS;

    /* new zone_ward levels 2 */
    @Column(name = "care_ward_no")
    private Long careWardNo;

    @Column(name = "CARE_WARD_NO_ENG")
    private String careWardNoEng;

    @Column(name = "CARE_WARD_NO_REG")
    private String careWardNoReg;

    @Column(name = "care_ward_no1")
    private Long careWardNo1;

    @Column(name = "CARE_WARD_NO1_ENG")
    private String careWardNoEng1;

    @Column(name = "CARE_WARD_NO1_REG")
    private String careWardNoReg1;
    
    @Column(name = "care_ward_no2")
    private Long careWardNo2;

    @Column(name = "CARE_WARD_NO2_ENG")
    private String careWardNoEng2;

    @Column(name = "CARE_WARD_NO2_REG")
    private String careWardNoReg2;
    
    @Column(name = "care_ward_no3")
    private Long careWardNo3;

    @Column(name = "CARE_WARD_NO3_ENG")
    private String careWardNoEng3;

    @Column(name = "CARE_WARD_NO3_REG")
    private String careWardNoReg3;
    
    @Column(name = "care_ward_no4")
    private Long careWardNo4;

    @Column(name = "CARE_WARD_NO4_ENG")
    private String careWardNoEng4;

    @Column(name = "CARE_WARD_NO4_REG")
    private String careWardNoReg4;

    @Column(name = "NOOFDAY")
    private Long numberOfDay;

    @Column(name = "STATE_ENG")
    private String stateNameEng;

    @Column(name = "CSM_SERVICE_ID")
    private String csmServiceId;

    @Column(name = "CARE_MODE_CODE")
    private String modeTypeCode;// cpd_value of RFM prefix
    
    @Column(name = "APA_AREANM")
    private String apaAreaName;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getComplaintReqId() {
        return complaintReqId;
    }

    public void setComplaintReqId(Long complaintReqId) {
        this.complaintReqId = complaintReqId;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionNameEng() {
        return divisionNameEng;
    }

    public void setDivisionNameEng(String divisionNameEng) {
        this.divisionNameEng = divisionNameEng;
    }

    public String getDivisionNameReg() {
        return divisionNameReg;
    }

    public void setDivisionNameReg(String divisionNameReg) {
        this.divisionNameReg = divisionNameReg;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictNameEng() {
        return districtNameEng;
    }

    public void setDistrictNameEng(String districtNameEng) {
        this.districtNameEng = districtNameEng;
    }

    public String getDistrictNameReg() {
        return districtNameReg;
    }

    public void setDistrictNameReg(String districtNameReg) {
        this.districtNameReg = districtNameReg;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgNameEng() {
        return orgNameEng;
    }

    public void setOrgNameEng(String orgNameEng) {
        this.orgNameEng = orgNameEng;
    }

    public String getOrgNameReg() {
        return orgNameReg;
    }

    public void setOrgNameReg(String orgNameReg) {
        this.orgNameReg = orgNameReg;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getCompNameEng() {
        return compNameEng;
    }

    public void setCompNameEng(String compNameEng) {
        this.compNameEng = compNameEng;
    }

    public String getCompNameReg() {
        return compNameReg;
    }

    public void setCompNameReg(String compNameReg) {
        this.compNameReg = compNameReg;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptNameEng() {
        return deptNameEng;
    }

    public void setDeptNameEng(String deptNameEng) {
        this.deptNameEng = deptNameEng;
    }

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocNameEng() {
        return locNameEng;
    }

    public void setLocNameEng(String locNameEng) {
        this.locNameEng = locNameEng;
    }

    public String getLocNameReg() {
        return locNameReg;
    }

    public void setLocNameReg(String locNameReg) {
        this.locNameReg = locNameReg;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceNameEng() {
        return serviceNameEng;
    }

    public void setServiceNameEng(String serviceNameEng) {
        this.serviceNameEng = serviceNameEng;
    }

    /*
     * public Long getCodIdOperLevel1() { return codIdOperLevel1; } public void setCodIdOperLevel1(Long codIdOperLevel1) {
     * this.codIdOperLevel1 = codIdOperLevel1; } public Long getCodIdOperLevel2() { return codIdOperLevel2; } public void
     * setCodIdOperLevel2(Long codIdOperLevel2) { this.codIdOperLevel2 = codIdOperLevel2; } public Long getCodIdOperLevel3() {
     * return codIdOperLevel3; } public void setCodIdOperLevel3(Long codIdOperLevel3) { this.codIdOperLevel3 = codIdOperLevel3; }
     * public Long getCodIdOperLevel4() { return codIdOperLevel4; } public void setCodIdOperLevel4(Long codIdOperLevel4) {
     * this.codIdOperLevel4 = codIdOperLevel4; } public Long getCodIdOperLevel5() { return codIdOperLevel5; } public void
     * setCodIdOperLevel5(Long codIdOperLevel5) { this.codIdOperLevel5 = codIdOperLevel5; }
     */

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Date getLastDateOfAction() {
        return lastDateOfAction;
    }

    public void setLastDateOfAction(Date lastDateOfAction) {
        this.lastDateOfAction = lastDateOfAction;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApmName() {
        return apmName;
    }

    public void setApmName(String apmName) {
        this.apmName = apmName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(Long empType) {
        this.empType = empType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getApaMobilno() {
        return apaMobilno;
    }

    public void setApaMobilno(String apaMobilno) {
        this.apaMobilno = apaMobilno;
    }

    public String getApaEmail() {
        return apaEmail;
    }

    public void setApaEmail(String apaEmail) {
        this.apaEmail = apaEmail;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public Long getApplicationSlaDurationInMS() {
        return applicationSlaDurationInMS;
    }

    public void setApplicationSlaDurationInMS(Long applicationSlaDurationInMS) {
        this.applicationSlaDurationInMS = applicationSlaDurationInMS;
    }

    public Long getCareWardNo() {
        return careWardNo;
    }

    public String getCareWardNoEng() {
        return careWardNoEng;
    }

    public String getCareWardNoReg() {
        return careWardNoReg;
    }

    public String getCareWardNoEng1() {
        return careWardNoEng1;
    }

    public String getCareWardNoReg1() {
        return careWardNoReg1;
    }

    public void setCareWardNo(Long careWardNo) {
        this.careWardNo = careWardNo;
    }

    public void setCareWardNoEng(String careWardNoEng) {
        this.careWardNoEng = careWardNoEng;
    }

    public void setCareWardNoReg(String careWardNoReg) {
        this.careWardNoReg = careWardNoReg;
    }

    public Long getCareWardNo1() {
        return careWardNo1;
    }

    public void setCareWardNo1(Long careWardNo1) {
        this.careWardNo1 = careWardNo1;
    }

    public void setCareWardNoEng1(String careWardNoEng1) {
        this.careWardNoEng1 = careWardNoEng1;
    }

    public void setCareWardNoReg1(String careWardNoReg1) {
        this.careWardNoReg1 = careWardNoReg1;
    }

    public Long getCareWardNo2() {
		return careWardNo2;
	}

	public void setCareWardNo2(Long careWardNo2) {
		this.careWardNo2 = careWardNo2;
	}

	public String getCareWardNoEng2() {
		return careWardNoEng2;
	}

	public void setCareWardNoEng2(String careWardNoEng2) {
		this.careWardNoEng2 = careWardNoEng2;
	}

	public String getCareWardNoReg2() {
		return careWardNoReg2;
	}

	public void setCareWardNoReg2(String careWardNoReg2) {
		this.careWardNoReg2 = careWardNoReg2;
	}

	public Long getCareWardNo3() {
		return careWardNo3;
	}

	public void setCareWardNo3(Long careWardNo3) {
		this.careWardNo3 = careWardNo3;
	}

	public String getCareWardNoEng3() {
		return careWardNoEng3;
	}

	public void setCareWardNoEng3(String careWardNoEng3) {
		this.careWardNoEng3 = careWardNoEng3;
	}

	public String getCareWardNoReg3() {
		return careWardNoReg3;
	}

	public void setCareWardNoReg3(String careWardNoReg3) {
		this.careWardNoReg3 = careWardNoReg3;
	}

	public Long getCareWardNo4() {
		return careWardNo4;
	}

	public void setCareWardNo4(Long careWardNo4) {
		this.careWardNo4 = careWardNo4;
	}

	public String getCareWardNoEng4() {
		return careWardNoEng4;
	}

	public void setCareWardNoEng4(String careWardNoEng4) {
		this.careWardNoEng4 = careWardNoEng4;
	}

	public String getCareWardNoReg4() {
		return careWardNoReg4;
	}

	public void setCareWardNoReg4(String careWardNoReg4) {
		this.careWardNoReg4 = careWardNoReg4;
	}

	public Long getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(Long numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public String getStateNameEng() {
        return stateNameEng;
    }

    public void setStateNameEng(String stateNameEng) {
        this.stateNameEng = stateNameEng;
    }

    public String getCsmServiceId() {
        return csmServiceId;
    }

    public void setCsmServiceId(String csmServiceId) {
        this.csmServiceId = csmServiceId;
    }

    public String getModeTypeReg() {
        return modeTypeReg;
    }

    public void setModeTypeReg(String modeTypeReg) {
        this.modeTypeReg = modeTypeReg;
    }

    public String getModeTypeCode() {
        return modeTypeCode;
    }

    public void setModeTypeCode(String modeTypeCode) {
        this.modeTypeCode = modeTypeCode;
    }

	public String getApaAreaName() {
		return apaAreaName;
	}

	public void setApaAreaName(String apaAreaName) {
		this.apaAreaName = apaAreaName;
	}

	

}
