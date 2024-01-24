package com.abm.mainet.additionalservices.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.service.NOCForBuildingPermissionService;
import com.abm.mainet.additionalservices.ui.model.NOCForBuildingPermissionApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

@Controller
@RequestMapping(value = "/NOCForBuildingPermissionApproval.html")
public class NOCForBuildingPermissionApprovalController
		extends AbstractFormController<NOCForBuildingPermissionApprovalModel> {

	@Resource
	private TbServicesMstService tbServicesMstService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private NOCForBuildingPermissionService nocBuildPerService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	
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
		NOCForBuildingPermissionApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(NOCForBuildPermissionConstant.NOCFORBUILDINGPERMISSIONAPPROVAL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final int langId = UserSession.getCurrent().getLanguageId();
		Long application = Long.valueOf(complainNo);
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		getModel().bind(httpServletRequest);
		deptList = tbDepartmentService.findByOrgId(orgId, Long.valueOf(langId));
		serviceMstList = tbServicesMstService.findAllServiceListByOrgId(orgId);
		this.getModel().setDeptList(deptList);
		this.getModel().setServiceMstList(serviceMstList);
		model.addAttribute("locations", loadLocation());
		Boolean checkFinalAproval = nocBuildPerService.checkEmployeeRole(UserSession.getCurrent());
		model.addAttribute("CheckFinalApp", checkFinalAproval);
		NOCForBuildingPermissionDTO tbBirthCertDto = nocBuildPerService.getRegisteredAppliDetail(application, orgId);
		this.getModel().setNocBuildingPermissionDto(tbBirthCertDto);
		List<CFCAttachment> checkList = new ArrayList<>();
		checkList = iChecklistVerificationService.findAttachmentsForAppId(application, null, orgId);
		approvalModel.setFetchDocumentList(checkList);
		getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId));
        getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
        return new ModelAndView(NOCForBuildPermissionConstant.NOC_FOR_BUILDING_PERMISSION_APPROVAL,
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
				String.valueOf(this.getModel().getNocBuildingPermissionDto().getApmApplicationId()),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getWorkflowActionDto().getTaskName());// request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(NOCForBuildPermissionConstant.WFSTATUS,
				this.getModel().getNocBuildingPermissionDto().getBirthRegstatus());
		object.put("APPSTATUS", this.getModel().getNocBuildingPermissionDto().getStatus());
		object.put("LOI", this.getModel().getLoiNo());
		return object;
	}

	@RequestMapping(params = "nocCertificate", method = { RequestMethod.POST })
	public ModelAndView nocCertificate(HttpServletRequest request) {
		// bindModel(request);
		final NOCForBuildingPermissionApprovalModel birthModel = this.getModel();
		this.getModel();
		NOCForBuildingPermissionDTO dto = birthModel.getNocBuildingPermissionDto();

		LookUp usaget = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(dto.getUsageType1()),
				new Organisation(dto.getOrgId()));

		LookUp title = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(dto.getTitleId()),
				new Organisation(dto.getOrgId()));
		dto.setUsaget(usaget.getDescLangFirst());
		dto.setFinacialYear(Utility.getCurrentFinancialYear());
		dto.setTitle(title.getDescLangFirst());

		SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

		dto.setRefdat(formatter.format(dto.getRefDate()));
		dto.setSaleD(formatter.format(dto.getSaleDate()));
		dto.setScrutinyD(formatter.format(dto.getScrutinyDate()));
		dto.setUpdateD(formatter.format(dto.getUpdatedDate()));

		return new ModelAndView("nocCertificate", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "generateLoiCharges", method = RequestMethod.POST)
	public ModelAndView generateLoiCharges(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		NOCForBuildingPermissionApprovalModel model = this.getModel();
				model.setLoiChargeApplFlag(MainetConstants.FlagY);
		model.setShowFlag(MainetConstants.FlagY);
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(NOCForBuildPermissionConstant.NOC_BUILDING_PERMISSION_TAX_CODE, MainetConstants.PG_REQUEST_PROPERTY.TXN,
				UserSession.getCurrent().getOrganisation());
		model.setTaxDesc(lookup.getLookUpDesc());
		model.setTotalLoiAmount(Math.ceil(model.getNocBuildingPermissionDto().getMalabaCharge() * model.getNocBuildingPermissionDto().getBuiltUpArea()));
		return new ModelAndView(NOCForBuildPermissionConstant.NOC_FOR_BUILDING_PERMISSION_APPROVAL,
				MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "downloadDemandDraft", method = RequestMethod.POST)
	public @ResponseBody String downloadDetailBill(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		NOCForBuildingPermissionApprovalModel model = this.getModel();
		return ServiceEndpoints.NOC_BIRT_REPORT_URL + "DemandOnFinalApproval.rptdesign&OrgId="
				+ UserSession.getCurrent().getOrganisation().getOrgid() + "&AppId=" + model.getNocBuildingPermissionDto().getApmApplicationId();

	}
	
}
