package com.abm.mainet.common.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.CommonConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.SiteInpectionReportDto;
import com.abm.mainet.common.dto.TbVisitorSchedule;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbVisitorScheduleService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.sun.istack.FinalArrayList;

/**
 * Spring MVC controller for 'TbVisitorSchedule' management.
 */
@Controller
@RequestMapping("/SiteInspection.html")
public class TbSiteInspectionController extends AbstractController {

	private static final String REDIRECT_SITE_INSPECTION_HTML_INPESCTION_LETTER = "redirect:/SiteInspection.html?inpesctionLetter";
	// --- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "tbSiteInspection";
	private static final String MAIN_LIST_NAME = "list";

	// --- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM = "tbSiteInspection/form";
	private static final String JSP_INSPECTION_LETTER = "tbSiteInspection/letter";

	// --- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE = "SiteInspection.html?create";
	private static final String SAVE_ACTION_UPDATE = "SiteInspection.html?update";
	private static final String COMMAND = "command";
	private static final String COM = "COM";
	private static final String tb_visitor_schedule = "tb_visitor_schedule";
	private static final String VIS_INSP_NO = "VIS_INSP_NO";
	private static final String F = "F";
	private static final String inspectionLetter = "scutiny.letter.inspection";

	@Resource
	private TbVisitorScheduleService tbVisitorScheduleService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private IEmployeeService EmployeeService;

	@Resource
	private ICFCApplicationMasterService cfcService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
    private IFileUploadService fileUpload;
	
	@Autowired
    private ISMSAndEmailService ismsAndEmailService;


	public TbSiteInspectionController() {
		super(TbSiteInspectionController.class, MAIN_ENTITY_NAME);
		log("TbSiteInspectionController created.");
	}

