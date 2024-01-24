/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Entity class for Media Details for Respective RTI application filed.
 */
package com.abm.mainet.rti.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_RTI_FWD_EMPLOYEE")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class TbRtiFwdEmployeeEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3011378454321662333L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RTI_FWD_ID", nullable = false)
    private Long rtiFwdId;

    @Column(name = "RTI_ID", nullable = false)
    private Long rtiId;

    /*
     * @Column(name = "RTI_ID") private Long rtiMedId;
     */

    @Column(name = "DEPT_ID")
    private Long deptId;

    /*
     * @Column(name = "CARE_REQUEST_ID") private Long careRequestId;
     */

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "RTI_APPLICATION_ID")
    private Long applicationId;



    @Column(name = "FWD_EMPID")
    private Long fwdEmpId;
    
    @Column(name = "FWD_SLI_DAYS", length = 200)
    private String slADays;

    @Column(name = "RTI_FWD_REMARK")
    private String empFwdRemark;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lModDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

	public Long getRtiFwdId() {
		return rtiFwdId;
	}

	public void setRtiFwdId(Long rtiFwdId) {
		this.rtiFwdId = rtiFwdId;
	}

	public Long getRtiId() {
		return rtiId;
	}

	public void setRtiId(Long rtiId) {
		this.rtiId = rtiId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}



	public Long getFwdEmpId() {
		return fwdEmpId;
	}

	public void setFwdEmpId(Long fwdEmpId) {
		this.fwdEmpId = fwdEmpId;
	}

	public String getSlADays() {
		return slADays;
	}

	public void setSlADays(String slADays) {
		this.slADays = slADays;
	}

	public String getEmpFwdRemark() {
		return empFwdRemark;
	}

	public void setEmpFwdRemark(String empFwdRemark) {
		this.empFwdRemark = empFwdRemark;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getlModDate() {
		return lModDate;
	}

	public void setlModDate(Date lModDate) {
		this.lModDate = lModDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	  public String[] getPkValues() {
	        return new String[] { "RTI", "RTI_FWD_ID", "RTI_FWD_ID" };
	    }

}
