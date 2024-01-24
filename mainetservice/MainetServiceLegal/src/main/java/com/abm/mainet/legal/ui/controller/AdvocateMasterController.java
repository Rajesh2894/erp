package com.abm.mainet.legal.ui.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.model.AdvocateMasterModel;

@Controller
@RequestMapping("/AdvocateMaster.html")
public class AdvocateMasterController  extends AbstractFormController<AdvocateMasterModel>{
	
	 @Autowired
	 private IAdvocateMasterService advocateMasterService;
	 
	 @Autowired
	 private ICourtMasterService courtMasterService;
	 
	 @Autowired
	 private  ICaseEntryService caseEntryService;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;
	
     private CourtMasterDTO courtMasterDto;	 
     
	 private List<CourtMasterDTO> getCourtMasterList() {

	        List<CourtMasterDTO> courtMasterDTOList = courtMasterService
	                .getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
	        
	        return courtMasterDTOList;
	    }
	 
	 
	 
	 @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	 public ModelAndView index(final HttpServletRequest httpServletRequest) {
	    sessionCleanup(httpServletRequest);
	    this.getModel().setAdvocateMasterDTOList(advocateMasterService.getAllAdvocateMaster(UserSession.getCurrent().getOrganisation().getOrgid()));
	    this.getModel().setCourtNameList(getCourtMasterList());
	    this.getModel().setCommonHelpDocs("AdvocateMaster.html");
	    return defaultResult();
	 }