	/**
	 * Populates the Spring MVC model with the given entity and eventually other
	 * useful data
	 * 
	 * @param model
	 * @param tbVisitorSchedule
	 */
	private void populateModel(final Model model, final TbVisitorSchedule tbVisitorSchedule, final FormMode formMode) {
		// --- Main entity
		model.addAttribute(COMMAND, new TbVisitorSchedule());

		final List<TbDepartment> department = tbDepartmentService.findAll();
		model.addAttribute(CommonConstants.DEPARTMENT_LIST, department);

		model.addAttribute(MAIN_ENTITY_NAME, tbVisitorSchedule);
		if (formMode == FormMode.CREATE) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
			// --- Other data useful in this screen in "create" mode (all fields)
		} else if (formMode == FormMode.UPDATE) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
			// --- Other data useful in this screen in "update" mode (only non-pk fields)
		}
	}

	/**
	 * Shows a list with all the occurrences of TbVisitorSchedule found in the
	 * database
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(final Model model) {
		log("Action 'list'");
		final List<TbVisitorSchedule> list = tbVisitorScheduleService.findAll();

		final TbVisitorSchedule tbVisitorSchedule = new TbVisitorSchedule();

		populateModel(model, tbVisitorSchedule, FormMode.CREATE);
		model.addAttribute(MAIN_LIST_NAME, list);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to create a new TbVisitorSchedule
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(final Model model) {
		log("Action 'formForCreate'");
		// --- Populates the model with a new instance
		final TbVisitorSchedule tbVisitorSchedule = new TbVisitorSchedule();
		populateModel(model, tbVisitorSchedule, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by
	 * 'http redirect'<br>
	 * 
	 * @param tbVisitorSchedule
	 *            entity to be created
	 * @param bindingResult
	 *            Spring MVC binding result
	 * @param model
	 *            Spring MVC model
	 * @param redirectAttributes
	 *            Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = "create") // GET or POST
	public String saveIspectionLetter(@Valid final TbVisitorSchedule tbVisitorSchedule,
			final BindingResult bindingResult, final Model model, final HttpServletRequest httpServletRequest) {
		log("Action 'create' class:TbSiteInspectionController method:saveIspectionLetter()");
		try {
			if (!bindingResult.hasErrors()) {

				final ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();
				lableValueDTO.setApplicationId(tbVisitorSchedule.getVisApplicationId());
				lableValueDTO.setLableId(tbVisitorSchedule.getLabelId());
				lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getUserId());
				lableValueDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				lableValueDTO.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
				lableValueDTO.setLevel(tbVisitorSchedule.getLevel());
				lableValueDTO.setLableValue(tbVisitorSchedule.getLabelVal());

				final TbVisitorSchedule tbVisitorScheduleCreated = tbVisitorScheduleService.create(tbVisitorSchedule,
						lableValueDTO);
				model.addAttribute(MAIN_ENTITY_NAME, tbVisitorScheduleCreated);
				
				//Defect #142243			// send SMS and Email to the citizen 
				final CFCApplicationAddressEntity cfcAddressEntity = cfcService.getApplicantsDetails(tbVisitorSchedule.getVisApplicationId());
				final TbCfcApplicationMstEntity applicantDetails = cfcService.getCFCApplicationByApplicationId(tbVisitorSchedule.getVisApplicationId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) 
						&& MainetConstants.DEPT_SHORT_NAME.WATER.equals(applicantDetails.getTbServicesMst().getTbDepartment().getDpDeptcode())) {
					if(tbVisitorSchedule != null) {
						HashMap<String, String> applicantDetailsMap = new HashMap<>();
						applicantDetailsMap.put("email", cfcAddressEntity.getApaEmail());//
						applicantDetailsMap.put("mobile",cfcAddressEntity.getApaMobilno() );//
						applicantDetailsMap.put("appName", getApplicantFullName(applicantDetails));
						applicantDetailsMap.put("langId", cfcAddressEntity.getLangId().toString());
						applicantDetailsMap.put("userId", tbVisitorSchedule.getUserId().toString());
						applicantDetailsMap.put("siteInspectionDate", tbVisitorSchedule.getDateTimeStr());
						sendSMSandEMail(tbVisitorScheduleCreated, tbVisitorSchedule.getVisApplicationId(), MainetConstants.DEPT_SHORT_NAME.WATER,
								UserSession.getCurrent().getOrganisation(), applicantDetailsMap, applicantDetails.getTbServicesMst());
					}
				}
				
				return redirectToForm(httpServletRequest, tbVisitorSchedule.getVisId(), tbVisitorSchedule.getOrgid());
			} else {
				populateModel(model, tbVisitorSchedule, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch (final Exception e) {
			log("Action 'create' : Exception - " + e.getMessage());
			messageHelper.addException(model, "tbVisitorSchedule.error.create", e);
			populateModel(model, tbVisitorSchedule, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	@RequestMapping(params = "getDeptEmpl", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getDeptEmpl(@RequestParam("deptId") final Long deptId) {
		final List<Employee> employeeList = EmployeeService
				.getAllListEmployeeByDeptId(UserSession.getCurrent().getOrganisation(), deptId);
		return employeeList;
	}

	@RequestMapping(params = "inpesctionLetter")
	public String generateInpesctionLetter(final Model model, final HttpServletRequest httpServletRequest) {
		log("Action 'inpesction Letter'");
		final SiteInpectionReportDto inpectionReportDto = new SiteInpectionReportDto();
		TbVisitorSchedule tbVisitorSchedule = null;
		Long applicationId = null;
		Long labelId = null;
		Long serviceId = null;
		String labelVal = null;
		if (httpServletRequest.getSession().getAttribute(MainetConstants.APPL_ID) != null) {
			applicationId = Long
					.parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.APPL_ID).toString());
			labelId = Long.parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.LABEL_ID).toString());
			serviceId = Long.parseLong(httpServletRequest.getSession()
					.getAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID).toString());
			labelVal = httpServletRequest.getSession().getAttribute(MainetConstants.LABEL_VAL).toString();
			httpServletRequest.getSession().removeAttribute(MainetConstants.APPL_ID);
			httpServletRequest.getSession().removeAttribute(MainetConstants.LABEL_ID);
			httpServletRequest.getSession().removeAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID);
			httpServletRequest.getSession().removeAttribute(MainetConstants.LABEL_VAL);
		}

		model.addAttribute(MainetConstants.APPL_ID, applicationId);
		model.addAttribute(MainetConstants.LABEL_ID, labelId);
		model.addAttribute(MainetConstants.LABEL_VAL, labelVal);
		model.addAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, serviceId);

		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);

		tbVisitorSchedule = tbVisitorScheduleService.findByAppId(applicationId, orgId);
		if ((tbVisitorSchedule != null) && (tbVisitorSchedule.getVisInspLetterDt() == null)) {
			tbVisitorSchedule.setVisInspLetterDt(new Date());
			tbVisitorSchedule.setVisInspNo(ApplicationSession.getInstance().getMessage(inspectionLetter)
					+ MainetConstants.operator.FORWARD_SLACE + serviceMaster.getSmShortdesc()
					+ MainetConstants.operator.FORWARD_SLACE + UserSession.getCurrent().getOrganisation().getOrgid()
					+ MainetConstants.operator.FORWARD_SLACE + Utility.getCurrentYear()
					+ MainetConstants.operator.FORWARD_SLACE
					+ seqGenFunctionUtility.generateSequenceNo(COM, tb_visitor_schedule, VIS_INSP_NO, orgId, F, null));

			tbVisitorSchedule = tbVisitorScheduleService.update(tbVisitorSchedule);
		}

		final TbCfcApplicationMstEntity applicantDetails = cfcService.getCFCApplicationByApplicationId(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final CFCApplicationAddressEntity cfcAddressEntity = cfcService.getApplicantsDetails(applicationId);
		inpectionReportDto.setApplicantName(applicantDetails.getApmFname());
		if (tbVisitorSchedule != null && tbVisitorSchedule.getVisDate() != null)
			inpectionReportDto.setInspectionDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tbVisitorSchedule.getVisDate()));
		inpectionReportDto.setInspectionTime(tbVisitorSchedule.getVisTime());
		inpectionReportDto.setApplicationNo(applicationId.toString());
		inpectionReportDto.setOfficerName(EmployeeService.getEmployeeById(tbVisitorSchedule.getVisEmpid(),
				UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO).getEmpname());
		if (applicantDetails.getApmApplicationDate() != null)
			inpectionReportDto.setApplicationDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(applicantDetails.getApmApplicationDate()));
		inpectionReportDto.setUlbName(applicantDetails.getApmOrgnName());
		if (tbVisitorSchedule.getVisInspLetterDt() != null)
			inpectionReportDto.setLetterDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tbVisitorSchedule.getVisInspLetterDt()));
		inpectionReportDto.setLetterNo(tbVisitorSchedule.getVisInspNo());

		if (cfcAddressEntity.getApaPincode() != null) {
			inpectionReportDto.setPinCode(cfcAddressEntity.getApaPincode().toString());
		}

		inpectionReportDto.setFlatNo(cfcAddressEntity.getApaFloor());
		inpectionReportDto.setBuildingName(cfcAddressEntity.getApaBldgnm());
		inpectionReportDto.setRoadName(cfcAddressEntity.getApaRoadnm());
		inpectionReportDto.setAreaName(cfcAddressEntity.getApaAreanm());
		inpectionReportDto.setServiceCode(serviceMaster.getSmShortdesc());

		if (UserSession.getCurrent().getLanguageId() == 1) {
			inpectionReportDto.setServiceName(serviceMaster.getSmServiceName());
		} else {
			inpectionReportDto.setServiceName(serviceMaster.getSmServiceNameMar());
		}
		
		//D#148874
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
				&& MainetConstants.DeptCode.WATER.equals(serviceMaster.getTbDepartment().getDpDeptcode())) {
			inpectionReportDto.setDeptCode(serviceMaster.getTbDepartment().getDpDeptcode());
			if(!MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(serviceMaster.getSmShortdesc())
					&& !MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE.equals(serviceMaster.getSmShortdesc())
					&& !MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER.equals(serviceMaster.getSmShortdesc())) {
				String csCcn = tbVisitorScheduleService.getConnectionDetailsByAppIdAndOrgId(applicationId, serviceMaster, UserSession.getCurrent().getOrganisation().getOrgid());
				inpectionReportDto.setConnectionNo(csCcn);
				
			}
		}
				
		
		//US#102672					 // pushing document to DMS
		String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
				+ ApplicationSession.getInstance().getMessage("birtName.siteInspection")
				+ "&__format=pdf&RP_ORGID=" + orgId + "&RP_ApplicationID=" + applicationId.toString() + "&RP_INSP_NO="
				+ tbVisitorSchedule.getVisInspNo();
		Utility.pushDocumentToDms(URL, applicationId.toString(), serviceMaster.getTbDepartment().getDpDeptcode(),fileUpload);
		fileUpload.sessionCleanUpForFileUpload();
		
		model.addAttribute(MainetConstants.INSPECTION_LETTER_DTO, inpectionReportDto);
		return JSP_INSPECTION_LETTER;
	}

	@RequestMapping(params = "ScrutinyInspectionLetter", method = RequestMethod.POST)
	public String scrutinyInspectionLetter(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest, final Model model) {

		model.addAttribute(MainetConstants.APPL_ID, applId);
		model.addAttribute(MainetConstants.LABEL_ID, labelId);
		model.addAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, serviceId);
		model.addAttribute(MainetConstants.CommonConstants.LEVEL, level);
		model.addAttribute(MainetConstants.LABEL_VAL, labelVal);

		if ((tbVisitorScheduleService.findByAppId(applId,
				UserSession.getCurrent().getOrganisation().getOrgid()) != null)) {
			httpServletRequest.getSession().setAttribute(MainetConstants.APPL_ID, applId);
			httpServletRequest.getSession().setAttribute(MainetConstants.LABEL_ID, labelId);
			httpServletRequest.getSession().setAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, serviceId);
			httpServletRequest.getSession().setAttribute(MainetConstants.LABEL_VAL, labelVal);
			return new String(REDIRECT_SITE_INSPECTION_HTML_INPESCTION_LETTER);
		}

		final TbVisitorSchedule tbVisitorSchedule = new TbVisitorSchedule();
		populateModel(model, tbVisitorSchedule, FormMode.CREATE);
		return JSP_FORM;

	}
	
	private void sendSMSandEMail(final TbVisitorSchedule inpectionReportDto, final Long applicationNo,
			 final String deptShortCode, final Organisation organisation, final HashMap applicantDto, final ServiceMaster sm) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		if (!StringUtils.isEmpty(sm.getSmServiceName())) {
			dto.setServName(sm.getSmServiceName());
		}
		dto.setAppNo(""+applicationNo);
		dto.setEmail(""+applicantDto.get("email"));
		dto.setMobnumber(""+applicantDto.get("mobile"));
		dto.setAppName(""+applicantDto.get("appName"));
		dto.setAppDate(""+applicantDto.get("siteInspectionDate"));
		dto.setDeptShortCode(deptShortCode);
		dto.setLangId(Integer.valueOf(""+applicantDto.get("langId")));
		dto.setOrgId(organisation.getOrgid());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(Long.valueOf(""+applicantDto.get("userId")));
		dto.setServiceId(sm.getSmServiceId());
		ismsAndEmailService.sendEmailSMS(deptShortCode, "SiteInspection.html",
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, dto, organisation, Integer.valueOf(""+applicantDto.get("langId")));
	}

	private static String getApplicantFullName(final TbCfcApplicationMstEntity applicantDto) {
		final StringBuilder builder = new StringBuilder();
		builder.append(applicantDto.getApmFname() == null ? "" : applicantDto.getApmFname());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApmMname() == null ? "" : applicantDto.getApmMname());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApmLname() == null ? "" : applicantDto.getApmLname());
		return builder.toString();
	}
	
	
	
}
