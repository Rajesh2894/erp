package com.abm.mainet.socialsecurity.ui.controller;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.socialsecurity.service.IBeneficiaryPaymentOrderService;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.ui.dto.BeneficiaryPaymentOrderDto;
import com.abm.mainet.socialsecurity.ui.model.BeneficiaryPaymentOrderModel;
import com.abm.mainet.socialsecurity.ui.model.PensionSchemeMasterModel;

@Controller
@RequestMapping("BeneficiaryPaymentOrder.html")

public class BeneficiaryPaymentOrderController extends AbstractFormController<BeneficiaryPaymentOrderModel> {

	private static final Logger logger = Logger.getLogger(BeneficiaryPaymentOrderController.class);

	@Autowired
	ServiceMasterService serviceMasterService;
	@Autowired
	TbDepartmentService tbDepartmentService;
	@Autowired
	IBeneficiaryPaymentOrderService beneficiaryPaymentOrderService;
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;
	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IOrganisationService iOrganisationService;
	 
	@Autowired
	private IPensionSchemeMasterService pensionSchemeMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(httpServletRequest);
		BeneficiaryPaymentOrderModel bpomodel = this.getModel();
		bpomodel.getAndSetCommonValues();
		return index();
	}

	@RequestMapping(params = "filterSearchData", method = { RequestMethod.POST })
	public @ResponseBody List<BeneficiaryPaymentOrderDto> filterSearchData(final HttpServletRequest request,
			@RequestParam(value = "serviceId") final Long serviceId) {
		BeneficiaryPaymentOrderModel bpomodel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BeneficiaryPaymentOrderDto> filterSearchDataList = beneficiaryPaymentOrderService
				.filterSearchData(serviceId, orgId);
		bpomodel.setDto(filterSearchDataList);
		return filterSearchDataList;
	}

	@RequestMapping(params = "saveBeneficiaryDetails", method = { RequestMethod.POST })
	public ModelAndView saveBeneficiaryDetails(final HttpServletRequest request,
			@RequestParam(value = "list[]") final String[] list, @RequestParam(value = "remark") final String remark) {
		this.getModel().bind(request);
		BeneficiaryPaymentOrderModel bpomodel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		String ipMacAddress = Utility.getClientIpAddress(request);
		Calendar dtoCalendar = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		boolean error = false;
		// added by rahul.chaubey:START
		// #33167
		for (String id : list) {
			Date certificateDate = beneficiaryPaymentOrderService.getCertificateDate(id, orgId);
			BeneficiaryPaymentOrderDto dto = beneficiaryPaymentOrderService.getRtgsData(id, orgId);
			if(certificateDate!=null) {
			if (certificateDate.compareTo(today.getTime()) < 0) {
				this.getModel().addValidationError(ApplicationSession.getInstance()
						.getMessage(MainetConstants.SocialSecurity.LIFE_CERTIFICATE + id));
				error = true;
			}
			}
			if (dto.getWorkOrdrerDate() != null) {
				dtoCalendar.setTime(dto.getWorkOrdrerDate());
				// ------------------------------------------MONTHLY
				// STARTS------------------------------------------
				if (CommonMasterUtility
						.getNonHierarchicalLookUpObject(dto.getPaymentScheduleId(),
								UserSession.getCurrent().getOrganisation())
						.getDescLangFirst().equals(MainetConstants.SocialSecurity.MONTHLY) && error != true) {
					YearMonth paidYearMonth = YearMonth.of(dtoCalendar.get(Calendar.YEAR),
							dtoCalendar.get(Calendar.MONTH));
					YearMonth currentYearMonth = YearMonth.of(today.get(Calendar.YEAR), today.get(Calendar.MONTH));
					if (paidYearMonth.compareTo(currentYearMonth) == 0) {
						this.getModel().addValidationError(ApplicationSession.getInstance()
								.getMessage(MainetConstants.SocialSecurity.MONTHLY_PAYMENT + id));
						error = true;
					}
				}
				// ------------------------------------------MONTHLY
				// ENDS------------------------------------------

				// ------------------------------------------BI-MONTHLY
				// STARTS------------------------------------------
				else if (CommonMasterUtility
						.getNonHierarchicalLookUpObject(dto.getPaymentScheduleId(),
								UserSession.getCurrent().getOrganisation())
						.getDescLangFirst().equals(MainetConstants.SocialSecurity.BIMONTHLY) && error != true) // bimonthly
				{
					if (beneficiaryPaymentOrderService.getBiMonthlyCount(id, orgId,
							today.MONTH) >= MainetConstants.NUMBERS.TWO) {
						this.getModel().addValidationError(ApplicationSession.getInstance()
								.getMessage(MainetConstants.SocialSecurity.BIMONTHLY_PAYMENT + id));
						error = true;
					}

				}
				// ------------------------------------------BI-MONTHLY
				// ENDS------------------------------------------

				// ------------------------------------------YEARLY
				// STARTS------------------------------------------
				else if (CommonMasterUtility
						.getNonHierarchicalLookUpObject(dto.getPaymentScheduleId(),
								UserSession.getCurrent().getOrganisation())
						.getDescLangFirst().equals(MainetConstants.SocialSecurity.YEARLY) && error != true) // yearly
				{
					if (dtoCalendar.YEAR == today.YEAR) {
						this.getModel().addValidationError(ApplicationSession.getInstance()
								.getMessage(MainetConstants.SocialSecurity.YEARLY_PAYMENT + id));
						error = true;
					}

				}
				// ------------------------------------------YEARLY
				// ENDS------------------------------------------

				// ------------------------------------------HALF-YEARLY
				// STARTS------------------------------------------

				else if (CommonMasterUtility
						.getNonHierarchicalLookUpObject(dto.getPaymentScheduleId(),
								UserSession.getCurrent().getOrganisation())
						.getDescLangFirst().equals(MainetConstants.SocialSecurity.HALFYEARLY) && error != true) {
					if (dtoCalendar.YEAR == today.YEAR) {

						if (today.MONTH - 1 >= Calendar.JANUARY && today.MONTH - 1 <= Calendar.JUNE) { // checks in
																										// which half
																										// the current
																										// date is.
							if (dtoCalendar.MONTH - 1 >= Calendar.JANUARY && dtoCalendar.MONTH - 1 <= Calendar.JUNE) // first
																														// half
							{
								this.getModel().addValidationError(ApplicationSession.getInstance()
										.getMessage(MainetConstants.SocialSecurity.FIRST_HALF_YEARLY_PAYMENT + id));
								error = true;
							}

						} else // second half
						{
							if (dtoCalendar.MONTH - 1 >= Calendar.JULY && dtoCalendar.MONTH - 1 <= Calendar.DECEMBER) {
								this.getModel().addValidationError(ApplicationSession.getInstance()
										.getMessage(MainetConstants.SocialSecurity.SECOND_HALF_YEARLY_PAYMENT + id));
								error = true;
							}
						}
					}

				}
				// ------------------------------------------HALF-YEARLY
				// ENDS------------------------------------------

				// ------------------------------------------QUATERLY
				// START-----------------------------------------
				else if (CommonMasterUtility
						.getNonHierarchicalLookUpObject(dto.getPaymentScheduleId(),
								UserSession.getCurrent().getOrganisation())
						.getDescLangFirst().equals(MainetConstants.SocialSecurity.QUATERLY) && error != true) {

					if (today.MONTH - 1 >= Calendar.JANUARY && today.MONTH - 1 <= Calendar.MARCH) {
						// 1st quarter
						if (dtoCalendar.MONTH - 1 >= Calendar.JANUARY && dtoCalendar.MONTH - 1 <= Calendar.MARCH) // first
																													// half
						{
							this.getModel().addValidationError(ApplicationSession.getInstance()
									.getMessage(MainetConstants.SocialSecurity.FIRST_QUATERLY_PAYMENT + id));
							error = true;
						}
					} else if (today.MONTH - 1 >= Calendar.APRIL && today.MONTH - 1 <= Calendar.JUNE) {

						if (dtoCalendar.MONTH - 1 >= Calendar.APRIL && dtoCalendar.MONTH - 1 <= Calendar.JUNE) // Second
																												// Quarter
						{// 2nd quarter
							this.getModel().addValidationError(ApplicationSession.getInstance()
									.getMessage(MainetConstants.SocialSecurity.SECOND_QUATERLY_PAYMENT + id));
							error = true;
						}
					} else if (today.MONTH - 1 >= Calendar.JULY && today.MONTH - 1 <= Calendar.SEPTEMBER) {

						if (dtoCalendar.MONTH - 1 >= Calendar.JULY && dtoCalendar.MONTH - 1 <= Calendar.SEPTEMBER) // Third
																													// Quarter
						{// 3rd quarter
							this.getModel().addValidationError(ApplicationSession.getInstance()
									.getMessage(MainetConstants.SocialSecurity.THIRD_QUATERLY_PAYMENT + id));
							error = true;
						}
					}

					else if (today.MONTH - 1 >= Calendar.OCTOBER && today.MONTH - 1 <= Calendar.DECEMBER) {

						if (dtoCalendar.MONTH - 1 >= Calendar.OCTOBER && dtoCalendar.MONTH - 1 <= Calendar.DECEMBER) // Fourth
																														// Quarter
						{
							this.getModel().addValidationError(ApplicationSession.getInstance()
									.getMessage(MainetConstants.SocialSecurity.FOURTH_QUATERLY_PAYMENT + id));
							error = true;
						}
					}
				}
			}

			// ------------------------------------------QUATERLY
			// ENDS------------------------------------------
		}
		if (error == true) {
			ModelAndView modelAndView = new ModelAndView("beneficiaryPaymentOrderValidn", MainetConstants.FORM_NAME,
					this.getModel());
			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());

			return modelAndView;
		} else {
			// calls model.
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
			this.getModel().setEnvflag(MainetConstants.FlagY);
		
			bpomodel.saveBeneficiaryDetails(orgId, langId, list, remark, ipMacAddress);
			bpomodel.getWorkflowActionDto().getReferenceId(); // command.workflowActionDto.referenceId
			return new ModelAndView("rtgsPaymentAdviceReport", MainetConstants.FORM_NAME, bpomodel);
		}
		// END
	}

	// workflow show details
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final String serviceId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			final HttpServletRequest request) {
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(request);
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		BeneficiaryPaymentOrderModel model = this.getModel();
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setReferenceId(applicationId);
		model.getAndSetCommonValues();
		try {

			BeneficiaryPaymentOrderDto dto = beneficiaryPaymentOrderService.getViewDataFromRtgsPayment(orgId,
					applicationId);
			model.setBpoDto(dto);

		} catch (final Exception ex) {
			logger.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}

		return new ModelAndView("beneficiaryPaymentOrderView", MainetConstants.FORM_NAME, model);
	}

	// workflow save decision
	@RequestMapping(params = "saveDecision", method = RequestMethod.POST)
	public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

		JsonViewObject respObj = null;

		this.bindModel(httpServletRequest);

		BeneficiaryPaymentOrderModel aModel = this.getModel();

		String decision = aModel.getWorkflowActionDto().getDecision();

		boolean updFlag = aModel.callWorkFlow();
		boolean lastApprove = false;
		String benificiaryNo=null;
		if (beneficiaryPaymentOrderService.checkAccountActiveOrNot()
				&& decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
				&& iWorkFlowTypeService.isLastTaskInCheckerTaskList(aModel.getWorkflowActionDto().getTaskId())) {
			benificiaryNo=aModel.accountBillPostingForthesocialSecurity();
			lastApprove = true;

		}
		if (updFlag) {

			if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
				if (lastApprove) {

					respObj = JsonViewObject.successResult(
							ApplicationSession.getInstance().getMessage("assest.info.application.approved")+ "  Benificiary No is  :  "
									+ benificiaryNo);
				}

			}

			else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
			else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("assest.info.application.sendBack"));
		} else {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
		}

		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}
	
	@RequestMapping(params = "filterSearchDatas", method = { RequestMethod.POST })
	public @ResponseBody List<BeneficiaryPaymentOrderDto> filterSearchDatas(final HttpServletRequest request) {
		this.getModel().bind(request);
		BeneficiaryPaymentOrderModel bpomodel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BeneficiaryPaymentOrderDto> filterSearchDataList = beneficiaryPaymentOrderService
				.filterSearchDatas(bpomodel.getApplicationformdto().getSelectSchemeName(), orgId,bpomodel.getApplicationformdto().getSwdward1(),bpomodel.getApplicationformdto().getSwdward2(),bpomodel.getApplicationformdto().getSubSchemeName());
		bpomodel.setDto(filterSearchDataList);
		return filterSearchDataList;
	}
	
	@RequestMapping(params = "getSubScheme", method = RequestMethod.POST)
	public ModelAndView getSubScheme(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "serviceId") Long serviceId) {
		this.getModel().bind(httpServletRequest);
		BeneficiaryPaymentOrderModel bpomodel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<LookUp> list = new ArrayList<>();
		String shortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(serviceId, orgId);
		LookUp look = CommonMasterUtility.getHieLookupByLookupCode("SS", MainetConstants.SocialSecurity.FTR, 1, orgId);
		List<LookUp> secondLevelData = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,orgId);
		Organisation orgid = iOrganisationService.getSuperUserOrganisation();
		for (int i = 0; i < secondLevelData.size(); i++) {
			if (look.getLookUpId() == secondLevelData.get(i).getLookUpParentId()) {
				String id = pensionSchemeMasterService.getPrefixOtherValue(secondLevelData.get(i).getLookUpId(),
						orgid.getOrgid());
				if (id != null && id != "" && id.equals(shortCode))
					list.add(secondLevelData.get(i));
			} /*
				 * else { list.add(secondLevelData.get(i)); }
				 */
		}
		bpomodel.setSubTypeList(list);
		bpomodel.getApplicationformdto().setSelectSchemeName(serviceId);
		return new ModelAndView("beneficiaryPaymentOrderValidn", MainetConstants.FORM_NAME, bpomodel);
	}

}
