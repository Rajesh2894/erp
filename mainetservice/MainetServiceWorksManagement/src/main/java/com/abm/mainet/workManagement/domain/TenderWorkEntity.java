package com.abm.mainet.workManagement.domain;

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

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
@Entity
@Table(name = "TB_WMS_TENDER_WORK")
public class TenderWorkEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -334118514466571600L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TNDW_ID", nullable = false)
	private Long tndWId;

	@ManyToOne
	@JoinColumn(name = "TND_ID", referencedColumnName = "TND_ID", nullable = false)
	private TenderMasterEntity tenderMasEntity;

	@ManyToOne
	@JoinColumn(name = "WORK_ID", referencedColumnName = "WORK_ID", nullable = false)
	private WorkDefinationEntity workDefinationEntity;

	@Column(name = "WORK_ESTAMT")
	private BigDecimal workEstimateAmt;

	@ManyToOne
	@JoinColumn(name = "TND_VENDER", referencedColumnName = "VM_VENDORID", nullable = true)
	private TbAcVendormasterEntity vendorMaster;

	@Column(name = "TND_VED_CLASS")
	private Long venderClassId;

	@Column(name = "TND_EMD_AMOUNT")
	private BigDecimal tenderSecAmt;

	@Column(name = "TND_FEE_AMOUNT")
	private BigDecimal tenderFeeAmt;

	@Column(name = "TND_STAMPFEE")
	private BigDecimal tenderStampFee;

	@Column(name = "TND_VENWORKPE")
	private String vendorWorkPeriod;

	@Column(name = "TND_VENWORKPE_UNIT")
	private Long vendorWorkPeriodUnit;

	@Column(name = "TND_TYPE")
	private Long tenderType;

	@Column(name = "TND_NODAYAGREE")
	private Long tenderNoOfDayAggremnt;

	@Column(name = "TND_VALUE")
	private BigDecimal tenderValue;
	
	@Column(name = "TND_SUBMIT_DATE", nullable = true)
	private Date tndSubmitDate;
	
	@Column(name = "TND_GST_APL", nullable = true)
	private String tndGSTApl;
	
	@Column(name = "TND_COPNT_AUTH", nullable = true)
	private Long tndCopntAuth;
	
	@Column(name = "TND_AWD_RSONO", nullable = true)
	private String tndAwdResNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TND_AWD_RSODATE", nullable = true)
	private Date tndAwdResDate;

	@Column(name = "TND_PG_TYPID", nullable = true)
	private Long tndPGTypeId;
	
	@Column(name = "TND_AUTH_DSGID", nullable = true)
	private Long tndAuthDesgid;
	
	@Column(name = "TND_PG_RATE", nullable = true)
	private BigDecimal tndPGRate;
	
	@Column(name = "TND_PG_AMOUNT", nullable = true)
	private BigDecimal tndPGAmount;
	
	@Column(name = "TND_PG_MODEID", nullable = true)
	private Long tndPGModeId;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@ManyToOne
	@JoinColumn(name = "WORK_ASSIGNEE", referencedColumnName = "EMPID", nullable = false)
	private Employee employee;

	@Column(name = "WORK_ASSIGNEDATE", nullable = true)
	private Date workAssignedDate;

	@Column(name = "TND_LOA_NO", length = 40, nullable = true)
	private String tndLOANo;

	@Column(name = "TND_LOA_DATE", nullable = true)
	private Date tndLOADate;

	@Column(name = "TND_AMOUNT")
	private BigDecimal tenderAmount;

	@Column(name = "CONT_ID")
	private Long contractId;
	
	@Column(name = "TND_TYPE_PERCENT", nullable = true)
	private Long tendTypePercent;
	
	@Column(name = "PR_ID")
	private Long prId;
	
	@Column(name = "DISPOSAL_ID")
	private Long expiryId;

	public Long getTndWId() {
		return tndWId;
	}

	public void setTndWId(Long tndWId) {
		this.tndWId = tndWId;
	}

	public TenderMasterEntity getTenderMasEntity() {
		return tenderMasEntity;
	}

	public void setTenderMasEntity(TenderMasterEntity tenderMasEntity) {
		this.tenderMasEntity = tenderMasEntity;
	}

	public WorkDefinationEntity getWorkDefinationEntity() {
		return workDefinationEntity;
	}

	public void setWorkDefinationEntity(WorkDefinationEntity workDefinationEntity) {
		this.workDefinationEntity = workDefinationEntity;
	}

	public BigDecimal getWorkEstimateAmt() {
		return workEstimateAmt;
	}

	public void setWorkEstimateAmt(BigDecimal workEstimateAmt) {
		this.workEstimateAmt = workEstimateAmt;
	}
	

	public Date getTndSubmitDate() {
		return tndSubmitDate;
	}

	public void setTndSubmitDate(Date tndSubmitDate) {
		this.tndSubmitDate = tndSubmitDate;
	}


	public String getTndGSTApl() {
		return tndGSTApl;
	}

	public void setTndGSTApl(String tndGSTApl) {
		this.tndGSTApl = tndGSTApl;
	}

	public Long getTndCopntAuth() {
		return tndCopntAuth;
	}


	public void setTndCopntAuth(Long tndCopntAuth) {
		this.tndCopntAuth = tndCopntAuth;
	}

	
	public String getTndAwdResNo() {
		return tndAwdResNo;
	}


	public void setTndAwdResNo(String tndAwdResNo) {
		this.tndAwdResNo = tndAwdResNo;
	}

	public Date getTndAwdResDate() {
		return tndAwdResDate;
	}

	
	public void setTndAwdResDate(Date tndAwdResDate) {
		this.tndAwdResDate = tndAwdResDate;
	}


	public Long getTndPGTypeId() {
		return tndPGTypeId;
	}

	public void setTndPGTypeId(Long tndPGTypeId) {
		this.tndPGTypeId = tndPGTypeId;
	}

	
	public Long getTndAuthDesgid() {
		return tndAuthDesgid;
	}


	public void setTndAuthDesgid(Long tndAuthDesgid) {
		this.tndAuthDesgid = tndAuthDesgid;
	}

	public BigDecimal getTndPGRate() {
		return tndPGRate;
	}

	public void setTndPGRate(BigDecimal tndPGRate) {
		this.tndPGRate = tndPGRate;
	}

	
	public BigDecimal getTndPGAmount() {
		return tndPGAmount;
	}

	
	public void setTndPGAmount(BigDecimal tndPGAmount) {
		this.tndPGAmount = tndPGAmount;
	}
	public Long getTndPGModeId() {
		return tndPGModeId;
	}

	public void setTndPGModeId(Long tndPGModeId) {
		this.tndPGModeId = tndPGModeId;
	}
	

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getWorkAssignedDate() {
		return workAssignedDate;
	}

	public void setWorkAssignedDate(Date workAssignedDate) {
		this.workAssignedDate = workAssignedDate;
	}

	public TbAcVendormasterEntity getVendorMaster() {
		return vendorMaster;
	}

	public void setVendorMaster(TbAcVendormasterEntity vendorMaster) {
		this.vendorMaster = vendorMaster;
	}

	public Long getVenderClassId() {
		return venderClassId;
	}

	public void setVenderClassId(Long venderClassId) {
		this.venderClassId = venderClassId;
	}

	public BigDecimal getTenderSecAmt() {
		return tenderSecAmt;
	}

	public void setTenderSecAmt(BigDecimal tenderSecAmt) {
		this.tenderSecAmt = tenderSecAmt;
	}

	public BigDecimal getTenderFeeAmt() {
		return tenderFeeAmt;
	}

	public void setTenderFeeAmt(BigDecimal tenderFeeAmt) {
		this.tenderFeeAmt = tenderFeeAmt;
	}

	public Long getTenderType() {
		return tenderType;
	}

	public void setTenderType(Long tenderType) {
		this.tenderType = tenderType;
	}

	public BigDecimal getTenderValue() {
		return tenderValue;
	}

	public void setTenderValue(BigDecimal tenderValue) {
		this.tenderValue = tenderValue;
	}

	public String getVendorWorkPeriod() {
		return vendorWorkPeriod;
	}

	public void setVendorWorkPeriod(String vendorWorkPeriod) {
		this.vendorWorkPeriod = vendorWorkPeriod;
	}

	public Long getVendorWorkPeriodUnit() {
		return vendorWorkPeriodUnit;
	}

	public void setVendorWorkPeriodUnit(Long vendorWorkPeriodUnit) {
		this.vendorWorkPeriodUnit = vendorWorkPeriodUnit;
	}

	public BigDecimal getTenderStampFee() {
		return tenderStampFee;
	}

	public void setTenderStampFee(BigDecimal tenderStampFee) {
		this.tenderStampFee = tenderStampFee;
	}

	public Long getTenderNoOfDayAggremnt() {
		return tenderNoOfDayAggremnt;
	}

	public void setTenderNoOfDayAggremnt(Long tenderNoOfDayAggremnt) {
		this.tenderNoOfDayAggremnt = tenderNoOfDayAggremnt;
	}

	public String getTndLOANo() {
		return tndLOANo;
	}

	public void setTndLOANo(String tndLOANo) {
		this.tndLOANo = tndLOANo;
	}

	public Date getTndLOADate() {
		return tndLOADate;
	}

	public void setTndLOADate(Date tndLOADate) {
		this.tndLOADate = tndLOADate;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getTendTypePercent() {
		return tendTypePercent;
	}

	public void setTendTypePercent(Long tendTypePercent) {
		this.tendTypePercent = tendTypePercent;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_TENDER_WORK", "TNDW_ID" };
	}
	
   public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	public Long getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(Long expiryId) {
		this.expiryId = expiryId;
	}
	
	

        
}
