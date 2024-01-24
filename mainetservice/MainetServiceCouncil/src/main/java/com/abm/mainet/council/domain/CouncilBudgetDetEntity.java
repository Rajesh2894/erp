package com.abm.mainet.council.domain;

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
@Table(name = "tb_cmt_councilBudet_Det")
public class CouncilBudgetDetEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CB_ID", nullable = false)
    private Long cbId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROPOSAL_ID", nullable = false)
    private CouncilProposalMasterEntity councilMasEntity;

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
    
    @Column(name = "FILED_ID", nullable = false)
    private Long filedId;
    
    @Column(name = "PROPOSAL_DEP_ID", nullable = false)
    private Long proposalDepId;

	public CouncilProposalMasterEntity getCouncilMasEntity() {
		return councilMasEntity;
	}

	public void setCouncilMasEntity(CouncilProposalMasterEntity councilMasEntity) {
		this.councilMasEntity = councilMasEntity;
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

	public String getYeActive() {
		return yeActive;
	}

	public void setYeActive(String yeActive) {
		this.yeActive = yeActive;
	}

	public Long getCbId() {
		return cbId;
	}

	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	public String[] getPkValues() {
        return new String[] { "CMT", "tb_cmt_councilBudet_Det", "CB_ID" };
    }
	
	public Long getFiledId() {
		return filedId;
	}

	public void setFiledId(Long filedId) {
		this.filedId = filedId;
	}
	
	public Long getProposalDepId() {
		return proposalDepId;
	}

	public void setProposalDepId(Long proposalDepId) {
		this.proposalDepId = proposalDepId;
	}

}
