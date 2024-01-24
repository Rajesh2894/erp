package com.abm.mainet.tradeLicense.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Jugnu.Pandey
 * @since 07 Dec 2018
 */
@Entity
@Table(name = "TB_ML_ITEM_DETAIL_HIST")
public class TbMlItemDetailHist implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1135577683106353036L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRI_ID_H", unique = true, nullable = false)
    private Long triIdh;
    
    @Column(name = "TRI_ID", unique = true, nullable = false)
    private Long triId;

    @ManyToOne
    @JoinColumn(name = "TRD_ID", referencedColumnName = "TRD_ID")
    private TbMlTradeMastHist masterTradeId;
    
  /*  @Column(name = "TRD_ID", nullable = false)
    private Long trdId;*/

    @Column(name = "TRI_COD1", nullable = false)
    private Long triCod1;

    @Column(name = "TRI_COD2")
    private Long triCod2;

    @Column(name = "TRI_COD3")
    private Long triCod3;

    @Column(name = "TRI_COD4")
    private Long triCod4;

    @Column(name = "TRI_COD5")
    private Long triCod5;

    @Column(name = "TRI_RATE", nullable = false, precision = 10, scale = 2)
    private BigDecimal triRate;
    
    @Column(name = "TRI_PENALTY", nullable = false, precision = 10, scale = 2)
    private BigDecimal triPenalty;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "TRI_STATUS", length = 2)
    private String triStatus;

    @Column(name = "TRD_QUANTITY", nullable = false, precision = 10, scale = 2)
    private BigDecimal trdQuantity;

    @Column(name = "TRD_UNIT")
    private BigDecimal trdUnit;
    
    @Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;
    
    @Column(name = "H_STATUS", length = 1)
    private String historyStatus;
    
	@Column(name = "LICENSE_FEE")
	private Long licenseFee;

	@Column(name = "DEPOSIT_AMT")
	private BigDecimal depositAmt;

    public Long getTriId() {
        return triId;
    }

    public void setTriId(Long triId) {
        this.triId = triId;
    }

    public Long getTriCod1() {
        return triCod1;
    }

    public void setTriCod1(Long triCod1) {
        this.triCod1 = triCod1;
    }

    public Long getTriCod2() {
        return triCod2;
    }

    public void setTriCod2(Long triCod2) {
        this.triCod2 = triCod2;
    }

    public Long getTriCod3() {
        return triCod3;
    }

    public void setTriCod3(Long triCod3) {
        this.triCod3 = triCod3;
    }

    public Long getTriCod4() {
        return triCod4;
    }

    public void setTriCod4(Long triCod4) {
        this.triCod4 = triCod4;
    }

    public Long getTriCod5() {
        return triCod5;
    }

    public void setTriCod5(Long triCod5) {
        this.triCod5 = triCod5;
    }

    public BigDecimal getTriRate() {
        return triRate;
    }

    public void setTriRate(BigDecimal triRate) {
        this.triRate = triRate;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

   

    public TbMlTradeMastHist getMasterTradeId() {
		return masterTradeId;
	}

	public void setMasterTradeId(TbMlTradeMastHist masterTradeId) {
		this.masterTradeId = masterTradeId;
	}

	public String getTriStatus() {
        return triStatus;
    }

    public void setTriStatus(String triStatus) {
        this.triStatus = triStatus;
    }

    public BigDecimal getTrdQuantity() {
        return trdQuantity;
    }

    public BigDecimal getTrdUnit() {
        return trdUnit;
    }

    public void setTrdQuantity(BigDecimal trdQuantity) {
        this.trdQuantity = trdQuantity;
    }

    public void setTrdUnit(BigDecimal trdUnit) {
        this.trdUnit = trdUnit;
    }

	public BigDecimal getTriPenalty() {
		return triPenalty;
	}

	public void setTriPenalty(BigDecimal triPenalty) {
		this.triPenalty = triPenalty;
	}
	
	 public Long getTriIdh() {
		return triIdh;
	}

	public void setTriIdh(Long triIdh) {
		this.triIdh = triIdh;
	}

	/*public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}*/
	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}
	

	public Long getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(Long licenseFee) {
		this.licenseFee = licenseFee;
	}

	public BigDecimal getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(BigDecimal depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String[] getPkValues() {
	        return new String[] { "ML", "TB_ML_ITEM_DETAIL_HIST", "TRI_ID_H" };
	    }

}