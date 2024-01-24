package com.abm.mainet.mrm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.abm.mainet.mrm.dto.WifeDTO;

@WebService
public interface IMarriageService {

    MarriageDTO saveMarriageRegInDraftMode(MarriageDTO marriageDTO);

    Long saveMarriage(MarriageDTO marriageDTO);

    Long saveHusband(HusbandDTO husbandDTO);

    Long saveWife(WifeDTO wifeDTO);

    MarriageDTO saveWitnessDetails(MarriageDTO marriageDTO);

    Long saveAppointment(AppointmentDTO appointmentDTO);

    boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId, String actionStatus);

    Map<Long, Double> getLoiCharges(Long orgId, Long serviceId, String serviceCode, Date marriageDate, Date applicationDate);

    List<MarriageDTO> fetchSearchData(MarriageRequest marriageRequest);

    MarriageDTO getMarriageDetailsById(Long marId, Long applicationId, String serialNo, Date marDate, Long orgId, String hitFrom);

    Boolean saveAppointmentAndDecision(WorkflowTaskAction taskAction, RequestDTO requestDto,
            List<DocumentDetailsVO> approvalDocumentAttachment, ServiceMaster serviceMaster, AppointmentDTO appointmentDTO);

    ApplicantDetailDTO getApplicantData(Long applicationId, Long orgId);

    MarriageResponse getMarriageData(MarriageRequest marriageRequest);

    Boolean checkApplicationIdExist(Long applicationId, Long orgId);

    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    boolean generateCertificate(String docPath, Long applicationNo, Long orgId);

}
