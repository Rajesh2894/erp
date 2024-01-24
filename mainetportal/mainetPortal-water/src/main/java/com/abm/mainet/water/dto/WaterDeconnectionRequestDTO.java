package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

public class WaterDeconnectionRequestDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = -329720355687725641L;

	private Long userId;
	private Long orgId;
	private Long serviceId;
	private String connectionNo;
	private boolean freeService;
	private List<DocumentDetailsVO> uploadDocument;

	private TBWaterDisconnectionDTO disconnectionDto;
	/**
	 * @return the disconnectionDto
	 */
	public TBWaterDisconnectionDTO getDisconnectionDto() {
		return disconnectionDto;
	}

	/**
	 * @param disconnectionDto the disconnectionDto to set
	 */
	public void setDisconnectionDto(TBWaterDisconnectionDTO disconnectionDto) {
		this.disconnectionDto = disconnectionDto;
	}

	private ApplicantDetailDTO applicantDetailsDto;
	private CustomerInfoDTO connectionInfo;
    private String consumerName;


	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @param consumerName the consumerName to set
	 */
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	@Override
	public Long getOrgId() {
		return orgId;
	}

	@Override
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}


	public ApplicantDetailDTO getApplicantDetailsDto() {
		return applicantDetailsDto;
	}

	public void setApplicantDetailsDto(final ApplicantDetailDTO applicantDetailsDto) {
		this.applicantDetailsDto = applicantDetailsDto;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<DocumentDetailsVO> getUploadDocument() {

		return uploadDocument;
	}

	public void setUploadDocument(final List<DocumentDetailsVO> uploadDocument) {
		this.uploadDocument = uploadDocument;
	}

	public boolean isFreeService() {
		return freeService;
	}

	public void setFreeService(final boolean freeService) {
		this.freeService = freeService;
	}

	public CustomerInfoDTO getConnectionInfo() {
		return connectionInfo;
	}

	public void setConnectionInfo(final CustomerInfoDTO connectionInfo) {
		this.connectionInfo = connectionInfo;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

}
