package com.abm.mainet.cms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_poll_response")
public class OpinionPollOptionResponse extends BaseEntity {

    private static final long serialVersionUID = 7992538874136137893L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RESPONSE_ID", precision = 12, scale = 0, nullable = false)
    private long responseId;
    
    @Column(name = "POLL_ID", precision = 12, scale = 0, nullable = false)
    private long pnId;
    
    @Column(name = "POLL_OPTION_ID", precision = 12, scale = 0, nullable = false)
    private long pOptionId;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = false, updatable = false)
    private Organisation orgId;
    
    @Column(name = "CITIZEN_NAME", length = 250, nullable = true)
    private String citizenName;

    @Column(name = "CITIZEN_EMAIL", length = 500, nullable = true)
    private String citizenEmail;

    @Column(name = "CITIZEN_MOBILE", length = 50, nullable = true)
    private String citizenMobile;
    
    @Column(name = "RESPONSED_DATE", nullable = true)
    private Date answeredDate;

    @Column(name = "IS_DELETED", length = 1, nullable = false)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Transient
    private String newDate;
   
    @Transient
    OpinionDTO opinion = null;
    
    @Transient
    List<OpinionPollDTO> opinionPollList=null;
    
    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(final String newDate) {
        this.newDate = newDate;
    }

    /**
     * @return the pnId
     */
    public long getPnId() {
        return pnId;
    }

    /**
     * @param pnId the pnId to set
     */
    public void setPnId(final long pnId) {
        this.pnId = pnId;
    }

   

    public Date getAnsweredDate() {
		return answeredDate;
	}

	public void setAnsweredDate(Date answeredDate) {
		this.answeredDate = answeredDate;
	}

	/**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {

        return getPnId();
    }

    @Override
    public String[] getPkValues() {
    	return new String[] { "ONL", "tb_poll_response", "RESPONSE_ID" };
    }

    @Transient
    private String chekkerflag1;

    public String getChekkerflag1() {
        return chekkerflag1;
    }

    public void setChekkerflag1(String chekkerflag1) {
        this.chekkerflag1 = chekkerflag1;
    }


    @Override
    public int getLangId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLangId(int langId) {
        // TODO Auto-generated method stub

    }

	public long getpOptionId() {
		return pOptionId;
	}

	public void setpOptionId(long pOptionId) {
		this.pOptionId = pOptionId;
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	/**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

	public String getCitizenName() {
		return citizenName;
	}

	public void setCitizenName(String citizenName) {
		this.citizenName = citizenName;
	}

	public String getCitizenEmail() {
		return citizenEmail;
	}

	public void setCitizenEmail(String citizenEmail) {
		this.citizenEmail = citizenEmail;
	}

	public String getCitizenMobile() {
		return citizenMobile;
	}

	public void setCitizenMobile(String citizenMobile) {
		this.citizenMobile = citizenMobile;
	}

	public OpinionDTO getOpinion() {
		return opinion;
	}

	public void setOpinion(OpinionDTO opinion) {
		this.opinion = opinion;
	}

	public List<OpinionPollDTO> getOpinionPollList() {
		return opinionPollList;
	}

	public void setOpinionPollList(List<OpinionPollDTO> opinionPollList) {
		this.opinionPollList = opinionPollList;
	}
	
	
}