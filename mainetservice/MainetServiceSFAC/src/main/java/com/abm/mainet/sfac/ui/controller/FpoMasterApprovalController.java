/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.MainetMultiTenantConnectionProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FpoMasterApprovalModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.FPO_MASTER_APPROVAL_CONTROLLER)
public class FpoMasterApprovalController extends AbstractFormController<FpoMasterApprovalModel>{

	
	@Autowired
	private FPOMasterService fpoMasService;
	
	@Autowired
	private IAMasterService iaMasterService;
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView showDetails(
			@RequestParam(MainetConstants.Sfac.APP_NO) final String applicationId,
			@RequestParam(value = MainetConstants.Sfac.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.Sfac.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.Sfac.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.Sfac.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		FpoMasterApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("FpoMastApprovalController.html");
		
		FPOMasterDto mastDto = fpoMasService.getFpoDetByAppId(Long.valueOf(applicationId));
		this.getModel().setDto(mastDto);
		if(mastDto.getUdyogAadharDate() !=null  && StringUtils.isNotEmpty(mastDto.getUdyogAadharNo())){
			this.getModel().setShowUdyogDet(MainetConstants.FlagY);
		}else
			this.getModel().setShowUdyogDet(MainetConstants.FlagN);
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		final List<LookUp> allcCatgList = CommonMasterUtility.getLevelData("ALC", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setAllocationCatgList(allcCatgList);
		List<LookUp> alcCateSubList = new ArrayList<LookUp>();
		if (mastDto.getAllocationCategory() != null) {
			List<LookUp> alcList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			alcCateSubList = alcList.stream().filter(lookUp -> lookUp.getLookUpParentId() == mastDto.getAllocationCategory())
					.collect(Collectors.toList());
			this.getModel().setAllocationSubCatgList(alcCateSubList);
		}
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		return new ModelAndView("fpoMasterApprovalForm", MainetConstants.FORM_NAME, getModel());
	}
}
 