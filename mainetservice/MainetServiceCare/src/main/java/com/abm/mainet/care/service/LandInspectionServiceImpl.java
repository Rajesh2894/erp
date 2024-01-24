package com.abm.mainet.care.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.care.domain.DemarcationDetails;
import com.abm.mainet.care.domain.EncroachmentDetails;
import com.abm.mainet.care.domain.LandCaseDetails;
import com.abm.mainet.care.domain.LandInspection;
import com.abm.mainet.care.dto.DemarcationDetailsDto;
import com.abm.mainet.care.dto.EncroachmentDetailsDto;
import com.abm.mainet.care.dto.LandCaseDetailsDto;
import com.abm.mainet.care.dto.LandInspectionDto;
import com.abm.mainet.care.repository.LandInspectionRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class LandInspectionServiceImpl implements ILandInspectionService {

    @Autowired
    LandInspectionRepository inspectionRepository;

    @Autowired
    IFileUploadService fileUploadService;

    @Autowired
    private ICareWorkflowService careWorkflowService;

    @Override
    @Transactional
    public String saveLandInspectionEntryAndInitiate(LandInspectionDto inspectionDto, List<DocumentDetailsVO> documentList,
            RequestDTO requestDTO, WorkflowTaskAction workflowAction) {
        LandInspection inspection = new LandInspection();
        BeanUtils.copyProperties(inspectionDto, inspection);
        List<DemarcationDetails> demarcations = new ArrayList<DemarcationDetails>();
        // DEMARCATION
        if (inspectionDto.getHadbandi().equals("Y") && !inspectionDto.getDemarcationsDtos().isEmpty()) {
            inspectionDto.getDemarcationsDtos().forEach(demarcationDto -> {
                DemarcationDetails demarcation = new DemarcationDetails();
                BeanUtils.copyProperties(demarcationDto, demarcation);
                demarcation.setCreatedBy(inspectionDto.getCreatedBy());
                demarcation.setLgIpMac(inspectionDto.getLgIpMac());
                demarcation.setOrgId(inspectionDto.getOrgId());
                demarcation.setCreatedDate(inspectionDto.getCreatedDate());
                demarcation.setFlagStatus("Y");
                demarcation.setLandInspection(inspection);
                demarcations.add(demarcation);
            });
        }

        List<EncroachmentDetails> encroachments = new ArrayList<EncroachmentDetails>();
        if (inspectionDto.getEncrSingleSelect().equals("Y")) {
            inspectionDto.getEncroachmentsDtos().forEach(encroachmentDto -> {
                EncroachmentDetails encroachment = new EncroachmentDetails();
                BeanUtils.copyProperties(encroachmentDto, encroachment);
                // doing this because from front end it coming like empty if more than one row in multiple ENCROACHMENT
                if (StringUtils.isEmpty(encroachmentDto.getEncrType())) {
                    encroachment.setEncrType("M");
                }
                encroachment.setCreatedBy(inspectionDto.getCreatedBy());
                encroachment.setLgIpMac(inspectionDto.getLgIpMac());
                encroachment.setOrgId(inspectionDto.getOrgId());
                encroachment.setCreatedDate(inspectionDto.getCreatedDate());
                encroachment.setFlagStatus("Y");
                encroachment.setLandInspection(inspection);
                encroachments.add(encroachment);
            });
        }

        List<LandCaseDetails> landCases = new ArrayList<LandCaseDetails>();
        if (inspectionDto.getCasePendancySelect().equals("Y")) {
            if (!inspectionDto.getLandCasesDtos().isEmpty()) {
                inspectionDto.getLandCasesDtos().forEach(landCaseDto -> {
                    LandCaseDetails lnCase = new LandCaseDetails();
                    BeanUtils.copyProperties(landCaseDto, lnCase);
                    lnCase.setCreatedBy(inspectionDto.getCreatedBy());
                    lnCase.setLgIpMac(inspectionDto.getLgIpMac());
                    lnCase.setOrgId(inspectionDto.getOrgId());
                    lnCase.setCreatedDate(inspectionDto.getCreatedDate());
                    lnCase.setLandInspection(inspection);
                    landCases.add(lnCase);
                });
            }
        }

        inspection.setDemarcations(demarcations);
        inspection.setEncroachments(encroachments);
        inspection.setLandCases(landCases);

        inspectionRepository.save(inspection);
        // 1st save in
        requestDTO.setIdfId("LIE" + MainetConstants.WINDOWS_SLASH + inspection.getOrgId() + MainetConstants.WINDOWS_SLASH
                + inspection.getLnInspId());

        TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDeptByCode(inspection.getOrgId(), MainetConstants.FlagA,
                        MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        if ((documentList != null) && !documentList.isEmpty()) {
            requestDTO.setDeptId(deptObj.getDpDeptid());
            fileUploadService.doMasterFileUpload(documentList, requestDTO);
        }

        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("LIE", inspection.getOrgId());

        // initiate workflow code here
        // Code related to work flow
        WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                .resolveServiceWorkflowType(inspection.getOrgId(), deptObj.getDpDeptid(),
                        sm.getSmServiceId(), null, null, null, null, null);

        // create reference Id
        String referenceId = deptObj.getDpDeptcode() + "/" + sm.getSmShortdesc() + "/"
                + String.format("%05d", inspection.getLnInspId());
        workflowAction.setReferenceId(referenceId);

        WorkflowTaskActionResponse response = careWorkflowService.initiateAndUpdateWorkFlowMC(workflowAction,
                workFlowMas.getWfId(), "LandInspection.html", MainetConstants.FlagA, sm.getSmShortdesc());

        if (response != null) {
            return referenceId;
        } else {
            return null;
        }

    }

    @Override
    public LandInspectionDto getLandInspectionData(Long lnInspId) {
        LandInspection landInspection = inspectionRepository.findOne(lnInspId);

        LandInspectionDto landInspectionDto = new LandInspectionDto();
        BeanUtils.copyProperties(landInspection, landInspectionDto);

        List<DemarcationDetailsDto> demarcationDtos = new ArrayList<DemarcationDetailsDto>();
        if (!landInspection.getDemarcations().isEmpty()) {
            landInspection.getDemarcations().forEach(demarcation -> {

                DemarcationDetailsDto demarcationDto = new DemarcationDetailsDto();
                BeanUtils.copyProperties(demarcation, demarcationDto);
                // check duplicate OBJ
                if (!demarcationDtos.isEmpty()) {
                    if (!demarcationDtos.stream().anyMatch(o -> o.getDemarcationId().equals(demarcation.getDemarcationId()))) {
                        demarcationDtos.add(demarcationDto);
                    }
                } else {
                    demarcationDtos.add(demarcationDto);
                }
            });
        }

        landInspectionDto.setDemarcationsDtos(demarcationDtos);

        // code for ENCROACHMENT
        List<EncroachmentDetailsDto> encroachmentsDtos = new ArrayList<>();
        List<EncroachmentDetailsDto> multiEncroachmentsDtos = new ArrayList<>();
        landInspectionDto.setEncrSingleSelect("N");
        landInspectionDto.setEncrMultipleSelect("N");
        if (!landInspection.getEncroachments().isEmpty()) {
            landInspection.getEncroachments().forEach(encroachment -> {
                EncroachmentDetailsDto encroachDto = new EncroachmentDetailsDto();
                BeanUtils.copyProperties(encroachment, encroachDto);
                if (encroachment.getEncrType().equals("S")) {
                    if (!encroachmentsDtos.isEmpty()) {
                        if (!encroachmentsDtos.stream()
                                .anyMatch(o -> o.getEncroachmentId().equals(encroachment.getEncroachmentId()))) {
                            encroachmentsDtos.add(encroachDto);
                        }
                    } else {
                        encroachmentsDtos.add(encroachDto);
                    }
                    landInspectionDto.setEncrSingleSelect("Y");
                } else {
                    if (!multiEncroachmentsDtos.isEmpty()) {
                        if (!multiEncroachmentsDtos.stream()
                                .anyMatch(o -> o.getEncroachmentId().equals(encroachment.getEncroachmentId()))) {
                            multiEncroachmentsDtos.add(encroachDto);
                        }
                    } else {
                        multiEncroachmentsDtos.add(encroachDto);
                    }
                    landInspectionDto.setEncrMultipleSelect("Y");
                }
            });
        }

        landInspectionDto.setEncroachmentsDtos(encroachmentsDtos);
        landInspectionDto.setMultiEncroachmentsDtos(multiEncroachmentsDtos);

        List<LandCaseDetailsDto> landCaseDetailsDtos = new ArrayList<LandCaseDetailsDto>();
        landInspectionDto.setCasePendancySelect("N");
        if (!landInspection.getLandCases().isEmpty()) {
            landInspectionDto.setCasePendancySelect("Y");
            landInspection.getLandCases().forEach(landCase -> {
                LandCaseDetailsDto landCaseDetailsDto = new LandCaseDetailsDto();
                BeanUtils.copyProperties(landCase, landCaseDetailsDto);
                if (!landCaseDetailsDtos.isEmpty()) {
                    if (!landCaseDetailsDtos.stream().anyMatch(o -> o.getCaseId().equals(landCase.getCaseId()))) {
                        landCaseDetailsDtos.add(landCaseDetailsDto);
                    }
                } else {
                    landCaseDetailsDtos.add(landCaseDetailsDto);
                }
            });
        }

        landInspectionDto.setLandCasesDtos(landCaseDetailsDtos);
        return landInspectionDto;
    }

}
