package com.abm.mainet.rnl.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_RL_TANKERBOOKINGDETAILS")
public class TankerBookingDetailsEntity implements Serializable{
	private static final long serialVersionUID = -4172501212383484097L;
	 
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "D_ID", nullable = false)
    private Long id;
	
	@Column(name = "DRIVER_ID")
    private Long driverId;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "TANKER_RETURN_DATE")
    @Temporal(TemporalType.DATE)
    private Date tankerReturnDate;

    @Column(name = "RETRUN_REMARK")
    private String returnRemark;
    
    @OneToOne
    @JoinColumn(name = "EBK_ID",referencedColumnName = "EBK_ID")
    private EstateBooking estateBooking;
    
    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "LANGID")
    private long langId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTankerReturnDate() {
		return tankerReturnDate;
	}

	public void setTankerReturnDate(Date tankerReturnDate) {
		this.tankerReturnDate = tankerReturnDate;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public EstateBooking getEstateBooking() {
		return estateBooking;
	}

	public void setEstateBooking(EstateBooking estateBooking) {
		this.estateBooking = estateBooking;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
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

	public String getLgIpMacUp() {
		return lgIpMacUp;
	}

	public void setLgIpMacUp(String lgIpMacUp) {
		this.lgIpMacUp = lgIpMacUp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, "TB_RL_TANKERBOOKINGDETAILS",
                "D_ID" };
    }

}
