/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * @author pooja.maske
 *
 */
public class ChangeofBlockDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 777616152059752439L;

	private String cbboName;

	private Long commodityName;

	private Long financialYear;
	
	private Long noofFPOAllocated;

	private Long changeReqBlock;
	
	private String ReasonForChange;
	
	private Long sdb1;

	private Long sdb2;

	private Long sdb3;
	
	private Long apmApplicationId;
	
	private Long langId;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	private Long userId;
	
	private List<DocumentDetailsVO> documentList;
	
	
	

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the commodityName
	 */
	public Long getCommodityName() {
		return commodityName;
	}

	/**
	 * @param commodityName the commodityName to set
	 */
	public void setCommodityName(Long commodityName) {
		this.commodityName = commodityName;
	}

	/**
	 * @return the financialYear
	 */
	public Long getFinancialYear() {
		return financialYear;
	}

	/**
	 * @param financialYear the financialYear to set
	 */
	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	/**
	 * @return the noofFPOAllocated
	 */
	public Long getNoofFPOAllocated() {
		return noofFPOAllocated;
	}

	/**
	 * @param noofFPOAllocated the noofFPOAllocated to set
	 */
	public void setNoofFPOAllocated(Long noofFPOAllocated) {
		this.noofFPOAllocated = noofFPOAllocated;
	}

	/**
	 * @return the changeReqBlock
	 */
	public Long getChangeReqBlock() {
		return changeReqBlock;
	}

	/**
	 * @param changeReqBlock the changeReqBlock to set
	 */
	public void setChangeReqBlock(Long changeReqBlock) {
		this.changeReqBlock = changeReqBlock;
	}

	/**
	 * @return the reasonForChange
	 */
	public String getReasonForChange() {
		return ReasonForChange;
	}

	/**
	 * @param reasonForChange the reasonForChange to set
	 */
	public void setReasonForChange(String reasonForChange) {
		ReasonForChange = reasonForChange;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the sdb2
	 */
	public Long getSdb2() {
		return sdb2;
	}

	/**
	 * @param sdb2 the sdb2 to set
	 */
	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	/**
	 * @return the sdb3
	 */
	public Long getSdb3() {
		return sdb3;
	}

	/**
	 * @param sdb3 the sdb3 to set
	 */
	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}

	/**
	 * @return the apmApplicationId
	 */
	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	/**
	 * @param apmApplicationId the apmApplicationId to set
	 */
	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
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
	 * @return the documentList
	 */
	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList the documentList to set
	 */
	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	

}
