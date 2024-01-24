package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
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

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(final String consumerName) {
		this.consumerName = consumerName;
	}

	public Long getConsumerIdNo() {
		return consumerIdNo;
	}

	public void setConsumerIdNo(final Long consumerIdNo) {
		this.consumerIdNo = consumerIdNo;
	}

	public Long getTarrifCategoryId() {
		return tarrifCategoryId;
	}

	public void setTarrifCategoryId(final Long tarrifCategoryId) {
		this.tarrifCategoryId = tarrifCategoryId;
	}

	public Long getPremiseTypeId() {
		return premiseTypeId;
	}

	public void setPremiseTypeId(final Long premiseTypeId) {
		this.premiseTypeId = premiseTypeId;
	}

	public Long getDiscMethodId() {
		return discMethodId;
	}

	public void setDiscMethodId(final Long discMethodId) {
		this.discMethodId = discMethodId;
	}

	public Long getDiscType() {
		return discType;
	}

	public void setDiscType(final Long discType) {
		this.discType = discType;
	}

	public String getDiscDate() {
		return discDate;
	}

	public void setDiscDate(final String discDate) {
		this.discDate = discDate;
	}

	public String getDiscRemarks() {
		return discRemarks;
	}

	public void setDiscRemarks(final String discRemarks) {
		this.discRemarks = discRemarks;
	}

	public String getTarrifCategory() {
		return tarrifCategory;
	}

	public void setTarrifCategory(final String tarrifCategory) {
		this.tarrifCategory = tarrifCategory;
	}

	public String getPremiseType() {
		return premiseType;
	}

	public void setPremiseType(final String premiseType) {
		this.premiseType = premiseType;
	}

	public String getDiscMethod() {
		return discMethod;
	}

	public void setDiscMethod(final String discMethod) {
		this.discMethod = discMethod;
	}

	public String getDisconnectionType() {
		return disconnectionType;
	}

	public void setDisconnectionType(final String disconnectionType) {
		this.disconnectionType = disconnectionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public int getUploadedDocSize() {
		return uploadedDocSize;
	}

	public void setUploadedDocSize(final int uploadedDocSize) {
		this.uploadedDocSize = uploadedDocSize;
	}

	public Date getDiscAppDate() {
		return discAppDate;
	}

	public void setDiscAppDate(final Date discAppDate) {
		this.discAppDate = discAppDate;
	}

	public Long getPlumberId() {
		return plumberId;
	}

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
