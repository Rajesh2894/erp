package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the tb_lgl_hearing database table.
 * 
 * @author Lalit.Prusti
 */
public class CaseHearingDTO implements Serializable {

    private static final long serialVersionUID = 8171472883498420813L;

    private Long hrId;

    private Long advId;

    private String judgeId;

    private Long cseId;

    private String hrAttendee;

    private Date hrDate;

    private String hrPreparation;

    private String hrProceeding;

    private Long hrRating;

    private Long orgid;

    private Long hrStatus;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private String file;
    
    private String judgeName;
    
    private Long cseDeptid;


	public Long getHrId() {
        return this.hrId;
    }

    public void setHrId(Long hrId) {
        this.hrId = hrId;
    }

    public Long getAdvId() {
        return this.advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCseId() {
        return this.cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getHrAttendee() {
        return this.hrAttendee;
    }

    public void setHrAttendee(String hrAttendee) {
        this.hrAttendee = hrAttendee;
    }

    public Date getHrDate() {
        return this.hrDate;
    }

    public void setHrDate(Date hrDate) {
        this.hrDate = hrDate;
    }

    public String getHrPreparation() {
        return this.hrPreparation;
    }

    public void setHrPreparation(String hrPreparation) {
        this.hrPreparation = hrPreparation;
    }

    public String getHrProceeding() {
        return this.hrProceeding;
    }

    public void setHrProceeding(String hrProceeding) {
        this.hrProceeding = hrProceeding;
    }

    public Long getHrRating() {
        return this.hrRating;
    }

    public void setHrRating(Long hrRating) {
        this.hrRating = hrRating;
    }

    public Long getHrStatus() {
        return this.hrStatus;
    }

    public void setHrStatus(Long hrStatus) {
        this.hrStatus = hrStatus;
    }

    public String getJudgeId() {
        return this.judgeId;
    }

    public void setJudgeId(String judgeId) {
        this.judgeId = judgeId;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    

    public String getJudgeName() {
		return judgeName;
	}

	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
	}

	public Long getCseDeptid() {
		return cseDeptid;
	}

	public void setCseDeptid(Long cseDeptid) {
		this.cseDeptid = cseDeptid;
	}

}