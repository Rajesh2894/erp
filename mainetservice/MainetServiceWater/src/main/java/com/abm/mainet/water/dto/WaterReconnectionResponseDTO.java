package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

public class WaterReconnectionResponseDTO implements Serializable {

	private static final long serialVersionUID = -4362562178483341226L;

	private String connectionNo;
	private String consumerName;
	private Long consumerIdNo;
	private Long tarrifCategoryId;
	private Long premiseTypeId;
	private Long connectionSize;
	private Long discMethodId;
	private Long discType;
	private String discDate;
	private String discRemarks;
	private String tarrifCategory;
	private String premiseType;
	private String discMethod;
	private String disconnectionType;
	private String status;
	private Long applicationId;
	private int uploadedDocSize;
	private Date discAppDate;
	private Long plumberId;
	private Long applicantTypeId;
	private String applicantType;
	private Long meterTypeId;
	private String meterType;
	private boolean isAlreadyApplied;
	private Long csIdn;
	private boolean isPermenantDisconnection;

	/**
	 * @return the connectionNo
	 */
	public String getConnectionNo() {
		return connectionNo;
	}

	/**
	 * @param connectionNo
	 *            the connectionNo to set
	 */
	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @param consumerName
	 *            the consumerName to set
	 */
	public void setConsumerName(final String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @return the consumerIdNo
	 */
	public Long getConsumerIdNo() {
		return consumerIdNo;
	}

	/**
	 * @param consumerIdNo
	 *            the consumerIdNo to set
	 */
	public void setConsumerIdNo(final Long consumerIdNo) {
		this.consumerIdNo = consumerIdNo;
	}

	/**
	 * @return the tarrifCategoryId
	 */
	public Long getTarrifCategoryId() {
		return tarrifCategoryId;
	}

	/**
	 * @param tarrifCategoryId
	 *            the tarrifCategoryId to set
	 */
	public void setTarrifCategoryId(final Long tarrifCategoryId) {
		this.tarrifCategoryId = tarrifCategoryId;
	}

	/**
	 * @return the premiseTypeId
	 */
	public Long getPremiseTypeId() {
		return premiseTypeId;
	}

	/**
	 * @param premiseTypeId
	 *            the premiseTypeId to set
	 */
	public void setPremiseTypeId(final Long premiseTypeId) {
		this.premiseTypeId = premiseTypeId;
	}

	/**
	 * @return the discMethodId
	 */
	public Long getDiscMethodId() {
		return discMethodId;
	}

	/**
	 * @param discMethodId
	 *            the discMethodId to set
	 */
	public void setDiscMethodId(final Long discMethodId) {
		this.discMethodId = discMethodId;
	}

	/**
	 * @return the discType
	 */
	public Long getDiscType() {
		return discType;
	}

	/**
	 * @param discType
	 *            the discType to set
	 */
	public void setDiscType(final Long discType) {
		this.discType = discType;
	}

	/**
	 * @return the discDate
	 */
	public String getDiscDate() {
		return discDate;
	}

	/**
	 * @param discDate
	 *            the discDate to set
	 */
	public void setDiscDate(final String discDate) {
		this.discDate = discDate;
	}

	/**
	 * @return the discRemarks
	 */
	public String getDiscRemarks() {
		return discRemarks;
	}

	/**
	 * @param discRemarks
	 *            the discRemarks to set
	 */
	public void setDiscRemarks(final String discRemarks) {
		this.discRemarks = discRemarks;
	}

	/**
	 * @return the tarrifCategory
	 */
	public String getTarrifCategory() {
		return tarrifCategory;
	}

	/**
	 * @param tarrifCategory
	 *            the tarrifCategory to set
	 */
	public void setTarrifCategory(final String tarrifCategory) {
		this.tarrifCategory = tarrifCategory;
	}

	/**
	 * @return the premiseType
	 */
	public String getPremiseType() {
		return premiseType;
	}

	/**
	 * @param premiseType
	 *            the premiseType to set
	 */
	public void setPremiseType(final String premiseType) {
		this.premiseType = premiseType;
	}

	/**
	 * @return the discMethod
	 */
	public String getDiscMethod() {
		return discMethod;
	}

	/**
	 * @param discMethod
	 *            the discMethod to set
	 */
	public void setDiscMethod(final String discMethod) {
		this.discMethod = discMethod;
	}

	/**
	 * @return the disconnectionType
	 */
	public String getDisconnectionType() {
		return disconnectionType;
	}

	/**
	 * @param disconnectionType
	 *            the disconnectionType to set
	 */
	public void setDisconnectionType(final String disconnectionType) {
		this.disconnectionType = disconnectionType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the uploadedDocSize
	 */
	public int getUploadedDocSize() {
		return uploadedDocSize;
	}

	/**
	 * @param uploadedDocSize
	 *            the uploadedDocSize to set
	 */
	public void setUploadedDocSize(final int uploadedDocSize) {
		this.uploadedDocSize = uploadedDocSize;
	}

	/**
	 * @return the discAppDate
	 */
	public Date getDiscAppDate() {
		return discAppDate;
	}

	/**
	 * @param discAppDate
	 *            the discAppDate to set
	 */
	public void setDiscAppDate(final Date discAppDate) {
		this.discAppDate = discAppDate;
	}

	/**
	 * @return the plumberId
	 */
	public Long getPlumberId() {
		return plumberId;
	}

	/**
	 * @param plumberId
	 *            the plumberId to set
	 */
	public void setPlumberId(final Long plumberId) {
		this.plumberId = plumberId;
	}

	public Long getApplicantTypeId() {
		return applicantTypeId;
	}

	public void setApplicantTypeId(final Long applicantTypeId) {
		this.applicantTypeId = applicantTypeId;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(final String applicantType) {
		this.applicantType = applicantType;
	}

	public Long getMeterTypeId() {
		return meterTypeId;
	}

	public void setMeterTypeId(final Long meterTypeId) {
		this.meterTypeId = meterTypeId;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(final String meterType) {
		this.meterType = meterType;
	}

	public boolean isAlreadyApplied() {
		return isAlreadyApplied;
	}

	public void setAlreadyApplied(final boolean isAlreadyApplied) {
		this.isAlreadyApplied = isAlreadyApplied;
	}

	public Long getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final Long csIdn) {
		this.csIdn = csIdn;
	}

	public Long getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(Long connectionSize) {
		this.connectionSize = connectionSize;
	}

	public boolean isPermenantDisconnection() {
		return isPermenantDisconnection;
	}

	public void setPermenantDisconnection(boolean isPermenantDisconnection) {
		this.isPermenantDisconnection = isPermenantDisconnection;
	}
	
}
