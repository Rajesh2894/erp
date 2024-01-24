package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.constants.WorkManagementConstant;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.rest.dto.ExpiryItemsDto;
import com.abm.mainet.workManagement.rest.dto.PurchaseRequistionDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.TenderInitiationApprovalModel;

/**
 * @author vishwajeet.kumar
 * @since 05 feb 2020
 */
@Controller
@RequestMapping("/TenderInitiationApproval.html")
public class TenderInitiationApprovalController extends AbstractFormController<TenderInitiationApprovalModel> {

	@Autowired
	private TenderInitiationService tenderInitiationService;

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private TbDepartmentService iTbDepartmentService;
	
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
		TenderInitiationApprovalModel approvalModel = this.getModel();
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
		.sessionCleanUpForFileUpload();
		this.getModel().setDepartmentList(
				departmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		Long tenderId = tenderInitiationService.getTenderIdByInitiationNumber(tenderInitiationNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		approvalModel.setInitiationDto(tenderInitiationService.getPreparedTenderDetails(tenderId));
		approvalModel.getWorkflowActionDto().setReferenceId(tenderInitiationNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setTenderTpyes(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		approvalModel.setVenderCategoryList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VEC,
				UserSession.getCurrent().getOrganisation()));
		approvalModel.setWorkDurationUnit(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.UTS,
				UserSession.getCurrent().getOrganisation()));

		TenderMasterDto masDto = tenderInitiationService.getPreparedTenderDetails(tenderId);

		masDto.setDeptCode(iTbDepartmentService.findById(masDto.getDeptId()).getDpDeptcode());		
		this.getModel().getInitiationDto().setDeptCode(masDto.getDeptCode());
		approvalModel.setInitiationDto(masDto);
		if (MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(masDto.getDeptCode())) {
			getPurchaseRequistionData(masDto);
		} else {
			List<Long> workIds = new ArrayList<>();
			masDto.getWorkDto().forEach(work -> {
				workIds.add(work.getWorkId());
			});
			this.getModel().setWorkList(workDefinitionService
					.findAllWorkByWorkList(UserSession.getCurrent().getOrganisation().getOrgid(), workIds));

			masDto.getWorkDto().forEach(tenderWork -> {
				this.getModel().getWorkList().forEach(work -> {
					work.setTenderInitiated(true);
					if (work.getWorkId().longValue() == tenderWork.getWorkId().longValue()) {
						String cpdValue = CommonMasterUtility
								.getCPDDescription(tenderWork.getTenderType().longValue(), MainetConstants.FlagV);
						if (cpdValue.equals(MainetConstants.WorksManagement.PERCENT)) {
							work.setTenderpercent(cpdValue);
						}
						if (cpdValue.equals(MainetConstants.WorksManagement.AMT)) {
							work.setTenderpercent(cpdValue);
						}
					}
				});
			});
		}		
		return new ModelAndView("tenderInitiationApproval", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "saveTenderApproval", method = RequestMethod.POST)
	public Map<String, Object> saveTenderApproval(HttpServletRequest request) {
		this.getModel().bind(request);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		this.getModel().saveTenderApproval();
		map.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		map.put("tenderInitiationNo", this.getModel().getInitiationNo());
		map.put("tndApprovalStatus", this.getModel().getTndApprovalStatus());
		return map;
	}

	@Override
	@RequestMapping(params = "viewRefNoDetails")
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		this.getModel().bind(request);
		TenderInitiationApprovalModel approvalModel = this.getModel();
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();

		this.getModel().setDepartmentList(
				departmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setCompletedFlag(MainetConstants.FlagY);
		Long tenderId = tenderInitiationService.getTenderIdByInitiationNumber(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		approvalModel.setInitiationDto(tenderInitiationService.getPreparedTenderDetails(tenderId));
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(taskId);
		// approvalModel.getWorkflowActionDto().setTaskName(taskName);

		approvalModel.setTenderTpyes(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		approvalModel.setVenderCategoryList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VEC,
				UserSession.getCurrent().getOrganisation()));
		approvalModel.setWorkDurationUnit(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.UTS,
				UserSession.getCurrent().getOrganisation()));

		TenderMasterDto masDto = tenderInitiationService.getPreparedTenderDetails(tenderId);

		approvalModel.setInitiationDto(masDto);
		masDto.setDeptCode(iTbDepartmentService.findById(masDto.getDeptId()).getDpDeptcode());		
		if (MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(masDto.getDeptCode())) {
					getPurchaseRequistionData(masDto);
		} else {
			List<Long> workIds = new ArrayList<>();
			masDto.getWorkDto().forEach(work -> {
				workIds.add(work.getWorkId());
			});
			this.getModel().setWorkList(workDefinitionService
					.findAllWorkByWorkList(UserSession.getCurrent().getOrganisation().getOrgid(), workIds));

			masDto.getWorkDto().forEach(tenderWork -> {
				this.getModel().getWorkList().forEach(work -> {
					work.setTenderInitiated(true);
					if (work.getWorkId().longValue() == tenderWork.getWorkId().longValue()) {
						String cpdValue = CommonMasterUtility.getCPDDescription(tenderWork.getTenderType().longValue(),
								MainetConstants.FlagV);
						if (cpdValue.equals(MainetConstants.WorksManagement.PERCENT)) {
							work.setTenderpercent(cpdValue);
						}
						if (cpdValue.equals(MainetConstants.WorksManagement.AMT)) {
							work.setTenderpercent(cpdValue);
						}
					}
				});
			});
		}
		return new ModelAndView("tenderInitiationApproval", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/**
	 * @param tenderDto
	 */
	private void getPurchaseRequistionData(TenderMasterDto tenderDto) {
		List<Long> workIds =new ArrayList<>();		  
		final ResponseEntity<?> responseEntity;

		if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(tenderDto.getProjectCode())) {
			tenderDto.getWorkDto().forEach(dto -> workIds.add(dto.getPrId()));
			
			PurchaseRequistionDto purRequistionDto =new PurchaseRequistionDto();
			purRequistionDto.setOrgId(tenderDto.getOrgId());
			purRequistionDto.setPrIds(workIds);
			responseEntity = RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.FETCH_PURCHASE_REQ);
		}else {
			tenderDto.getWorkDto().forEach(dto -> workIds.add(dto.getExpiryId()));
			  
			ExpiryItemsDto expiryItemsDto =new ExpiryItemsDto();
			expiryItemsDto.setOrgId(tenderDto.getOrgId());
			expiryItemsDto.setExpiryIds(workIds);
			responseEntity = RestClient.callRestTemplateClient(expiryItemsDto, ServiceEndpoints.FETCH_EXPIRY_DISPOSAL_CODES);
		}
		
		if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
			final LinkedHashMap<String, String> responseMap = (LinkedHashMap<String, String>) responseEntity.getBody();
			tenderDto.getWorkDto().forEach(tenderWork -> {
				if(null != tenderWork.getPrId()) {
					tenderWork.setWorkCode(responseMap.get(tenderWork.getPrId().toString()));
				} else
					tenderWork.setWorkCode(responseMap.get(tenderWork.getExpiryId().toString()));

				tenderWork.setWorkName(MainetConstants.BLANK);
			});
		} else {
			tenderDto.getWorkDto().forEach(tenderWork -> {
				tenderWork.setWorkCode(MainetConstants.BLANK);
				tenderWork.setWorkName(MainetConstants.BLANK);
			});
		}
	}
}
