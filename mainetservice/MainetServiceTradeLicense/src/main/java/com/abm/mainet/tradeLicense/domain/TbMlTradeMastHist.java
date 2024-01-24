package com.abm.mainet.tradeLicense.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gayatri.Kokane
 * @since 11 Feb 2019
 */
@Entity
@Table(name = "tb_ml_trade_mast_hist ")
public class TbMlTradeMastHist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589206275885772695L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TRD_ID_H", nullable = false)
	private Long trdIdH;
	@Column(name = "TRD_ID", nullable = false)
	private Long trdId;

	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

	@Column(name = "TRD_TYPE", nullable = false)
	private Long trdType;

	@Column(name = "TRD_LICTYPE", nullable = false)
	private Long trdLictype;

	@Column(name = "TRD_BUSNM", nullable = false, length = 50)
	private String trdBusnm;

	@Column(name = "TRD_BUSADD", nullable = false, length = 200)
	private String trdBusadd;

	@Column(name = "TRD_OLDLICNO", nullable = false, length = 100)
	private String trdOldlicno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICFROM_DATE")
	private Date trdLicfromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICTO_DATE")
	private Date trdLictoDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICISDATE")
	private Date trdLicisdate;

	@Column(name = "TRD_WARD1", nullable = false)
	private Long trdWard1;

	@Column(name = "TRD_WARD2")
	private Long trdWard2;

	@Column(name = "TRD_WARD3")
	private Long trdWard3;

	@Column(name = "TRD_WARD4")
	private Long trdWard4;

	@Column(name = "TRD_WARD5")
	private Long trdWard5;

	@Column(name = "TRD_FTYPE", nullable = false)
	private Long trdFtype;

	@Column(name = "TRD_FAREA", nullable = false, length = 20)
	private String trdFarea;

	@Column(name = "TRD_ETY", length = 2)
	private String trdEty;

	@Column(name = "TRD_STATUS", length = 10)
	private Long trdStatus;
	@Column(name = "CAN_REMARK")
	private String canRemark;
	@Column(name = "TRD_LICNO", length = 2)
	private String trdLicno;

	@Column(name = "ORGID", nullable = false)
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
	@Column(name = "PM_PROPNO")
	private String pmPropNo;
	@Column(name = "TRD_OWNERNM")
	private String trdOwnerNm;

	@Column(name = "DUE_ON_PROPERTY")
	private BigDecimal dueOnProperty;

	@Column(name = "DP_ZONE")
	private Long dpZone;

	@Column(name = "BUILDING_PERMISSION_NO")
	private String buldingPermissionNo;

	@Column(name = "FIRE_NOC_APPLICABLE", length = 1)
	private String fireNonApplicable;

	@Column(name = "TRD_FRE_NOC")
	private String fireNOCNo;

	@Column(name = "Trans_Mode_TYPE")
	private String transferMode;

	@Column(name = "TRD_REN_CYCLE")
	private Long renewCycle;

	@Column(name = "PROP_OWNER_ADDRS")
	private String propertyOwnerAddress;

	@Column(name = "DUE_ON_WATER")
	private BigDecimal dueOnWater;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;
	
	@Column(name = "PINCODE", precision = 6, scale = 0, nullable = true)
	private Long pincode;
	
	@Column(name = "LAND_MARK",length = 250,nullable = true)
	private String landMark;
	
	@Column(name = "DIRECTORS_NAME_ADDRES", length = 500,nullable = true)
	private String directorsNameAndAdd;
	
	@Column(name = "SOURCE",length = 10, nullable = true)
	private String source;
	
	@Column(name = "TRD_BUS_NATURE", nullable = true, length = 250)
	private String bussinessNature;
	

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMlItemDetailHist> itemDetails = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMlOwnerDetailHist> ownerDetails = new ArrayList<>();

	public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public Long getTrdType() {
		return trdType;
	}

	public void setTrdType(Long trdType) {
		this.trdType = trdType;
	}

	public Long getTrdLictype() {
		return trdLictype;
	}

	public void setTrdLictype(Long trdLictype) {
		this.trdLictype = trdLictype;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public Date getTrdLicfromDate() {
		return trdLicfromDate;
	}

	public void setTrdLicfromDate(Date trdLicfromDate) {
		this.trdLicfromDate = trdLicfromDate;
	}

	public Date getTrdLictoDate() {
		return trdLictoDate;
	}

	public void setTrdLictoDate(Date trdLictoDate) {
		this.trdLictoDate = trdLictoDate;
	}

	public Date getTrdLicisdate() {
		return trdLicisdate;
	}

	public void setTrdLicisdate(Date trdLicisdate) {
		this.trdLicisdate = trdLicisdate;
	}

	public Long getTrdWard1() {
		return trdWard1;
	}

	public void setTrdWard1(Long trdWard1) {
		this.trdWard1 = trdWard1;
	}

	public Long getTrdWard2() {
		return trdWard2;
	}

	public void setTrdWard2(Long trdWard2) {
		this.trdWard2 = trdWard2;
	}

	public Long getTrdWard3() {
		return trdWard3;
	}

	public void setTrdWard3(Long trdWard3) {
		this.trdWard3 = trdWard3;
	}

	public Long getTrdWard4() {
		return trdWard4;
	}

	public void setTrdWard4(Long trdWard4) {
		this.trdWard4 = trdWard4;
	}

	public Long getTrdWard5() {
		return trdWard5;
	}

	public void setTrdWard5(Long trdWard5) {
		this.trdWard5 = trdWard5;
	}

	public Long getTrdFtype() {
		return trdFtype;
	}

	public void setTrdFtype(Long trdFtype) {
		this.trdFtype = trdFtype;
	}

	public String getTrdFarea() {
		return trdFarea;
	}

	public void setTrdFarea(String trdFarea) {
		this.trdFarea = trdFarea;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public String getTrdEty() {
		return trdEty;
	}

	public void setTrdEty(String trdEty) {
		this.trdEty = trdEty;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<TbMlItemDetailHist> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<TbMlItemDetailHist> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public List<TbMlOwnerDetailHist> getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(List<TbMlOwnerDetailHist> ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getTrdStatus() {
		return trdStatus;
	}

	public void setTrdStatus(Long trdStatus) {
		this.trdStatus = trdStatus;
	}

	public String getPmPropNo() {
		return pmPropNo;
	}

	public void setPmPropNo(String pmPropNo) {
		this.pmPropNo = pmPropNo;
	}

	public String getTrdOwnerNm() {
		return trdOwnerNm;
	}

	public void setTrdOwnerNm(String trdOwnerNm) {
		this.trdOwnerNm = trdOwnerNm;
	}

	public String getCanRemark() {
		return canRemark;
	}

	public void setCanRemark(String canRemark) {
		this.canRemark = canRemark;
	}

	public Long getTrdIdH() {
		return trdIdH;
	}

	public void setTrdIdH(Long trdIdH) {
		this.trdIdH = trdIdH;
	}

	public BigDecimal getDueOnProperty() {
		return dueOnProperty;
	}

	public void setDueOnProperty(BigDecimal dueOnProperty) {
		this.dueOnProperty = dueOnProperty;
	}

	public Long getDpZone() {
		return dpZone;
	}

	public void setDpZone(Long dpZone) {
		this.dpZone = dpZone;
	}

	public String getFireNonApplicable() {
		return fireNonApplicable;
	}

	public void setFireNonApplicable(String fireNonApplicable) {
		this.fireNonApplicable = fireNonApplicable;
	}

	public String getBuldingPermissionNo() {
		return buldingPermissionNo;
	}

	public void setBuldingPermissionNo(String buldingPermissionNo) {
		this.buldingPermissionNo = buldingPermissionNo;
	}

	public String getFireNOCNo() {
		return fireNOCNo;
	}

	public void setFireNOCNo(String fireNOCNo) {
		this.fireNOCNo = fireNOCNo;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public String getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}


	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getDirectorsNameAndAdd() {
		return directorsNameAndAdd;
	}

	public void setDirectorsNameAndAdd(String directorsNameAndAdd) {
		this.directorsNameAndAdd = directorsNameAndAdd;
	}
	
	

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getPkValues() {
		return new String[] { "ML", "tb_ml_trade_mast_hist", "TRD_ID_H" };
	}

	public Long getRenewCycle() {
		return renewCycle;
	}

	public void setRenewCycle(Long renewCycle) {
		this.renewCycle = renewCycle;
	}

	public String getPropertyOwnerAddress() {
		return propertyOwnerAddress;
	}

	public BigDecimal getDueOnWater() {
		return dueOnWater;
	}

	public void setPropertyOwnerAddress(String propertyOwnerAddress) {
		this.propertyOwnerAddress = propertyOwnerAddress;
	}

	public void setDueOnWater(BigDecimal dueOnWater) {
		this.dueOnWater = dueOnWater;
	}
	

	public String getBussinessNature() {
		return bussinessNature;
	}

	public void setBussinessNature(String bussinessNature) {
		this.bussinessNature = bussinessNature;
	}
}