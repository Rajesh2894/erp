package com.abm.mainet.additionalservices.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.TreeCuttingInfoDto;
import com.abm.mainet.additionalservices.service.TreeCuttingPermissionService;
import com.abm.mainet.additionalservices.ui.model.NursingHomeApprovalModel;
import com.abm.mainet.additionalservices.ui.model.TreeCuttingTrimminApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/TreeCuttingTrimmingApproval.html")
public class TreeCuttingTrimmingApprovalController  extends AbstractFormController<TreeCuttingTrimminApprovalModel>{
	
	@Resource
	private TbServicesMstService tbServicesMstService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private TreeCuttingPermissionService treeCuttingService;

	
	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMst;

	private List<TbDepartment> deptList = Collections.emptyList();
	private List<TbServicesMst> serviceMstList = Collections.emptyList();

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
		TreeCuttingTrimminApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(NOCForBuildPermissionConstant.NOCFORBUILDINGPERMISSIONAPPROVAL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final int langId = UserSession.getCurrent().getLanguageId();
		Long application = Long.valueOf(complainNo);
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		getModel().bind(httpServletRequest);
		/*
		 * deptList = tbDepartmentService.findByOrgId(orgId, Long.valueOf(langId));
		 * serviceMstList=tbServicesMstService.findAllServiceListByOrgId(orgId);
		 * this.getModel().setDeptList(deptList);
		 * this.getModel().setServiceMstList(serviceMstList);
		 */
		model.addAttribute("locations", loadLocation());
		/*
		 * Boolean checkFinalAproval =
		 * cfcNursingHomeService.checkEmployeeRole(UserSession.getCurrent());
		 * model.addAttribute("CheckFinalApp", checkFinalAproval);
		 */
		/*
		 * CFCNursingHomeInfoDTO cfcNuringHomeInfoDto = cfcNursingHomeService
		 * .findByApplicationId(Long.parseLong(complainNo));
		 */
		
		TreeCuttingInfoDto treeCuttingInfoDto=treeCuttingService.getTreeCuttingInfo(Long.parseLong(complainNo));
		
		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMst.findById(Long.parseLong(complainNo));
		this.getModel().setCfcApplicationMst(cfcApplicationMst);
		//this.getModel().setCfcNuringHomeInfoDto(cfcNuringHomeInfoDto);
		this.getModel().setCuttingInfoDto(treeCuttingInfoDto);
		List<CFCAttachment> checkList = new ArrayList<>();
		List<CFCAttachment> checklist = new ArrayList<>();
		checkList = iChecklistVerificationService.findAttachmentsForAppId(application, null, orgId);
		for (int i = 0; i < checkList.size(); i++) {
			if (checkList.get(i).getClmRemark() == null) {
				checklist.add(checkList.get(i));
			}
		}
		approvalModel.setFetchDocumentList(checklist);

		return new ModelAndView("treeCuttingServiceApproval",
				MainetConstants.FORM_NAME, this.getModel());

	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}

	@ResponseBody
	@RequestMapping(params = "saveRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveBirthRegApproval(HttpServletRequest request) {
		getModel().bind(request);
		this.getModel().saveApprovalDetails(
				String.valueOf(this.getModel().getCuttingInfoDto().getApmApplicationId()),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getWorkflowActionDto().getTaskName(),
				this.getModel().getWorkflowActionDto().getTaskId());// request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(NOCForBuildPermissionConstant.WFSTATUS,
				this.getModel().getCuttingInfoDto().getBirthRegstatus());
		return object;
	}
}
