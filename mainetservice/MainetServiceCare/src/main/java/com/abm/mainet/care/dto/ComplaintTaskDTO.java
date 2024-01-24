package com.abm.mainet.care.dto;

import com.abm.mainet.common.workflow.dto.UserTaskDTO;

public class ComplaintTaskDTO extends UserTaskDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private CareRequestDTO careRequest;

    public CareRequestDTO getCareRequest() {
        return careRequest;
    }

    public void setCareRequest(CareRequestDTO careRequest) {
        this.careRequest = careRequest;
    }

    private String pincode;
    private String apmFname;
    private String apmLname;
    private String apaEmail;
    private String apaMobilno;
    private String apmSex;
    private String apmSexDesc;
    private String apaAreanm;
    private long ageOfRequest;
    private String referenceDate;
    
    //#135509,#142867
    private String apmSexDescReg;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getApmFname() {
        return apmFname;
    }

    public void setApmFname(String apmFname) {
        this.apmFname = apmFname;
    }

    public String getApmLname() {
        return apmLname;
    }

    public void setApmLname(String apmLname) {
        this.apmLname = apmLname;
    }

    public String getApaEmail() {
        return apaEmail;
    }

    public void setApaEmail(String apaEmail) {
        this.apaEmail = apaEmail;
    }

    public String getApaMobilno() {
        return apaMobilno;
    }

    public void setApaMobilno(String apaMobilno) {
        this.apaMobilno = apaMobilno;
    }

    public String getApmSex() {
        return apmSex;
    }

    public void setApmSex(String apmSex) {
        this.apmSex = apmSex;
    }

    public String getApmSexDesc() {
        return apmSexDesc;
    }

    public void setApmSexDesc(String apmSexDesc) {
        this.apmSexDesc = apmSexDesc;
    }

    public String getApaAreanm() {
        return apaAreanm;
    }

    public void setApaAreanm(String apaAreanm) {
        this.apaAreanm = apaAreanm;
    }

    public long getAgeOfRequest() {
        return ageOfRequest;
    }

    public void setAgeOfRequest(long ageOfRequest) {
        this.ageOfRequest = ageOfRequest;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

	public String getApmSexDescReg() {
		return apmSexDescReg;
	}

	public void setApmSexDescReg(String apmSexDescReg) {
		this.apmSexDescReg = apmSexDescReg;
	}

}
