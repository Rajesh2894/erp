package com.abm.mainet.tradeLicense.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


/**
 * @author Gayatri.kokane
 * @since 11 Feb 2019
 */
@Entity
@Table(name = "tb_ml_renewal_mast_hist")
public class TbMlRenewalMastHist implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4589206275885772695L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "TRE_ID_H" , nullable = false)
    private Long treIdH;
    
    @Column(name = "TRE_ID")
    private Long treId;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "TRD_ID", nullable = false)
    private Long trdId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRE_LICFROM_DATE", nullable = false)
    private Date treLicfromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRE_LICTO_DATE", nullable = false, length = 50)
    private Date treLictoDate ;

    @Column(name = "TRE_STATUS", nullable = false, length = 200)
    private String treStatus;

    @Column(name = "ORGID")
    private Long orgid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;
    
    @Column(name = "H_STATUS", length = 1)
	private String historyStatus;

	public Long getTreIdH() {
		return treIdH;
	}

	public Long getTreId() {
		return treId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public Long getTrdId() {
		return trdId;
	}

	public Date getTreLicfromDate() {
		return treLicfromDate;
	}

	public Date getTreLictoDate() {
		return treLictoDate;
	}

	public String getTreStatus() {
		return treStatus;
	}

	public Long getOrgid() {
		return orgid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setTreIdH(Long treIdH) {
		this.treIdH = treIdH;
	}

	public void setTreId(Long treId) {
		this.treId = treId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public void setTreLicfromDate(Date treLicfromDate) {
		this.treLicfromDate = treLicfromDate;
	}

	public void setTreLictoDate(Date treLictoDate) {
		this.treLictoDate = treLictoDate;
	}

	public void setTreStatus(String treStatus) {
		this.treStatus = treStatus;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	
    
	public String getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}

	public String[] getPkValues() {
		return new String[] { "ML", "tb_ml_renewal_mast_hist", "TRE_ID_H" };
	}
   
   
}