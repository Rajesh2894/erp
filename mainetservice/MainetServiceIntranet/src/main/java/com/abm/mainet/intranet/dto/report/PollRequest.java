package com.abm.mainet.intranet.dto.report;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

public class PollRequest {
    @NotBlank
    @Size(max = 140)
    private String question;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<ChoiceRequest> choices;

    @NotNull
    @Valid
    private PollLength pollLength;
    
    private String lgIpMac;
	private String lgIpMacUpd;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private int langId;
	private Date lmodDate;
	private Long orgId;
	
	private Long deptId;
	private String pollName;
	private Date fromDate;
	private Date toDate;
	private String pollStatus;
	
	private Long pollid;
	private String mode;
	
	private Long loggedInEmp;
	private Long choiceid;
	private String pollQue;
	private String choiceDescVal;
	private String choiceDesc;
	
	private Long checkLoggedEmp;
	
	public Long getCheckLoggedEmp() {
		return checkLoggedEmp;
	}

	public void setCheckLoggedEmp(Long checkLoggedEmp) {
		this.checkLoggedEmp = checkLoggedEmp;
	}

	public String getChoiceDesc() {
		return choiceDesc;
	}

	public void setChoiceDesc(String choiceDesc) {
		this.choiceDesc = choiceDesc;
	}

	public String getChoiceDescVal() {
		return choiceDescVal;
	}

	public void setChoiceDescVal(String choiceDescVal) {
		this.choiceDescVal = choiceDescVal;
	}

	public String getPollQue() {
		return pollQue;
	}

	public void setPollQue(String pollQue) {
		this.pollQue = pollQue;
	}

	public Long getLoggedInEmp() {
		return loggedInEmp;
	}

	public void setLoggedInEmp(Long loggedInEmp) {
		this.loggedInEmp = loggedInEmp;
	}

	public Long getChoiceid() {
		return choiceid;
	}

	public void setChoiceid(Long choiceid) {
		this.choiceid = choiceid;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
    public Long getPollid() {
		return pollid;
	}

	public void setPollid(Long pollid) {
		this.pollid = pollid;
	}

	public String getPollStatus() {
		return pollStatus;
	}

	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getPollName() {
		return pollName;
	}

	public void setPollName(String pollName) {
		this.pollName = pollName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceRequest> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceRequest> choices) {
        this.choices = choices;
    }

    public PollLength getPollLength() {
        return pollLength;
    }

    public void setPollLength(PollLength pollLength) {
        this.pollLength = pollLength;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
    
    
}
