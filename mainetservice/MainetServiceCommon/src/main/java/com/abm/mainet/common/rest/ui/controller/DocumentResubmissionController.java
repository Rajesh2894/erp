package com.abm.mainet.common.rest.ui.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.checklist.dto.ChecklistServiceDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionRequestDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionResponseDTO;
import com.abm.mainet.cfc.checklist.modelmapper.ChecklistMapper;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

/**
 * @author Lalit.Prusti
 * @since 31-May-2016
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/DocumentResubmission")
public class DocumentResubmissionController {

	private static final String SAVE_DOCUMENT_DETAILS = "/saveDocumentDetails";
	private static final String SEARCH_APPLICANT_DETAILS = "/searchApplicantDetails";

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentResubmissionController.class);

	@Resource
	private IChecklistVerificationService documentUplodService;

	@Resource
	private IChecklistSearchService checklistSearchService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private ChecklistMapper mapper;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Resource
	private IWorkflowExecutionService workflowExecutionService;

	@Resource
	private ITaskService taskService;
	@Resource
	private TbCfcApplicationAddressJpaRepository cfcAddrsRepo;

	@Autowired
	private TbDepartmentService tbDeptService;

	@RequestMapping(value = SEARCH_APPLICANT_DETAILS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object searchApplicantDetails(@RequestBody final DocumentResubmissionRequestDTO requestVO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		final DocumentResubmissionResponseDTO response = new DocumentResubmissionResponseDTO();

		try {

			final ChecklistServiceDTO checklistDetail = mapper
					.mapChecklistStatusViewToChecklistServiceDTO(checklistSearchService
							.getCheckListDataByApplication(requestVO.getOrgId(), requestVO.getApplicationId()));

			final List<CFCAttachmentsDTO> attachmentList = documentUplodService.getUploadedDocumentByDocumentStatus(
					requestVO.getApplicationId(), MainetConstants.Common_Constant.NO, requestVO.getOrgId());
			if (!attachmentList.isEmpty()) {
				attachmentList.forEach(doc -> {
					FileNetApplicationClient fileNetApplicationClient = null;
					String existingPath = null;
					if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
						existingPath = doc.getAttPath().replace('/', '\\');
					} else {
						existingPath = doc.getAttPath().replace('\\', '/');
					}
					String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
							MainetConstants.operator.COMA);
					try {
						final byte[] image = fileNetApplicationClient.getInstance().getFileByte(doc.getAttFname(),
								directoryPath);
						Base64 base64 = new Base64();
						String pdfDoc = base64.encodeToString(image);
						doc.setDocument(pdfDoc);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				});
			}

			response.setChecklistDetail(checklistDetail);
			response.setAttachmentList(attachmentList);
			response.setStatus(MainetConstants.Req_Status.SUCCESS);

		} catch (final Exception ex) {

			response.setStatus(MainetConstants.Req_Status.FAIL);
			LOGGER.error("source dto object does not match to destination dto:", bindingResult.getAllErrors());
		}

		return response;

	}

	@RequestMapping(value = SAVE_DOCUMENT_DETAILS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object saveDocumentDetails(@RequestBody final DocumentResubmissionRequestDTO requestVO,
			final HttpServletRequest request, final BindingResult bindingResult) {
		final DocumentResubmissionResponseDTO response = new DocumentResubmissionResponseDTO();

		try {
    //D#131715
			if (requestVO.getDeptId() != null) {
				String serviceShortCode = tbDeptService.findDepartmentShortCodeByDeptId(requestVO.getDeptId(),
						requestVO.getOrgId());
				if (serviceShortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
					List<Object[]> objArr = cfcAddrsRepo.findAddressInfo(requestVO.getApplicationId(),
							requestVO.getOrgId());
					requestVO.setUserId(Long.valueOf(objArr.get(0)[3].toString()));
				}
			}

			String processName = serviceMasterService.getProcessName(requestVO.getServiceId(), requestVO.getOrgId());
			if (processName != null) {

				List<UserTaskDTO> list = taskService.getTaskList(requestVO.getApplicationId().toString());
				if (list == null || list.isEmpty()) {
					LOGGER.info("Unable to resubmit Request due to task not found For  RequestNo: "
							+ requestVO.getApplicationId());
					throw new FrameworkException(
							"Unable to resubmit due to task not found For  RequestNo: " + requestVO.getApplicationId());
				}
				UserTaskDTO task = list.get(0);
				Long taskId = task.getTaskId();

				WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
				WorkflowTaskAction workflowAction = new WorkflowTaskAction();

				workflowAction.setTaskId(taskId);
				workflowAction.setApplicationId(requestVO.getApplicationId());
				workflowAction.setDateOfAction(new Date());
				// changes Approved to Submitted as discussion with Akshay D#131715
				workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
				workflowAction.setOrgId(requestVO.getOrgId());
				workflowAction.setEmpId(requestVO.getUserId());
				workflowAction.setModifiedBy(requestVO.getUserId());
				workflowAction.setEmpType(UserSession.getCurrent().getEmployee().getEmpId());
				workflowAction.setCreatedBy(requestVO.getUserId());
				workflowAction.setCreatedDate(new Date());
				List<Long> attachementIds = new ArrayList<>();
				requestVO.getDocumentList().forEach(doc -> {
					attachementIds.add(doc.getAttachmentId());
				});
				workflowAction.setAttachementId(attachementIds);
				workflowdto.setProcessName(processName);
				workflowdto.setWorkflowTaskAction(workflowAction);
				workflowExecutionService.updateWorkflow(workflowdto);
			}
			checklistSearchService.updateApplicationChecklistStatus(requestVO.getApplicationId(), requestVO.getOrgId(),
					requestVO.getApplicationStatus());
			fileUploadService.doFileUpload(requestVO.getDocumentList(), requestVO);
			response.setStatus(MainetConstants.Req_Status.SUCCESS);
		} catch (final Exception ex) {
			response.setStatus(MainetConstants.Req_Status.FAIL);
			LOGGER.error("source dto object does not match to destination dto:", bindingResult.getAllErrors());
			throw new FrameworkException("Exception in checklist verification for jbpm workflow : " + ex.getMessage(),
					ex);
		}

		return response;

	}

}
