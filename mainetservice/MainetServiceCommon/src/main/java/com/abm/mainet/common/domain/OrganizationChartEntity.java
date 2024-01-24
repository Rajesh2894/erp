package com.abm.mainet.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "vw_designationmaster_grp")
public class OrganizationChartEntity implements Serializable {

	private static final long serialVersionUID = 4143473474869947236L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DESG_ID")
	private String desigId;

	@Column(name = "Reporting_manager")
	private String desigReportingname;

	@Column(name = "PARENT_DESG_ID")
	private String desigParentId;

	@Column(name = "Designation_Name")
	private String desiName;

	@Column(name = "Employee_Cadre")
	private String EmployeeCadre;

	@Column(name = "Employee_Class")
	private String EmployeeClass;

	@Column(name = "ULB_Code")
	private Long orgId;

	@Column(name = "ULB_Name")
	private String ULBName;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDesigId() {
		return desigId;
	}

	public String getDesigReportingname() {
		return desigReportingname;
	}

	public String getDesigParentId() {
		return desigParentId;
	}

	public void setDesigId(String desigId) {
		this.desigId = desigId;
	}

	public void setDesigReportingname(String desigReportingname) {
		this.desigReportingname = desigReportingname;
	}

	public void setDesigParentId(String desigParentId) {
		this.desigParentId = desigParentId;
	}

	public String[] getPkValues() {
		return new String[] { "COM", "vw_designationmaster_grp", "DESG_ID" };
	}

	public String getDesiName() {
		return desiName;
	}

	public String getEmployeeCadre() {
		return EmployeeCadre;
	}

	public String getEmployeeClass() {
		return EmployeeClass;
	}

	public Long getOrgId() {
		return orgId;
	}

	public String getULBName() {
		return ULBName;
	}

	public void setDesiName(String desiName) {
		this.desiName = desiName;
	}

	public void setEmployeeCadre(String employeeCadre) {
		EmployeeCadre = employeeCadre;
	}

	public void setEmployeeClass(String employeeClass) {
		EmployeeClass = employeeClass;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public void setULBName(String uLBName) {
		ULBName = uLBName;
	}

	@Override
	public String toString() {
		return "OrganizationChartEntity [desigId=" + desigId + ", desigReportingname=" + desigReportingname
				+ ", desigParentId=" + desigParentId + ", desiName=" + desiName + ", EmployeeCadre=" + EmployeeCadre
				+ ", EmployeeClass=" + EmployeeClass + ", orgId=" + orgId + ", ULBName=" + ULBName + "]";
	}

}
