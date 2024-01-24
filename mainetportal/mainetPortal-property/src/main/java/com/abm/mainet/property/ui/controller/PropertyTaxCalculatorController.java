package com.abm.mainet.property.ui.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;

@Controller
@RequestMapping("/PropertyTaxCalculator.html")
public class PropertyTaxCalculatorController extends AbstractFormController<SelfAssesmentNewModel> {

	@Autowired
	private SelfAssessmentService selfAssessmentService;

	@Autowired
	private IPortalServiceMasterService serviceMaster;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		final UserSession session = UserSession.getCurrent();
		if (session.getOrganisation().getOrgid() == 1) {
			return new ModelAndView("PropertyTaxCalculatorMainPage", MainetConstants.FORM_NAME, this.getModel());
		} else {
			displayPageAfterOrgSelection(session);
			return new ModelAndView("PropertyTaxCalculator", MainetConstants.FORM_NAME, this.getModel());
		}
	}

	private void displayPageAfterOrgSelection(UserSession session) {
		SelfAssesmentNewModel model = getModel();
		model.setOrganizationName(session.getOrganisation().getONlsOrgname());
		setCommonFields(session.getOrganisation().getOrgid());
		Organisation org = UserSession.getCurrent().getOrganisation();
		SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(org.getOrgid(),
				getModel().getDeptId());
		data.setOrgId(org.getOrgid());
		data.setDeptId(getModel().getDeptId());
		Date date = new Date();
		model.getDefaultData().setUiDate(date);
		SelfAssessmentFinancialDTO dto = selfAssessmentService.getFinyearDate(model.getDefaultData());
		ProvisionalAssesmentDetailDto detailsDto = new ProvisionalAssesmentDetailDto();
		detailsDto.setFaYearId(dto.getFinYearId());
		model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().add(detailsDto);
		model.getDefaultData().setGivenfinYearId(dto.getFinYearId());
		SelfAssessmentFinancialDTO financialDto = selfAssessmentService.fetchFromGivenDate(getModel().getDefaultData());
		getModel().setFinYearList(financialDto.getFinYearList());
		model.setFinancialYearMap(data.getFinancialYearMap());
		model.setDefaultData(data);
	}

	@RequestMapping(params = "ShowFormDetails", method = RequestMethod.POST)
	public ModelAndView ShowFormDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SelfAssesmentNewModel model = getModel();
		getModel().bind(request);
		setCommonFields(getModel().getOrgId());
		SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(getModel().getOrgId(),
				getModel().getDeptId());
		data.setOrgId(getModel().getOrgId());
		data.setDeptId(getModel().getDeptId());
		Date date = new Date();
		model.getDefaultData().setUiDate(date);
		SelfAssessmentFinancialDTO dto = selfAssessmentService.getFinyearDate(model.getDefaultData());
		ProvisionalAssesmentDetailDto detailsDto = new ProvisionalAssesmentDetailDto();
		detailsDto.setFaYearId(dto.getFinYearId());
		model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().add(detailsDto);
		model.getDefaultData().setGivenfinYearId(dto.getFinYearId());
		SelfAssessmentFinancialDTO financialDto = selfAssessmentService.fetchFromGivenDate(getModel().getDefaultData());
		getModel().setFinYearList(financialDto.getFinYearList());
		model.setFinancialYearMap(data.getFinancialYearMap());
		model.setDefaultData(data);
		setOrganisationData(request, response, getModel().getOrgId());
		return new ModelAndView("PropertyTaxCalculatorDiv", MainetConstants.FORM_NAME, this.getModel());
	}

	private void setCommonFields(Long orgId) {
		SelfAssesmentNewModel model = this.getModel();
		final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, orgId);
		final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
		model.setOrgId(orgId);
		model.setDeptId(deptId);
		model.setServiceId(serviceId);
	}

	@RequestMapping(params = "nextToViewPage", method = RequestMethod.POST)
	public ModelAndView nextToViewPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		SelfAssesmentNewModel model = this.getModel();
		Organisation org = new Organisation();
		org.setOrgid(getModel().getOrgId());
		this.getModel().setDropDownValues(org);
		getCheckListAndcharges(model);
		return new ModelAndView("PropertyTaxCalculatorView", MainetConstants.FORM_NAME, model);
	}

	private void getCheckListAndcharges(SelfAssesmentNewModel model) {
		ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
		dto.setOrgId(model.getOrgId());
		dto.setSmServiceId(model.getServiceId());
		model.getDefaultData().setOrgId(model.getOrgId());
		model.getDefaultData().setProvisionalMas(dto);
		model.getDefaultData().setProvAsseFactDtlDto(model.getProvAsseFactDtlDto());
		model.getDefaultData().setFinYearList(model.getFinYearList());
		AssessmentChargeCalDTO checklistCharge = selfAssessmentService.fetchCharges(model.getDefaultData());
		model.setDemandLevelRebateList(checklistCharge.getDemandLevelRebateList());
		checklistCharge.getProvisionalMas().setBillTotalAmt(checklistCharge.getBillTotalAmt());
		model.setBillMasList(checklistCharge.getBillMasList());
		Map<String, List<BillDisplayDto>> displayMap = selfAssessmentService.getTaxMapForDisplayCategoryWise(
				checklistCharge.getDisplayDto(), UserSession.getCurrent().getOrganisation(), model.getDeptId(),
				checklistCharge.getTaxCatList());
		model.setProvisionalAssesmentMstDto(checklistCharge.getProvisionalMas());
		model.setDisplayMap(displayMap);
	}

	@RequestMapping(params = "BackToMainPage", method = RequestMethod.POST)
	public ModelAndView BackToMainPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		SelfAssesmentNewModel model = this.getModel();
		UserSession.getCurrent().getOrganisation().setOrgid(getModel().getOrgId());
		Long max = 0l;
		List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
				.getProvisionalAssesmentDetailDtoList();
		if (detailDto != null && !detailDto.isEmpty()) {
			for (ProvisionalAssesmentDetailDto dto : detailDto) {
				if (dto.getAssdUnitNo() > max) {
					max = dto.getAssdUnitNo();
				}
			}
		}
		model.setMaxUnit(max);
		return new ModelAndView("BackToPropertyTaxCalculator", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "viewApplication", method = RequestMethod.POST)
	public ModelAndView viewApplication(HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		SelfAssesmentNewModel model = this.getModel();
		UserSession session = UserSession.getCurrent();
		if (UserSession.getCurrent().getOrganisation().getDefaultStatus().equals(MainetConstants.YES)) {
			return new ModelAndView("PropertyTaxCalculatorMain", MainetConstants.FORM_NAME, model);
		} else {
			displayPageAfterOrgSelection(session);
			return new ModelAndView("PropertyTaxCalculatorAfterUlBSelection", MainetConstants.FORM_NAME,
					this.getModel());
		}
	}

	@RequestMapping(params = "clearSessionData", method = RequestMethod.POST)
	public @ResponseBody void clearSessionData(HttpServletRequest request) throws Exception {
		UserSession.getCurrent().getOrganisation()
				.setOrgid(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
	}

	private void setOrganisationData(final HttpServletRequest request, final HttpServletResponse response,
			final Long long1) {
		if ((long1 != null)) {
			try {
				final UserSession userSession = UserSession.getCurrent();
				userSession.getMapList().clear();
				final long orgId = Long.valueOf(long1);
				setSessionAttributeValue(request, response, orgId, userSession);

			} catch (final Exception exception) {
				throw new FrameworkException("Exception occured while setting ULB information: ", exception);
			}
		}
	}

	private void setSessionAttributeValue(final HttpServletRequest request, final HttpServletResponse response,
			final long orgId, final UserSession userSession) {
		final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
		getModel().setOrganizationName(organisation.getONlsOrgname());
		userSession.setOrganisation(organisation);
		final ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		final String loginName = context.getMessage("citizen.noUser.loginName", (Object[]) null,
				"citizen.noUser.loginName", LocaleContextHolder.getLocale());
		userSession.setEmployee(
				iEmployeeService.getEmployeeByLoginName(loginName, organisation, MainetConstants.IsDeleted.ZERO));
		userSession.getEmployee().setEmppiservername(Utility.getClientIpAddress(request));
		UserSession.getCurrent().setQuickLinkReg(null);
		UserSession.getCurrent().setQuickLinkEng(null);
		UserSession.getCurrent().setLogoImagesList(null);
		UserSession.getCurrent().setSlidingImgLookUps(null);
	}

}
