/**
 * 
 */
package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
public class WorkflowMasDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long wfId;
	private List<WorkflowDetDTO> workflowDet = new ArrayList<WorkflowDetDTO>();
	private Long deptId;
	private Long serviceId;
	private Long workflowMode;
	private Long complaintId;
	private String type;
	private Long codIdOperLevel1;
	private Long codIdOperLevel2;
	private Long codIdOperLevel3;
	private Long codIdOperLevel4;
	private Long codIdOperLevel5;
	private String status;
	private Long currentOrgId;
	private Long createdBy;
	private Date createDate;
	private Long updatedBy;
	private Date updatedDate;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMac;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMacUpd;
	private String hiddeValue;
	private String prefixName;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private Long wmSchCodeId1;
	private Long wmSchCodeId2;
	private Long extIdentifier;

	public Long getWfId() {
		return wfId;
	}

	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(Long codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public Long getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(Long codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public Long getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(Long codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public Long getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(Long codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public Long getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(Long codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getHiddeValue() {
		return hiddeValue;
	}

	public void setHiddeValue(String hiddeValue) {
		this.hiddeValue = hiddeValue;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}

	public Long getCurrentOrgId() {
		return currentOrgId;
	}

	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	public List<WorkflowDetDTO> getWorkflowDet() {
		return workflowDet;
	}

	public void setWorkflowDet(List<WorkflowDetDTO> workflowDet) {
		this.workflowDet = workflowDet;
	}

	public Long getWorkflowMode() {
		return workflowMode;
	}

	public void setWorkflowMode(Long workflowMode) {
		this.workflowMode = workflowMode;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public Long getWmSchCodeId1() {
		return wmSchCodeId1;
	}

	public void setWmSchCodeId1(Long wmSchCodeId1) {
		this.wmSchCodeId1 = wmSchCodeId1;
	}

	public Long getWmSchCodeId2() {
		return wmSchCodeId2;
	}

	public void setWmSchCodeId2(Long wmSchCodeId2) {
		this.wmSchCodeId2 = wmSchCodeId2;
	}
	

	public Long getExtIdentifier() {
		return extIdentifier;
	}

	public void setExtIdentifier(Long extIdentifier) {
		this.extIdentifier = extIdentifier;
	}
	

}
