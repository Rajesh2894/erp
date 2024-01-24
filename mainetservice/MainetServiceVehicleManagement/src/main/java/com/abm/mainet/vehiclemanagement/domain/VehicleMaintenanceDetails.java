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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_vm_veremen_mast database table.
 * 
 * @author Niraj.Prusti
 *
 * Created Date : 22-May-2018
 */
@Entity
@Table(name = "TB_VM_VEREMEN_MAST")
public class VehicleMaintenanceDetails implements Serializable {

    private static final long serialVersionUID = 629756571633251958L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEM_ID", unique = true, nullable = false)
    private Long vemId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name="ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    // TODO : field is not available in DB
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

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

	@Column(name = "requestNo")
	private String requestNo;


	@Temporal(TemporalType.DATE)
	@Column(name = "requestDate")
	private Date requestDate;
	
	@Column(name = "driverName", length = 50)
	private String driverName;
	

	@Column(name = "locId")
	private Long locId;
	

	@Column(name = "maintCategory")
	private Long maintCategory;	
		

	@Column(name = "inspectedBy")
	private Long inspectedBy;


	@Column(name = "inspectionDet", length = 100)
	private String inspectionDet;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "acceptDate")
	private Date acceptDate;


	@Column(name = "maintEndReading")
	private BigDecimal maintEndReading;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "maintCompDate")
	private Date maintCompDate;

	@Column(name = "VENDOR_ID")
	private Long vendorId;
	

	@Column(name = "WF_STATUS")
	private String wfStatus;
    
	@Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;
	
    @Column(name = "VEM_QUOTATIONAMT")
	private BigDecimal quotationAmt;
	
    @Column(name = "VEM_QUOTATIONAPPDET")
	private String quotApprovDetails;
	
	public VehicleMaintenanceDetails() {
    }

    public Long getVemId() {
        return this.vemId;
    }

    public void setVemId(Long vemId) {
        this.vemId = vemId;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public void setVemReceiptdate(Date vemReceiptdate) {
        this.vemReceiptdate = vemReceiptdate;
    }

    public Long getVemReceiptno() {
        return this.vemReceiptno;
    }

    public void setVemReceiptno(Long vemReceiptno) {
        this.vemReceiptno = vemReceiptno;
    }

    public Long getVemExpHead() {
        return vemExpHead;
    }

    public void setVemExpHead(Long vemExpHead) {
        this.vemExpHead = vemExpHead;
    }

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_VEREMEN_MAST", "VEM_ID" };
    }

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Long getMaintCategory() {
		return maintCategory;
	}

	public void setMaintCategory(Long maintCategory) {
		this.maintCategory = maintCategory;
	}

	public Long getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(Long inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public String getInspectionDet() {
		return inspectionDet;
	}

	public void setInspectionDet(String inspectionDet) {
		this.inspectionDet = inspectionDet;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public BigDecimal getMaintEndReading() {
		return maintEndReading;
	}

	public void setMaintEndReading(BigDecimal maintEndReading) {
		this.maintEndReading = maintEndReading;
	}

	public Date getMaintCompDate() {
		return maintCompDate;
	}

	public void setMaintCompDate(Date maintCompDate) {
		this.maintCompDate = maintCompDate;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	
	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}

	public BigDecimal getQuotationAmt() {
		return quotationAmt;
	}

	public void setQuotationAmt(BigDecimal quotationAmt) {
		this.quotationAmt = quotationAmt;
	}

	public String getQuotApprovDetails() {
		return quotApprovDetails;
	}

	public void setQuotApprovDetails(String quotApprovDetails) {
		this.quotApprovDetails = quotApprovDetails;
	}
	

}