package com.abm.mainet.bnd.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.dto.BirthRegistrationCorrDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.ui.model.BirthCertificateGenerationModelModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
/**
 * 
 * @author Bhagyashri.Dongardive
 *
 */
@Controller
@RequestMapping(value = "/BirthCertificateGeneration.html")
public class BirthCertificateGenerationController extends AbstractFormController<BirthCertificateGenerationModelModel> {

	/**
	 * @param complainNo
	 * @param actualTaskId
	 * @param serviceId
	 * @param workflowId
	 * @param taskName
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */

	@Autowired
	private IBirthRegService iBirthRegSevice;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView workorder(
			@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {

		sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();

		BirthCertificateGenerationModelModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegistrationCorrectionApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		getModel().bind(httpServletRequest);

		// Query for Fetching Data

		// Load the role from TB_GROUP_MAST
		// Boolean checkFinalAproval =
		// iDeathRegistrationService.checkEmployeeRole(UserSession.getCurrent());
		Boolean checkFinalAproval = iBirthRegSevice.checkEmployeeRole(UserSession.getCurrent());
		model.addAttribute("CheckFinalApp", checkFinalAproval);

		// fetch data from birth registration correction
		List<BirthRegistrationCorrDTO> tbBirthRegCorrDtoList = iBirthRegSevice
				.getBirthRegisteredAppliDetailFromApplnId(Long.valueOf(complainNo), orgId);
		this.getModel().setTbBirthregcorrDTO(tbBirthRegCorrDtoList.get(0));
		this.getModel().setServiceId(serviceId);

		// fetch data from birth registration
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice
				.getBirthRegApplnData(tbBirthRegCorrDtoList.get(0).getBrId(), orgId);
		this.getModel().setBirthRegDto(registrationDetail.get(0));

		RequestDTO requestDTO = iBirthRegSevice.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
		requestDTO.setApplicationId(Long.valueOf(complainNo));
		this.getModel().getBirthRegDto().setRequestDTO(requestDTO);
		this.getModel().setRequestDTO(requestDTO);
		this.getModel().setServiceId(serviceId);
		// fetch uploaded document
		// approvalModel.setFetchDocumentList(iChecklistVerificationService.getDocumentUploadedByApplnId(Long.valueOf(complainNo),
		// orgId));
		List<CFCAttachment> attachList = new ArrayList<>();
		List<CFCAttachment> checklist = new ArrayList<>();
		attachList = iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo), null, orgId);
		if (CollectionUtils.isNotEmpty(attachList)) {
			for (int i = 0; i < attachList.size(); i++) {
				if (attachList.get(i).getClmAprStatus().equals(MainetConstants.FlagY)) {
					checklist.add(attachList.get(i));
				}
			}
		}
		approvalModel.setFetchDocumentList(checklist);

		return new ModelAndView("BirthCertificategeneration", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "finalSaveAndCertificategeneration", method = RequestMethod.POST)
    public Map<String, Object> finalSaveAndCertificategeneration(HttpServletRequest request) {
        this.getModel().bind(request);
        BirthCertificateGenerationModelModel model = getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // complete the BPM task of Marriage Certificate because BPM last task is letter generation

        // JsonViewObject responseObj = null;

        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(true);
        taskAction.setIsObjectionAppealApplicable(false);
        if (StringUtils.isNotBlank(model.getRequestDTO().getEmail())) {
            taskAction.setEmpEmail(model.getRequestDTO().getEmail());
        }
        taskAction.setApplicationId(model.getRequestDTO().getApplicationId());
        taskAction.setDecision(MainetConstants.WorkFlow.Status.CLOSED);
        taskAction.setTaskId(getModel().getWorkflowActionDto().getTaskId());

        String certificateNo = iBirthRegSevice.executeFinalWorkflowAction(taskAction, this.getModel().getServiceId(),getModel().getBirthRegDto(),getModel().getTbBirthregcorrDTO());
          
        Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("BirthWfStatus",	this.getModel().getTbBirthregcorrDTO().getBirthRegstatus());
	    object.put("certificateNo", certificateNo);
	    return object;


    }

}
