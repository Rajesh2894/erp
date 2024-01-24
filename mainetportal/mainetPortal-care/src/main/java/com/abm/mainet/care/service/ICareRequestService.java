package com.abm.mainet.care.service;

import java.util.List;

import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.ActionDTOWithDoc;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.RequestDTO;

public interface ICareRequestService {

    /**
     * REST call to MaintService to get all Complaint raised by citizen by citizen id.
     *
     * @param id Citizen Id.
     * @return
     * @throws Exception
     */
    List<CareRequestDTO> getAllComplaintRaisedByCitizenById(Long empId, Long emplType, String searchString) throws Exception;

    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    CareRequestDTO getCareRequestById(Long id) throws Exception;

    /**
     * 
     * @param requestNo
     * @return
     * @throws Exception
     */
    CareRequestDTO getCareRequestByapplicationId(String applicationId) throws Exception;

    /**
     * 
     * @param requestNo
     * @return
     * @throws Exception
     */
    CareRequestDTO getCareRequestBycomplaintId(String complaintId) throws Exception;

    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    ActionDTO findActionById(Long id) throws Exception;

    /**
     * 
     * @param applicationId
     * @param orgId
     * @return
     */
    List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(Long applicationId, Long orgId, int lang);

    /**
     * 
     * @param careRequest
     * @param userSession
     * @return
     * @throws Exception
     */
    // ComplaintStatusAcknowledgementDTO constructRequestStatusAcknowledgement(CareRequestDTO careRequest, UserSession
    // userSession)
    // throws Exception;

    /**
     * 
     * @param applicationId
     * @return
     * @throws Exception
     */
    ComplaintRegistrationAcknowledgementDTO constructRequestStatusAcknowledgement(Long applicationId, int langId)
            throws Exception;

    List<LocationDTO> getLocationByOrgIdAndDeptId(Long orgId, long deptId, int lang) throws Exception;

    void sendOTPDetails(String mobileNo, String mobileOTP, RequestDTO applicantDetailDTO);

    List<OrganisationDTO> getOrgnizationsByDist(Long dist);

    List<CareRequestDTO> complaintStatusAcknowledgement(Long searchString) throws Exception;

    ComplaintRegistrationAcknowledgementDTO constructRequestStatusAcknowledgement(String complaintId, int langId)
            throws Exception;
    
    GrievanceReqDTO getCareRequestDetails(Long applicationId, Long orgId) throws Exception;
    
    CareRequestDTO getGrievanceOpWardZone(CareRequestDTO careRequestDTO) throws Exception;
    
    List<CareRequestDTO> getComplaintByRefIdAndMobNo(CareRequestDTO reqDto) throws Exception;

	boolean isFlatListEmpty(String refNo);
}