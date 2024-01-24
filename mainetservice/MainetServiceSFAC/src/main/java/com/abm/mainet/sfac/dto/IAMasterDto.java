/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aspose.slides.Collections.ArrayList;

/**
 * @author pooja.maske
 *
 */
public class IAMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5055262447771095796L;

	private Long IAId;

	private String IAName;

	private Long alcYear;

	private String CbboName;

	private String CbboUniqueId;

	private String fName;

	private String mName;

	private String lName;

	private String iaEmailId;

	private String iaAddress;

	private String iaPinCode;

	private String iaContactNo;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long langId;

	private String allcYear;

	private Long stateCate;

	private Long region;

	private List<IAMasterDetailDto> iaDetailDto = new ArrayList();

	private Long deptId;

	private String level;

	private Long State;

	private String iaShortName;

	private String activeInactiveStatus;
	
	private String summaryStatus;

	/**
	 * @return the iAId
	 */
	public Long getIAId() {
		return IAId;
	}

	/**
	 * @param iAId the iAId to set
	 */
	public void setIAId(Long iAId) {
		IAId = iAId;
	}

	/**
	 * @return the iAName
	 */
	public String getIAName() {
		return IAName;
	}

	/**
	 * @param iAName the iAName to set
	 */
	public void setIAName(String iAName) {
		IAName = iAName;
	}

	/**
	 * @return the alcYear
	 */
	public Long getAlcYear() {
		return alcYear;
	}

	/**
	 * @param alcYear the alcYear to set
	 */
	public void setAlcYear(Long alcYear) {
		this.alcYear = alcYear;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return CbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		CbboName = cbboName;
	}

	/**
	 * @return the cbboUniqueId
	 */
	public String getCbboUniqueId() {
		return CbboUniqueId;
	}

	/**
	 * @param cbboUniqueId the cbboUniqueId to set
	 */
	public void setCbboUniqueId(String cbboUniqueId) {
		CbboUniqueId = cbboUniqueId;
	}

	/**
	 * @return the allcYear
	 */
	public String getAllcYear() {
		return allcYear;
	}

	/**
	 * @param allcYear the allcYear to set
	 */
	public void setAllcYear(String allcYear) {
		this.allcYear = allcYear;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the mName
	 */
	public String getmName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the iaEmailId
	 */
	public String getIaEmailId() {
		return iaEmailId;
	}

	/**
	 * @param iaEmailId the iaEmailId to set
	 */
	public void setIaEmailId(String iaEmailId) {
		this.iaEmailId = iaEmailId;
	}

	/**
	 * @return the iaAddress
	 */
	public String getIaAddress() {
		return iaAddress;
	}

	/**
	 * @param iaAddress the iaAddress to set
	 */
	public void setIaAddress(String iaAddress) {
		this.iaAddress = iaAddress;
	}

	/**
	 * @return the iaPinCode
	 */
	public String getIaPinCode() {
		return iaPinCode;
	}

	/**
	 * @param iaPinCode the iaPinCode to set
	 */
	public void setIaPinCode(String iaPinCode) {
		this.iaPinCode = iaPinCode;
	}

	/**
	 * @return the iaContactNo
	 */
	public String getIaContactNo() {
		return iaContactNo;
	}

	/**
	 * @param iaContactNo the iaContactNo to set
	 */
	public void setIaContactNo(String iaContactNo) {
		this.iaContactNo = iaContactNo;
	}

	/**
	 * @return the stateCate
	 */
	public Long getStateCate() {
		return stateCate;
	}

	/**
	 * @param stateCate the stateCate to set
	 */
	public void setStateCate(Long stateCate) {
		this.stateCate = stateCate;
	}

	/**
	 * @return the region
	 */
	public Long getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Long region) {
		this.region = region;
	}

	/**
	 * @return the iaDetailDto
	 */
	public List<IAMasterDetailDto> getIaDetailDto() {
		return iaDetailDto;
	}

	/**
	 * @param iaDetailDto the iaDetailDto to set
	 */
	public void setIaDetailDto(List<IAMasterDetailDto> iaDetailDto) {
		this.iaDetailDto = iaDetailDto;
	}

	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return State;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		State = state;
	}

	/**
	 * @return the iaShortName
	 */
	public String getIaShortName() {
		return iaShortName;
	}

	/**
	 * @param iaShortName the iaShortName to set
	 */
	public void setIaShortName(String iaShortName) {
		this.iaShortName = iaShortName;
	}

	/**
	 * @return the activeInactiveStatus
	 */
	public String getActiveInactiveStatus() {
		return activeInactiveStatus;
	}

	/**
	 * @param activeInactiveStatus the activeInactiveStatus to set
	 */
	public void setActiveInactiveStatus(String activeInactiveStatus) {
		this.activeInactiveStatus = activeInactiveStatus;
	}

	/**
	 * @return the summaryStatus
	 */
	public String getSummaryStatus() {
		return summaryStatus;
	}

	/**
	 * @param summaryStatus the summaryStatus to set
	 */
	public void setSummaryStatus(String summaryStatus) {
		this.summaryStatus = summaryStatus;
	}

	
}
