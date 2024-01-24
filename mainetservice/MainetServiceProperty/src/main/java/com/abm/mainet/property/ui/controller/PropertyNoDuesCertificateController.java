package com.abm.mainet.property.ui.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.PropertyNoDuesCertificateModel;

@Controller
@RequestMapping("/PropertyNoDuesCertificate.html")
public class PropertyNoDuesCertificateController extends AbstractFormController<PropertyNoDuesCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyNoDuesCertificateController.class);

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private ViewPropertyDetailsService viewPropertyDetailsService;

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@Autowired
	private TbCfcApplicationMstService tbCfcservice;

	/**
	 * used for showing default page for No dues certificate
	 * 
	 * @param request
	 * @return default view
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		setCommonFeilds();
		getModel().getNoDuesCertificateDto().setServiceUrl("PropertyNoDuesCertificate.html");
		getModel().setCommonHelpDocs("PropertyNoDuesCertificate.html");
		return defaultResult();

	}

	private void setCommonFeilds() {
		PropertyNoDuesCertificateModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setOrgId(orgId);
		ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.NDT, orgId);
		final LookUp processLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
		model.setServiceId(service.getSmServiceId());
		model.setServiceName(service.getSmServiceName());
		model.setDeptId(service.getTbDepartment().getDpDeptid());
		model.setDeptName(service.getTbDepartment().getDpDeptdesc());
		model.setDeptName(service.getTbDepartment().getDpDeptdesc());
		model.setServiceMaster(service);
		if (StringUtils.equalsIgnoreCase(
				CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmProcessId(),
						UserSession.getCurrent().getOrganisation()).getLookUpCode(),
				MainetConstants.CommonConstants.NA)) {
			model.setLastApproval(true);// for print instant certificate
		}
		model.setAllowToGenerate(MainetConstants.Y_FLAG);
		if (StringUtils.equalsIgnoreCase(CommonMasterUtility
				.getNonHierarchicalLookUpObject(service.getSmChklstVerify(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode(), MainetConstants.FlagA)) {
			model.setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			model.setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (service.getSmFeesSchedule().equals(1l)) {
			model.setAppliChargeFlag(service.getSmAppliChargeFlag());
			model.setFree(false);
		} else {
			model.setAppliChargeFlag(MainetConstants.N_FLAG);
			model.setFree(true);
		}
		if ((service.getSmScrutinyApplicableFlag().equals(MainetConstants.N_FLAG)
				&& processLookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA))
				|| (processLookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA))) {
			model.setScrutinyAppliFlag(MainetConstants.N_FLAG);
		} else {
			model.setScrutinyAppliFlag(MainetConstants.Y_FLAG);
		}
		getModel().setNoDuesCertificateDto(new NoDuesCertificateDto());

		LookUp outstandingCheckPrefix = CommonMasterUtility.getValueFromPrefixLookUp("NDT", "OSC",
				UserSession.getCurrent().getOrganisation());
		if (outstandingCheckPrefix != null)
			if (MainetConstants.FlagY.equals(outstandingCheckPrefix.getOtherField())) {
				model.setNeedToCheckOutstanding(true);
			}

		setSearchCommonData(model, orgId);
	}

	private void setSearchCommonData(PropertyNoDuesCertificateModel model, Long orgId) {
		// location list
		List<LookUp> locList = getModel().getLocation();
		List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
				model.getDeptId());
		if (location != null && !location.isEmpty()) {
			location.forEach(loc -> {
				LookUp lookUp = new LookUp();
				lookUp.setLookUpId(loc.getLocId());
				lookUp.setDescLangFirst(loc.getLocNameEng());
				lookUp.setDescLangSecond(loc.getLocNameReg());
				locList.add(lookUp);
			});
		}
		model.getSearchDto().setOrgId(orgId);
		model.getSearchDto().setDeptId(model.getDeptId());
		model.setLocation(locList);
	}

	/**
	 * used to search records by property no or old prop no and check current dues
	 * of search property no , display details if found No dues
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param propNo
	 * @param oldPropNo
	 * 
	 * @return view if record found
	 */
	@RequestMapping(params = MainetConstants.Property.GET_PROPERTY_DETAILS, method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo) {
		this.getModel().bind(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = getModel().getNoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setPropNo(propNo);
		noDuesDto.setOldpropno(oldPropNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getPropertyDetailsByPropertyNumber(noDuesDto);
		if (dto != null && dto.getSuccessFlag() != null && dto.getSuccessFlag().equals(MainetConstants.FlagY)) {
			getModel().setNoDuesCertificateDto(dto);
			if (dto.getTotalOutsatandingAmt() <= 0) {
				getModel().setAllowToGenerate(MainetConstants.Y_FLAG);
				return defaultMyResult();
			} else {
				this.getModel().addValidationError("property.noDues.pendingDuesValid" + noDuesDto.getPropNo());
			}
		} else {
			this.getModel().addValidationError("property.noDues.propertySearchValid");
		}

		ModelAndView mv = new ModelAndView(MainetConstants.Property.PROP_NO_DUES_CERTIFICATE_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	/**
	 * used to fetch checklist and charges if enable
	 * 
	 * @param httpServletRequest
	 * 
	 */
	@RequestMapping(params = MainetConstants.Property.GET_CHECKLIST_AND_CHANGES, method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = getModel().getNoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setSmServiceId(model.getServiceId());
		noDuesDto.setDeptId(model.getDeptId());

		if (!model.checkChequeClearanceStatus()
				|| (model.isNeedToCheckOutstanding() && !model.checkOutstandingStatus())) {
			return defaultMyResult();
		}
		if (model.getCheckListApplFlag().equals(MainetConstants.Y_FLAG)) {
			List<DocumentDetailsVO> docs = propertyNoDuesCertificate.fetchCheckList(noDuesDto);
			if (CollectionUtils.isNotEmpty(docs)) {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
			} else {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
			}
			model.setCheckList(docs);
		}
		if (model.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
			NoDuesCertificateDto dto = propertyNoDuesCertificate.fetchChargesForNoDues(noDuesDto);
			model.setNoDuesCertificateDto(dto);
			if (CollectionUtils.isNotEmpty(dto.getCharges())) {
				this.getModel().setAppliChargeFlag(MainetConstants.FlagN);
			} else {
				this.getModel().setAppliChargeFlag(MainetConstants.FlagY);
				this.getModel().addValidationError("No charges found. Please configure rule sheet");
				ModelAndView mv = new ModelAndView(MainetConstants.Property.PROP_NO_DUES_CERTIFICATE_VALIDN,
						MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}
		return defaultMyResult();
	}

	/**
	 * used to generate certificate
	 */
	@RequestMapping(params = MainetConstants.Property.GENERATE_NO_DUES_CERTI, method = RequestMethod.POST)
	public ModelAndView getNoDuesCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		if (!model.isWorkflowDefined() || model.isLastApproval()) {
			getModel().setFilePath(
					ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=NoDueCertificateReport_PT.rptdesign&AppId="
							+ model.getNoDuesCertificateDto().getApmApplicationId());
			getModel().setRedirectURL("AdminHome.html");
			return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
		} else {
			Date date = new Date();
			model.setDate(Utility.dateToString(date, MainetConstants.REQUIRED_PG_PARAM.DATEFORMAT_DDMMYYYY));
			String currentYear = Utility.getFinancialYearFromDate(date);
			getModel().setFinYear(currentYear);
			return new ModelAndView("propertyAcknowledgement", MainetConstants.FORM_NAME, model);
			// return new ModelAndView("redirect:/AdminHome.html");
		}

	}

	@RequestMapping(params = "getAcknowledement", method = RequestMethod.POST)
	public ModelAndView getTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			final Model model) throws Exception {
		Date date = new Date();
		this.getModel().setDate(Utility.dateToString(date, MainetConstants.DATE_FORMAT));
		String currentYear = Utility.getFinancialYearFromDate(date);
		getModel().setFinYear(currentYear);
		this.getModel().setDueDate(Utility.dateToString(this.getModel().dueDate(date), MainetConstants.DATE_FORMAT));
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		this.getModel().setTime(localDateFormat.format(new Date()));
		model.addAttribute("empName", UserSession.getCurrent().getEmployee().getFullName());
		try {
			CFCSchedulingCounterDet counterDet = null;
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
				counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null) {
					model.addAttribute("cfcCeter", counterDet.getCollcntrno());
					model.addAttribute("cfcCounterNo", counterDet.getCounterno());
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception occure while seting the Counter scheduling info:" + e);
		}
		return new ModelAndView("propertyAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

	/**
	 * @param propNo
	 * @param flatNo
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "getPropertyDetailsByFlatNo", method = RequestMethod.POST)
	public @ResponseBody NoDuesCertificateDto getPropertyDetailsByFlatNo(@RequestParam("propNo") String propNo,
			@RequestParam("flatNo") String flatNo, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		NoDuesCertificateDto noDuesDto = new NoDuesCertificateDto();
		noDuesDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		noDuesDto.setPropNo(propNo);
		noDuesDto.setFlatNo(flatNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getPropertyDetailsByPropertyNumberNFlatNo(noDuesDto);
		return dto;

	}

	/**
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param deletedRowCount
	 */
	@RequestMapping(params = "deletePropertyNo", method = RequestMethod.POST)
	public @ResponseBody void deletePropRow(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "deletedRow") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		NoDuesPropertyDetailsDto detDto = this.getModel().getNoDuesCertificateDto().getPropertyDetails()
				.get(deletedRowCount);
		if (detDto != null) {
			detDto.setIsDeleted(MainetConstants.Y_FLAG);
		}
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "backToForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView backToNodues(HttpServletRequest request) {
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(request);
		String viewName = "PropertyNoDuesCertificate";
		return customResult(viewName);
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "searchPage", method = RequestMethod.POST)
	public ModelAndView searchPage(HttpServletRequest request) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(request);
		setSearchCommonData(model, UserSession.getCurrent().getOrganisation().getOrgid());
		return customResult("SearchPropertyDetailsTemplate");
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request) {
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(request);
		model.setSearchDtoResult(new ArrayList<>(0));
		ProperySearchDto dto = model.getSearchDto();
		if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty())
				&& (dto.getOldPid() == null || dto.getOldPid().isEmpty())
				&& (dto.getMobileno() == null || dto.getMobileno().isEmpty())
				&& (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
				&& (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
				&& (dto.getLocId() == null || dto.getLocId() <= 0)
				&& (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
				&& (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
				&& (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)
				&& (dto.getFlatNo() == null || dto.getFlatNo().isEmpty())) {
			model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
		}
		List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getProertyNo(), dto.getOrgId());
		String checkActiveFlag = null;
		if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
			checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
		}
		if (StringUtils.isNotBlank(checkActiveFlag)
				&& StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
			model.addValidationError(getApplicationSession()
					.getMessage("This property is Inactive through Amalgamation or Bifurcation service"));
		}
		return customResult("SearchPropertyDetailsTemplate");
	}

	@RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
			HttpServletRequest httpServletRequest) {
		List<ProperySearchDto> result = null;
		int count = 0;
		PropertyNoDuesCertificateModel model = this.getModel();
		ProperySearchDto dto = model.getSearchDto();
		if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty())
				&& (dto.getOldPid() == null || dto.getOldPid().isEmpty())
				&& (dto.getMobileno() == null || dto.getMobileno().isEmpty())
				&& (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
				&& (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
				&& (dto.getLocId() == null || dto.getLocId() <= 0)
				&& (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
				&& (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
				&& (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)) {
			model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
		}
		final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
		final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);
		
		if (!model.hasValidationErrors()) {
			result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(),
					getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest),
					null, UserSession.getCurrent().getLoggedLocId());
			if (result != null && !result.isEmpty()) {
				count = viewPropertyDetailsService.getTotalSearchCountForAll(model.getSearchDto(),
						getModel().createPagingDTO(httpServletRequest),
						getModel().createGridSearchDTO(httpServletRequest), null);
			}
		}
		return this.getModel().paginate(httpServletRequest, page, rows, count, result);
	}

	@RequestMapping(params = "generateCertificate", method = RequestMethod.POST)
	public @ResponseBody String getCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		if (MainetConstants.FlagN.equals(model.getScrutinyAppliFlag())) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=NoDueCertificateReport_PT.rptdesign&AppId="
					+ model.getNoDuesCertificateDto().getApmApplicationId();
		} else {
			return "AdminHome.html";
		}
	}

	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public Map<String, List<String>> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo,
			HttpServletRequest request) {
		this.getModel().bind(request);
		Organisation org = UserSession.getCurrent().getOrganisation();
		List<String> flatNoList = new ArrayList<String>();
		Map<String, List<String>> resultMap = new HashMap<>();
		String billingMethod = null;
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, org.getOrgid());
		LookUp billingMethodLookUp = null;
		try {
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (billingMethodLookUp != null) {
			billingMethod = billingMethodLookUp.getLookUpCode();
			getModel().setBillMethod(billingMethod);
		}

		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, org.getOrgid());
		} else {
			flatNoList = new ArrayList<String>();
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, org.getOrgid());
		}
		if (billingMethod == null) {
			resultMap.put("NULL", flatNoList);
		} else {
			resultMap.put(billingMethod, flatNoList);
		}

		return resultMap;
	}

}
