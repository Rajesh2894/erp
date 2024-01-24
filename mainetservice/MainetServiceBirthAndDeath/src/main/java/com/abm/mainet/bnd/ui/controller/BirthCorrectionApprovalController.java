package com.abm.mainet.bnd.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.ui.model.BirthCorrectionApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;


@Controller
@RequestMapping(value = "/BirthRegistrationCorrectionApproval.html")
public class BirthCorrectionApprovalController extends AbstractFormController<BirthCorrectionApprovalModel> {

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
	
	@Autowired
	private IdeathregCorrectionService iDeathregCorrectionService;

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
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();

		BirthCorrectionApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegistrationCorrectionApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
		
		//Query for Fetching Data
	
		 //Load the role from TB_GROUP_MAST
		 //Boolean checkFinalAproval = iDeathRegistrationService.checkEmployeeRole(UserSession.getCurrent());
		 Boolean checkFinalAproval = iBirthRegSevice.checkEmployeeRole(UserSession.getCurrent());
	     model.addAttribute("CheckFinalApp", checkFinalAproval);
	     
	     //fetch data from birth registration correction
	     List<BirthRegistrationCorrDTO> tbBirthRegCorrDtoList = iBirthRegSevice.getBirthRegisteredAppliDetailFromApplnId(Long.valueOf(complainNo), orgId);
	     this.getModel().setTbBirthregcorrDTO(tbBirthRegCorrDtoList.get(0));
	     
