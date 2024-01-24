package com.abm.mainet.tradeLicense.domain;


import java.io.Serializable;
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
@Table(name = "TB_ML_OWNER_DETAIL_HIST")
public class TbMlOwnerDetailHist implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4944263514753832014L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRO_ID_H", unique = true, nullable = false)
    private Long troIdh;
    
    @Column(name = "TRO_ID", nullable = false)
    private Long troId;

    @ManyToOne
    @JoinColumn(name = "TRD_ID", referencedColumnName = "TRD_ID")
    private TbMlTradeMastHist masterTradeId;
  /*  
    @Column(name = "TRD_ID", nullable = false)
    private Long trdId;*/

    @Column(name = "TRO_NAME", nullable = false, length = 100)
    private String troName;

    @Column(name = "TRO_ADDRESS", nullable = false, length = 200)
    private String troAddress;

    @Column(name = "TRO_TITLE", nullable = false)
    private Long troTitle;

    @Column(name = "TRO_MNAME", nullable = false, length = 50)
    private String troMname;

    @Column(name = "TRO_GEN", nullable = false)
    private String troGen;

    @Column(name = "TRO_EMAILID", nullable = false, length = 256)
    private String troEmailid;

    @Column(name = "TRO_MOBILENO", nullable = false, length = 10)
    private String troMobileno;

    @Column(name = "TRO_ADHNO", nullable = false)
    private Long troAdhno;

    @Column(name = "TRO_PR", nullable = false)
    private String troPr;

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
    
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;

    public Long getTroId() {
        return troId;
    }

    public void setTroId(Long troId) {
        this.troId = troId;
    }

    public String getTroName() {
        return troName;
    }

    public void setTroName(String troName) {
        this.troName = troName;
    }

    public String getTroAddress() {
        return troAddress;
    }

    public void setTroAddress(String troAddress) {
        this.troAddress = troAddress;
    }

    public String getTroEmailid() {
        return troEmailid;
    }

    public void setTroEmailid(String troEmailid) {
        this.troEmailid = troEmailid;
    }

    public String getTroMobileno() {
        return troMobileno;
    }

    public void setTroMobileno(String troMobileno) {
        this.troMobileno = troMobileno;
    }

    public Long getTroAdhno() {
        return troAdhno;
    }

    public void setTroAdhno(Long troAdhno) {
        this.troAdhno = troAdhno;
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

	public Long getTroTitle() {
        return troTitle;
    }

    public void setTroTitle(Long troTitle) {
        this.troTitle = troTitle;
    }

    public String getTroMname() {
        return troMname;
    }

    public void setTroMname(String troMname) {
        this.troMname = troMname;
    }

    public String getTroGen() {
        return troGen;
    }

    public void setTroGen(String troGen) {
        this.troGen = troGen;
    }

    public String getTroPr() {
        return troPr;
    }

    public void setTroPr(String troPr) {
        this.troPr = troPr;
    }

    public Long getTroIdh() {
		return troIdh;
	}

	public void setTroIdh(Long troIdh) {
		this.troIdh = troIdh;
	}


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

	public String[] getPkValues() {
        return new String[] { "ML", "TB_ML_OWNER_DETAIL_HIST", "TRO_ID_H" };
    }

	/*public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}*/

}