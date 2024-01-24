package com.abm.mainet.rts.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;

/**
 * @author rahul.chaubey
 * @since 06 March 2020
 */
@Controller
@RequestMapping("/rtsService.html")
public class RtsServiceFormController extends AbstractFormController<RtsServiceFormModel> {

	@Autowired
	private DepartmentService departmentService;


	@Autowired
	private DrainageConnectionService drainageConnectionService;

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	BRMSCommonService brmsCommonService;

	private static final Logger LOGGER = Logger.getLogger(RtsServiceFormController.class);

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		loadDepartment(httpServletRequest);
		loadWardZone(httpServletRequest);
		loadSummaryData(httpServletRequest);
		loadService(httpServletRequest);
		return index();
	}

	private void loadDepartment(HttpServletRequest httpServletRequest) {
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		this.getModel().setDepList(deptMap);
	}

	private void loadWardZone(HttpServletRequest httpServletRequest) {
		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();
		wardList = locationMasterService.findlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		this.getModel().setWardList(wardMapList);

	}

	/*
	 * private void loadSummaryData(HttpServletRequest httpServletRequest) {
	 * List<RequestDTO> summaryData = new ArrayList<RequestDTO>(); summaryData =
	 * drainageConnectionService.loadSummaryData(UserSession.getCurrent().
	 * getOrganisation().getOrgid(), MainetConstants.RightToService.RTS_DEPT_CODE);
	 * this.getModel().setRequestDtoList(summaryData); //code for Defect #80721
	 * start int count = 0; // int count2 = 0; int n = 0; List<AttachDocs>
	 * attachsList = new ArrayList<>(); List<CFCAttachment> cfcAtt = new
	 * ArrayList(); List<AttachDocs> att = new ArrayList();
	 * 
	 * for (int i = 0; i <= summaryData.size() - 1; i++) { TbServicesMst service =
	 * tbServicesMstService.findById(summaryData.get(i).getServiceId()); if
	 * (!service.getSmShortdesc().equalsIgnoreCase("DCS")) { cfcAtt =
	 * ichckService.findAttachmentsForAppId(summaryData.get(i).getApplicationId(),
	 * null, UserSession.getCurrent().getOrganisation().getOrgid()); if
	 * (!cfcAtt.isEmpty()) { int count1 = 0; for (int j = count; j <= cfcAtt.size()
	 * - 1 + count; j++) {
	 * 
	 * AttachDocs doc = new AttachDocs(); if (cfcAtt.get(count1).getClmDesc() ==
	 * null || cfcAtt.get(count1).getClmDesc().isEmpty()) {
	 * doc.setAttPath(cfcAtt.get(count1).getAttPath());
	 * doc.setIdfId(cfcAtt.get(count1).getApplicationId().toString());
	 * doc.setAttFname(cfcAtt.get(count1).getAttFname()); attachsList.add(doc);
	 * break; } count1++;
	 * 
	 * n++; } } } else { att =
	 * (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().
	 * getOrgid(), summaryData.get(i).getApplicationId().toString())); if
	 * (!att.isEmpty()) { int k = 0; // for (int j = count2; j <= att.size() - 1 +
	 * count2; j++) { for (int j = 0; j < att.size(); j++) { AttachDocs doc = new
	 * AttachDocs(); doc.setAttPath(att.get(k).getAttPath());
	 * doc.setIdfId(att.get(k).getIdfId());
	 * doc.setAttFname(att.get(k).getAttFname()); attachsList.add(doc); n++; k++; }
	 * 
	 * } //end Defect #80721 }
	 * 
	 * } this.getModel().setAttachDocsList(attachsList);
	 * 
	 * 
	 * this.getModel().setRequestDtoList(drainageConnectionService
	 * .loadSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(),
	 * MainetConstants.RightToService.RTS_DEPT_CODE));
	 * 
	 * 
	 * }
	 */
	
	/*
	 * code reverted after changing code for file Upload and store document related
	 * data in cfc_attach_document table.
	 */
	private void loadSummaryData(HttpServletRequest httpServletRequest) {
		List<RequestDTO> summaryData = new ArrayList<RequestDTO>();
		summaryData = drainageConnectionService.loadSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.RightToService.RTS_DEPT_CODE);
		this.getModel().setRequestDtoList(summaryData);

		/*
		 * int count = 0; int n = 0; List<AttachDocs> attachsList = new ArrayList<>();
		 * List<AttachDocs> att = new ArrayList<>(); for (int i = 0; i <=
		 * summaryData.size() - 1; i++) { att =
		 * (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().
		 * getOrgid(), summaryData.get(i).getApplicationId().toString())); if
		 * (!att.isEmpty()) { for (int j = count; j <= att.size() - 1 + count; j++) {
		 * attachsList.addAll(j, att); n++; } count = n; } }
		 * this.getModel().setAttachDocsList(attachsList);
		 */

		/*
		 * this.getModel().setRequestDtoList(drainageConnectionService
		 * .loadSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(),
		 * MainetConstants.RightToService.RTS_DEPT_CODE));
		 */

	}

	// Loads the service on to the service page.
	private ModelAndView loadService(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		List<Object[]> service = null;
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("RTS");
		final Map<Long, String> serviceMap = new LinkedHashMap<Long, String>();

		service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.findAllActiveServicesForDepartment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId);

		for (final Object[] obj : service) {
			if (obj[0] != null) {
				if (obj[1].toString().compareTo("First Appeal") != 0
						&& obj[1].toString().compareTo("Second Appeal") != 0) {
					serviceMap.put((Long) obj[0], (String) obj[1]);
				}

			}
		}
		this.getModel().setServiceList(serviceMap);

		return new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/*
	 * // get service for search on the basis of deptartmentId.
	 * 
	 * @RequestMapping(params = "searchService", method = RequestMethod.POST)
	 * private ModelAndView searchService(HttpServletRequest
	 * httpServletRequest, @RequestParam Long deId) { ModelAndView mv = null;
	 * List<Object[]> service = null; this.getModel().setDeptId(deId); final
	 * Map<Long, String> serviceMap = new LinkedHashMap<Long, String>(); service =
	 * ApplicationContextProvider.getApplicationContext().getBean(
	 * ServiceMasterService.class)
	 * .findAllActiveServicesForDepartment(UserSession.getCurrent().getOrganisation(
	 * ).getOrgid(), deId);
	 * 
	 * for (final Object[] obj : service) { if (obj[0] != null) {
	 * serviceMap.put((Long) (obj[0]), (String) (obj[1])); } }
	 * this.getModel().setServiceList(serviceMap); mv = new
	 * ModelAndView("RtsServiceSearchForm", MainetConstants.FORM_NAME,
	 * this.getModel()); return mv;
	 * 
	 * }
	 */

	// loads the application number on the basis of service id (onChange)
	@RequestMapping(params = "searchApplicationId", method = RequestMethod.POST)
	private ModelAndView loadApplicationNo(HttpServletRequest httpServletRequest, @RequestParam Long serviceId) {
		ModelAndView mv = null;
		ServiceMaster serviceMasterData = null;
		this.getModel().setServiceId(serviceId);
		if (serviceId != null) {
			serviceMasterData = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMaster(serviceId, UserSession.getCurrent().getOrganisation().getOrgid());
		}
		this.getModel().setApplicationNo(ApplicationContextProvider.getApplicationContext()
				.getBean(DrainageConnectionService.class).getApplicationNo(
						UserSession.getCurrent().getOrganisation().getOrgid(), serviceMasterData.getSmServiceId()));
		mv = new ModelAndView("RtsServiceSearchForm", MainetConstants.FORM_NAME, this.getModel());
		this.getModel().getRequestDTO().setServiceId(serviceId);
		return mv;
	}

	// search
	@RequestMapping(params = "search", method = RequestMethod.POST)
	private ModelAndView searchData(HttpServletRequest httpServletRequest, @RequestParam Long applicationId,
			@RequestParam Long serviceId) {
		ModelAndView mv = null;

		List<RequestDTO> dataList = new ArrayList<RequestDTO>();

		this.getModel().bind(httpServletRequest);
		dataList = ApplicationContextProvider.getApplicationContext().getBean(DrainageConnectionService.class)
				.searchData(applicationId, serviceId, UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setRequestDtoList(dataList);

		this.getModel().getRequestDTO().setApplicationId(applicationId);
		mv = new ModelAndView("RtsServiceSearchForm", MainetConstants.FORM_NAME, this.getModel());

		return mv;
	}

	// view and add
	@RequestMapping(params = "applicantForm", method = RequestMethod.POST)
	private ModelAndView applicantForm(final HttpServletRequest httpServletRequest, @RequestParam Long applicationId) {
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		loadService(httpServletRequest);
		if (applicationId == MainetConstants.ZERO_LONG) {
			this.getModel().setSaveMode("C");
			mv = new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, this.getModel());
		} else {
			RequestDTO requestDto = ApplicationContextProvider.getApplicationContext()
					.getBean(DrainageConnectionService.class)
					.getApplicationFormData(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setRequestDTO(requestDto);
			this.getModel().setSaveMode("V");
			this.getModel().getRequestDTO().setStatus("V");
			mv = new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, this.getModel());
		}
		return mv;
	}

	@RequestMapping(params = "serviceForm", method = RequestMethod.POST)
	private String serviceForm(final HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		// this.sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		LOGGER.info("serviceForm stated----------------->");
		String redirect = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		/*
		 * ServiceMaster serviceMasterData =
		 * ApplicationContextProvider.getApplicationContext()
		 * .getBean(ServiceMasterService.class)
		 * .getServiceMaster(this.getModel().getRequestDTO().getServiceId(), orgId);
		 */
		String shortCode=MainetConstants.RightToService.DCS;
		LOGGER.info("shortCode----------------->"+shortCode);
		// here redirect to particular handler based on service code
		switch (shortCode) {
		case MainetConstants.RightToService.SERVICE_CODE.BIRTH_CERTIFICATE:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getRequestDTO());
			redirect = "redirect:ApplyforBirthCertificate.html";
			break;
		case MainetConstants.RightToService.SERVICE_CODE.DEATH_CERTIFICATE:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getRequestDTO());
			redirect = "redirect:applyForDeathCertificate.html";
			break;
		case MainetConstants.RightToService.DCS:
			redirectAttributes.addFlashAttribute("requestDTO", this.getModel().getRequestDTO());
			redirect = "redirect:drainageConnection.html";
			break;

		}
		LOGGER.info("redirect----------------->"+redirect);
		return redirect;
	}

}