	     //fetch data from birth registration
	     List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegApplnData(tbBirthRegCorrDtoList.get(0).getBrId(), orgId);
	     this.getModel().setBirthRegDto(registrationDetail.get(0));
	     this.getModel().getBirthRegDto().setAuthRemark(null);
	     RequestDTO requestDTO = iBirthRegSevice.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
	     requestDTO.setApplicationId(Long.valueOf(complainNo));
	     this.getModel().getBirthRegDto().setRequestDTO(requestDTO);
	     this.getModel().setRequestDTO(requestDTO);
	     this.getModel().setServiceId(serviceId);
	     //fetch uploaded document
		 //approvalModel.setFetchDocumentList(iChecklistVerificationService.getDocumentUploadedByApplnId(Long.valueOf(complainNo), orgId));
	     List<CFCAttachment> attachList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 attachList=iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId);
		 if(CollectionUtils.isNotEmpty(attachList)) {
		 for(int i=0;i<attachList.size();i++)
		 {
			 if(attachList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(attachList.get(i));
			 }
		 }
		}
		approvalModel.setFetchDocumentList(checklist);
		return new ModelAndView("BirthRegistrationCorrectionApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
	@ResponseBody
	@RequestMapping(params = "saveBirthRegCorrApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		String certificateNo = this.getModel().saveBirthRegCorrApprovalDetails(String.valueOf(this.getModel().getTbBirthregcorrDTO().getApmApplicationId()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("BirthWfStatus",	this.getModel().getTbBirthregcorrDTO().getBirthRegstatus());
	    object.put("certificateNo", certificateNo);
	    return object;
   }
	
	
  	@RequestMapping(params = "displaybirthCorrCharge", method = { RequestMethod.POST })
    public ModelAndView openMarriageCharge(final HttpServletRequest request) {
        this.getModel().bind(request);
        getModel().setSuccessFlag(MainetConstants.FlagY);
        if (getModel().saveLoiData()) {
            getModel().setPayableFlag(MainetConstants.FlagN);
            List<TbLoiDet> loiDetailList = new ArrayList<>();
            Double totalAmount = new Double(0);
            if (CollectionUtils.isNotEmpty(getModel().getLoiDetail())) {
                for (TbLoiDet detail : getModel().getLoiDetail()) {
                    TbLoiDet loiDetail = new TbLoiDet();
                    BeanUtils.copyProperties(detail, loiDetail);
                    String taxDesc = ApplicationContextProvider.getApplicationContext()
                            .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
                                    UserSession.getCurrent().getOrganisation().getOrgid());
                    loiDetail.setLoiRemarks(taxDesc);
                    totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
                    loiDetailList.add(loiDetail);
                    getModel().setTotalLoiAmount(totalAmount);
                }
            }
            getModel().setLoiDetail(loiDetailList);
            getModel().setAmountToPay(totalAmount);
            getModel().getOfflineDTO().setAmountToShow(getModel().getAmountToPay());
            RequestDTO requestDTO = this.getModel().getRequestDTO();
            String fullName = String.join(" ", Arrays.asList(requestDTO.getfName(),
            		requestDTO.getmName(), requestDTO.getlName()));
            requestDTO.setfName(fullName);      
            this.getModel().setRequestDTO(requestDTO);
            this.getModel().getBirthRegDto().setAuthRemark(this.getModel().getWorkflowActionDto().getComments());
            if (this.getModel().getBirthRegDto().getAuthRemark() != null && !this.getModel().getBirthRegDto().getAuthRemark().isEmpty()) {
            iBirthRegSevice.updateBirthRemark(this.getModel().getBirthRegDto().getBrId(), this.getModel().getBirthRegDto().getAuthRemark(),UserSession.getCurrent().getOrganisation().getOrgid());
            }

        } else {
            getModel().addValidationError(getApplicationSession()
                    .getMessage("Problem occured while fetching LOI Charges from BRMS Sheet"));
        }
        return new ModelAndView("birthcorrCharge", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }
	
	
	@RequestMapping(method = RequestMethod.POST, params = "generateLOIForBND")
    public ModelAndView generateLOIForMRM(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
         BirthCorrectionApprovalModel model = this.getModel();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(MainetConstants.FAILED);
        taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
        if (StringUtils.isNotBlank(model.getRequestDTO().getEmail())) {
            taskAction.setEmpEmail(model.getRequestDTO().getEmail());
        }
        taskAction.setApplicationId(model.getRequestDTO().getApplicationId());
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(getModel().getWorkflowActionDto().getTaskId());
        taskAction.setLoiDetails(getModel().getWorkflowActionDto().getLoiDetails());
        taskAction.setComments(getModel().getWorkflowActionDto().getComments());
        iDeathregCorrectionService.executeApprovalWorkflowAction(taskAction, getModel().getServiceId());
        getModel().getBirthRegDto().setApmApplicationId(model.getRequestDTO().getApplicationId());
        getModel().getBirthRegDto().setServiceId(this.getModel().getServiceId());
        getModel().getBirthRegDto().setAmount(this.getModel().getAmountToPay());
        this.getModel().sendSmsAndEmailLoi(getModel().getBirthRegDto());
        return jsonResult(JsonViewObject
                .successResult(ApplicationSession.getInstance().getMessage("mrm.loi.generate.success", new Object[] {
                        getModel().getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber() })));
    }
	
	
	@ResponseBody
	@RequestMapping(params = "saveBirthRegCorrApprLOI", method = RequestMethod.POST)
	public Map<String, Object> saveBirthRegCorrApprLOI(HttpServletRequest request)
    {
		getModel().bind(request);
		BirthCorrectionApprovalModel model = getModel();
		BirthRegistrationCorrDTO tbBirthregcorrDTO = model.getTbBirthregcorrDTO();
        BirthRegistrationDTO birthRegDto = model.getBirthRegDto();
		 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        // complete the BPM task of Marriage Certificate because BPM last task is letter generation
	        WorkflowTaskAction taskAction = getModel().getWorkflowActionDto();
	        taskAction.setOrgId(orgId);
	        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
	        taskAction.setDateOfAction(new Date());
	        taskAction.setCreatedDate(new Date());
	        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        taskAction.setIsFinalApproval(false);
	        taskAction.setIsObjectionAppealApplicable(false);
	        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
	        taskAction.setReferenceId(null);
	        if (StringUtils.isNotBlank(model.getRequestDTO().getEmail())) {
	            taskAction.setEmpEmail(model.getRequestDTO().getEmail());
	        }
	        taskAction.setApplicationId(model.getRequestDTO().getApplicationId());
	        taskAction.setDecision(getModel().getWorkflowActionDto().getDecision());
	        taskAction.setTaskId(getModel().getWorkflowActionDto().getTaskId());
	        
	        tbBirthregcorrDTO.setApmApplicationId(taskAction.getApplicationId());
	        birthRegDto.setApmApplicationId(taskAction.getApplicationId());
	        iDeathregCorrectionService.executeApprovalWorkflowAction(taskAction, getModel().getServiceId());
	        iBirthRegSevice.updateBirthCorrectionRemark(tbBirthregcorrDTO.getBrCorrId(), getModel().getWorkflowActionDto().getComments(),orgId);
	        iBirthRegSevice.updateBirthWorkFlowStatusBR(tbBirthregcorrDTO.getBrId(), taskAction.getDecision(), orgId, tbBirthregcorrDTO.getBrStatus());
	        iBirthRegSevice.updateBirthRemark(tbBirthregcorrDTO.getBrId(), getModel().getWorkflowActionDto().getComments(),orgId);
	        iBirthRegSevice.smsAndEmailApproval(birthRegDto,taskAction.getDecision());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("BirthWfStatus",	getModel().getWorkflowActionDto().getDecision());
	    return object;
   }
	
	@Override
	@RequestMapping(params ="viewRefNoDetails", method = RequestMethod.POST)
 	public ModelAndView viewDetails(@RequestParam("appNo") final String complainNo,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest httpServletRequest )throws Exception{      

		sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();

		BirthCorrectionApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegistrationCorrectionApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
	     
	     //fetch data from birth registration correction
	     List<BirthRegistrationCorrDTO> tbBirthRegCorrDtoList = iBirthRegSevice.getBirthRegisteredAppliDetailFromApplnId(Long.valueOf(complainNo), orgId);
	     this.getModel().setTbBirthregcorrDTO(tbBirthRegCorrDtoList.get(0));
	     
	     //fetch data from birth registration
	     List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegApplnData(tbBirthRegCorrDtoList.get(0).getBrId(), orgId);
	     this.getModel().setBirthRegDto(registrationDetail.get(0));
	     this.getModel().getBirthRegDto().setAuthRemark(null);
	     RequestDTO requestDTO = iBirthRegSevice.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
	     requestDTO.setApplicationId(Long.valueOf(complainNo));
	     this.getModel().getBirthRegDto().setRequestDTO(requestDTO);
	     this.getModel().setRequestDTO(requestDTO);
	     this.getModel().setServiceId(serviceId);
	     //fetch uploaded document
	     List<CFCAttachment> attachList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 attachList=iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId);
		 if(CollectionUtils.isNotEmpty(attachList)) {
		 for(int i=0;i<attachList.size();i++)
		 {
			 if(attachList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(attachList.get(i));
			 }
		 }
		}
		approvalModel.setFetchDocumentList(checklist);
		approvalModel.setViewFlag("Y");
		return new ModelAndView("BirthRegistrationCorrectionApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
}
