package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_STKHLDR_MST")
public class TbStkhldrMasEntity implements Serializable {

	private static final long serialVersionUID = 6823600750625011224L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID", nullable = false)
    private Long stakeholderId;
	
	@ManyToOne
	@JoinColumn(name = "TCP_DEV_REG_NO")
	private TbDeveloperRegistrationEntity developerRegMas;

    @Column(name = "NAME",  nullable = true)
    private String stakeholderName;

    @Column(name = "DESIGNATION", nullable = true)
    private String stakeholderDesignation;

    @Column(name = "PERCENTAGE",  nullable = true)
    private Long stakeholderPercentage;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;
    
    @Column(name = "CREATED_DATE ", nullable = true)
    private Date createdDate;

    @Column(name = "MODIFIED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "MODIFIED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "IS_DELETED", nullable = true)
    private String is_deleted;


	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String[] getPkValues() {
        return new String[] { "DRN", "TB_BPMS_STKHLDR_MST", "ID" };
    }


	public String getStakeholderName() {
		return stakeholderName;
	}

	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}

	public String getStakeholderDesignation() {
		return stakeholderDesignation;
	}

	public void setStakeholderDesignation(String stakeholderDesignation) {
		this.stakeholderDesignation = stakeholderDesignation;
	}

	public Long getStakeholderPercentage() {
		return stakeholderPercentage;
	}

	public void setStakeholderPercentage(Long stakeholderPercentage) {
		this.stakeholderPercentage = stakeholderPercentage;
	}

	public TbDeveloperRegistrationEntity getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(TbDeveloperRegistrationEntity developerRegMas) {
		this.developerRegMas = developerRegMas;
	}

	public Long getStakeholderId() {
		return stakeholderId;
	}

	public void setStakeholderId(Long stakeholderId) {
		this.stakeholderId = stakeholderId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

   }