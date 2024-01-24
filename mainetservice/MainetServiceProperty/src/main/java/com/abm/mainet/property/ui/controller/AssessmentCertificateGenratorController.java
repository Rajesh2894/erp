package com.abm.mainet.property.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.abm.mainet.property.ui.model.PropertyNoDuesCertificateModel;

@Controller
@RequestMapping("/AssessmentCertificateGenrator.html")
public class AssessmentCertificateGenratorController extends AbstractFormController<PropertyNoDuesCertificateModel> {
	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private TbCfcApplicationMstService tbCfcservice;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		setCommonFeilds();
		getModel().getNoDuesCertificateDto().setServiceUrl("AssessmentCertificateGenrator.html");
		getModel().setCommonHelpDocs("AssessmentCertificateGenrator.html");
		return index();
	}

	private void setCommonFeilds() {
		PropertyNoDuesCertificateModel model = this.getModel();
		getModel().setNoDuesCertificateDto(new NoDuesCertificateDto());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setOrgId(orgId);
		getModel().setAllowToGenerate(MainetConstants.Y_FLAG);
		ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.ACG, orgId);
		final LookUp processLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
		model.setServiceId(service.getSmServiceId());
		model.setServiceName(service.getSmServiceName());
		model.setServiceShrtCode(service.getSmShortdesc());
		model.setDeptId(service.getTbDepartment().getDpDeptid());
		model.setDeptName(service.getTbDepartment().getDpDeptdesc());
		model.setServiceMaster(service);
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

		if (StringUtils.equalsIgnoreCase(CommonMasterUtility
				.getNonHierarchicalLookUpObject(service.getSmChklstVerify(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode(), MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (service.getSmFeesSchedule().equals(1l)) {
			model.setAppliChargeFlag(service.getSmAppliChargeFlag());
		} else {
			model.setAppliChargeFlag(MainetConstants.N_FLAG);
		}
		if ((service.getSmScrutinyApplicableFlag().equals(MainetConstants.N_FLAG)
				&& processLookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA))
				|| (processLookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA))) {
			model.setScrutinyAppliFlag(MainetConstants.N_FLAG);
		} else {
			model.setScrutinyAppliFlag(MainetConstants.Y_FLAG);
		}

		LookUp outstandingCheckPrefix = CommonMasterUtility.getValueFromPrefixLookUp("ACG", "OSC",
				UserSession.getCurrent().getOrganisation());
		if (outstandingCheckPrefix != null)
			if (MainetConstants.FlagY.equals(outstandingCheckPrefix.getOtherField())) {
				model.setNeedToCheckOutstanding(true);
			}
	}

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
			;
			if (dto.getTotalOutsatandingAmt() <= 0) {
				getModel().setAllowToGenerate(MainetConstants.Y_FLAG);
				return defaultMyResult();
			} else {
				this.getModel().addValidationError("property.noDues.pendingDuesValid" + noDuesDto.getPropNo());
			}
		} else {
			this.getModel().addValidationError("property.noDues.propertySearchValid");
		}

		ModelAndView mv = new ModelAndView("ExtractPropertyValidn", MainetConstants.FORM_NAME, this.getModel());
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
					ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RP_AssessmentCertificate_SKDCL.rptdesign&RP_OrgID="
							+ model.getNoDuesCertificateDto().getOrgId() + "&RP_AppID="
							+ model.getNoDuesCertificateDto().getApmApplicationId());
			getModel().setRedirectURL("AdminHome.html");
			return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
		} else {
			return new ModelAndView("redirect:/AdminHome.html");
		}

	}

	@RequestMapping(params = "getPropertyDetailsByFlatNo", method = RequestMethod.POST)
	public @ResponseBody NoDuesCertificateDto getPropertyDetailsByFlatNo(@RequestParam("propNo") String propNo,
			@RequestParam("flatNo") String flatNo, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = new NoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setPropNo(propNo);
		noDuesDto.setFlatNo(flatNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getPropertyDetailsByPropertyNumberNFlatNo(noDuesDto);
		return dto;

	}

	@RequestMapping(params = "backToForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView backToNodues(HttpServletRequest request) {
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(request);
		return defaultResult();
	}

	@RequestMapping(params = "generateCertificate", method = RequestMethod.POST)
	public @ResponseBody String getCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		if (MainetConstants.FlagN.equals(model.getScrutinyAppliFlag())) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RP_AssessmentCertificate_SKDCL.rptdesign&RP_OrgID="
					+ model.getNoDuesCertificateDto().getOrgId() + "&RP_AppID="
					+ model.getNoDuesCertificateDto().getApmApplicationId();
		} else {
			return "AdminHome.html";
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
			logger.info("Exception occure while seting the Counter scheduling info:" + e);
		}
		return new ModelAndView("propertyAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

}
