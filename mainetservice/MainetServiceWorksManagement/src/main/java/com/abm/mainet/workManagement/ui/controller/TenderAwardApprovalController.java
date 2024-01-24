package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.ui.model.TenderAwardApprovalModel;
import com.abm.mainet.workManagement.ui.model.TenderInitiationApprovalModel;

@Controller
@RequestMapping("/TenderAwardApproval.html")
public class TenderAwardApprovalController extends AbstractFormController<TenderAwardApprovalModel>{
	@Autowired
	private TenderInitiationService tenderInitiationService;

	@Autowired
	private TbDepartmentService departmentService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView tenderApproval(
			@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String tenderInitiationNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		TenderAwardApprovalModel approvalModel = this.getModel();
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		this.getModel().setDepartmentList(
				departmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		Long tenderId = tenderInitiationService.getTenderIdByInitiationNumber(tenderInitiationNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		TenderMasterDto tenderDto =tenderInitiationService.getPreparedTenderDetails(tenderId);
		List<TenderWorkDto> workDtoList = new ArrayList<TenderWorkDto>();
		tenderDto.getWorkDto().forEach(dto -> {
			if (dto.getTenderType() != null) {
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getTenderType(),
						UserSession.getCurrent().getOrganisation());
				dto.setTenderTypeCode(lookUp.getLookUpCode());
			}
			workDtoList.add(dto);

		});
		tenderDto.setWorkDto(workDtoList);
		approvalModel.setInitiationDto(tenderDto);
		approvalModel.getWorkflowActionDto().setReferenceId(tenderInitiationNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		this.getModel().setMode(MainetConstants.FlagV);
		final Long vendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
						UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
				.getLookUpId();
		model.addAttribute(MainetConstants.WorksManagement.VENDOR_LIST,
				ApplicationContextProvider.getApplicationContext().getBean(TbAcVendormasterService.class)
						.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
		return new ModelAndView("tenderAwardApproval", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
    @RequestMapping(params = "saveTenderApproval", method = RequestMethod.POST)
    public Map<String, Object> saveTenderApproval(HttpServletRequest request) {
        this.getModel().bind(request);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        this.getModel().saveTenderApproval();
        map.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        return map;
    }

}
