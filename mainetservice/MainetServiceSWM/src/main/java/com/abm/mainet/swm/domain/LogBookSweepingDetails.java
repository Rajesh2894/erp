package com.abm.mainet.swm.domain;

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

@Entity
@Table(name = "tb_sw_sweeping")
public class LogBookSweepingDetails implements Serializable {
	
	private static final long serialVersionUID = -4716447220292545850L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SWP_ID")
    private Long swpId;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "SWP_DATE")
    private Date swpDate;
	
	@Column(name = "SWP_EMPNAME_BH1")
    private String swpEmpnamebh1;
	
	@Column(name = "SWP_TIME_BT1")
    private String swptimebt1;
	
	@Column(name = "SWP_EMPNAME_BH2")
    private String swpEmpnamebh2;
	
	@Column(name = "SWP_TIME_BT2")
    private String swptimebt2;
	
	@Column(name = "SWP_EMPNAME_DH1")
    private String swpEmpnamedh1;
	
	@Column(name = "SWP_TIME_DT1")
    private String swptimedt1;
	
	@Column(name = "SWP_EMPNAME_DH2")
    private String swpEmpnamedh2;
	
	@Column(name = "SWP_TIME_DT2")
    private String swptimedt2;
	
	@Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;
    
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public LogBookSweepingDetails() {
    }

    public Long getSwpId() {
		return swpId;
	}

	public void setSwpId(Long swpId) {
		this.swpId = swpId;
	}

	public Date getSwpDate() {
		return swpDate;
	}

	public void setSwpDate(Date swpDate) {
		this.swpDate = swpDate;
	}

	public String getSwpEmpnamebh1() {
		return swpEmpnamebh1;
	}

	public void setSwpEmpnamebh1(String swpEmpnamebh1) {
		this.swpEmpnamebh1 = swpEmpnamebh1;
	}

	public String getSwptimebt1() {
		return swptimebt1;
	}

	public void setSwptimebt1(String swptimebt1) {
		this.swptimebt1 = swptimebt1;
	}

	public String getSwpEmpnamebh2() {
		return swpEmpnamebh2;
	}

	public void setSwpEmpnamebh2(String swpEmpnamebh2) {
		this.swpEmpnamebh2 = swpEmpnamebh2;
	}

	public String getSwptimebt2() {
		return swptimebt2;
	}

	public void setSwptimebt2(String swptimebt2) {
		this.swptimebt2 = swptimebt2;
	}

	public String getSwpEmpnamedh1() {
		return swpEmpnamedh1;
	}

	public void setSwpEmpnamedh1(String swpEmpnamedh1) {
		this.swpEmpnamedh1 = swpEmpnamedh1;
	}

	public String getSwptimedt1() {
		return swptimedt1;
	}

	public void setSwptimedt1(String swptimedt1) {
		this.swptimedt1 = swptimedt1;
	}

	public String getSwpEmpnamedh2() {
		return swpEmpnamedh2;
	}

	public void setSwpEmpnamedh2(String swpEmpnamedh2) {
		this.swpEmpnamedh2 = swpEmpnamedh2;
	}

	public String getSwptimedt2() {
		return swptimedt2;
	}

	public void setSwptimedt2(String swptimedt2) {
		this.swptimedt2 = swptimedt2;
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public String[] getPkValues() {
        return new String[] { "SWM", "tb_sw_sweeping", "swpId" };
    }
       
}
