package com.abm.mainet.care.rest.ui.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.care.domain.CareFeedback;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.dto.LandInspectionReqDTO;
import com.abm.mainet.care.service.ICareFeedbackService;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.service.ILandInspectionService;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * @author jasvinder singh bhomra
 * @author Sanket Joshi <sanket.joshi@ambindia.com>
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/newGrievance")
public class GrievanceServiceRestController {

    private static final Logger log = LoggerFactory.getLogger(GrievanceServiceRestController.class);

    @Autowired
    private ICareRequestService careRequestService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IComplaintTypeService iComplaintService;

    @Autowired
    private ICareFeedbackService careFeedbackService;

    @Autowired
    private ApplicationService applicationService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    private ILandInspectionService inspectionService;
    
    @Resource
    private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
    
    @Autowired
    private ITaskService taskService;

    @Autowired
    private ApplicationSession applicationSession;
    
    

    @RequestMapping(value = "/saveGrievance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveGrievances(@RequestBody final GrievanceReqDTO grievanceReqDTO, final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log.info("REST request to save grievance");
        RequestDTO applicantDetailDto = grievanceReqDTO.getApplicantDetailDto();
        CareRequestDTO crdto = grievanceReqDTO.getCareRequest();
        WorkflowTaskAction startAction = grievanceReqDTO.getAction();

        // D#111261check here SKDCL ENV
        WorkflowMas workflowType = null;
        try {
        	//D#136931
            if (crdto.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                workflowType = workflowTyepResolverService.resolveServiceWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), null, null, null, null);
            } else {
                workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), crdto.getWard2(),
                        crdto.getWard3(), crdto.getWard4(), crdto.getWard5());
            }
            
            
        } catch (FrameworkException e) {

        }

        if (workflowType == null) {
            // D#123953
            ActionResponse errorRes = new ActionResponse();
            errorRes.setResponse(MainetConstants.WebServiceStatus.FAIL);
            errorRes.setError(ApplicationSession.getInstance().getMessage("care.validator.worklfow.notfound.location"));
            return new ResponseEntity<>(errorRes, HttpStatus.OK);
        }

        applicantDetailDto.setServiceId(workflowType.getService().getSmServiceId());
        Long applicationNuber = applicationService.createApplication(applicantDetailDto);
        applicantDetailDto.setApplicationId(applicationNuber);
        applicantDetailDto.setReferenceId("SUBMITED");
        fileUploadService.doFileUpload(grievanceReqDTO.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationNuber,
                applicantDetailDto.getOrgId());
        startAction.setAttachementId(attachmentId);

        crdto.setApplicationId(applicationNuber);
        crdto.setCreatedDate(new Date());
        crdto.setDateOfRequest(new Date());
        crdto.setRequestType(MainetConstants.BLANK);
        // D#38733
        crdto.setReferenceDate(grievanceReqDTO.getCareRequest().getReferenceDate());
        CareRequest careRequest = CareUtility.toCareRequest(crdto);

        careRequest.setDepartmentComplaint(crdto.getDepartmentComplaint());
        careRequest.setComplaintType(crdto.getComplaintType());
        careRequest.setLocation(iLocationMasService.findbyLocationId(crdto.getLocation()));

        ActionResponse actResponse = careRequestService.startCareProces(careRequest, startAction, null);
        log.info("Grievance saved successfully with application number | " + applicationNuber);
        return actResponse;
    }

    @RequestMapping(value = "/reopenGrievance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object reoprnGrievance(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request to reopen grievance");
        Long applicationId = grievanceReqDTO.getAction().getApplicationId();
        CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
        WorkflowTaskAction reopenAction = grievanceReqDTO.getAction();

        applicantDetailDto.setOrgId(reopenAction.getOrgId());
        fileUploadService.doFileUpload(grievanceReqDTO.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId,
                applicantDetailDto.getOrgId());

        reopenAction.setAttachementId(attachmentId);
        reopenAction.setCreatedDate(new Date());
        reopenAction.setDateOfAction(new Date());
        reopenAction.setDecision(MainetConstants.WorkFlow.Decision.REOPENED);
        ActionResponse actResponse = careRequestService.restartCareProces(careRequest, reopenAction);
        if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(actResponse.getResponse())) {
            log.info("Grievance reopened successfully with application number | " + applicationId);
        }
        return actResponse;
    }

    @RequestMapping(value = "/resubmitGrievance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object resubmitGrievance(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request to resubmit grievance");
        Long applicationId = grievanceReqDTO.getAction().getApplicationId();
        CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
        WorkflowTaskAction resubmitAction = grievanceReqDTO.getAction();

        applicantDetailDto.setOrgId(resubmitAction.getOrgId());
        fileUploadService.doFileUpload(grievanceReqDTO.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId,
                applicantDetailDto.getOrgId());

        resubmitAction.setAttachementId(attachmentId);
        resubmitAction.setCreatedDate(new Date());
        resubmitAction.setDateOfAction(new Date());
        resubmitAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
        ActionResponse actResponse = careRequestService.resubmitCareProces(careRequest, resubmitAction);
        log.info("Grievance resubmited successfully with application number | " + applicationId);
        return actResponse;
    }

    @RequestMapping(value = "/updateGrievance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ActionResponse approveRequests(@RequestBody final GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult)
            throws EntityNotFoundException, Exception {
        log.info("REST request to update grievance");
        Long applicationId = grievanceReqDTO.getAction().getApplicationId();
        CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
        WorkflowTaskAction departmentAction = grievanceReqDTO.getAction();

        applicantDetailDto.setOrgId(departmentAction.getOrgId());
        fileUploadService.doFileUpload(grievanceReqDTO.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId,
                applicantDetailDto.getOrgId());

        departmentAction.setAttachementId(attachmentId);
        departmentAction.setCreatedDate(new Date());
        departmentAction.setDateOfAction(new Date());
        departmentAction.setOrgId(careRequest.getOrgId());
        ActionResponse actResponse = careRequestService.updateCareProces(careRequest, departmentAction);
        log.info("Grievance updated successfully with application number | " + applicationId);
        return actResponse;
    }

    @RequestMapping(value = "/saveFeedback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveFeedback(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO requestDTOFromPortla,
            final HttpServletRequest request, final BindingResult bindingResult)
            throws EntityNotFoundException, Exception {

        ActionResponse actResponse = new ActionResponse();
        CareFeedbackDTO cfd = requestDTOFromPortla.getCareFeedback();
        CareFeedback cf = CareUtility.toCareFeedback(cfd);
        if (cf != null) {
            careFeedbackService.saveCareFeedbak(cf);
            actResponse.addResponseData(MainetConstants.RESPONSE, MainetConstants.ALERT_SAVE_SUCCESS);
        }
        return actResponse;
    }

    @RequestMapping(value = "/fetchDepartmentsByOrgId/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<DepartmentComplaint> fetchDepartmentsByOrgId(@PathVariable("id") Long id, HttpServletRequest request) {
        List<DepartmentComplaint> deptList = iComplaintService.getcomplainedDepartment(id);
        return deptList;
    }

    @RequestMapping(value = "/fetchLocationsById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public TbLocationMas fetchLocationsById(@PathVariable("id") Long id, HttpServletRequest request) {
        TbLocationMas locationMasEntity = iLocationMasService.findById(id);
        return locationMasEntity;
    }

    @RequestMapping(value = "/fetchLocationsByOrgId/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<TbLocationMas> fetchLocationsByOrgId(@PathVariable("orgId") Long orgId, HttpServletRequest request) {
        List<TbLocationMas> locations = iLocationMasService.fillAllActiveLocationByOrgId(orgId);
        return locations;
    }

    @RequestMapping(value = "/getLocationByPinCode/{pincode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<TbLocationMas> fetchLocationsByPinCodeId(@PathVariable("pincode") Integer pincode,
            HttpServletRequest request) {
        List<TbLocationMas> locations = iLocationMasService.getlocationByPinCode(Long.valueOf(pincode));
        return locations;
    }

    @RequestMapping(value = "/careRequests/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO getCareRequestById(@PathVariable("id") Long id, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        return careRequestService.findById(id);
    }

    @RequestMapping(value = "/careRequests/applicationId/{applicationId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO getCareRequestByapplicationId(@PathVariable("applicationId") Long applicationId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        return CareUtility.toCareRequestDTO(careRequestService.findByApplicationId(applicationId));
    }

    @RequestMapping(value = "/careRequests/complaintId/{complaintId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO getCareRequestByComplaintId(@PathVariable("complaintId") String complaintId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        CareRequestDTO careRequestDto = CareUtility.toCareRequestDTO(careRequestService.findByComplaintId(complaintId));
        if (careRequestDto != null) {
        	// code for External WorkFlow Flag
        	ComplaintType complaintType = iComplaintService.findComplaintTypeById(careRequestDto.getComplaintType());
        	 log.info("External WorkFlow Flag------------------------------------------------------> " + complaintType.getExternalWorkFlowFlag());
        	careRequestDto.setExternalWorkFlowFlag(complaintType.getExternalWorkFlowFlag());
            // code for attach document
            List<CFCAttachment> att = iChecklistVerificationService.findAttachmentsForAppId(careRequestDto.getApplicationId(),
                    null,
                    careRequestDto.getOrgId());
            List<DocumentDetailsVO> docList = new ArrayList<DocumentDetailsVO>();
            att.forEach(at -> {
                if (at.getReferenceId() != null && at.getReferenceId().equalsIgnoreCase("SUBMITED")) {
                    DocumentDetailsVO dvo = new DocumentDetailsVO();
                    dvo.setAttachmentId(at.getAttId());
                    dvo.setDocumentSerialNo(at.getClmSrNo());
                    dvo.setDocumentName(at.getAttFname());
                    dvo.setUploadedDocumentPath(at.getAttPath());
                    dvo.setDocumentByteCode(CareUtility.convertInByteCode(at.getAttFname(), at.getAttPath()));
                    docList.add(dvo);
                }
            });
            careRequestDto.setAttachments(docList);
        }
        return careRequestDto;
    }

    @RequestMapping(value = "/careRequests/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<CareRequestDTO> searchCareRequest(@RequestBody ComplaintSearchDTO filter, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        List<CareRequestDTO> careRequests = careRequestService.findComplaint(filter);
        return careRequests;
    }

    @RequestMapping(value = "/careRequests/acknowledgement/applicationId/{applicationId}/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ComplaintAcknowledgementModel getCareRequestComplaintAcknowledgementByapplicationId(
            @PathVariable("applicationId") Long applicationId, @PathVariable("langId") int langId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        langId = (langId == 0) ? MainetConstants.ENGLISH : langId;
        CareRequest cr = careRequestService.findByApplicationId(applicationId);
        return careRequestService.getComplaintAcknowledgementModel(cr, langId);
    }

    @RequestMapping(value = "/careRequests/acknowledgement/complaintId/{complaintId}/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ComplaintAcknowledgementModel getCareRequestComplaintAcknowledgementByComplaintId(
            @PathVariable("complaintId") String complaintId, @PathVariable("langId") int langId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        langId = (langId == 0) ? MainetConstants.ENGLISH : langId;
        CareRequest cr = careRequestService.findByComplaintId(complaintId);
        return careRequestService.getComplaintAcknowledgementModel(cr, langId);
    }

    @RequestMapping(value = "/careRequests/empId/emplType/{empId}/{emplType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<CareRequestDTO> getCareRequestsByEmpIdAndEmplType(@PathVariable("empId") Long empId,
            @PathVariable("emplType") Long emplType, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        List<CareRequestDTO> crs = careRequestService.getCareRequestsByEmpIdAndEmplType(empId, emplType);
        return crs;
    }

    @RequestMapping(value = "/findDepartmentComplaintByDepartmentId/{id}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public DepartmentComplaint findDepartmentComplaintByDepartmentId(@PathVariable("id") Long id,
            @PathVariable("orgId") Long orgId, HttpServletRequest request) {
        DepartmentComplaint departmentComplaint = iComplaintService.findComplainedDepartmentByDeptId(id, orgId);
        return departmentComplaint;
    }

    @RequestMapping(value = "/findComplaintTypeById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ComplaintType findComplaintTypeById(@PathVariable("id") Long id, HttpServletRequest request) {
        ComplaintType complaintType = iComplaintService.findComplaintTypeById(id);
        return complaintType;
    }

    @RequestMapping(value = "/getApplicationFeedback/{tokenNumber}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareFeedbackDTO getApplicationFeedback(@PathVariable("tokenNumber") String tokenNumber,
            HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException, Exception {
        CareFeedbackDTO feedbackDto = new CareFeedbackDTO();
        CareFeedback feedback = careFeedbackService.getFeedbackByApplicationId(tokenNumber);
        if (feedback != null) {
            BeanUtils.copyProperties(feedbackDto, feedback);
        }
        return feedbackDto;
    }

    @RequestMapping(value = "/careWorkflowActions/log/{applicationId}/{orgId}/lang/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<WorkflowTaskActionWithDocs> getCareWorkflowActionLogByApplicationId(
            @PathVariable("applicationId") Long applicationId, @PathVariable("orgId") Long orgId,
            @PathVariable("langId") long langId, HttpServletRequest request) {
        return careRequestService.getCareWorkflowActionLogByApplicationId(applicationId, orgId, langId);
    }

    /**
     * POST /organisationsByDistrictId/:districtId : To retrieve organization by district
     * 
     * @param districtCpdId
     * @param request
     * @return
     */
    @RequestMapping(value = "/organisationsByDistrictId/{districtId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<OrganisationDTO> getOrganisationsByDistrict(
            @PathVariable(MainetConstants.Common_Constant.DISTRICT_ID) Long districtCpdId, HttpServletRequest request) {
        List<OrganisationDTO> organisations = careRequestService.getOrganisationByDistrict(districtCpdId);
        return organisations;

    }

    /**
     * POST /departmentComplaintsByOrgId/:orgId : To retrieve DepartmentComplaint by organization
     * 
     * @param orgId
     * @param request
     * @return
     */
    @RequestMapping(value = "/departmentComplaintsByOrgId/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<DepartmentComplaintDTO> getDepartmentComplaintsByOrgId(@PathVariable("orgId") Long orgId,
            HttpServletRequest request) {
        Set<DepartmentComplaintDTO> careWorkflowDefinedDeptList = careRequestService
                .getDepartmentComplaintsByOrgId(orgId);
        return careWorkflowDefinedDeptList;

    }

    /**
     * POST /fetchLocationsByOrgIdAndDeptId/:orgId/:deptId : To retrieve locations by department and organization
     * 
     * @param orgId
     * @param deptId
     * @param request
     * @return
     */
    @RequestMapping(value = "/fetchLocationsByOrgIdAndDeptId/{orgId}/{deptId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<LocationDTO> fetchLocationsByOrgIdAndDeptId(@PathVariable("orgId") Long orgId,
            @PathVariable("deptId") Long deptId, HttpServletRequest request) {
        List<LocationMasEntity> locationList = careRequestService.getDepartmentCompalintLocations(orgId, deptId);
        Set<LocationDTO> locations = new HashSet<>();
        locationList.forEach(loc -> {
            locations.add(CareUtility.toLocationMasEntityDTO(loc, 0));
        });
        return locations;
    }

    /**
     * POST /departmentComplaintTypeByDepartmentId/:deptId/:orgId : To retrieve ComplaintType by department and organization
     * 
     * @param deptId
     * @param orgId
     * @param request
     * @return
     */
    @RequestMapping(value = "/departmentComplaintTypeByDepartmentId/{deptId}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<DepartmentComplaintTypeDTO> getDepartmentComplaintTypeByDepartmentId(@PathVariable("deptId") Long deptId,
            @PathVariable("orgId") Long orgId, HttpServletRequest request) {
        Set<DepartmentComplaintTypeDTO> complaintTypes = careRequestService
                .getDepartmentComplaintTypeByDepartmentId(deptId, orgId);
        return complaintTypes;
    }

    @RequestMapping(value = "/getGrievanceDetails/applicationId/{applicationId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public GrievanceReqDTO getGrievanceDetails(@PathVariable("applicationId") Long applicationId,
            final HttpServletRequest request) {

        GrievanceReqDTO grievanceReqDTO = new GrievanceReqDTO();
        CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
        grievanceReqDTO.setApplicantDetailDto(applicantDetailDto);
        grievanceReqDTO.setCareRequest(CareUtility.toCareRequestDTO(careRequest));
        List<CFCAttachment> att = iChecklistVerificationService.findAttachmentsForAppId(applicationId, null,
                careRequest.getOrgId());
        List<DocumentDetailsVO> docList = new ArrayList<DocumentDetailsVO>();
        att.forEach(at -> {
            if (at.getReferenceId() != null && at.getReferenceId().equalsIgnoreCase("SUBMITED")) {
                DocumentDetailsVO dvo = new DocumentDetailsVO();
                dvo.setAttachmentId(at.getAttId());
                dvo.setCheckkMANDATORY(at.getMandatory());
                dvo.setDocumentSerialNo(at.getClmSrNo());
                dvo.setDocumentName(at.getAttFname());
                dvo.setUploadedDocumentPath(at.getAttPath());
                dvo.setDocumentByteCode(CareUtility.convertInByteCode(at.getAttFname(), at.getAttPath()));
                docList.add(dvo);
            }
        });
        grievanceReqDTO.setAttachments(docList);
        return grievanceReqDTO;
    }

    // D#110049
    @RequestMapping(value = "/getGrievanceOpWardZone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO getGrievanceOpWardZone(@RequestBody CareRequestDTO careDTO, HttpServletRequest request) {
        // get DEPT id of CFC
        /*
         * Department dept = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
         * .findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER); LocOperationWZMappingDto locOperationWZMappingDto =
         * iLocationMasService.findOperLocationAndDeptId(careDTO.getLocation(), dept.getDpDeptid()); // set in careRequest OBJ of
         * ward if (locOperationWZMappingDto != null) { careDTO.setWard1(locOperationWZMappingDto.getCodIdOperLevel1()); if
         * (locOperationWZMappingDto.getCodIdOperLevel2() != null)
         * careDTO.setWard2(locOperationWZMappingDto.getCodIdOperLevel2()); if (locOperationWZMappingDto.getCodIdOperLevel3() !=
         * null) careDTO.setWard3(locOperationWZMappingDto.getCodIdOperLevel3()); if
         * (locOperationWZMappingDto.getCodIdOperLevel4() != null)
         * careDTO.setWard4(locOperationWZMappingDto.getCodIdOperLevel4()); if (locOperationWZMappingDto.getCodIdOperLevel5() !=
         * null) careDTO.setWard5(locOperationWZMappingDto.getCodIdOperLevel5()); } else { log.info("CWZ prefix not found ");
         * careDTO.setWard1(null); careDTO.setWard2(null); careDTO.setWard3(null); careDTO.setWard4(null); careDTO.setWard5(null);
         * }
         */

        String prefixName = tbDepartmentService.findDepartmentPrefixName(careDTO.getDepartmentComplaint(), careDTO.getOrgId());

        if (prefixName != null && !prefixName.isEmpty()) {
            // D#130416 check at least one level define or not in prefix
            // if not than it throws exception which is handle using try catch and inside catch pass prefix CWZ
            try {
                Organisation org = new Organisation();
                org.setOrgid(careDTO.getOrgId());
                CommonMasterUtility.getLevelData(prefixName, 1, org);
                careDTO.setPrefixName(prefixName);
            } catch (FrameworkException e) {
                careDTO.setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
            }

        } else {
            careDTO.setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
            return careDTO;
        }

        return careDTO;
    }

    @RequestMapping(value = "/checkOutstandingDues", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String checkOutstandingDues(@RequestBody CareRequestDTO careDTO, HttpServletRequest request) {
        String message = "";
        // by using deptId and reference no search for dues
        // get DEPT code using deptId
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentShortCodeByDeptId(careDTO.getDepartmentComplaint(), careDTO.getOrgId());
        if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
            // rest call to property
            PropertyDetailDto detailDTO = null;
            PropertyInputDto propInputDTO = new PropertyInputDto();
            propInputDTO.setPropertyNo(careDTO.getExtReferNumber());
            propInputDTO.setOrgId(careDTO.getOrgId());
            propInputDTO.setFlatNo(careDTO.getPropFlatNo());
            final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                    ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                if (detailDTO.getStatus().equalsIgnoreCase("Fail")) {
                    log.info("invalid property no" + detailDTO);
                } else {
                    message = detailDTO.getTotalOutsatandingAmt() > 0 ? "Outstanding Amount is exist, So Complaint request deny"
                            : "";
                }
            }
        } else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {

            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("CsCcn", careDTO.getExtReferNumber());
            requestParam.put(MainetConstants.Common_Constant.ORGID, careDTO.getOrgId().toString());
            TbBillMas tbBillMas = null;
            ResponseEntity<?> responseEntity = null;
            DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
            dd.setParsePath(true);
            URI uri = dd.expand(ServiceEndpoints.BRMSMappingURL.WATER_BILL_BY_CONN_NO, requestParam);

            try {
                responseEntity = RestClient.callRestTemplateClient(tbBillMas, uri.toString());
                HttpStatus statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    tbBillMas = (TbBillMas) RestClient.castResponse(responseEntity, TbBillMas.class);
                    if (tbBillMas != null) {
                        message = tbBillMas.getBmTotalOutstanding() > 0 ? "Outstanding Amount is exist, So Complaint request deny"
                                : "";
                    }
                }
            } catch (Exception ex) {
                log.info("calling for water dues" + ex);
            }

        }
        return message;
    }

    @RequestMapping(value = "/findOpWardZoneByRefereceNo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO findOpWardZoneByRefereceNo(@RequestBody CareRequestDTO careDTO, HttpServletRequest request) {

        // get DEPT code using deptId
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentShortCodeByDeptId(careDTO.getDepartmentComplaint(), careDTO.getOrgId());
        if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
            // rest call to property
            PropertyDetailDto detailDTO = null;
            PropertyInputDto propInputDTO = new PropertyInputDto();
            propInputDTO.setPropertyNo(careDTO.getExtReferNumber());
            propInputDTO.setOrgId(careDTO.getOrgId());
            propInputDTO.setFlatNo(careDTO.getPropFlatNo());
            final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                    ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                if (detailDTO.getStatus().equalsIgnoreCase("Fail")) {
                    log.info("invalid property no" + detailDTO);
                    careDTO.setWard1(null);
                    careDTO.setWard2(null);
                    careDTO.setWard3(null);
                    careDTO.setWard4(null);
                    careDTO.setWard5(null);
                } else {
                    careDTO.setWard1(detailDTO.getWd1() != null && detailDTO.getWd1() != 0 ? detailDTO.getWd1() : null);
                    careDTO.setWard2(detailDTO.getWd2() != null && detailDTO.getWd2() != 0 ? detailDTO.getWd2() : null);
                    careDTO.setWard3(detailDTO.getWd3() != null && detailDTO.getWd3() != 0 ? detailDTO.getWd3() : null);
                    careDTO.setWard4(detailDTO.getWd4() != null && detailDTO.getWd4() != 0 ? detailDTO.getWd4() : null);
                    careDTO.setWard5(detailDTO.getWd5() != null && detailDTO.getWd5() != 0 ? detailDTO.getWd5() : null);
                    // careDTO.setApaMobNo(detailDTO.getPrimaryOwnerMobNo());
                }
            }
        } else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("CsCcn", careDTO.getExtReferNumber());
            requestParam.put(MainetConstants.Common_Constant.ORGID, careDTO.getOrgId().toString());
            TbBillMas tbBillMas = null;
            ResponseEntity<?> responseEntity = null;
            DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
            dd.setParsePath(true);
            URI uri = dd.expand(ServiceEndpoints.BRMSMappingURL.WATER_BILL_BY_CONN_NO, requestParam);

            try {
                responseEntity = RestClient.callRestTemplateClient(tbBillMas, uri.toString());
                HttpStatus statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    tbBillMas = (TbBillMas) RestClient.castResponse(responseEntity, TbBillMas.class);
                    if (tbBillMas != null) {
                        // water ward zone
                        careDTO.setWard1(tbBillMas.getCodDwzid1());
                        careDTO.setWard2(tbBillMas.getCodDwzid2());
                        careDTO.setWard3(tbBillMas.getCodDwzid3());
                        careDTO.setWard4(tbBillMas.getCodDwzid4());
                        careDTO.setWard5(tbBillMas.getCodDwzid5());
                        // careDTO.setApaMobNo(tbBillMas.getMobileNo());
                    } else {
                        careDTO.setWard1(null);
                        careDTO.setWard2(null);
                        careDTO.setWard3(null);
                        careDTO.setWard4(null);
                        careDTO.setWard5(null);
                    }
                }
            } catch (Exception ex) {
                log.info("calling for water dues " + ex);
            }
        }
        return careDTO;
    }

    @RequestMapping(value = "/saveLandInspection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveLandInspection(@RequestBody final LandInspectionReqDTO landInspectionReqDto,
            final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log.info("REST request to save land inspection system");
        String responseMSG = "FAIL";
        RequestDTO applicantDetailDto = landInspectionReqDto.getApplicantDetailDto();

        WorkflowTaskAction workflowAction = landInspectionReqDto.getAction();

        workflowAction.setPaymentMode(MainetConstants.FlagF);

        // logic for save ENCROACHMENT Data
        if (landInspectionReqDto.getLandInspectionDto().getEncrMultipleSelect().equals("Y")) {
            // add multiEncroachment in encroachmentsDtos
            landInspectionReqDto.getLandInspectionDto().getEncroachmentsDtos()
                    .addAll(landInspectionReqDto.getLandInspectionDto().getMultiEncroachmentsDtos());
        }
        String referenceId = inspectionService.saveLandInspectionEntryAndInitiate(landInspectionReqDto.getLandInspectionDto(),
                java.util.Collections.emptyList(), applicantDetailDto,
                workflowAction);
        if (referenceId != null) {
            responseMSG = ApplicationSession.getInstance().getMessage("ln.inspec.savesuccessmsg",
                    new Object[] { referenceId });
        } else {
            responseMSG = ApplicationSession.getInstance().getMessage("ln.inspec.workflowfailedmsg");
        }

        applicantDetailDto.setReferenceId("SUBMITED");
        fileUploadService.doFileUpload(landInspectionReqDto.getAttachments(), applicantDetailDto);

        log.info("Land inspection saved successfully with application number | " + referenceId);
        return responseMSG;
    }

    @RequestMapping(value = "/saveSwmGrievances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveSwmGrievances(@RequestBody final GrievanceReqDTO grievanceReqDTO, final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log.info("REST request to save SWM grievance");
        CareRequestDTO crdto = grievanceReqDTO.getCareRequest();
        crdto.setApplicationId(crdto.getApplicationId());
        CareRequest careRequest = CareUtility.toCareRequest(crdto);
        careRequest.setDepartmentComplaint(crdto.getDepartmentComplaint());
        careRequest.setComplaintType(crdto.getComplaintType());
        careRequest.setLocation(iLocationMasService.findbyLocationId(crdto.getLocation()));
        TbDepartment dept = tbDepartmentService.findById(careRequest.getDepartmentComplaint());
   	    boolean status=false;
		if (dept.getDpDeptcode()!=null && dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT)) {
			status = careRequestService.callPotHoleAPI(careRequest);
		} else {
		 status = careRequestService.callSWMWorkForceAPI(careRequest);
		}
        log.info("Status------------------------------------------------------> " + status);

		if (status) {
			TbCfcApplicationMstEntity tbCfcApplicationMstEntity = tbCfcApplicationMstJpaRepository
					.findByApmApplicationId(careRequest.getApplicationId()).get();
			tbCfcApplicationMstEntity.setApmMode(MainetConstants.FlagS);
			tbCfcApplicationMstEntity.setUpdatedDate(new Date());
			tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		}
		 
        return status;
    }
    
    @RequestMapping(value = "/validComplForReopen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object validComplForReopen(@RequestBody final CareRequestDTO careRequestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request to isValidComplForReopen ");
        Long applicationId = careRequestDTO.getApplicationId();
        List<UserTaskDTO> list = taskService.getTaskList(applicationId.toString());

        ActionResponse response = null;
        if (list == null || list.isEmpty()) {
            String errorMsg = "Unable to reopen CARE Request due to task not found For  RequestNo: " + applicationId;
            response = new ActionResponse(MainetConstants.COMMON_STATUS.FAILURE.toUpperCase());
            response.addResponseData(MainetConstants.ERROR_MESSAGE, errorMsg);
            return response;
        }

        Date reopenEndDate = null;
        Date currentDate = new Date();
        String reopenDay = null;
        /* D123919 start If reopen count days completed then complaint should not be able to reopen */
        UserTaskDTO task = list.get(0);
        Organisation organisation = new Organisation();
        // CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
        organisation.setOrgid(careRequestDTO.getOrgId());
        Optional<LookUp> lookup = CommonMasterUtility
                .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_EXPIRY_DURATION_DAYS_PREFIX,
                        organisation)
                .stream().findFirst();
        if (lookup.isPresent()) {
            reopenDay = lookup.get().getLookUpCode();
            reopenEndDate = Utility.getAddedDateBy2(task.getCreatedDate(), Integer.parseInt(reopenDay));
        }
        /* Checked if reopend days are completed or not */
        if (currentDate.after(reopenEndDate)) {
            /* setting error code & error msg in eng & reg in response if reopend days are completed */
            response = new ActionResponse(MainetConstants.COMMON_STATUS.FAILURE.toUpperCase());
            response.setError(MainetConstants.ERROR_CODE.CARE_REOPEN_EXPIRED_ERROR);
            String reopenErrMsgEng = applicationSession.getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,
                    MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, new Locale(MainetConstants.REG_ENG.ENGLISH),
                    new Object[] { reopenDay });
            String reopenErrMsgReg = applicationSession.getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,
                    MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, new Locale(MainetConstants.REG_ENG.REGIONAL),
                    new Object[] { reopenDay });
            response.addResponseData(MainetConstants.ERROR_MESSAGE, reopenErrMsgEng);
            response.addResponseData(MainetConstants.ERROR_MESSAGE_REG, reopenErrMsgReg);
            return response;
        }
        response = new ActionResponse(MainetConstants.COMMON_STATUS.SUCCESS.toUpperCase());
        response.addResponseData(MainetConstants.RESPONSE, MainetConstants.COMMON_STATUS.SUCCESS);
        return response;
    }
}

