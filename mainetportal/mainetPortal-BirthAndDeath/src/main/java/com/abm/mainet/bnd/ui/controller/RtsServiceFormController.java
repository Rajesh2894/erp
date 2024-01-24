package com.abm.mainet.bnd.ui.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.bnd.constant.RtsConstants;
import com.abm.mainet.bnd.service.IRtsService;
import com.abm.mainet.bnd.service.RtsServiceImpl;
import com.abm.mainet.bnd.ui.model.RtsServiceFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Controller("bndServiceFormController")
@RequestMapping("/bndService.html")
public class RtsServiceFormController extends AbstractFormController<RtsServiceFormModel>  {

	@Autowired
	IPortalServiceMasterService serviceMasterService;

	@Autowired
	private ICommonBRMSService brmsCommonService, iCommonBRMSService;

	@Autowired
	private IRtsService rtsService;

	private static final Logger LOGGER = Logger.getLogger(RtsServiceFormController.class);

	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("bndService.html");
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
		if (this.getModel().getReqDTO().getApplicationId() != null) {
			RequestDTO reqDto = iService
					.fetchRtsApplicationInformationById(this.getModel().getReqDTO().getApplicationId(), orgId);
			reqDto.setServiceShortCode(this.getModel().getReqDTO().getServiceShortCode());
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

		mv = new ModelAndView("bndApplicantForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@RequestMapping(params = "serviceForm", method = RequestMethod.POST)
	private String serviceForm(final HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		this.getModel().bind(httpServletRequest);
		String redirect = null;
		String shortCode = this.getModel().getReqDTO().getServiceShortCode().replaceAll("[^a-zA-Z0-9\\s+]", "");
		this.getModel().getReqDTO().setServiceShortCode(shortCode);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		// here redirect to particular handler based on service code
		switch (shortCode) {
		case RtsConstants.RBC:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getReqDTO());
			redirect = "redirect:bndApplyforBirthCertificate.html";
			break;
		case RtsConstants.RDC:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getReqDTO());
			redirect = "redirect:bndApplyForDeathCertificate.html";
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
			List<DocumentDetailsVO> checkListList1 = rtsService.fetchDrainageConnectionDocsByAppNo(
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
		switch (shortCode) {
		case RtsConstants.RBC:
			model.setServiceName(RtsConstants.APPLY_FOR_BIRTH_CERTIFICATE_SERVICE);
			model.setFormName(RtsConstants.BIRTH_CERTIFICATE_FORMNAME);
			break;
		case RtsConstants.RDC:
			model.setServiceName(RtsConstants.APPLY_FOR_DEATH_CERTIFICATE_SERVICE);
			model.setFormName(RtsConstants.DEATH_CERTIFICATE_FORMNAME);
			break;
		case MainetConstants.RightToService.DCS:
			model.setServiceName(MainetConstants.RightToService.APPLY_DRAINAGE_CONNECTION);
			model.setFormName(RtsConstants.DRAINAGE_CONNECTION_FORMNAME);
			break;
		}

		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "applyForNewCertificate", method = { RequestMethod.GET, RequestMethod.POST },  produces = "Application/JSON")
	public ModelAndView applyForNewCertificate(final HttpServletRequest httpServletRequest,@RequestParam("shortCode") String shortCode) {
		sessionCleanup(httpServletRequest);

		final Employee emp = UserSession.getCurrent().getEmployee();
		if (emp != null) {
			setApplicantDetail(emp, this.getModel());
		}
		this.getModel().getReqDTO().setServiceShortCode(shortCode);
		LoadWardZone(httpServletRequest);
		return new ModelAndView("bndApplicantForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		getModel().bind(httpServletRequest);
		RtsServiceFormModel model = new RtsServiceFormModel();
		model.setDrainageConnectionDto(this.getModel().getDrainageConnectionDto());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		try {
			Map<String, String> serviceMap = rtsService
					.fetchRtsService(UserSession.getCurrent().getOrganisation().getOrgid());
			model.setServiceMap(serviceMap);
			Map<Long, String> wardMap = wardMap = rtsService
					.fetchWardZone(UserSession.getCurrent().getOrganisation().getOrgid());
			model.setWardList(wardMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);

		RequestDTO reqDto = rtsService.fetchRtsApplicationInformationById(appId, orgId);
		model.setReqDTO(reqDto);
		if(getModel().getServiceCode().equals(MainetConstants.RightToService.DCS))
		{
		model.getReqDTO().setServiceShortCode(MainetConstants.RightToService.DCS);
		}
		else if(getModel().getServiceCode().equals(RtsConstants.RBC))
		{
			model.getReqDTO().setServiceShortCode(RtsConstants.RBC);
		}
		else  if(getModel().getServiceCode().equals(RtsConstants.RDC))
		{
			model.getReqDTO().setServiceShortCode(RtsConstants.RDC);
		}

		mv = new ModelAndView("bndApplicantFormValidn", MainetConstants.FORM_NAME, model);

		return mv;
	}
}
