package com.abm.mainet.property.ui.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.BillingMethodChangeModel;

/**
 * @author Arun Shinde
 * @since 28 July 2021
 *
 */
@Controller
@RequestMapping("/BillingMethodChange.html")
public class BillingMethodChangeController extends AbstractFormController<BillingMethodChangeModel> {

	private static final Logger LOGGER = Logger.getLogger(BillingMethodChangeController.class);

	@Resource
	private ILocationMasService iLocationMasService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Resource
	private SelfAssessmentService selfAssessmentService;

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private IFinancialYearService iFinancialYear;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private ViewPropertyDetailsService viewDetailService;

	@Autowired
	private IWorkflowRequestService workflowReqService;

	@Autowired
	private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

	@Resource
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IProvisionalAssesmentMstService provisionalAssesmentMstService;

	@Autowired
	private DepartmentService departmentService;
	
    @Autowired
    private IFileUploadService fileUpload;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("BillingMethodChange", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo) throws Exception {

		LOGGER.info("<---------Start method getPropertyDetails()-------->");
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		BillingMethodChangeModel model = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();

		ModelAndView mv = new ModelAndView("BillingMethodChangeValidn", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		// To check property is active or not
		List<String> checkActiveFlagList = null;
		if (StringUtils.isBlank(propNo)) {
			checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNo(oldPropNo, org.getOrgid());
		} else {
			checkActiveFlagList = assesmentMastService.checkActiveFlag(propNo, org.getOrgid());
		}
		if (CollectionUtils.isNotEmpty(checkActiveFlagList) && StringUtils.equals(MainetConstants.STATUS.INACTIVE,
				checkActiveFlagList.get(checkActiveFlagList.size() - 1))) {
			getModel().addValidationError(getApplicationSession().getMessage("property.inactive"));
			return mv;
		}

		ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.BMC, org.getOrgid());
		model.setServiceId(service.getSmServiceId());
		ProvisionalAssesmentMstDto assMst = null;
		LOGGER.info("Fetch details from assessment master");
		if (StringUtils.isBlank(propNo)) {
			assMst = assesmentMastService.fetchAssessmentMasterByOldPropNo(org.getOrgid(), oldPropNo);
		} else {
			assMst = assesmentMastService.fetchAssessmentMasterByPropNo(org.getOrgid(), propNo);
		}

		if (assMst != null) {
			if (assMst.getBillMethod() != null && MainetConstants.FlagI.equals(
					CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getBillMethod(), org).getLookUpCode())) {
				model.addValidationError(getApplicationSession().getMessage("property.billMethodindividual"));
				return mv;
			}
			model.setProvisionalAssesmentMstDto(assMst);
			// To check application already in progress
			ProvisionalAssesmentMstDto assessMst = null;
			if (StringUtils.isBlank(propNo)) {
				assessMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByOldPropNo(oldPropNo,
						org.getOrgid());
			} else {
				assessMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByPropNo(propNo, org.getOrgid());
			}
			if (assessMst != null && assessMst.getApmApplicationId() != null) {
				WorkflowRequest workflowRequest = workflowReqService
						.getWorkflowRequestByAppIdOrRefId(assessMst.getApmApplicationId(), null, org.getOrgid());
				if (workflowRequest != null && MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
					getModel().addValidationError(getApplicationSession().getMessage("property.applicationInProgress"));
					return mv;
				}
			}

			//
			LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org);
			model.setOwnershipPrefix(ownerType.getLookUpCode());

			final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
			String financialYear = null;
			for (final FinancialYear finYearTemp : finYearList) {
				financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
			}
			
