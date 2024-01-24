package com.abm.mainet.common.leavemanagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "TB_LEAVE_GRANT_MASTER")
public class TbLeaveGrantMaster  implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -1054971424165320626L;

		@Id
	    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	    @GeneratedValue(generator = "MyCustomGenerator")
	    @Column(name = "LEAVE_GRANT_ID", nullable = false)
	    private Long leaveGrantId;
	    
	    @Column(name = "EMPID", nullable = false)
	    private Long empId;
	    
	    @Column(name = "ORGID", nullable = false)
	    private Long orgId;
	    
	    @Column(name = "SL_LEAVEBALANCE", nullable = false)
	    private BigDecimal slLeaveBalance;
	    
	    @Column(name = "CL_LEAVEBALANCE", nullable = false)
	    private BigDecimal clLeaveBalance;
	    
	    @Column(name = "PL_LEAVEBALANCE", nullable = false)
	    private BigDecimal plLeaveBalance;
	    
	    @Column(name = "ML_LEAVEBALANCE", nullable = false)
	    private BigDecimal mlLeaveBalance;
	    
	    @Column(name = "PT_LEAVEBALANCE", nullable = false)
	    private BigDecimal ptLeaveBalance;
	    
	    @Column(name = "NO_OF_LEAVE_GRANTED", nullable = false)
	    private BigDecimal noOfLeavesGranted;
	    
	    @Column(name = "FINALCIAL_YEAR", nullable = false)
	    private Date financialYear;
	    
	  
	    @Column(name = "CREATED_BY", nullable = false, updatable = false)
	    private Long createdBy;

	    @Column(name = "CREATED_DATE", nullable = false)
	    private Date createdDate;

	    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
	    private Long updatedBy;

	    @Column(name = "UPDATED_DATE", nullable = true)
	    private Date updatedDate;
	    
	    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
	    private String lgIpMac;

	    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	    private String lgIpMacUpd;

		public Long getLeaveGrantId() {
			return leaveGrantId;
		}

		public void setLeaveGrantId(Long leaveGrantId) {
			this.leaveGrantId = leaveGrantId;
		}

		public Long getEmpId() {
			return empId;
		}

		public void setEmpId(Long empId) {
			this.empId = empId;
		}

		public Long getOrgId() {
			return orgId;
		}

		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}

		public BigDecimal getSlLeaveBalance() {
			return slLeaveBalance;
		}

		public void setSlLeaveBalance(BigDecimal slLeaveBalance) {
			this.slLeaveBalance = slLeaveBalance;
		}

		public BigDecimal getClLeaveBalance() {
			return clLeaveBalance;
		}

		public void setClLeaveBalance(BigDecimal clLeaveBalance) {
			this.clLeaveBalance = clLeaveBalance;
		}

		public BigDecimal getPlLeaveBalance() {
			return plLeaveBalance;
		}

		public void setPlLeaveBalance(BigDecimal plLeaveBalance) {
			this.plLeaveBalance = plLeaveBalance;
		}

		public BigDecimal getMlLeaveBalance() {
			return mlLeaveBalance;
		}

		public void setMlLeaveBalance(BigDecimal mlLeaveBalance) {
			this.mlLeaveBalance = mlLeaveBalance;
		}

		public BigDecimal getPtLeaveBalance() {
			return ptLeaveBalance;
		}

		public void setPtLeaveBalance(BigDecimal ptLeaveBalance) {
			this.ptLeaveBalance = ptLeaveBalance;
		}

		public BigDecimal getNoOfLeavesGranted() {
			return noOfLeavesGranted;
		}

		public void setNoOfLeavesGranted(BigDecimal noOfLeavesGranted) {
			this.noOfLeavesGranted = noOfLeavesGranted;
		}

		public Date getFinancialYear() {
			return financialYear;
		}

		public void setFinancialYear(Date financialYear) {
			this.financialYear = financialYear;
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
	    
	    
		public String[] getPkValues() {
	        return new String[] { "LEAVE", "TB_LEAVE_GRANT_MASTER", "LEAVE_GRANT_ID" };
	    }

	     

}
