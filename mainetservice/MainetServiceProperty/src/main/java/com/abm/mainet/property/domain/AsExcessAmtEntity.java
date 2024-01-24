package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_excess_amt")
public class AsExcessAmtEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7393687201635638129L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EXCESS_ID")
    private long excessId;

    @Column(name = "RM_RCPTID")
    private Long rmRcptid;

    @Column(name = "PROP_NO")
    private String propNo;

    @Column(name = "ADJ_AMT")
    private double adjAmt;

    @Column(name = "EXC_AMT")
    private double excAmt;

    @Column(name = "EXCADJ_FLAG")
    private String excadjFlag;

    @Column(name = "EXCESS_DIS")
    private String excessDis;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "orgid")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "EXCESS_ACTIVE")
    private String excessActive;

    @Column(name = "EXTRA_COL2")
    private String extraCol2;

    @Column(name = "EXTRA_COL3")
    private String extraCol3;
	
	@Column(name = "pd_flatno")
	private String flatNo;
    

    public long getExcessId() {
        return excessId;
    }

    public void setExcessId(long excessId) {
        this.excessId = excessId;
    }

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public double getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(double adjAmt) {
        this.adjAmt = adjAmt;
    }

    public double getExcAmt() {
        return excAmt;
    }

    public void setExcAmt(double excAmt) {
        this.excAmt = excAmt;
    }

    public String getExcadjFlag() {
        return excadjFlag;
    }

    public void setExcadjFlag(String excadjFlag) {
        this.excadjFlag = excadjFlag;
    }

    public String getExcessDis() {
        return excessDis;
    }

    public void setExcessDis(String excessDis) {
        this.excessDis = excessDis;
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

    public String getExtraCol2() {
        return extraCol2;
    }

    public void setExtraCol2(String extraCol2) {
        this.extraCol2 = extraCol2;
    }

    public String getExtraCol3() {
        return extraCol3;
    }

    public void setExtraCol3(String extraCol3) {
        this.extraCol3 = extraCol3;
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

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_excess_amt", "EXCESS_ID" };
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String getExcessActive() {
        return excessActive;
    }

    public void setExcessActive(String excessActive) {
        this.excessActive = excessActive;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

    
}
