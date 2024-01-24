package com.abm.mainet.workManagement.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_wms_projectBudet_Det")
public class ProjectBudgetDetEntity {
	
	private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PB_ID", nullable = false)
    private Long pbId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJ_ID", nullable = false)
    private TbWmsProjectMaster projectMasEntity;

    @Column(name = "SAC_HEAD_ID", nullable = true)
    private Long sacHeadId;

    @Column(name = "FA_YEARID", nullable = false)
    private Long faYearId;

    @Column(name = "YE_BUGEDED_AMOUNT", precision = 15, scale = 2, nullable = false)
    private BigDecimal yeBugAmount;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "YE_FINANCE_CODE_DESC", length = 50)
    private String financeCodeDesc;
    
    @Column(name = "YE_ACTIVE", length = 1, nullable = false)
    private String yeActive;
    
    @Column(name = "REMARK", nullable = true)
    private String remark;
    
    @Column(name = "FIELD_ID")
    private Long fieldId;

	public TbWmsProjectMaster getProjectMasEntity() {
		return projectMasEntity;
	}

	public void setProjectMasEntity(TbWmsProjectMaster projectMasEntity) {
		this.projectMasEntity = projectMasEntity;
	}

	public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

    public BigDecimal getYeBugAmount() {
        return yeBugAmount;
    }

    public void setYeBugAmount(BigDecimal yeBugAmount) {
        this.yeBugAmount = yeBugAmount;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getFinanceCodeDesc() {
        return financeCodeDesc;
    }

    public void setFinanceCodeDesc(String financeCodeDesc) {
        this.financeCodeDesc = financeCodeDesc;
    }

    public Long getPbId() {
		return pbId;
	}

	public void setPbId(Long pbId) {
		this.pbId = pbId;
	}

	public String getYeActive() {
		return yeActive;
	}

	public void setYeActive(String yeActive) {
		this.yeActive = yeActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String[] getPkValues() {
        return new String[] { "WMS", "tb_wms_projectBudet_Det", "PB_ID" };
    }

}
