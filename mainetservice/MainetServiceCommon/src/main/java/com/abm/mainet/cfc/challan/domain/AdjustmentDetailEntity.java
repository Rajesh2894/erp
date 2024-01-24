package com.abm.mainet.cfc.challan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ADJUSTMENT_DET")
public class AdjustmentDetailEntity implements Serializable {

    private static final long serialVersionUID = 4143621660949211377L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "adjd_id", precision = 12, scale = 0, nullable = false)
    private long adjDetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adj_id", nullable = false)
    private AdjustmentMasterEntity adjId;

    @Column(name = "tax_id", nullable = false, updatable = false)
    private Long taxId;

    @Column(name = "adj_amount", precision = 15, scale = 2, nullable = true)
    private double adjAmount;

    @Column(name = "adj_adjusted_amount", precision = 15, scale = 2, nullable = true)
    private double adjAdjustedAmount;

    @Column(name = "adj_balance_amount", precision = 15, scale = 2, nullable = true)
    private double adjBalanceAmount;

    @Column(name = "adj_remark", nullable = true)
    private String adjRemark;

    @Column(name = "adj_adjusted_flag", nullable = true)
    private String adjAdjustedFlag;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;
    
    @Column(name = "BD_BILLDETID")
    private Long bdBilldetid;
    
    @Column(name = "BM_IDNO")
    private Long bmIdNo;

    public String[] getPkValues() {
        return new String[] { "COM", "TB_ADJUSTMENT_DET", "ADJD_ID" };
    }

    public long getAdjDetId() {
        return adjDetId;
    }

    public void setAdjDetId(final long adjDetId) {
        this.adjDetId = adjDetId;
    }

    public AdjustmentMasterEntity getAdjId() {
        return adjId;
    }

    public void setAdjId(final AdjustmentMasterEntity adjId) {
        this.adjId = adjId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public double getAdjAmount() {
        return adjAmount;
    }

    public void setAdjAmount(final double adjAmount) {
        this.adjAmount = adjAmount;
    }

    public double getAdjAdjustedAmount() {
        return adjAdjustedAmount;
    }

    public void setAdjAdjustedAmount(final double adjAdjustedAmount) {
        this.adjAdjustedAmount = adjAdjustedAmount;
    }

    public double getAdjBalanceAmount() {
        return adjBalanceAmount;
    }

    public void setAdjBalanceAmount(final double adjBalanceAmount) {
        this.adjBalanceAmount = adjBalanceAmount;
    }

    public String getAdjAdjustedFlag() {
        return adjAdjustedFlag;
    }

    public void setAdjAdjustedFlag(final String adjAdjustedFlag) {
        this.adjAdjustedFlag = adjAdjustedFlag;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(adjAdjustedAmount);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        result = (prime * result) + ((adjAdjustedFlag == null) ? 0 : adjAdjustedFlag.hashCode());
        temp = Double.doubleToLongBits(adjAmount);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(adjBalanceAmount);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        result = (prime * result) + (int) (adjDetId ^ (adjDetId >>> 32));
        result = (prime * result) + ((adjId == null) ? 0 : adjId.hashCode());
        result = (prime * result) + ((adjRemark == null) ? 0 : adjRemark.hashCode());
        result = (prime * result) + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = (prime * result) + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = (prime * result) + ((lgIpMac == null) ? 0 : lgIpMac.hashCode());
        result = (prime * result) + ((lgIpMacUpd == null) ? 0 : lgIpMacUpd.hashCode());
        result = (prime * result) + ((orgId == null) ? 0 : orgId.hashCode());
        result = (prime * result) + ((taxId == null) ? 0 : taxId.hashCode());
        result = (prime * result) + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = (prime * result) + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdjustmentDetailEntity other = (AdjustmentDetailEntity) obj;
        if (Double.doubleToLongBits(adjAdjustedAmount) != Double.doubleToLongBits(other.adjAdjustedAmount)) {
            return false;
        }
        if (adjAdjustedFlag == null) {
            if (other.adjAdjustedFlag != null) {
                return false;
            }
        } else if (!adjAdjustedFlag.equals(other.adjAdjustedFlag)) {
            return false;
        }
        if (Double.doubleToLongBits(adjAmount) != Double.doubleToLongBits(other.adjAmount)) {
            return false;
        }
        if (Double.doubleToLongBits(adjBalanceAmount) != Double.doubleToLongBits(other.adjBalanceAmount)) {
            return false;
        }
        if (adjDetId != other.adjDetId) {
            return false;
        }
        if (adjId == null) {
            if (other.adjId != null) {
                return false;
            }
        } else if (!adjId.equals(other.adjId)) {
            return false;
        }
        if (adjRemark == null) {
            if (other.adjRemark != null) {
                return false;
            }
        } else if (!adjRemark.equals(other.adjRemark)) {
            return false;
        }
        if (createdBy == null) {
            if (other.createdBy != null) {
                return false;
            }
        } else if (!createdBy.equals(other.createdBy)) {
            return false;
        }
        if (createdDate == null) {
            if (other.createdDate != null) {
                return false;
            }
        } else if (!createdDate.equals(other.createdDate)) {
            return false;
        }
        if (lgIpMac == null) {
            if (other.lgIpMac != null) {
                return false;
            }
        } else if (!lgIpMac.equals(other.lgIpMac)) {
            return false;
        }
        if (lgIpMacUpd == null) {
            if (other.lgIpMacUpd != null) {
                return false;
            }
        } else if (!lgIpMacUpd.equals(other.lgIpMacUpd)) {
            return false;
        }
        if (orgId == null) {
            if (other.orgId != null) {
                return false;
            }
        } else if (!orgId.equals(other.orgId)) {
            return false;
        }
        if (taxId == null) {
            if (other.taxId != null) {
                return false;
            }
        } else if (!taxId.equals(other.taxId)) {
            return false;
        }
        if (updatedBy == null) {
            if (other.updatedBy != null) {
                return false;
            }
        } else if (!updatedBy.equals(other.updatedBy)) {
            return false;
        }
        if (updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        } else if (!updatedDate.equals(other.updatedDate)) {
            return false;
        }
        return true;
    }

    public String getAdjRemark() {
        return adjRemark;
    }

    public void setAdjRemark(final String adjRemark) {
        this.adjRemark = adjRemark;
    }

	public Long getBdBilldetid() {
		return bdBilldetid;
	}

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBdBilldetid(Long bdBilldetid) {
		this.bdBilldetid = bdBilldetid;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}
    
}
