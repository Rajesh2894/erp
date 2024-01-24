package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author ritesh.patil
 *
 */
public class BookingReqDTO extends RequestDTO implements Serializable {

    private static final long serialVersionUID = -6379169204654325039L;
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    private EstateBookingDTO estateBookingDTO = new EstateBookingDTO();
    private EstatePropResponseDTO estatePropResponseDTO = new EstatePropResponseDTO();
    private List<DocumentDetailsVO> documentList = new ArrayList<>();
    private TankerBookingDetailsDTO tankerBookingDetailsDTO = new TankerBookingDetailsDTO();
    private Long deptId;
    private String serviceName;

    /**
     * @return the applicantDetailDto
     */
    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    /**
     * @param applicantDetailDto the applicantDetailDto to set
     */
    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    /**
     * @return the estateBookingDTO
     */
    public EstateBookingDTO getEstateBookingDTO() {
        return estateBookingDTO;
    }

    /**
     * @param estateBookingDTO the estateBookingDTO to set
     */
    public void setEstateBookingDTO(final EstateBookingDTO estateBookingDTO) {
        this.estateBookingDTO = estateBookingDTO;
    }

    /**
     * @return the estatePropResponseDTO
     */
    public EstatePropResponseDTO getEstatePropResponseDTO() {
        return estatePropResponseDTO;
    }

    /**
     * @param estatePropResponseDTO the estatePropResponseDTO to set
     */
    public void setEstatePropResponseDTO(final EstatePropResponseDTO estatePropResponseDTO) {
        this.estatePropResponseDTO = estatePropResponseDTO;
    }

    /**
     * @return the documentList
     */
    @Override
    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    /**
     * @param documentList the documentList to set
     */
    @Override
    public void setDocumentList(final List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    /**
     * @return the deptId
     */
    @Override
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    @Override
    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

	public TankerBookingDetailsDTO getTankerBookingDetailsDTO() {
		return tankerBookingDetailsDTO;
	}

	public void setTankerBookingDetailsDTO(TankerBookingDetailsDTO tankerBookingDetailsDTO) {
		this.tankerBookingDetailsDTO = tankerBookingDetailsDTO;
	}
        
}
