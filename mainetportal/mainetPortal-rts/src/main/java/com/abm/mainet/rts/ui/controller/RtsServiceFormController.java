package com.abm.mainet.rts.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.service.IRtsService;
import com.abm.mainet.rts.service.RtsBrmsService;
import com.abm.mainet.rts.service.RtsServiceImpl;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;

@Controller
@RequestMapping("/rtsService.html")
public class RtsServiceFormController extends AbstractFormController<RtsServiceFormModel>  {

	@Autowired
	IPortalServiceMasterService serviceMasterService;

	@Autowired
	private ICommonBRMSService brmsCommonService, iCommonBRMSService;

	@Autowired
	private RtsBrmsService checklistService;

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	@Autowired
	private IRtsService rtsService;

	private static final Logger LOGGER = Logger.getLogger(RtsServiceFormController.class);

	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("rtsService.html");
		final Employee emp = UserSession.getCurrent().getEmployee();
		if (emp != null) {
			setApplicantDetail(emp, this.getModel());
		}
		loadService(httpServletRequest);
		LoadWardZone(httpServletRequest);
		return new ModelAndView("RtsServiceForm", MainetConstants.FORM_NAME, this.getModel());
	}

	private void LoadWardZone(HttpServletRequest httpServletRequest) {
		Map<Long, String> wardMap = new LinkedHashMap<Long, String>();
		wardMap = rtsService.fetchWardZone(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWardList(wardMap);
	}

	@RequestMapping(params = "getServiceList", method = RequestMethod.POST)
	private ModelAndView loadService(HttpServletRequest httpServletRequest) {

		Map<String, String> serviceMap = rtsService
				.fetchRtsService(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> deptMap = new LinkedHashMap<>(0);

		this.getModel().setServiceMap(serviceMap);

		return new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, this.getModel());
	}

	// reload
	@RequestMapping(params = "applicantForm", method = RequestMethod.POST)
	private ModelAndView applicantForm(final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		IRtsService iService = new RtsServiceImpl();
		/*
		 * // setting servicelist and wordZone for showing at the time of back to
		 * applicant form Defect #81731
		 *
		 */
		if (this.getModel().getReqDTO().getApplicationId() != null
				&& this.getModel().getReqDTO().getServiceShortCode().equals(MainetConstants.RightToService.DCS)) {
			RequestDTO reqDto = iService
					.fetchRtsApplicationInformationById(this.getModel().getReqDTO().getApplicationId(), orgId);
			reqDto.setServiceShortCode(MainetConstants.RightToService.DCS);
			this.getModel().setReqDTO(reqDto);
			this.getModel().setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);
		}
		try {
			Map<String, String> servicemap = iService.fetchRtsService(orgId);
			Map<Long, String> deptMap = new LinkedHashMap<>(0);
			this.getModel().setServiceMap(servicemap);
			Map<Long, String> wardMap = new LinkedHashMap<Long, String>();
			wardMap = iService.fetchWardZone(orgId);
			this.getModel().setWardList(wardMap);
		} catch (Exception e) { // TODO: handle exception
			e.printStackTrace();
		}

		mv = new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@RequestMapping(params = "serviceForm", method = RequestMethod.POST)
	private String serviceForm(final HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		this.getModel().bind(httpServletRequest);
		String redirect = null;
		String shortCode = MainetConstants.RightToService.DCS;
		this.getModel().getReqDTO().setServiceShortCode(shortCode);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		// here redirect to particular handler based on service code
		switch (shortCode) {
		case MainetConstants.RightToService.SERVICE_CODE.BIRTH_CERTIFICATE:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getReqDTO());
			redirect = "redirect:ApplyforBirthCertificate.html";
			break;
		case MainetConstants.RightToService.SERVICE_CODE.DEATH_CERTIFICATE:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getReqDTO());
			redirect = "redirect:applyForDeathCertificate.html";
			break;
		case MainetConstants.RightToService.DCS:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getReqDTO());
			redirect = "redirect:drainageConnection.html";
			break;

		}
		return redirect;
	}

	private void setApplicantDetail(final Employee emp, final RtsServiceFormModel model) {
		model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
		model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpMName());
		model.getApplicantDetailDto().setApplicantLastName(emp.getEmpLName());
		model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
		model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
		model.getApplicantDetailDto().setApplicantTitle(emp.getTitle());
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((emp.getEmpGender() != null) && !emp.getEmpGender().isEmpty()) {
				if (lookUp.getLookUpCode().equals(emp.getEmpGender())) {
					model.getApplicantDetailDto().setGender(String.valueOf(lookUp.getLookUpId()));
					break;
				}
			}
		}
		// model.setGender(model.getApplicantDetailDto().getGender());
		if (emp.getPincode() != null) {
			model.getApplicantDetailDto().setPinCode(emp.getPincode());
		}
	}

	@RequestMapping(params = "applicantFormBackData", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView applicantFormBackData(Model model, final HttpServletRequest request) {
		this.getModel().bind(request);
		return new ModelAndView("rtsApplicantFormValidn", MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		this.getModel().bind(httpServletRequest);
		RtsServiceFormModel model = this.getModel();
		String shortCode = this.getModel().getReqDTO().getServiceShortCode().replaceAll("[^a-zA-Z0-9\\s+]", "");
		this.getModel().getReqDTO().setServiceShortCode(shortCode);
		
		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<DocumentDetailsVO> checkListList1 = drainageConnectionService.fetchDrainageConnectionDocsByAppNo(
					model.getApmApplicationId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (checkListList1 != null && !checkListList1.isEmpty()) {
				model.setDocumentList(checkListList1);
			}
		}

		String name = " ";
		if (model.getReqDTO().getfName() != null) {
			name = model.getReqDTO().getfName() + " ";
		}
		if (model.getReqDTO().getmName() != null) {
			name += model.getReqDTO().getmName() + " ";
		}
		if (model.getReqDTO().getlName() != null) {
			name += model.getReqDTO().getlName();
		}
		model.getApplicantDetailDto().setApplicantFirstName(name);
		model.setApmApplicationId(model.getApmApplicationId());
		int langId = UserSession.getCurrent().getLanguageId();
		if (langId == 1) {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		} else {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
		}
		switch (shortCode) {
		case MainetConstants.RightToService.SERVICE_CODE.BIRTH_CERTIFICATE:
			model.setServiceName(getApplicationSession().getMessage("BirthCertificateDTO.serName"));
			model.setFormName(getApplicationSession().getMessage("BirthCertificateDTO.serviceName"));
			break;
		case MainetConstants.RightToService.SERVICE_CODE.DEATH_CERTIFICATE:
			model.setServiceName(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
			model.setFormName(getApplicationSession().getMessage("TbDeathregDTO.drDeathSerEngName"));
			break;
		case MainetConstants.RightToService.DCS:
			model.setServiceName(getApplicationSession().getMessage("DrainageConnectionDTO.serName"));
			model.setFormName(getApplicationSession().getMessage("DrainageConnectionDTO.serviceName"));
			break;
		}

		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);

	}

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetailRts", MainetConstants.FORM_NAME, getModel());
	}
}
