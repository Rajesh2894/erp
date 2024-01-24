package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JudgeMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String judgeFName;
    private String judgeMName;
    private String judgeBenchName;
    private String judgeLName;
    private String contactPersonName;
   /* private String contactPersonPhoneNo;*/
    private String contactPersonEmail;
    private Long judgeGender;
    private Date judgeDob;
    private String judgeContactNo;
    private String judgeEmail;
    private String judgePanNo;
    private String judgeAdharNo;
    private String judgeAddress;
    private Long orgId;
    private Long createdBy;
    private Date createDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String fulName;
    private List<JudgeDetailMasterDTO> judgeDetails = new ArrayList<>();
    private Long contactPersonPhoneNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudgeFName() {
        return judgeFName;
    }

    public void setJudgeFName(String judgeFName) {
        this.judgeFName = judgeFName;
    }

    public String getJudgeLName() {
        return judgeLName;
    }

    public void setJudgeLName(String judgeLName) {
        this.judgeLName = judgeLName;
    }

    public Long getJudgeGender() {
        return judgeGender;
    }

    public void setJudgeGender(Long judgeGender) {
        this.judgeGender = judgeGender;
    }

    public Date getJudgeDob() {
        return judgeDob;
    }

    public void setJudgeDob(Date judgeDob) {
        this.judgeDob = judgeDob;
    }

    public String getJudgeContactNo() {
        return judgeContactNo;
    }

    public void setJudgeContactNo(String judgeContactNo) {
        this.judgeContactNo = judgeContactNo;
    }

    public String getJudgeEmail() {
        return judgeEmail;
    }

    public void setJudgeEmail(String judgeEmail) {
        this.judgeEmail = judgeEmail;
    }

    public String getJudgePanNo() {
        return judgePanNo;
    }

    public void setJudgePanNo(String judgePanNo) {
        this.judgePanNo = judgePanNo;
    }

    public String getJudgeAdharNo() {
        return judgeAdharNo;
    }

    public void setJudgeAdharNo(String judgeAdharNo) {
        this.judgeAdharNo = judgeAdharNo;
    }

    public String getJudgeAddress() {
        return judgeAddress;
    }

    public void setJudgeAddress(String judgeAddress) {
        this.judgeAddress = judgeAddress;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public List<JudgeDetailMasterDTO> getJudgeDetails() {
        return judgeDetails;
    }

    public void setJudgeDetails(List<JudgeDetailMasterDTO> judgeDetails) {
        this.judgeDetails = judgeDetails;
    }
    public String getJudgeMName() {
		return judgeMName;
	}

	public void setJudgeMName(String judgeMName) {
		this.judgeMName = judgeMName;
	}

	public String getJudgeBenchName() {
		return judgeBenchName;
	}

	public void setJudgeBenchName(String judgeBenchName) {
		this.judgeBenchName = judgeBenchName;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

/*	public String getContactPersonPhoneNo() {
		return contactPersonPhoneNo;
	}

	public void setContactPersonPhoneNo(String contactPersonPhoneNo) {
		this.contactPersonPhoneNo = contactPersonPhoneNo;
	}*/

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	public String getFulName() {
	    return fulName;
	}

	public void setFulName(String fulName) {
	    this.fulName = fulName;
	}

	public Long getContactPersonPhoneNo() {
		return contactPersonPhoneNo;
	}

	public void setContactPersonPhoneNo(Long contactPersonPhoneNo) {
		this.contactPersonPhoneNo = contactPersonPhoneNo;
	}

	
	
}
