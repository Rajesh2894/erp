package com.abm.mainet.cms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_opinion_poll_option_hist")
public class OpinionPollOptionHistory extends BaseEntity {

    private static final long serialVersionUID = 7992538874136137893L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "POLL_OPTION_HIST_ID", precision = 12, scale = 0, nullable = false)
    private long pOptionHistId;

    @Column(name = "POLL_OPTION_ID", precision = 12, scale = 0, nullable = false)
    private long pOptionId;
    
    @Column(name = "POLL_ID", precision = 12, scale = 0, nullable = false)
    private long pnId;

    @Column(name = "OPTION_EN", length = 250, nullable = true)
    private String optionEn;

    @Column(name = "OPTION_REG", length = 500, nullable = true)
    private String optionReg;

    @Column(name = "ANSWERED_DATE", nullable = true)
    private Date answeredDate;

    @Column(name = "IS_SELETED", length = 1, nullable = false)
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

   
    

    public long getpOptionHistId() {
		return pOptionHistId;
	}

	public void setpOptionHistId(long pOptionHistId) {
		this.pOptionHistId = pOptionHistId;
	}

	@Transient
    private String attachmentName;

    @Transient
    private String newDate;

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

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(final String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_OPINION_POLL_OPTION_HIST", "POLL_OPTION_HIST_ID" };
    }

    @Override
    public int getLangId() {
        return 0;
    }

    @Override
    public void setLangId(int langId) {

    }

	public long getpOptionId() {
		return pOptionId;
	}

	public void setpOptionId(long pOptionId) {
		this.pOptionId = pOptionId;
	}

	public String getOptionEn() {
		return optionEn;
	}

	public void setOptionEn(String optionEn) {
		this.optionEn = optionEn;
	}

	public String getOptionReg() {
		return optionReg;
	}

	public void setOptionReg(String optionReg) {
		this.optionReg = optionReg;
	}

	public Date getAnsweredDate() {
		return answeredDate;
	}

	public void setAnsweredDate(Date answeredDate) {
		this.answeredDate = answeredDate;
	}

	@Override
	public Organisation getOrgId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrgId(Organisation orgId) {
		// TODO Auto-generated method stub
		
	}
    
    
    
}