			if (assMst.getBillMethod() != null) {
				String billMethodCode = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getBillMethod(), org)
						.getLookUpCode();
				model.setBillMethodCode(billMethodCode);
			}
			model.setDropDownValues(org);

			LOGGER.info("Set bill and collection details");
			ProperySearchDto searchDto = model.getSearchDto();
			searchDto.setOrgId(org.getOrgid());
			searchDto.setProertyNo(assMst.getAssNo());
			searchDto.setDeptId(service.getTbDepartment().getDpDeptid());
			List<TbBillMas> billMasList = viewDetailService.getViewData(searchDto);
			billMasList.forEach(billMas -> {
				double bmTotalDemand = 0;
				double bmTotalBalance = 0;
				for (TbBillDet tbBillDet : billMas.getTbWtBillDet()) {
					bmTotalDemand += tbBillDet.getBdCurTaxamt();
					bmTotalBalance += tbBillDet.getBdCurBalTaxamt();
				}
				billMas.setBmTotalAmount(bmTotalDemand);
				billMas.setBmTotalOutstanding(bmTotalBalance);
			});
			model.setBillMasList(billMasList);
			model.setCollectionDetails(viewDetailService.getCollectionDetails(searchDto));

			LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmChklstVerify(),
					org);
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				LOGGER.info("Trying to fetch checklist");
				List<DocumentDetailsVO> checklist = getChecklist(model, org);
				if (CollectionUtils.isEmpty(checklist)) {
					model.addValidationError(getApplicationSession().getMessage("property.checklistNotFound"));
					return mv;
				}
			}
		} else {
			model.addValidationError(getApplicationSession().getMessage("property.noDues.propertySearchValid"));
			return mv;
		}
		LOGGER.info("End method getPropertyDetails()");
		return new ModelAndView("BillingMethodChangeEdit", MainetConstants.FORM_NAME, this.getModel());
	}

	@SuppressWarnings("unchecked")
	private List<DocumentDetailsVO> getChecklist(BillingMethodChangeModel model, Organisation org) {
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> models = RestClient.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			checkListModel.setOrgId(org.getOrgid());
			checkListModel.setServiceCode(MainetConstants.Property.BMC);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
			checklistReqDto.setDataModel(checkListModel);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
				List<DocumentDetailsVO> checkListList = Collections.emptyList();
				checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
				if ((checkListList != null) && !checkListList.isEmpty()) {
					this.getModel().setChecklist(checkListList);
					return checkListList;
				}
			}
		}
		return null;
	}

	@RequestMapping(params = "backToSearch", method = RequestMethod.POST)
	public ModelAndView backToSearch(HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("BillingMethodChangeValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@Override
	@RequestMapping(params = "generatWorkOrderAbstract", method = RequestMethod.GET)
	public String generatWorkOrder(final HttpServletRequest request) {
		try {
			bindModel(request);
			Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
			final String applicationId = UserSession.getCurrent().getScrutinyCommonParamMap()
					.get(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
			final String serviceId = UserSession.getCurrent().getScrutinyCommonParamMap()
					.get(MainetConstants.SCRUTINY_COMMON_PARAM.SM_SERVICE_ID);
			final String taskId = UserSession.getCurrent().getScrutinyCommonParamMap()
					.get(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID);
			WorkflowTaskAction workFlowActionDto = getModel().getWorkflowActionDto();
			workFlowActionDto.setApplicationId(Long.valueOf(applicationId));
			workFlowActionDto.setTaskId(Long.valueOf(taskId));
			workFlowActionDto.setServiceId(Long.valueOf(serviceId));
			workFlowActionDto.setOrgId(orgId);
			getModel().setWorkflowActionDto(workFlowActionDto);
			List<AssesmentMastEntity> provAssDtoList = assesmentMastService
					.getAssesmentMstEntListByAppId(Long.valueOf(applicationId), orgId);
			AssesmentMastEntity assesmentMastEntity = provAssDtoList.get(provAssDtoList.size() - 1);
			List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity = assesmentMastService
					.fetchOwnerDetailListByProAssId(assesmentMastEntity, orgId);
			// getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
			String applicantName = oldOwnerDtlEntity.stream().map(owner -> owner.getAssoOwnerName())
					.collect(Collectors.joining(" & "));
			getModel().setApplicantName(applicantName);
			getModel().setServiceName(
					serviceMaster.getServiceMaster(Long.valueOf(serviceId), orgId).getSmServiceNameMar());
			getModel().setDeptName(
					departmentService.getDepartmentDescByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.now();
			getModel().setCurrentTime(dtf.format(localTime));
		} catch (final Exception e) {
			logger.error("Problem occurred generatWorkOrder service:", e);
			e.printStackTrace();
		}
		return new String("BillMethodWorkOrder");
	}

	@RequestMapping(params = "saveWorkOrder", method = RequestMethod.POST)
	public ModelAndView saveHearing(final HttpServletRequest httpServletRequest) throws Exception {
		this.getModel().bind(httpServletRequest);
		WorkflowTaskAction workFlowActionDto = getModel().getWorkflowActionDto();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Employee emp = UserSession.getCurrent().getEmployee();
		String processName = serviceMasterService.getProcessName(workFlowActionDto.getServiceId(), org.getOrgid());
		if (processName != null) {
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			workflowAction.setTaskId(workFlowActionDto.getTaskId());
			workflowAction.setApplicationId(workFlowActionDto.getApplicationId());
			workflowAction.setDateOfAction(new Date());
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			workflowAction.setOrgId(org.getOrgid());
			workflowAction.setEmpId(emp.getEmpId());
			workflowAction.setModifiedBy(emp.getEmpId());
			workflowAction.setEmpType(emp.getEmplType());
			workflowAction.setEmpName(emp.getEmpname());
			workflowAction.setCreatedBy(emp.getEmpId());
			workflowAction.setCreatedDate(new Date());
			workflowdto.setProcessName(processName);
			workflowdto.setWorkflowTaskAction(workflowAction);
			workflowExecutionService.updateWorkflow(workflowdto);
		}		
		this.getModel().setSuccessMessage(getApplicationSession().getMessage("property.billMethodWorkOrderSave"));
		return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
	}

}
