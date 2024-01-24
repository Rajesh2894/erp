/**
 * 
 */
package com.abm.mainet.common.workflow.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
@Entity
@Table(name = "TB_WORKFLOW_MAS")
public class WorkflowMas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WF_ID")
	private Long wfId;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "WF_ID", referencedColumnName = "WF_ID")
	@Fetch(FetchMode.SUBSELECT)
	@Where(clause = "WFD_STATUS='Y'")
	private List<WorkflowDet> workflowDetList = new ArrayList<WorkflowDet>();

	@ManyToOne
	@JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "SM_SERVICE_ID", referencedColumnName = "SM_SERVICE_ID")
	private ServiceMaster service;

	@Column(name = "WF_MODE")
	private Long workflowMode;

	@ManyToOne
	@JoinColumn(name = "COMP_ID", referencedColumnName = "COMP_ID")
	private ComplaintType complaint;

	@Column(name = "WF_LOC_TYPE")
	private String type;

	@Column(name = "COD_ID_OPER_LEVEL1", nullable = true)
	private Long codIdOperLevel1;

	@Column(name = "COD_ID_OPER_LEVEL2", nullable = true)
	private Long codIdOperLevel2;

	@Column(name = "COD_ID_OPER_LEVEL3", nullable = true)
	private Long codIdOperLevel3;

	@Column(name = "COD_ID_OPER_LEVEL4", nullable = true)
	private Long codIdOperLevel4;

	@Column(name = "COD_ID_OPER_LEVEL5", nullable = true)
	private Long codIdOperLevel5;

	@Column(name = "WF_CONDITION", nullable = true)
	private Long wmSchCodeId1;

	@Column(name = "WF_SCH_NAME", nullable = true)
	private Long wmSchCodeId2;

	@Column(name = "WF_STATUS")
	private String status;

	@Column(name = "WF_FROMAMT")
	private BigDecimal fromAmount;

	@Column(name = "WF_TOAMT")
	private BigDecimal toAmount;

	@Column(name = "COMP_RE")
	private String complaintType;

	@ManyToOne
	@JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
	private Organisation organisation;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	@Column(name = "WF_EXT_IDENTIFIER")
    private Long extIdentifier;

	public Long getWfId() {
		return wfId;
	}

	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ServiceMaster getService() {
		return service;
	}

	public void setService(ServiceMaster service) {
		this.service = service;
	}

	public Long getWorkflowMode() {
		return workflowMode;
	}

	public void setWorkflowMode(Long workflowMode) {
		this.workflowMode = workflowMode;
	}

	public ComplaintType getComplaint() {
		return complaint;
	}

	public void setComplaint(ComplaintType complaint) {
		this.complaint = complaint;
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

	public Long getWmSchCodeId1() {
		return wmSchCodeId1;
	}

	public void setWmSchCodeId1(Long wmSchCodeId1) {
		this.wmSchCodeId1 = wmSchCodeId1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
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

	public List<WorkflowDet> getWorkflowDetList() {
		return workflowDetList;
	}

	public void setWorkflowDetList(List<WorkflowDet> workflowDetList) {
		this.workflowDetList = workflowDetList;
	}

	public String[] getPkValues() {
		return new String[] { "COM", "TB_WORKFLOW_MAS", "WF_ID" };
	}

	public Long getWmSchCodeId2() {
		return wmSchCodeId2;
	}

	public void setWmSchCodeId2(Long wmSchCodeId2) {
		this.wmSchCodeId2 = wmSchCodeId2;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public Long getExtIdentifier() {
		return extIdentifier;
	}

	public void setExtIdentifier(Long extIdentifier) {
		this.extIdentifier = extIdentifier;
	}
	

}
