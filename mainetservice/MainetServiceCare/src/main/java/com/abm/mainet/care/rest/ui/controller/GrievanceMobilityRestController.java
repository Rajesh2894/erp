package com.abm.mainet.care.rest.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.persistence.EntityNotFoundException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.care.domain.CareFeedback;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.ComplaintTaskDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.dto.LandInspectionReqDTO;
import com.abm.mainet.care.service.ICareFeedbackService;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.service.ILandInspectionService;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.mapper.TbComparentMasServiceMapper;
import com.abm.mainet.common.master.repository.TbComparentMasJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author jasvinder singh bhomra
 * @author Sanket Joshi <sanket.joshi@ambindia.com>
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mobility/grievance")
@Produces("application/json")
@WebService
@Api(value = "/mobility/grievance")
@Path("/mobility/grievance")

public class GrievanceMobilityRestController {

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
    private IEmployeeService employeeService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    TbServicesMstService tbServicesMstService;

    @Autowired
    private TbComparamMasService tbComparamMasService;
    @Autowired
    private TbComparentMasJpaRepository tbComparentMasJpaRepository;

    @Autowired
    private TbComparentMasServiceMapper tbComparentMasServiceMapper;

    @Autowired
    private ILandInspectionService inspectionService;

    @ResponseStatus(HttpStatus.OK)
    @POST
    @Path("/save")
    @ApiOperation(value = "save mobile grievance", notes = "save mobile grievance", response = CareRequestDTO.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<CareRequestDTO> saveGrievances(@RequestBody final GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request from mobile to save grievance");
        RequestDTO applicantDetailDto = grievanceReqDTO.getApplicantDetailDto();
        CareRequestDTO crdto = grievanceReqDTO.getCareRequest();
        WorkflowTaskAction startAction = grievanceReqDTO.getAction();

        WorkflowMas workflowType = null;
        try {
            if (crdto.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                workflowType = workflowTyepResolverService.resolveServiceWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), null, null, null, null);
            } else {
                // D#128877 start
                /*
                 * workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(crdto.getOrgId(),
                 * crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getLocation());
                 */

                workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), crdto.getWard2(),
                        crdto.getWard3(), crdto.getWard3(), crdto.getWard5());
                // D#128877 end
            }
        } catch (FrameworkException e) {

        }

        if (workflowType == null) {
            // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            CareRequestDTO careErrorReq = new CareRequestDTO();
            careErrorReq.setErrorMsg(ApplicationSession.getInstance().getMessage("care.validator.worklfow.notfound.location"));
            return ResponseEntity.status(HttpStatus.OK).body(careErrorReq);
        }

        applicantDetailDto.setServiceId(workflowType.getService().getSmServiceId());
        Long applicationNuber = applicationService.createApplication(applicantDetailDto);
        applicantDetailDto.setApplicationId(applicationNuber);
        /* D#117094 Setting value to show only uploaded doc by citizen on web portal */
        applicantDetailDto.setReferenceId(MainetConstants.Council.Proposal.SET_DECISION_SUBMITTED);
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
        careRequest.setSmServiceId(crdto.getSmServiceId());

        careRequestService.startCareProces(careRequest, startAction, null);
        log.info("Grievance saved successfully with application number | " + applicationNuber);
        return new ResponseEntity<>(CareUtility.toCareRequestDTO(careRequest), HttpStatus.CREATED);

    }

    @POST
    @Path("/reopen")
    @ApiOperation(value = "reopen mobile grievance", notes = "reopen mobile grievance", response = ActionResponse.class)
    @RequestMapping(value = "/reopen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ActionResponse reoprnGrievance(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request from mobile to reopen grievance");
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
        ActionResponse actionResponse = careRequestService.restartCareProces(careRequest, reopenAction);
        if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(actionResponse.getResponse())) {
            log.info("Grievance reopened successfully with application number | " + applicationId);
        }
        return actionResponse;
    }

    @POST
    @Path("/resubmit")
    @ApiOperation(value = "resubmit mobile grievance", notes = "resubmit mobile grievance", response = ActionResponse.class)
    @RequestMapping(value = "/resubmit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ActionResponse resubmitGrievance(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log.info("REST request from mobile to reopen grievance");
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
        ActionResponse actionResponse = careRequestService.resubmitCareProces(careRequest, resubmitAction);
        log.info("Grievance reopened successfully with application number | " + applicationId);
        return actionResponse;
    }

    @POST
    @Path("/update")
    @ApiOperation(value = "update mobile grievance", notes = "update mobile grievance", response = ActionResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ActionResponse approveRequests(@RequestBody final GrievanceReqDTO grievanceReqDTO,
            final HttpServletRequest request, final BindingResult bindingResult)
            throws EntityNotFoundException, Exception {
        log.info("REST request from mobile to update grievance");
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
        ActionResponse actionResponse = careRequestService.updateCareProces(careRequest, departmentAction);
        log.info("Grievance updated successfully with application number | " + applicationId);
        return actionResponse;
    }

    @POST
    @Path("/feedback/save")
    @ApiOperation(value = "feedback save mobile grievance", notes = "feedback save mobile grievance", response = Object.class)
    @RequestMapping(value = "/feedback/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveFeedback(@RequestBody final com.abm.mainet.care.dto.GrievanceReqDTO requestDTOFromPortla,
            final HttpServletRequest request, final BindingResult bindingResult)
            throws EntityNotFoundException, Exception {
        log.info("REST request from mobile to save grievance feedback");
        ActionResponse actResponse = new ActionResponse();
        CareFeedbackDTO cfd = requestDTOFromPortla.getCareFeedback();
        CareFeedback cf = CareUtility.toCareFeedback(cfd);
        if (cf != null) {
            careFeedbackService.saveCareFeedbak(cf);
            actResponse.addResponseData(MainetConstants.RESPONSE, MainetConstants.ALERT_SAVE_SUCCESS);
        }
        log.info("Grievance feedback saved successfully");
        return actResponse;
    }

    @POST
    @Path("/tasks/employee")
    @ApiOperation(value = "get open task by employee id for mobile", notes = "get open task by employee id for mobile", response = ComplaintTaskDTO.class, responseContainer = "List")
    @RequestMapping(value = "/tasks/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public List<ComplaintTaskDTO> getOpenTaskByEmployeeId(@RequestBody final TaskSearchRequest requester)
            throws Exception {
        return careRequestService.getOpenComplaintTaskByEmployeeId(requester);
    }

    @POST
    @Path("/fetchDepartmentsByOrgId/{id}")
    @ApiOperation(value = "fetch Departments By OrgId for mobile", notes = "fetch Departments By OrgId for mobile", response = DepartmentComplaint.class, responseContainer = "List")
    @RequestMapping(value = "/fetchDepartmentsByOrgId/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<DepartmentComplaint> fetchDepartmentsByOrgId(@PathVariable("id") Long id, HttpServletRequest request) {
        List<DepartmentComplaint> deptList = iComplaintService.getcomplainedDepartment(id);
        return deptList;
    }

    @POST
    @Path("/fetchLocationsById/{id}")
    @ApiOperation(value = "fetch Locations By Id for mobile", notes = "fetch Locations By Id for mobile", response = TbLocationMas.class)
    @RequestMapping(value = "/fetchLocationsById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public TbLocationMas fetchLocationsById(@PathVariable("id") Long id, HttpServletRequest request) {
        TbLocationMas locationMasEntity = iLocationMasService.findById(id);
        return locationMasEntity;
    }

    @POST
    @Path("/fetchLocationsByOrgId/{orgId}")
    @ApiOperation(value = "fetch Locations By OrgId for mobile", notes = "fetch Locations By OrgId for mobile", response = TbLocationMas.class, responseContainer = "List")
    @RequestMapping(value = "/fetchLocationsByOrgId/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<TbLocationMas> fetchLocationsByOrgId(@PathVariable("orgId") Long orgId, HttpServletRequest request) {
        List<TbLocationMas> locations = iLocationMasService.fillAllActiveLocationByOrgId(orgId);
        return locations;
    }

    @POST
    @Path("/getLocationByPinCode/{pincode}")
    @ApiOperation(value = "get Locations By PinCode for mobile", notes = "get Locations By PinCode for mobile", response = TbLocationMas.class, responseContainer = "List")
    @RequestMapping(value = "/getLocationByPinCode/{pincode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<TbLocationMas> fetchLocationsByPinCodeId(@PathVariable("pincode") Integer pincode,
            HttpServletRequest request) {
        List<TbLocationMas> locations = iLocationMasService.getlocationByPinCode(Long.valueOf(pincode));
        return locations;
    }

    @POST
    @Path("/careRequests/{id}")
    @ApiOperation(value = "care requests by Id for mobile", notes = "care requests by Id for mobile", response = CareRequestDTO.class)
    @RequestMapping(value = "/careRequests/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CareRequestDTO> getCareRequestById(@PathVariable("id") Long id, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        CareRequestDTO cr = careRequestService.findById(id);
        return Optional.ofNullable(cr).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @POST
    @Path("/careRequests/applicationId/{applicationId}")
    @ApiOperation(value = "care requests by application Id for mobile", notes = "care requests by application Id for mobile", response = CareRequestDTO.class)
    @RequestMapping(value = "/careRequests/applicationId/{applicationId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CareRequestDTO> getCareRequestByapplicationId(
            @PathVariable("applicationId") Long applicationId, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        CareRequestDTO cr = CareUtility.toCareRequestDTO(careRequestService.findByApplicationId(applicationId));
        return Optional.ofNullable(cr).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @POST
    @Path("/careRequests/complaintId/{complaintId}")
    @ApiOperation(value = "care requests by complaintId for mobile", notes = "care requests by complaintId for mobile", response = CareRequestDTO.class)
    @RequestMapping(value = "/careRequests/complaintId/{complaintId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CareRequestDTO getCareRequestByComplaintId(@PathVariable("complaintId") String complaintId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {

		CareRequestDTO careRequestDto = CareUtility.toCareRequestDTO(careRequestService.findByComplaintId(complaintId));
		if (careRequestDto != null) {
			// code for attach document
			List<CFCAttachment> att = iChecklistVerificationService
					.findAttachmentsForAppId(careRequestDto.getApplicationId(), null, careRequestDto.getOrgId());
			List<DocumentDetailsVO> docList = new ArrayList<DocumentDetailsVO>();
			att.forEach(at -> {
				DocumentDetailsVO dvo = new DocumentDetailsVO();
				dvo.setAttachmentId(at.getAttId());
				dvo.setDocumentSerialNo(at.getClmSrNo());
				dvo.setDocumentName(at.getAttFname());
				dvo.setUploadedDocumentPath(at.getAttPath());

				if (!dvo.getUploadedDocumentPath().isEmpty() && null != dvo.getUploadedDocumentPath()
						&& !dvo.getDocumentName().isEmpty() && null != dvo.getDocumentName()) {

					String downloadLink = dvo.getUploadedDocumentPath() + MainetConstants.FILE_PATH_SEPARATOR
							+ dvo.getDocumentName();
					dvo.setDocDes(download(downloadLink));
				}
				/* getting bytecode from dms if dms flag is Y */
				if (MainetConstants.Common_Constant.YES
						.equals(ApplicationSession.getInstance().getMessage("dms.configure"))
						&& StringUtils.isNotEmpty(at.getDmsDocId())) {
					dvo.setDocumentByteCode(CareUtility.convertInByteCodeByDmsId(at.getDmsDocId()));
				} else
					dvo.setDocumentByteCode(CareUtility.convertInByteCode(at.getAttFname(), at.getAttPath()));

				docList.add(dvo);
			});
			careRequestDto.setAttachments(docList);
		}

		return careRequestDto;
    }

    @POST
    @Path("/careRequests/search")
    @ApiOperation(value = "care requests search for mobile", notes = "care requests search for mobile", response = CareRequestDTO.class, responseContainer = "List")
    @RequestMapping(value = "/careRequests/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<CareRequestDTO> searchCareRequest(@RequestBody ComplaintSearchDTO filter, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        List<CareRequestDTO> careRequests = careRequestService.findComplaint(filter);
        return careRequests;
    }

    @POST
    @Path("/careRequests/acknowledgement/applicationId/{applicationId}/{langId}")
    @ApiOperation(value = "care requests acknowledgement by applicationId and lang id for mobile", notes = "care requests acknowledgement by applicationId and lang id for mobile", response = ComplaintAcknowledgementModel.class)
    @RequestMapping(value = "/careRequests/acknowledgement/applicationId/{applicationId}/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ComplaintAcknowledgementModel> getCareRequestComplaintAcknowledgementByapplicationId(
            @PathVariable("applicationId") Long applicationId, @PathVariable("langId") int langId,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        CareRequest cr = careRequestService.findByApplicationId(applicationId);
        langId = (langId == 0) ? MainetConstants.ENGLISH : langId;
        ComplaintAcknowledgementModel acknowledgementModel = careRequestService.getComplaintAcknowledgementModel(cr,
                langId);
        return Optional.ofNullable(acknowledgementModel).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @POST
    @Path("/careRequests/acknowledgement/complaintId/{complaintId}/{langId}")
    @ApiOperation(value = "care requests acknowledgement by complaintId and lang id for mobile", notes = "care requests acknowledgement by complaintId and lang id for mobile", response = ComplaintAcknowledgementModel.class)
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

    @POST
    @Path("/careRequests/acknowledgement")
    @ApiOperation(value = "care requests acknowledgement by request for mobile", notes = "care requests acknowledgement by request for mobile", response = ComplaintAcknowledgementModel.class)
    @RequestMapping(value = "/careRequests/acknowledgement", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ComplaintAcknowledgementModel getCareRequestComplaintAcknowledgementByComplaintId(
            @RequestBody CareRequestDTO careRequestDTO,
            HttpServletRequest request) throws EntityNotFoundException, Exception {
        long langId;
        langId = (careRequestDTO.getLangId() == 0) ? MainetConstants.ENGLISH : careRequestDTO.getLangId();
        CareRequest cr = careRequestService.findByComplaintId(careRequestDTO.getComplaintId());
        return careRequestService.getComplaintAcknowledgementModel(cr, langId);
    }

    @POST
    @Path("/careRequests/empId/emplType/{empId}/{emplType}")
    @ApiOperation(value = "care requests by empId and emplType for mobile", notes = "care requests by empId and emplType for mobile", response = CareRequestDTO.class, responseContainer = "List")
    @RequestMapping(value = "/careRequests/empId/emplType/{empId}/{emplType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<CareRequestDTO> getCareRequestsByEmpIdAndEmplType(@PathVariable("empId") Long empId,
            @PathVariable("emplType") Long emplType, HttpServletRequest request)
            throws EntityNotFoundException, Exception {
        List<CareRequestDTO> crs = careRequestService.getCareRequestsByEmpIdAndEmplType(empId, emplType);
        return crs;
    }

    @POST
    @Path("/findDepartmentComplaintByDepartmentId/{id}/{orgId}")
    @ApiOperation(value = "find Department Complaint By Department Id and orgId for mobile", notes = "find Department Complaint By Department Id and orgId for mobile", response = DepartmentComplaint.class)
    @RequestMapping(value = "/findDepartmentComplaintByDepartmentId/{id}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public DepartmentComplaint findDepartmentComplaintByDepartmentId(@PathVariable("id") Long id,
            @PathVariable("orgId") Long orgId, HttpServletRequest request) {
        DepartmentComplaint departmentComplaint = iComplaintService.findComplainedDepartmentByDeptId(id, orgId);
        return departmentComplaint;
    }

    @POST
    @Path("/findComplaintTypeById/{id}")
    @ApiOperation(value = "find Complaint Type By Id for mobile", notes = "find Complaint Type By Id for mobile", response = ComplaintType.class)
    @RequestMapping(value = "/findComplaintTypeById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ComplaintType findComplaintTypeById(@PathVariable("id") Long id, HttpServletRequest request) {
        ComplaintType complaintType = iComplaintService.findComplaintTypeById(id);
        return complaintType;
    }

    @POST
    @Path("/getApplicationFeedback/{tokenNumber}")
    @ApiOperation(value = "get Application Feedback by token number for mobile", notes = "get Application Feedback by token number for mobile", response = CareFeedbackDTO.class)
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

    @POST
    @Path("/getAllEmployeeWithLoggedInEmployeeNotPresent/{orgid}/{departmentEmployeeId}")
    @ApiOperation(value = "get All Employee With LoggedIn Employee Not Present for mobile", notes = "get All Employee With LoggedIn Employee Not Present for mobile", response = EmployeeBean.class, responseContainer = "List")
    @RequestMapping(value = "/getAllEmployeeWithLoggedInEmployeeNotPresent/{orgid}/{departmentEmployeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<EmployeeBean> getAllEmployeeWithLoggedInEmployeeNotPresent(@PathVariable("orgid") Long orgid,
            @PathVariable("departmentEmployeeId") Long departmentEmployeeId, HttpServletRequest request)
            throws Exception {
        return employeeService.getAllEmployeeWithLoggedInEmployeeNotPresent(orgid, departmentEmployeeId);
    }

    @POST
    @Path("/getPrefixNameByDepartmentId/{deptId}/{orgId}")
    @ApiOperation(value = "get Prefix Name By DepartmentId for mobile", notes = "get Prefix Name By DepartmentId for mobile", response = String.class)
    @RequestMapping(value = "/getPrefixNameByDepartmentId/{deptId}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getOperationalWardZonePrefixName(@PathVariable("deptId") Long deptId,
            @PathVariable("orgId") Long orgId) {
        String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId, orgId);
        if (prefixName != null && !prefixName.isEmpty()) {
            // D#130416 check at least one level define or not in prefix
            // if not than it throws exception which is handle using try catch and inside catch pass prefix CWZ
            try {
                Organisation org = new Organisation();
                org.setOrgid(orgId);
                CommonMasterUtility.getLevelData(prefixName, 1, org);
            } catch (FrameworkException e) {
                prefixName = MainetConstants.COMMON_PREFIX.CWZ;
            }
        } else {
            prefixName = MainetConstants.COMMON_PREFIX.CWZ;
        }

        return prefixName;
    }

    @POST
    @Path("/isWardZoneRequired/{orgid}/{compTypeId}")
    @ApiOperation(value = "check WardZone Required by orgid and compTypeId for mobile", notes = "check WardZone Required by orgid and compTypeId for mobile")
    @RequestMapping(value = "/isWardZoneRequired/{orgid}/{compTypeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public Map<String, Boolean> isWardZoneRequired(@PathVariable("orgid") Long orgid,
            @PathVariable("compTypeId") Long compTypeId) {
        String workflowDefinitionType = careRequestService.resolveWorkflowTypeDefinition(orgid, compTypeId);
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("isWardZoneRequired", workflowDefinitionType.equals(MainetConstants.MENU.N));
        return result;
    }

    @POST
    @Path("/careRequests/actionLog/{applicationId}/{orgId}/lang/{langId}")
    @ApiOperation(value = "get CareWorkflowActionLog By ApplicationId and orgId and langId for mobile", notes = "get CareWorkflowActionLog By ApplicationId and orgId and langId for mobile", response = WorkflowTaskActionWithDocs.class, responseContainer = "List")
    @RequestMapping(value = "/careRequests/actionLog/{applicationId}/{orgId}/lang/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<WorkflowTaskActionWithDocs> getCareWorkflowActionLogByApplicationId(
            @PathVariable("applicationId") Long applicationId, @PathVariable("orgId") Long orgId,
            @PathVariable("langId") long langId, HttpServletRequest request) {
        List<WorkflowTaskActionWithDocs> actionList = careRequestService.getCareWorkflowActionLogByApplicationId(applicationId,
                orgId, langId);
        /* passing bytecode for file */
        actionList.forEach(al -> {
            if (CollectionUtils.isNotEmpty(al.getAttachements())) {
                al.getAttachements().forEach(doc -> {
                    /* getting bytecode from dms if dms flag is Y */
                    if (MainetConstants.Common_Constant.YES.equals(ApplicationSession.getInstance().getMessage("dms.configure"))
                            &&
                            StringUtils.isNotEmpty(doc.getDmsDocId())) {
                        doc.setOtherField(CareUtility.convertInByteCodeByDmsId(doc.getDmsDocId()));
                    } else if (StringUtils.isNotBlank(doc.getDefaultVal())) {
                        doc.setOtherField(CareUtility.convertInByteCode(doc.getLookUpCode(), doc.getDefaultVal()));
                    }
                });
            }
        });

        return actionList;
    }

    /**
     * POST /organisationsByDistrictId/:districtId : To retrieve organization by district
     * 
     * @param districtCpdId
     * @param request
     * @return
     */
    @POST
    @Path("/organisationsByDistrictId/{districtId}")
    @ApiOperation(value = "get Organisations By District for mobile", notes = "get Organisations By District for mobile", response = OrganisationDTO.class, responseContainer = "List")
    @RequestMapping(value = "/organisationsByDistrictId/{districtId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<OrganisationDTO> getOrganisationsByDistrict(
            @PathVariable(MainetConstants.Common_Constant.DISTRICT_ID) Long districtCpdId, HttpServletRequest request) {
        List<OrganisationDTO> organisations = careRequestService.getOrganisationByDistrict(districtCpdId);
        return organisations;

    }

    @CrossOrigin(origins = "*")
    @POST
    @Path("/getPrefixLevelData/{prefix}/{orgId}")
    @ApiOperation(value = "get PrefixLevelData for mobile", notes = "get PrefixLevelData for mobile", response = TbComparentMas.class, responseContainer = "List")
    @RequestMapping(value = "/getPrefixLevelData/{prefix}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<TbComparentMas> getPrefixLevelData(@PathVariable("prefix") String prefix,
            @PathVariable("orgId") Long orgId, HttpServletRequest request) {
        TbComparamMas tbComparamMas = tbComparamMasService.findComparamDetDataByCpmId(prefix);

        final List<TbComparentMasEntity> entities = tbComparentMasJpaRepository
                .findComparentMasDataById(tbComparamMas.getCpmId(), orgId);
        final List<TbComparentMas> beans = new ArrayList<>();

        for (final TbComparentMasEntity tbComparentMasEntity : entities) {
            beans.add(tbComparentMasServiceMapper.mapTbComparentMasEntityToTbComparentMas(tbComparentMasEntity));

        }
        return beans;
    }

    /**
     * POST /departmentComplaintsByOrgId/:orgId : To retrieve DepartmentComplaint by organization
     * 
     * @param orgId
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @POST
    @Path("/departmentComplaintsByOrgId/{orgId}/{type}")
    @ApiOperation(value = "get departmentComplaints By OrgId for mobile", notes = "get departmentComplaints By OrgId for mobile", response = DepartmentComplaintDTO.class, responseContainer = "Set")
    @RequestMapping(value = "/departmentComplaintsByOrgId/{orgId}/{type}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<DepartmentComplaintDTO> getdepartmentComplaintsByOrgId(@PathVariable("orgId") Long orgId,
            @PathVariable("type") String type, HttpServletRequest request) {
        Set<DepartmentComplaintDTO> careWorkflowDefinedDeptList = new HashSet<DepartmentComplaintDTO>();

        if (type.equalsIgnoreCase(MainetConstants.FlagR)) {
            Department dpts = tbDepartmentService.findDepartmentByCode("CFC");
            if (dpts != null) {
                try {
                    DepartmentComplaintDTO complaintDTO = new DepartmentComplaintDTO();
                    DepartmentDTO department = new DepartmentDTO();
                    BeanUtils.copyProperties(department, dpts);
                    complaintDTO.setDepartment(department);
                    careWorkflowDefinedDeptList.add(complaintDTO);
                } catch (Exception e) {
                    throw new FrameworkException(e.getMessage());
                }
            }

        } else {
            careWorkflowDefinedDeptList = careRequestService.getDepartmentComplaintsByOrgId(orgId);
        }
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

    @POST
    @Path("/fetchLocationsByOrgIdAndDeptId/{orgId}/{deptId}")
    @ApiOperation(value = "fetchLocations BY deptId and orgId for mobile", notes = "fetchLocations BY deptId and orgId for mobile", response = LocationDTO.class, responseContainer = "Set")
    @RequestMapping(value = "/fetchLocationsByOrgIdAndDeptId/{orgId}/{deptId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<LocationDTO> fetchLocationsByOrgIdAndDeptId(@PathVariable("orgId") Long orgId,
            @PathVariable("deptId") Long deptId, HttpServletRequest request) {
        // List<LocationMasEntity> locationList =
        // careRequestService.getDepartmentCompalintLocations(orgId, deptId);
        Set<LocationDTO> locations = new HashSet<>();
        List<TbLocationMas> locs = iLocationMasService.getlocationByDeptId(deptId, orgId);
        locs.forEach(locationDto -> {
            LocationMasEntity entity = new LocationMasEntity();
            try {
                BeanUtils.copyProperties(entity, locationDto);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new FrameworkException(e.getMessage());
            }
            locations.add(CareUtility.toLocationMasEntityDTO(entity, 0));
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
    @POST
    @Path("/departmentComplaintTypeByDepartmentId/{deptId}/{orgId}/{type}")
    @ApiOperation(value = "get departmentComplaintType BY deptId and orgId and complaint type for mobile", notes = "get departmentComplaintType BY deptId and orgId and complaint type for mobile", response = DepartmentComplaintTypeDTO.class, responseContainer = "Set")
    @RequestMapping(value = "/departmentComplaintTypeByDepartmentId/{deptId}/{orgId}/{type}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Set<DepartmentComplaintTypeDTO> getdepartmentComplaintTypeByDepartmentId(@PathVariable("deptId") Long deptId,
            @PathVariable("orgId") Long orgId, @PathVariable("type") String type, HttpServletRequest request) {
        TbDepartment dept = tbDepartmentService.findById(deptId);
        Set<DepartmentComplaintTypeDTO> complaintTypes = new HashSet<DepartmentComplaintTypeDTO>();
        if (dept != null && dept.getDpDeptcode().equalsIgnoreCase("CFC")
                && type.equalsIgnoreCase(MainetConstants.FlagR)) {
            List<TbServicesMst> serviceList = tbServicesMstService.findByDeptId(deptId, orgId);
            for (TbServicesMst tbServicesMst : serviceList) {
                DepartmentComplaintTypeDTO complaintTypeDTO = new DepartmentComplaintTypeDTO();
                complaintTypeDTO.setComplaintDesc(tbServicesMst.getSmServiceName());
                complaintTypeDTO.setComplaintDescReg(tbServicesMst.getSmServiceNameMar());
                complaintTypeDTO.setServiceId(tbServicesMst.getSmServiceId());
                complaintTypes.add(complaintTypeDTO);
            }
        } else {
            complaintTypes = careRequestService.getDepartmentComplaintTypeByDepartmentId(deptId, orgId);
        }

        return complaintTypes;
    }

    @CrossOrigin(origins = "*")
    @POST
    @Path("/getPrefixLevelDataForApplicantType/{prefix}/{orgId}")
    @ApiOperation(value = "get PrefixLevel Data For ApplicantType by prefix and orgId for mobile", notes = "get PrefixLevel Data For ApplicantType by prefix and orgId for mobile", response = LookUp.class, responseContainer = "List")
    @RequestMapping(value = "/getPrefixLevelDataForApplicantType/{prefix}/{orgId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<LookUp> getPrefixLevelDataForApplicantType(@PathVariable("prefix") String prefix,
            @PathVariable("orgId") Long orgId, HttpServletRequest request) {
        List<LookUp> lookUpList = new ArrayList<LookUp>();
        try {
            lookUpList = CommonMasterUtility.lookUpListByPrefix(prefix, orgId);
        } catch (FrameworkException e) {
            return lookUpList;
        }

        return lookUpList;
    }

    @POST
    @Path("/saveLandInspection")
    @ApiOperation(value = "save LandInspection for mobile", notes = "save LandInspection for mobile", response = Object.class)
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
    
    public String download(final String downloadLink) {
    	String fileNetPath="";
    	final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
    		+ MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
    	String filePath = Utility.downloadedFileUrl(downloadLink, outputPath, FileNetApplicationClient.getInstance());
    	if (StringUtils.isNotBlank(filePath)) {
    	fileNetPath=filePath;
    	}
    	return 	fileNetPath;
    }

}