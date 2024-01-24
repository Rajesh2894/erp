package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public class CollectorReqDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = -6379169204654325039L;
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private WasteCollectorDTO collectorDTO = new WasteCollectorDTO();
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	private Long deptId;

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public WasteCollectorDTO getCollectorDTO() {
		return collectorDTO;
	}

	public void setCollectorDTO(WasteCollectorDTO collectorDTO) {
		this.collectorDTO = collectorDTO;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "BookingReqDTO [applicantDetailDto=" + applicantDetailDto + ", collectorDTO=" + collectorDTO
				+ ", documentList=" + documentList + ", deptId=" + deptId + "]";
	}

}
