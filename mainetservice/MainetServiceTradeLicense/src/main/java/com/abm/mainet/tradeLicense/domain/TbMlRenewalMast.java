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
@Table(name = "tb_ml_renewal_mast ")
public class TbMlRenewalMast implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4589206275885772695L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "TRE_ID ", nullable = false)
    private Long treId;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "TRD_ID ", nullable = false)
    private Long trdId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRE_LICFROM_DATE ")
    private Date treLicfromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRE_LICTO_DATE ")
    private Date treLictoDate ;

    @Column(name = "TRE_STATUS ", nullable = false, length = 200)
    private String treStatus;
    
    @Column(name = "TRE_RNW_PRD")
    private Long renewalPeriod ;

    @Column(name = "ORGID ", nullable = false)
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

	public Long getRenewalPeriod() {
		return renewalPeriod;
	}

	public void setRenewalPeriod(Long renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
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
    
    
	public String[] getPkValues() {
		return new String[] { "ML", "tb_ml_renewal_mast", "TRE_ID" };
	}
   
   
}