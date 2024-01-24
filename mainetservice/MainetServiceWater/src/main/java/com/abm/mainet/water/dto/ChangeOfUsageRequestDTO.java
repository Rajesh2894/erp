package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class ChangeOfUsageRequestDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private ApplicantDetailDTO applicant;
	private String connectionNo;
	private Long connectionSize;
	private String consumerName;
	private ChangeOfUsageDTO changeOfUsage;
	private List<String> fileList;

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public ApplicantDetailDTO getApplicant() {
		return applicant;
	}

	public void setApplicant(final ApplicantDetailDTO applicant) {
		this.applicant = applicant;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public ChangeOfUsageDTO getChangeOfUsage() {
		if (null == changeOfUsage) {
			changeOfUsage = new ChangeOfUsageDTO();
		}
		return changeOfUsage;
	}

	public void setChangeOfUsage(final ChangeOfUsageDTO changeOfUsage) {
		this.changeOfUsage = changeOfUsage;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(final List<String> fileList) {
		this.fileList = fileList;
	}

	public Long getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(Long connectionSize) {
		this.connectionSize = connectionSize;
	}

}
