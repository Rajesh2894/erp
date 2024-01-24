/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.socialsecurity.datamodel.WaterRateMaster;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.service.ConfigurationMasterService;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppFamilyDetailsDto;
import com.abm.mainet.socialsecurity.ui.model.SchemeApplicationFormModel;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("SchemeApplicationForm.html")
public class SchemeApplicationFormController extends AbstractFormController<SchemeApplicationFormModel> {

	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	ConfigurationMasterService configurationMasterService;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private IPensionSchemeMasterService pensionSchemeMasterService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	private static final Logger LOGGER = Logger.getLogger(SchemeApplicationFormController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		SchemeApplicationFormModel appModel = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		appModel.getAndSetPrefix(orgId, langId, org);
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,UserSession.getCurrent().getOrganisation().getOrgid());			
            List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
					UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
					sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
							.collect(Collectors.toList()).get(0).getLookUpId(),
					2L));

			this.getModel().setSubTypeList(list);
			return new ModelAndView("SchemeApplicationSum", MainetConstants.FORM_NAME, appModel);
		}
		this.getModel().setCommonHelpDocs("SchemeApplicationForm.html");
		return index();

	}

	@RequestMapping(params = "addForm", method = RequestMethod.POST)
	public ModelAndView addForm(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		SchemeApplicationFormModel appModel = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		appModel.getAndSetPrefix(orgId, langId, org);
		this.getModel().setCommonHelpDocs("SchemeApplicationForm.html");
		return new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, appModel);

	}

	@RequestMapping(params = "saveCheckListAppdetails", method = RequestMethod.POST)
	public ModelAndView saveCheckListAppdetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		JsonViewObject respObj;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long langId = (long) UserSession.getCurrent().getLanguageId();
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		SchemeApplicationFormModel appModel = this.getModel();
		ApplicationFormDto dto = appModel.getApplicationformdto();
		// call a method for setting up the value
		// appModel.validateBean(appModel, ApplicationFormValidator.class);
		// this call is for checklist validation
		List<DocumentDetailsVO> docs = appModel.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		dto.setDocumentList(docs);
		fileUpload.validateUpload(appModel.getBindingResult());
		setGridId(appModel);
		if (appModel.hasValidationErrors()) {
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, appModel.getBindingResult());
			return new ModelAndView(MainetConstants.SocialSecurity.CHECK_LIST_FORM, MainetConstants.FORM_NAME,
					appModel);
		}
		List<Object[]> list = appModel.getServiceList().parallelStream()
				.filter(s -> s[0].equals(dto.getSelectSchemeName())).collect(Collectors.toList());
		list.parallelStream().forEach(l -> {
			dto.setServiceCode(l[2].toString());
		});
		dto.setOrgId(orgId);
		dto.setCreatedBy(empId);
		dto.setCreatedDate(new Date());
		dto.setLgIpMac(ipMacAddress);
		dto.setLangId(langId);
		dto.setUlbName(ulbName);
		if (appModel.saveForm()) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage(appModel.getSuccessMessage()));

		} else {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success"));

		}
		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}

	// workflow show details
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView executeChangeOfOwner(@RequestParam("appNo") final Long applicationId,
			@RequestParam("taskId") final String serviceId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam("workflowId") final Long workflowId, final HttpServletRequest request) {
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(request);
		getModel().bind(request);
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.findById(workflowId).getCurrentOrgId();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		SchemeApplicationFormModel model = this.getModel();
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		model.setParentOrgId(parentOrgId);
		try {
			ApplicationFormDto dto = schemeApplicationFormService.findApplicationdetails(applicationId, parentOrgId);
			model.getAndSetPrefix(parentOrgId, langId, org);
			dto.setBplid(model.getBplList().stream().filter(l -> l.getLookUpCode().contains(dto.getIsBplApplicable()))
					.collect(Collectors.toList()).get(0).getLookUpId());
			model.setApplicationformdto(dto);
			dto.setParentOrgId(parentOrgId);
			// #141514
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
				List<SchemeAppFamilyDetailsDto> familyDetailsDtoList = schemeApplicationFormService
						.getFamilyDetById(dto.getApplicationId(), dto.getOrgId());
				if (CollectionUtils.isNotEmpty(familyDetailsDtoList))
					this.getModel().getApplicationformdto().setOwnerFamilydetailDTO(familyDetailsDtoList);
			}
		} catch (final Exception ex) {
			LOGGER.warn("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
		return new ModelAndView("applicationFormView", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "showCheckList", method = RequestMethod.POST)
	public ModelAndView showCheckList(final Model model, final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		SchemeApplicationFormModel schemeAppmodel = this.getModel();
		try {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			List<DocumentDetailsVO> docs = null;
			final WSRequestDTO initRequestDto = new WSRequestDTO();
			String subschemeCode=null;

			/*
			 * add code for validating scheme eligibility age criteria by rajesh.das
			 */
			Long schemeMstId = schemeApplicationFormService
					.getAllSchemeData(schemeAppmodel.getApplicationformdto().getSelectSchemeName(), orgId);
//			LookUp   testLookup=CommonMasterUtility.getHieLookupByLookupCode("ADT", "FTR", 2, orgId);

			Long age = schemeAppmodel.getApplicationformdto().getAgeason();
			List<LookUp> lookupsCriteria = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,
					UserSession.getCurrent().getOrganisation().getOrgid());
			List<LookUp> lookupsFactor = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,
					UserSession.getCurrent().getOrganisation().getOrgid());

			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				String shortCode = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class)
						.fetchServiceShortCode(this.getModel().getApplicationformdto().getSelectSchemeName(),
								UserSession.getCurrent().getOrganisation().getOrgid());
				Organisation orgid = iOrganisationService.getSuperUserOrganisation();
				List<LookUp> subSchemelist = new ArrayList<>();
				for (LookUp l : lookupsFactor) {
					if (l.getLookUpCode().equalsIgnoreCase("SS")) {
						List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
								UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
								lookupsFactor.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
										.collect(Collectors.toList()).get(0).getLookUpId(),
								2L));
						for (int i = 0; i < list.size(); i++) {
							String ids = pensionSchemeMasterService.getPrefixOtherValue(list.get(i).getLookUpId(),
									orgid.getOrgid());
							if (ids != null && ids != "" && ids.equals(shortCode))
								subSchemelist.add(list.get(i));
						}
						schemeAppmodel.setSubTypeList(subSchemelist);
					}
				}
			}
			List<Object[]> objArr = new ArrayList<Object[]>();
			List<Object[]> objArray = new ArrayList<Object[]>();
			for (LookUp l : lookupsCriteria) {
				Object[] objarr = new Object[2];
				if (l.getLookUpCode().equalsIgnoreCase("MTY")) {
					objarr[0] = l.getLookUpId();
					objarr[1] = l.getLookUpParentId();
					objArray.add(objarr);
				} else if (l.getLookUpCode().equalsIgnoreCase("YAY")) {
					objarr[0] = l.getLookUpId();
					objarr[1] = l.getLookUpParentId();
					objArray.add(objarr);
				}
			}
			for (LookUp l : lookupsCriteria) {
				Object[] objarr = new Object[2];
				if (l.getLookUpCode().equalsIgnoreCase("MAE")) {
					objarr[0] = l.getLookUpId();
					objarr[1] = l.getLookUpParentId();
					objArr.add(objarr);
				} else if (l.getLookUpCode().equalsIgnoreCase("ADT")) {
					objarr[0] = l.getLookUpId();
					objarr[1] = l.getLookUpParentId();
					objArr.add(objarr);
				} else if (l.getLookUpCode().equalsIgnoreCase("ODA")) {
					objarr[0] = l.getLookUpId();
					objarr[1] = l.getLookUpParentId();
					objArr.add(objarr);
				}
			}

			// check in tb_swd_scheme_eligibility with scheme id and
			// SDSCHE_FACT_APL(ageCriteria) and SDSCHE_CRITERIA(ageCriteriaParent)
			boolean flag = false;
			if (schemeMstId != null) {
				ModelAndView modelAndView = null;

				if (schemeAppmodel.getApplicationformdto().getSubSchemeName() != null) {
					for (LookUp l : lookupsCriteria) {
						if (l.getLookUpId() == (schemeAppmodel.getApplicationformdto().getSubSchemeName())) {
							subschemeCode = l.getLookUpCode();
						}
					}
				}
				/*
				 * List<Object[]> schemeEligibility =
				 * schemeApplicationFormService.getScemeEligibilityData(schemeMstId, orgId);
				 */
				List<SocialSecuritySchemeEligibilty> schemeEligibility = schemeApplicationFormService
						.getLatestScemeEligbleData(schemeMstId);
				//Defect #138191
				flag=schemeAppmodel.validateSocialSecurityForm(schemeEligibility,lookupsFactor);
				if (((schemeEligibility != null && !schemeEligibility.isEmpty()) && schemeAppmodel != null)&&!flag) {

					for (SocialSecuritySchemeEligibilty socSecScmEligble : schemeEligibility) {
						if (socSecScmEligble.getFactorApplicableId() != null
								&& socSecScmEligble.getCriteriaId() != null) {
							Long scmCriteriaId = socSecScmEligble.getCriteriaId();
							Long scmFactApplId = socSecScmEligble.getFactorApplicableId();
							if (socSecScmEligble.getRangeFrom() != null && socSecScmEligble.getRangeTo() != null) {
								Long rangeFrom = Long.valueOf(socSecScmEligble.getRangeFrom());
								Long rangeTo = Long.valueOf(socSecScmEligble.getRangeTo());
								for (Object[] k : objArr) {
									if (k[0] != null && k[1] != null) {
										Long sdschCriteria1 = Long.valueOf(k[0].toString());
										Long aplFactId1 = Long.valueOf(k[1].toString());

										if ((scmCriteriaId != null && scmFactApplId != null)
												&& (aplFactId1 != null && sdschCriteria1 != null)) {
											if ((scmCriteriaId.equals(sdschCriteria1))
													&& (scmFactApplId.equals(aplFactId1))) {
												if (rangeFrom >= age || rangeTo <= age) {

													this.getModel()
															.addValidationError(ApplicationSession.getInstance()
																	.getMessage("eligibility.criteria.agerange")
																	+ rangeFrom + "  to " + rangeTo);
													flag = true;

												}
											}
										}
									}
								}
								//#154880-to validate income range
								if (schemeAppmodel.getApplicationformdto().getAnnualIncome() != null) {
									Long income = schemeAppmodel.getApplicationformdto().getAnnualIncome().longValue();
									for (Object[] k : objArray) {
										if (k[0] != null && k[1] != null) {
											Long sdschCriteria1 = Long.valueOf(k[0].toString());
											Long aplFactId1 = Long.valueOf(k[1].toString());

											if ((scmCriteriaId != null && scmFactApplId != null)
													&& (aplFactId1 != null && sdschCriteria1 != null)) {
												if ((scmCriteriaId.equals(sdschCriteria1))
														&& (scmFactApplId.equals(aplFactId1))) {
													Long fromRange = Long.valueOf(socSecScmEligble.getRangeFrom());
													Long toRange = Long.valueOf(socSecScmEligble.getRangeTo());
													if (fromRange >= income || toRange <= income) {
														this.getModel()
																.addValidationError(ApplicationSession.getInstance()
																		.getMessage("eligibility.criteria.incomeRange")
																		+ rangeFrom + "  to " + rangeTo);
														flag = true;

													}
												}
											}
										}
									}
								}
								if (schemeAppmodel.getTypeofdisabilityList().get(0)
										.getLookUpParentId() == scmFactApplId) {
									if (!schemeAppmodel.getApplicationformdto().getTypeofDisId()
											.equals(scmCriteriaId)) {
										this.getModel().addValidationError(ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.disabilityFact"));
										flag = true;
									} else if (schemeAppmodel.getApplicationformdto().getPercenrofDis() != null) {
										if (rangeFrom >= schemeAppmodel.getApplicationformdto().getPercenrofDis()
												|| rangeTo <= schemeAppmodel.getApplicationformdto()
														.getPercenrofDis()) {

											this.getModel()
													.addValidationError(ApplicationSession.getInstance()
															.getMessage("eligibility.criteria.disability.percentage")
															+ rangeFrom + "  to " + rangeTo);
											flag = true;
										}
									}

								}
							} else {
								if (schemeAppmodel.getGenderList().get(0).getLookUpParentId() == scmFactApplId) {
									if (!schemeAppmodel.getApplicationformdto().getGenderId().equals(scmCriteriaId)) {
										this.getModel().addValidationError(ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.genderFact"));
										flag = true;
									}

								}
								if (schemeAppmodel.getCategoryList().get(0).getLookUpParentId() == scmFactApplId) {
									if (!schemeAppmodel.getApplicationformdto().getCategoryId().equals(scmCriteriaId)) {
										this.getModel().addValidationError(ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.catagoryFact"));
										flag = true;
									}

								}
								
								if (!schemeAppmodel.getBplList().isEmpty() && schemeAppmodel.getBplList().get(0).getLookUpParentId() == scmFactApplId) {
									if (!schemeAppmodel.getApplicationformdto().getBplid().equals(scmCriteriaId)) {
										this.getModel().addValidationError(ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.bplFact"));
										flag = true;
									}

								}
								if (schemeAppmodel.getMaritalstatusList() != null && schemeAppmodel.getMaritalstatusList().get(0).getLookUpParentId() == scmFactApplId) {
									if (!schemeAppmodel.getApplicationformdto().getMaritalStatusId()
											.equals(scmCriteriaId)) {
										this.getModel().addValidationError(ApplicationSession.getInstance()
												.getMessage("eligibility.criteria.maritalFact"));
										flag = true;
									}

								}
							}
						}

					}
				}
				if (flag) {
					modelAndView = new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME,
							this.getModel());
					modelAndView.addObject(
							BindingResult.MODEL_KEY_PREFIX
									+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
							getModel().getBindingResult());
					return modelAndView;

				}
			}

			initRequestDto.setModelName(MainetConstants.RightToService.CHECKLIST_RTSRATE_MASTER);
			WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				checkListModel.setOrgId(orgId);
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)
						&& subschemeCode != null) {
					checkListModel.setUsageSubtype1(subschemeCode);
				}
				/* checkListModel.setServiceCode("IGNDS"); */
				//D#132886
				checkListModel.setServiceCode(MainetConstants.SocialSecurity.SERVICE_CODE);
				final WSRequestDTO checkRequestDto = new WSRequestDTO();
				checkRequestDto.setDataModel(checkListModel);
				WSResponseDTO checklistResp = brmsCommonService.getChecklist(checkRequestDto);
				if (response.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
					docs = (List<DocumentDetailsVO>) checklistResp.getResponseObj();
					if (docs != null && !docs.isEmpty()) {
						long cnt = 1;
						for (final DocumentDetailsVO doc : docs) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
					}
					schemeAppmodel.setCheckList(docs);

				}else { 
					  return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW); 
					  }

				// charges
				ServiceMaster serMast = serviceMasterService
						.getServiceMaster(schemeAppmodel.getApplicationformdto().getSelectSchemeName(), orgId);
				
				if (serMast.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
					 schemeAppmodel.setApplicationchargeApplFlag(MainetConstants.FlagY);
				} else {
					schemeAppmodel.setApplicationchargeApplFlag(MainetConstants.FlagN);
				}
				
				  if(schemeAppmodel.getApplicationchargeApplFlag().equals(MainetConstants.FlagY)) {
				  List<Object> waterRateMasterList = RestClient.castResponse(response,
				  WaterRateMaster.class, 1); 
				  final WaterRateMaster waterRateMaster =(WaterRateMaster) waterRateMasterList.get(0); 
				  if (response.getWsStatus() != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.
				  getWsStatus())) { 
					final WSRequestDTO taxRequestDto = new WSRequestDTO();
				  waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid(
				  ));
				  waterRateMaster.setServiceCode(serMast.getSmShortdesc());
				  waterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility. getValueFromPrefixLookUp( MainetConstants.NewWaterServiceConstants.APL,
				  MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
				  taxRequestDto.setDataModel(waterRateMaster); 
				  WSResponseDTO res = schemeApplicationFormService.getApplicableTaxes(taxRequestDto);
				  
				  if(MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) { 
					  if (!res.isFree()) { 
				  // final List<?> rates = this.castResponse(res, WaterRateMaster.class);
				  final List<Object> rates = (List<Object>)
				  res.getResponseObj();
				  final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
				  for (final Object rate : rates) { 
				  WaterRateMaster master1 = (WaterRateMaster) rate;
				  master1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				  master1.setServiceCode(serMast.getSmShortdesc()); 
				  master1.setRateStartDate(new Date().getTime()); 
				  master1.setDeptCode("SWD"); //master1 =
				 // populateChargeModel(schemeAppmodel, master1); 
				  requiredCHarges.add(master1); }
				  WSRequestDTO chargeReqDto = new WSRequestDTO();
				  chargeReqDto.setModelName("WaterRateMaster");
				  chargeReqDto.setDataModel(requiredCHarges); 
				  WSResponseDTO applicableCharges = schemeApplicationFormService.getApplicableCharges(chargeReqDto);
				  List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
				  //final List<ChargeDetailDTO> detailDTOs = checklistAndChargeService.getApplicableCharges(requiredCHarges);
				  //model.setFree(PrefixConstants.NewWaterServiceConstants.NO); //
				//  model.getReqDTO().setFree(false);
				 // model.setChargesInfo(detailDTOs);
				  schemeAppmodel.setCharges((chargesToPay(detailDTOs))); //
				  schemeAppmodel.getOfflineDTO().setAmountToShow(schemeAppmodel.getCharges());
				   Map<Long, Double> chargesMap = new HashMap<>();
					for (final ChargeDetailDTO dto : detailDTOs) {
						chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
					}
					schemeAppmodel.setChargesMap(chargesMap);
				  
				  } else {
				  
							/*
							 * model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
							 * model.getReqDTO().setFree(true); model.getReqDTO().setCharges(0.0d);
							 */
				  
				  }
			 } 
				  else { 
					  return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW); 
					  }
				  
				  
				  }
				 
				return new ModelAndView(MainetConstants.SocialSecurity.CHECK_LIST_FORM, MainetConstants.FORM_NAME,schemeAppmodel);
			}else {
				return new ModelAndView(MainetConstants.SocialSecurity.CHECK_LIST_FORM, MainetConstants.FORM_NAME,schemeAppmodel);
			}

			} else {
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
			}

		} catch (FrameworkException e) {
			LOGGER.warn(e.getErrMsg());
			schemeAppmodel.setCheckList(null);
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
		}

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	// workflow save decision call
	@RequestMapping(params = "saveDecision", method = RequestMethod.POST)
	public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

		JsonViewObject respObj = null;
		boolean updFlag = false;

		this.bindModel(httpServletRequest);

		SchemeApplicationFormModel asstModel = this.getModel();
		String decision = asstModel.getWorkflowActionDto().getDecision();
		//added for checking is it last task or not
		boolean isLastTask = iWorkFlowTypeService
				.isLastTaskInCheckerTaskList(asstModel.getWorkflowActionDto().getTaskId());

		updFlag = asstModel.callWorkFlow();

		if (updFlag) {

			if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
				//for checking last task and appending benificiary no
				if (isLastTask) {
					respObj = JsonViewObject.successResult(
							ApplicationSession.getInstance().getMessage("social.info.application.approved")
									+ "  On  Benificiary No :" + asstModel.getApplicationformdto().getBeneficiaryno());
				} else {
					respObj = JsonViewObject.successResult(
							ApplicationSession.getInstance().getMessage("social.info.application.approved"));
				}
			} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("social.info.application.reject"));
			else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("social.info.application.sendBack"));
		} else

		{
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("social.info.application.failure"));
		}

		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}

	// code added by rahul.chaubey
	// For checking the number of approved forms and restricting the process if the
	// limit exceeds the beneficary count(Configuration master)
	// Also For checking the application data satisfies the scheme criteria if not
	// then error will be displayed.
	@RequestMapping(params = "validateScheme", method = RequestMethod.POST)
	public ModelAndView validateScheme(final HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = null;

		this.getModel().bind(httpServletRequest);
		if (configurationMasterService.validateOnSave(this.getModel().getApplicationformdto().getSelectSchemeName(),
				this.getModel().getApplicationformdto().getOrgId(), true)) {
			this.getModel().getApplicationformdto().setApprovalFlag("Y");
			boolean flag = iWorkFlowTypeService
					.isLastTaskInCheckerTaskList(this.getModel().getWorkflowActionDto().getTaskId());
			if (flag) {
				this.getModel().setSuccessMessage(
						"save successfully   " + this.getModel().getApplicationformdto().getBeneficiaryno());
			}
			modelAndView = new ModelAndView("applicationFormView", MainetConstants.FORM_NAME, this.getModel());

		} else {
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage(
					"Scheme Configuration's Maximum Beneficiary Count Limit Reached: Application cannot be approved. "));
			modelAndView = new ModelAndView("applicationFormViewValidn", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());
			this.getModel().getApplicationformdto().setApprovalFlag("N");

		}

		return modelAndView;

	}

	@RequestMapping(params = "previousPage", method = RequestMethod.POST)
	public ModelAndView previousPage(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		// this.getModel().setRequestDTO(requestDTO);
		return new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, getModel());
	}

	// code added by rahul.chaubey
	// For checking the date of the scheme, if the date of application exceeds the
	// date of scheme (defined in Configuration master)then the user will be
	// restricted
	@RequestMapping(params = "checkDate", method = RequestMethod.POST)
	public ModelAndView checkDate(final HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = null;
		this.getModel().bind(httpServletRequest);
		this.getModel()
				.setIsFamilyDetReq(schemeApplicationFormService.checkFamilyDetailsReqOrNot(
						this.getModel().getApplicationformdto().getSelectSchemeName(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		// #142983
		this.getModel()
				.setIsLifeCertificateReq(schemeApplicationFormService.checkLifeCertificateDateReqOrNot(
						this.getModel().getApplicationformdto().getSelectSchemeName(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long id = null;
		for (LookUp l : sourceLookUp) {
			if (l.getLookUpCode().equalsIgnoreCase("ICE")) {
				id = l.getLookUpId();
			}
		}
		this.getModel()
				.setAnnualIncomeCheck(schemeApplicationFormService.annualIncome(
						this.getModel().getApplicationformdto().getSelectSchemeName(),
						UserSession.getCurrent().getOrganisation().getOrgid(), id));
		String shortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(this.getModel().getApplicationformdto().getSelectSchemeName(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		Organisation orgid = iOrganisationService.getSuperUserOrganisation();
		List<LookUp> subSchemelist = new ArrayList<>();
		for (LookUp l : sourceLookUp) {
			if (l.getLookUpCode().equalsIgnoreCase("SS")) {
				List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
						UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
						sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
								.collect(Collectors.toList()).get(0).getLookUpId(),
						2L));
				for (int i = 0; i < list.size(); i++) {
					String ids = pensionSchemeMasterService.getPrefixOtherValue(list.get(i).getLookUpId(),
							orgid.getOrgid());
					if (ids != null && ids != "" && ids.equals(shortCode))
						subSchemelist.add(list.get(i));
				}
				this.getModel().setSubTypeList(subSchemelist);
			}
		}

		if (configurationMasterService.validateOnSave(this.getModel().getApplicationformdto().getSelectSchemeName(),
				UserSession.getCurrent().getOrganisation().getOrgid(), false)) {
			this.getModel().getApplicationformdto().setApprovalFlag("Y");
			modelAndView = new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, this.getModel());

		} else {
			this.getModel().addValidationError(ApplicationSession.getInstance()
					.getMessage("Scheme is not available for application due to period validity"));
			modelAndView = new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());
			this.getModel().getApplicationformdto().setApprovalFlag("N");
		}
		String schemeStatus = schemeApplicationFormService.isSchemeActive(
				this.getModel().getApplicationformdto().getSelectSchemeName(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (schemeStatus.equalsIgnoreCase("Y")) {
			this.getModel().getApplicationformdto().setApprovalFlag("Y");
			modelAndView = new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, this.getModel());
		} else {
			this.getModel().addValidationError(
					ApplicationSession.getInstance().getMessage("social.validation.duplicatescheme"));
			modelAndView = new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());
			this.getModel().getApplicationformdto().setApprovalFlag("D");

		}
		return modelAndView;
	}

	// code added by rahul.chaubey
	// for restricting duplicate data during beneficiary payment order
	public void setGridId(SchemeApplicationFormModel appModel) {
		CriteriaDto dto = new CriteriaDto();
		List<Long> factorsId = new ArrayList<Long>();
		List<Long> criteriaId = new ArrayList<Long>();
		Long bplFactorId = 0l;
		Long ageCriteria = 0l;
		List<LookUp> lookups = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// set the factors values
		Long categoryFactorId = appModel.getCategoryList().get(0).getLookUpParentId();
		if(!appModel.getBplList().isEmpty()) {
			bplFactorId = appModel.getBplList().get(0).getLookUpParentId();
		}
		Long educationFactorId = appModel.getEducationList().get(0).getLookUpParentId();
		Long genderFactorId = appModel.getGenderList().get(0).getLookUpParentId();
		Long maritalFactorId = appModel.getMaritalstatusList().get(0).getLookUpParentId();
		Long disabilityFactorId = appModel.getTypeofdisabilityList().get(0).getLookUpParentId();
		Long ageFactorId = lookups.get(0).getLookUpId();
		// adding factors to a list
		factorsId.addAll(Arrays.asList(categoryFactorId, bplFactorId, educationFactorId, genderFactorId,
				maritalFactorId, disabilityFactorId, ageFactorId));
		dto.setFactrors(factorsId);

		// set the criteria values
		Long categoryId = appModel.getApplicationformdto().getCategoryId() != null
				? appModel.getApplicationformdto().getCategoryId()
				: 0l;
		Long bplId = appModel.getApplicationformdto().getBplid() != null ? appModel.getApplicationformdto().getBplid()
				: 0l;
		Long genderId = appModel.getApplicationformdto().getGenderId() != null
				? appModel.getApplicationformdto().getGenderId()
				: 0l;
		Long maritalId = appModel.getApplicationformdto().getMaritalStatusId() != null
				? appModel.getApplicationformdto().getMaritalStatusId()
				: 0l;
		Long disabilityId = appModel.getApplicationformdto().getTypeofDisId() != null
				? appModel.getApplicationformdto().getTypeofDisId()
				: 0l;
		Long educationId = appModel.getApplicationformdto().getEducationId() != null
				? appModel.getApplicationformdto().getEducationId()
				: 0l;
		// adding criteria to a list
		List<LookUp> lookupsCriteria = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (LookUp l : lookupsCriteria) {
			if (l.getLookUpParentId() == ageFactorId) {

				if (l.getLookUpCode().equalsIgnoreCase("MAE") && appModel.getApplicationformdto().getAgeason() >= 1
						&& appModel.getApplicationformdto().getAgeason() <= 17) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ADT")
						&& appModel.getApplicationformdto().getAgeason() >= 18
						&& appModel.getApplicationformdto().getAgeason() <= 57) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ODA")
						&& appModel.getApplicationformdto().getAgeason() > 57) {
					ageCriteria = l.getLookUpId();
					break;
				}

			}

		}

		criteriaId
				.addAll(Arrays.asList(categoryId, bplId, genderId, maritalId, disabilityId, educationId, ageCriteria));
		dto.setCriterias(criteriaId);
		dto.setOrgId(orgId);
		dto.setServiceId(appModel.getApplicationformdto().getSelectSchemeName());

		Long gridId = schemeApplicationFormService.getCriteriaGridId(dto);
		appModel.getApplicationformdto().setGridId(gridId);

	}

	@RequestMapping(params = "getOwnerFamilyDet", method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		/* if (model.getTradeMasterDetailDTO().getApmApplicationId() == null) { */
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		// TradeLicenseOwnerDetailDTO());
		// }

		return new ModelAndView("SocialSecurityFamilyShip", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "checkIsExixtingHolder", method = RequestMethod.POST)
	public ModelAndView checkIsExixtingHolder(Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam("aadharCard") String aadharCard) {
		ModelAndView mv = null;
		this.getModel().bind(httpServletRequest);

		List<ApplicationFormDto> appDtoList = schemeApplicationFormService.getExistingHolderByUID(aadharCard,
				UserSession.getCurrent().getOrganisation().getOrgid());
		// #141909 if data not present against given aadharNo then pop up should not be
		// displayed
		if (CollectionUtils.isNotEmpty(appDtoList)) {
			this.getModel().setApplicantDtoList(appDtoList);
			return new ModelAndView("schemeDetPopup", MainetConstants.FORM_NAME, this.getModel());
		}else
			return mv;
	}

	@ResponseBody
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView searchBirthDraft(final HttpServletRequest request, final Model model) {
		getModel().bind(request);
		this.getModel().getApplicantDtoList().clear();
		ApplicationFormDto dto = this.getModel().getApplicationformdto();
		ModelAndView mv = new ModelAndView("SchemeApplicationFormSumVal", MainetConstants.FORM_NAME, this.getModel());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<ApplicationFormDto> appDtoList = schemeApplicationFormService.getAppliDetail(dto.getSelectSchemeName(),
				dto.getSubSchemeName(), dto.getSwdward1(), dto.getSwdward2(), dto.getSwdward3(), dto.getSwdward4(),dto.getSwdward5(), dto.getAadharCard(), dto.getStatus(), orgId);

		if (CollectionUtils.isNotEmpty(appDtoList)) {
			this.getModel().setApplicantDtoList(appDtoList);
		}

		return mv;

	}

	@RequestMapping(params = "editData", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editData(Model model, @RequestParam("mode") String mode,
			@RequestParam("applicationId") Long applicationId, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		SchemeApplicationFormModel appModel = this.getModel();
		this.getModel().setSaveMode(mode);
		ApplicationFormDto dto = schemeApplicationFormService.findApplicationData(applicationId);
		this.getModel().setApplicationformdto(dto);
		appModel.getAndSetPrefix(orgId, langId, org);

		List<LookUp> sourceLookUp = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());

		String shortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(this.getModel().getApplicationformdto().getSelectSchemeName(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		Organisation orgid = iOrganisationService.getSuperUserOrganisation();
		List<LookUp> subSchemelist = new ArrayList<>();
		for (LookUp l : sourceLookUp) {
			if (l.getLookUpCode().equalsIgnoreCase("SS")) {
				List<LookUp> list = (schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(
						UserSession.getCurrent().getOrganisation().getOrgid(), "FTR",
						sourceLookUp.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
								.collect(Collectors.toList()).get(0).getLookUpId(),
						2L));
				for (int i = 0; i < list.size(); i++) {
					String ids = pensionSchemeMasterService.getPrefixOtherValue(list.get(i).getLookUpId(),
							orgid.getOrgid());
					if (ids != null && ids != "" && ids.equals(shortCode))
						subSchemelist.add(list.get(i));
				}
				this.getModel().setSubTypeList(subSchemelist);
			}
		}
		this.getModel()
		.setIsFamilyDetReq(schemeApplicationFormService.checkFamilyDetailsReqOrNot(
				this.getModel().getApplicationformdto().getSelectSchemeName(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		List<SchemeAppFamilyDetailsDto> familyDetailsDtoList = schemeApplicationFormService
				.getFamilyDetById(dto.getApplicationId(), dto.getOrgId());
		if (CollectionUtils.isNotEmpty(familyDetailsDtoList))
			this.getModel().getApplicationformdto().setOwnerFamilydetailDTO(familyDetailsDtoList);
		return new ModelAndView("SchemeApplicationFormValidn", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(params = MainetConstants.WorksManagement.GET_WORKFLOW_HISTORY, method = RequestMethod.POST)
	
	public @ResponseBody ModelAndView getWorkFlowHistory(@RequestParam(name = "applicationId") final Long applicationId,
			ModelMap modelMap) {
		ApplicationFormDto dto = schemeApplicationFormService.findApplicationData(applicationId);

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class).getWorkflowRequestByAppIdOrRefId(null,
						dto.getMasterAppId().toString(), UserSession.getCurrent().getOrganisation().getOrgid());
		if (workflowRequest != null) {
			List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowActionService.class)
					.getActionLogByUuidAndWorkflowId(dto.getMasterAppId().toString(), workflowRequest.getId(),
							(short) UserSession.getCurrent().getLanguageId());
			for (WorkflowTaskActionWithDocs workflowTaskAction : actionHistory) {
				Employee employee = employeeService.findEmployeeByIdAndOrgId(workflowTaskAction.getEmpId(),
						workflowTaskAction.getOrgId());
				// from changes
				StringBuffer empName = new StringBuffer(employee.getEmpname());
				if (employee.getEmpmname() != null && !(employee.getEmpmname()).isEmpty())
					empName.append(" " + employee.getEmpmname());
				if (employee.getEmplname() != null)
					empName.append(" " + employee.getEmplname());
				workflowTaskAction.setEmpName(empName.toString());
				workflowTaskAction.setEmpEmail(employee.getEmpemail());
				workflowTaskAction.setEmpGroupDescEng(employee.getDesignation().getDsgname());
				workflowTaskAction.setEmpGroupDescReg(employee.getDesignation().getDsgnameReg());
				modelMap.addAllAttributes(actionHistory);
			}

			modelMap.put(MainetConstants.WorksManagement.ACTIONS, actionHistory);
		}
		return new ModelAndView(MainetConstants.WorksManagement.WORK_WORKFLOW_HISTORY, MainetConstants.FORM_NAME,
				modelMap);
	}

	@RequestMapping(params = "printAcknowledgement", method = { RequestMethod.POST })
	public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		SchemeApplicationFormModel appModel = this.getModel();

		ServiceMaster serMast = serviceMasterService
				.getServiceMaster(appModel.getApplicationformdto().getSelectSchemeName(), orgId);
		if (serMast != null) {
			appModel.getApplicationformdto().setServDesc(serMast.getSmServiceName());
		}

		if (appModel.getApplicationformdto().getSwdward1() != null)
			appModel.getApplicationformdto()
					.setSwdward1(CommonMasterUtility
							.getHierarchicalLookUp(Long.valueOf(appModel.getApplicationformdto().getSwdward1()), orgId)
							.getDescLangFirst());

		if (appModel.getApplicationformdto().getSubSchemeName() != null)
			appModel.getApplicationformdto()
					.setObjOfschme((CommonMasterUtility
							.getHierarchicalLookUp(appModel.getApplicationformdto().getSubSchemeName(), orgId))
									.getLookUpDesc());
		List<CFCAttachment> attachList = new ArrayList<>();
		attachList = iChecklistVerificationService
				.findAttachmentsForAppId(appModel.getApplicationformdto().getMasterAppId(), null, orgId);
		appModel.setFetchDocumentList(attachList);
		return new ModelAndView("SchemeApplicationAck", MainetConstants.FORM_NAME, appModel);

	}

}