	 @ResponseBody
	 @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.ADD_ADVOCATE_MASTER)
	 public ModelAndView addAdvocateMaster(final HttpServletRequest httpServletRequest) {
		 sessionCleanup(httpServletRequest);
		 this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
		 this.getModel().setCourtNameList(getCourtMasterList());
		//120788 ENV Flag for tscl env
		 if(caseEntryService.isTSCLEnvPresent()) {
			 this.getModel().setEnvFlag(MainetConstants.FlagY);
		 }else {
			 this.getModel().setEnvFlag(MainetConstants.FlagN);
		 }
		 return new ModelAndView(MainetConstants.Legal.ADVOCATE_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
	 }
	 
	 @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.VIEW_ADVOCATE_MASTER)
	 public ModelAndView viewAdvocateMaster (@RequestParam(MainetConstants.Common_Constant.ID) Long id,
	            final HttpServletRequest httpServletRequest) {
	        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
	        this.getModel().setAdvocateMasterDTO(advocateMasterService.getAdvocateMasterById(id));
			this.getModel().setCourtNameList(getCourtMasterList());

	        return new ModelAndView("AdvocateMasterView", MainetConstants.FORM_NAME, this.getModel());
	 }

	 @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.EDIT_ADVOCATE_MASTER)
	 public ModelAndView editAdvocateMaster (@RequestParam(MainetConstants.Common_Constant.ID) Long id,
	            final HttpServletRequest httpServletRequest) {
	        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
	        this.getModel().setAdvocateMasterDTO(advocateMasterService.getAdvocateMasterById(id));
			 this.getModel().setCourtNameList(getCourtMasterList());
				//120788 ENV Flag for tscl env
			 if(caseEntryService.isTSCLEnvPresent()) {
				 this.getModel().setEnvFlag(MainetConstants.FlagY);
			 }else {
				 this.getModel().setEnvFlag(MainetConstants.FlagN);
			 }
	        return new ModelAndView("AdvocateMasterEdit", MainetConstants.FORM_NAME, this.getModel());
	 }
 
      //127193
	    @ResponseBody
	    @RequestMapping(params = "searchData", method = RequestMethod.POST)
	    public ModelAndView searchData(final HttpServletRequest request,
	    		 @RequestParam(required = false) Long advId, @RequestParam(required = false) Long crtId, @RequestParam(required = false) String barCouncilNo, 
	    		 @RequestParam(required = false) String advStatus) {
	        getModel().bind(request);
	        Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	        ModelAndView  mv  = null;
	        List<AdvocateMasterDTO>  advocateMasterDetails = advocateMasterService.getAdvocateMasterDetails(advId,crtId,barCouncilNo,advStatus,orgid);
	       if (CollectionUtils.isNotEmpty(advocateMasterDetails)) {  
	    	 this.getModel().setAdvocateMasterDTOList(advocateMasterDetails);
	         mv = new ModelAndView("AdvocateFormValidn", MainetConstants.FORM_NAME, this.getModel());
	        }
	       else{
	    	  mv = new ModelAndView("AdvocateFormValidn", MainetConstants.FORM_NAME,this.getModel());
			  mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	    	  this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("lgl.nofoundmsg"));	    	 
	       }
	       
	        this.getModel().getAdvocateMasterDTO().setAdv_advocateTypeId(advId);
	        this.getModel().getAdvocateMasterDTO().setAdv_courtNameId(crtId);
	        this.getModel().getAdvocateMasterDTO().setAdv_barCouncilNo(barCouncilNo);
	        return mv;
	 }
    
    //#88473

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewAdvocateForm(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId)
			throws ParseException {
		sessionCleanup(httpServletRequest);
		AdvocateMasterModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		AdvocateMasterDTO masterDto = advocateMasterService.findAdvDetByAppId(orgId, applicationId);
		model.setAdvocateMasterDTO(masterDto);
		model.getAdvocateMasterDTO().setApplicationId(applicationId);
		model.getAdvocateMasterDTO().setTaskId(taskId);
		model.getAdvocateMasterDTO().setAdvEducationDetailDTOList(advocateMasterService.findEducationDetById(masterDto.getAdvId(),masterDto.getOrgid()));
		return new ModelAndView("AdvocateMasterApprovalForm", MainetConstants.FORM_NAME, this.getModel());

	}
	
	
	@RequestMapping(method = RequestMethod.POST, params = "saveDecision")
	public ModelAndView saveDecisionApplication(final HttpServletRequest httpServletRequest)
			throws ParseException {
		bindModel(httpServletRequest);
		AdvocateMasterModel model = this.getModel();
 		AdvocateMasterDTO masterDto = model.getAdvocateMasterDTO();	
		WorkflowTaskAction workflowActionDto = model.getWorkflowActionDto();

		if (StringUtils.isEmpty(workflowActionDto.getDecision()) || StringUtils.isEmpty(workflowActionDto.getComments())) {
		if (StringUtils.isEmpty(workflowActionDto.getDecision())) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validate.decision"));
		}
		if (StringUtils.isEmpty(workflowActionDto.getComments())) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validate.remark"));
		}
		ModelAndView mv = new ModelAndView("AdvocateMasterApprovalForm", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
				getModel().getBindingResult());
		return mv;
		}

		try {
			Employee emp = UserSession.getCurrent().getEmployee();

			model.getAdvocateMasterDTO().setUpdatedBy(emp.getEmpId());
			model.getAdvocateMasterDTO().setUpdatedDate(new Date());
	
			if (workflowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			model.getAdvocateMasterDTO().setAdvStatus(MainetConstants.FlagY);
			model.getAdvocateMasterDTO().setAdvAppfromdate(new Date());
			}
			else {
				model.getAdvocateMasterDTO().setAdvStatus(MainetConstants.FlagN);
			}
			model.getAdvocateMasterDTO().setLgIpMacUpd(emp.getEmppiservername());
			
			
			advocateMasterService.saveDetails(model.getAdvocateMasterDTO());
		
			/* Update workflow */
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName("maker-checker");
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			workflowAction.setApplicationId(masterDto.getApplicationId());
			workflowAction.setDateOfAction(new Date());
			workflowAction.setOrgId(masterDto.getOrgid());
			workflowAction.setEmpId(emp.getEmpId());
			workflowAction.setEmpType(emp.getEmplType());
			workflowAction.setEmpName(emp.getEmpname());
			workflowAction.setCreatedBy(emp.getEmpId());
			workflowAction.setCreatedDate(new Date());
			workflowAction.setTaskId(masterDto.getTaskId());
			workflowAction.setModifiedBy(emp.getEmpId());
			workflowAction.setDecision(workflowActionDto.getDecision());
			workflowAction.setIsFinalApproval(false);
			workflowdto.setWorkflowTaskAction(workflowAction);
			if(workflowActionDto.getComments().length()<=1020)
			{
			workflowAction.setComments(workflowActionDto.getComments());
			}
			else
			{
				model.addValidationError("Remark field size limit exceeded.");
				ModelAndView modelAndView = new ModelAndView("LegalOpinionDecisionForm", MainetConstants.FORM_NAME, model);
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
				return modelAndView;
			}
		
			workflowExecutionService.updateWorkflow(workflowdto);

		} catch (Exception e) {
			model.addValidationError("Error while Updating Workflow:  " + " " + e.getMessage());
			ModelAndView modelAndView = new ModelAndView("AdvocateMasterApprovalForm", MainetConstants.FORM_NAME, model);
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					getModel().getBindingResult());

			return modelAndView;
		}
		return jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("lgl.legalOpinion.app.approved")));
	}

}
