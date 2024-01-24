/**
 * 
 */
package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class WaterDashboardDTO implements Serializable {

	private static final long serialVersionUID = 6015818432409385236L;

	private Long csIdn;

	private String csCcn;

	private Date csApldate;

	private Long applicationNo;

	private String deptShortName;

	private String deptName;

	public String getCsCcn() {
		return csCcn;
	}

	public void setCsCcn(String csCcn) {
		this.csCcn = csCcn;
	}

	public Date getCsApldate() {
		return csApldate;
	}

	public void setCsApldate(Date csApldate) {
		this.csApldate = csApldate;
	}

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(Long csIdn) {
		this.csIdn = csIdn;
	}

}
