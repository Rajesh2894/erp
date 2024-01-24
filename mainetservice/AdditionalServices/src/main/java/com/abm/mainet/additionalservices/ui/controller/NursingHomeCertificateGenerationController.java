package com.abm.mainet.additionalservices.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.CFCSonographyMastDto;
import com.abm.mainet.additionalservices.rest.ui.controller.CFCSchedulingCounterDetController;
import com.abm.mainet.additionalservices.service.CFCNursingHomeService;
import com.abm.mainet.additionalservices.ui.model.NursingHomeCertificateGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/NursingHomeCertificateGeneration.html")
public class NursingHomeCertificateGenerationController extends AbstractFormController<NursingHomeCertificateGenerationModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NursingHomeCertificateGenerationController.class);
	@Autowired
	private CFCNursingHomeService cfcNursingHomeService;
	
	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMst;

	@Resource
	private TbServicesMstService tbServicesMstService;

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
	public ModelAndView showCompleteCertificate(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
			@RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) Long serviceId,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest request) {

		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // #129863
		String shortCode = tbServicesMstService.getServiceShortDescByServiceId(serviceId);
		 if(shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			 CFCSonographyMastDto mastDto = cfcNursingHomeService.findDetByApplicationId(applicationId);
				this.getModel().setCfcSonographyMastDto(mastDto);
				this.getModel().setServiceCode(shortCode);
		 }else {
			 CFCNursingHomeInfoDTO cfcNuringHomeInfoDto = cfcNursingHomeService.findByApplicationId(applicationId);
			 this.getModel().setCfcNuringHomeInfoDto(cfcNuringHomeInfoDto);
			 this.getModel().setServiceCode(shortCode);
		 }
	

		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMst.findById(applicationId);
		
		this.getModel().setCfcApplicationMst(cfcApplicationMst);

		this.getModel().setNewDate(new Date()); 
		
		 int year = Utility.getYearByDate(new Date());
		// #129863
		final Calendar calenderobj = Calendar.getInstance();
		calenderobj.setTime(new Date());
		final int yr = calenderobj.get(Calendar.YEAR);
		final int month = calenderobj.get(Calendar.MONTH); 
		final int day = calenderobj.get(Calendar.DAY_OF_MONTH);
		String expriryDate = (day-1) + "/" + (month + 1) + "/" + (yr + 5);
		SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.DATE_FORMAT);

		Date date = null;
		try {
			date = sdf.parse(expriryDate);
		} catch (ParseException e) {
			LOGGER.error("Error while parsing date" + e);
		}
		sdf = new SimpleDateFormat("dd MMM yyyy");
		expriryDate = sdf.format(date);
		this.getModel().setExpDate(expriryDate);
		this.getModel().setTodate(date);
		 
		 if(shortCode.equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg)) {
	      this.getModel().setYear(year+3);
           String yWord = Utility.convertNumberToWord(year+3).replace("-",MainetConstants.WHITE_SPACE);
           this.getModel().setYearWord(eachLetterCaps(yWord));
		 }
		
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setApplicationId(applicationId);
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(actualTaskId);

		
		  WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		  workflowProcessParameter.setProcessName("scrutiny");
		  workflowProcessParameter.setWorkflowTaskAction(taskAction); 
		  
		  try {
			 ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class) .updateWorkflow(workflowProcessParameter);
		  }
		  catch (Exception exception) { 
			  throw new FrameworkException("Exception Occured when Update Workflow methods",exception); 
			 }
		  if(shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			  return new ModelAndView("cfcSonographyCompletionCertificate", MainetConstants.FORM_NAME, this.getModel());  
		  }else			  
		  return new ModelAndView("cfcComlpetionCertificate", MainetConstants.FORM_NAME, this.getModel());
	}
	
	public static String eachLetterCaps(String yWord) {
		String words[] = yWord.split(MainetConstants.WHITE_SPACE);
        String yearWord="";
        for(String word:words)
        {
        	if(!word.isEmpty()) {
	        	String fL=word.substring(0,1);           
	            String rL=word.substring(1);
	            yearWord+=fL.toUpperCase()+rL+MainetConstants.WHITE_SPACE;
        	}
        }
        return yearWord;
	}	

}
