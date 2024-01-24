
package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class WaterDeconnectionRequestDTO extends RequestDTO implements Serializable {

    private static final long serialVersionUID = -329720355687725641L;

    private Long userId;
    private Long orgId;
    private Long serviceId;
    private String connectionNo;
    private boolean freeService;
    private List<DocumentDetailsVO> uploadDocument;

    private TBWaterDisconnectionDTO disconnectionDto;
    public TBWaterDisconnectionDTO getDisconnectionDto() {
        return disconnectionDto;
    }

    public void setDisconnectionDto(TBWaterDisconnectionDTO disconnectionDto) {
        this.disconnectionDto = disconnectionDto;
    }

    private ApplicantDetailDTO applicantDetailsDto;
    private CustomerInfoDTO connectionInfo;
    private String consumerName;

    public String getConsumerName() {
        return consumerName;
    }

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

    /**
     * @return the uploadDocument
     */
    public List<DocumentDetailsVO> getUploadDocument() {
        return uploadDocument;
    }

    /**
     * @param uploadDocument the uploadDocument to set
     */
    public void setUploadDocument(final List<DocumentDetailsVO> uploadDocument) {
        this.uploadDocument = uploadDocument;
    }

    /**
     * @return the freeService
     */
    public boolean isFreeService() {
        return freeService;
    }

    /**
     * @param freeService the freeService to set
     */
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
