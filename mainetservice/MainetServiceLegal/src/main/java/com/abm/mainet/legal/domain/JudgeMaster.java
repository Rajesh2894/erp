package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_LGL_JUDGE_MAST")
public class JudgeMaster implements Serializable {

    private static final long serialVersionUID = 6457392985485916553L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "JUDGE_ID", nullable = false)
    private Long id;

    @Column(name = "JUDGE_FNAME", nullable = false)
    private String judgeFName;

    @Column(name = "MIDDLE_NAME")
    private String judgeMName;

    @Column(name = "JUDGE_LNAME", nullable = false)
    private String judgeLName;

    @Column(name = "BENCH_NAME")
    private String judgeBenchName;

    @Column(name = "JUDGE_GENDER")
    private Long judgeGender;

    @Column(name = "JUDGE_DOB", nullable = false)
    private Date judgeDob;

    @Column(name = "JUDGE_CONTACT_NO", nullable = false)
    private String judgeContactNo;

    @Column(name = "JUDGE_EMAIL_ID")
    private String judgeEmail;

    @Column(name = "JUDGE_PANNO")
    private String judgePanNo;

    @Column(name = "JUDGE_ADHARNO")
    private String judgeAdharNo;

    @Column(name = "JUDGE_ADDRESS")
    private String judgeAddress;

    @Column(name = "CONTACT_PERSON")
    private String contactPersonName;

/*    @Column(name = "CONTACT_PHONE_NO")
    private String contactPersonPhoneNo;*/
    
    @Column(name = "CONTACT_PHONE_NO")
    private Long contactPersonPhoneNo;

    @Column(name = "CONTACT_EMAIL_ID")
    private String contactPersonEmail;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "judge", cascade = CascadeType.ALL)
    private List<JudgeDetailMaster> judgeDetails = new ArrayList<>();

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

  /*  public String getContactPersonPhoneNo() {
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

    public List<JudgeDetailMaster> getJudgeDetails() {
        return judgeDetails;
    }

    public void setJudgeDetails(List<JudgeDetailMaster> judgeDetails) {
        this.judgeDetails = judgeDetails;
    }
    

    public Long getContactPersonPhoneNo() {
		return contactPersonPhoneNo;
	}

	public void setContactPersonPhoneNo(Long contactPersonPhoneNo) {
		this.contactPersonPhoneNo = contactPersonPhoneNo;
	}

	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_JUDGE_MAST", "JUDGE_ID" };
    }
}
