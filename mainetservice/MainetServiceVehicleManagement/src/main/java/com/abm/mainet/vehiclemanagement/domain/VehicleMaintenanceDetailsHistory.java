package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the tb_sw_veremen_mast_hist database table.
 * @author Lalit.Prusti
 *
 * Created Date : 22-May-2018
 */
@Entity
@Table(name = "TB_VM_VEREMEN_MAST_HIST")
public class VehicleMaintenanceDetailsHistory implements Serializable {

    private static final long serialVersionUID = 6599159139194331225L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEM_ID_H", unique = true, nullable = false)
    private Long vemIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "VE_ID")
    private Long veId;

    @Column(name = "VE_VETYPE")
    private Long veVetype;

    @Column(name = "VEM_COSTINCURRED", precision = 10, scale = 2)
    private BigDecimal vemCostincurred;

    @Temporal(TemporalType.DATE)
    @Column(name = "VEM_DATE")
    private Date vemDate;

    @Column(name = "VEM_DOWNTIME")
    private Long vemDowntime;

    @Column(name = "VEM_DOWNTIMEUNIT")
    private Long vemDowntimeunit;

    @Column(name = "VEM_ID")
    private Long vemId;

    @Column(name = "VEM_METYPE")
    private Long vemMetype;

    @Column(name = "VEM_READING")
    private BigDecimal vemReading;

    @Column(name = "VEM_REASON", length = 100)
    private String vemReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VEM_RECEIPTDATE")
    private Date vemReceiptdate;

    @Column(name = "VEM_RECEIPTNO")
    private Long vemReceiptno;

    @Column(name = "VEM_EXPHEAD")
    private Long vemExpHead;

    public VehicleMaintenanceDetailsHistory() {
    }

    public Long getVemIdH() {
        return this.vemIdH;
    }

    public void setVemIdH(Long vemIdH) {
        this.vemIdH = vemIdH;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getVeVetype() {
        return this.veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public BigDecimal getVemCostincurred() {
        return this.vemCostincurred;
    }

    public void setVemCostincurred(BigDecimal vemCostincurred) {
        this.vemCostincurred = vemCostincurred;
    }

    public Date getVemDate() {
        return this.vemDate;
    }

    public void setVemDate(Date vemDate) {
        this.vemDate = vemDate;
    }

    public Long getVemDowntime() {
        return this.vemDowntime;
    }

    public void setVemDowntime(Long vemDowntime) {
        this.vemDowntime = vemDowntime;
    }

    public Long getVemDowntimeunit() {
        return this.vemDowntimeunit;
    }

    public void setVemDowntimeunit(Long vemDowntimeunit) {
        this.vemDowntimeunit = vemDowntimeunit;
    }

    public Long getVemId() {
        return this.vemId;
    }

    public void setVemId(Long vemId) {
        this.vemId = vemId;
    }

    public Long getVemMetype() {
        return this.vemMetype;
    }

    public void setVemMetype(Long vemMetype) {
        this.vemMetype = vemMetype;
    }

    
	public BigDecimal getVemReading() {
		return vemReading;
	}

	public void setVemReading(BigDecimal vemReading) {
		this.vemReading = vemReading;
	}

	public String getVemReason() {
        return this.vemReason;
    }

    public void setVemReason(String vemReason) {
        this.vemReason = vemReason;
    }

    public Date getVemReceiptdate() {
        return this.vemReceiptdate;
    }

    public Long getVemExpHead() {
        return vemExpHead;
    }

    public void setVemExpHead(Long vemExpHead) {
        this.vemExpHead = vemExpHead;
    }

    public void setVemReceiptdate(Date vemReceiptdate) {
        this.vemReceiptdate = vemReceiptdate;
    }

    public Long getVemReceiptno() {
        return this.vemReceiptno;
    }

    public void setVemReceiptno(Long vemReceiptno) {
        this.vemReceiptno = vemReceiptno;
    }

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_VEREMEN_MAST_HIST", "VEM_ID_H" };
    }
}