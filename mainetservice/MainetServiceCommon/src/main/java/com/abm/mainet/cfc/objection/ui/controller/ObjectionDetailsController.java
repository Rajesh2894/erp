package com.abm.mainet.cfc.objection.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dao.HolidayMasterDao;
import com.abm.mainet.common.master.service.HolidayMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("/ObjectionDetails.html")
public class ObjectionDetailsController extends AbstractFormController<ObjectionDetailsModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectionDetailsController.class);

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private IObjectionDetailsService iObjectionDetailsService;

	@Resource
	private TbServicesMstService tbServicesMstService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	TbTaxMasService tbTaxMasService;

	@Autowired
	HolidayMasterService holidayMasterService;

	@Autowired
	private ICFCApplicationMasterService cfcService;
	@Autowired
	private HolidayMasterDao holiMasDao;

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("ObjectionDetails.html");
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final ObjectionDetailsModel model = getModel();
		setCommonFields(model);
		ModelAndView mv = null;

		// D#84026 set here objection/Appeal list
		List<LookUp> objOrAppealList = CommonMasterUtility.lookUpListByPrefix("OBJ",
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setObjOrAppealList(objOrAppealList);
		mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_FORM_URL, MainetConstants.FORM_NAME,
				getModel());
		return mv;
	}

	private void setCommonFields(ObjectionDetailsModel model) {
		final ObjectionDetailsDto dto = model.getObjectionDetailsDto();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		Set<LookUp> depts = iObjectionDetailsService.getDepartmentList(dto);
		model.setDepartments(depts);
	}

	/* Method to get Objection service by Department */
	@RequestMapping(params = "getObjectionServiceByDepartment", method = RequestMethod.POST)
	public @ResponseBody Set<LookUp> getObjectionServiceByDept(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @RequestParam("objectionDeptId") Long objectionDeptId) {
		final ObjectionDetailsDto dto = this.getModel().getObjectionDetailsDto();
		dto.setObjectionDeptId(objectionDeptId);
		dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		Set<LookUp> serviceList = iObjectionDetailsService.getservicesByDeptIdWithLookup(dto);
		this.getModel().setServiceList(serviceList);
		return serviceList;
	}

	@RequestMapping(params = "addObjection", method = RequestMethod.POST)
	public ModelAndView addObjectionEntryForm(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final ObjectionDetailsModel model = (ObjectionDetailsModel) httpServletRequest
				.getAttribute(MainetConstants.FORM_NAME);

		return new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_ADD_URL, MainetConstants.FORM_NAME, model);

	}
	/* end */


	/* Method to fetch Objection List based on search criteria */
	@RequestMapping(params = "getObjectionList", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getObjectionList(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @RequestParam("objectionDeptId") Long objectionDeptId,
			@RequestParam("objectionType") Long serviceId, @RequestParam("objectionNumber") String objectionNumber,
			@RequestParam("apmApplicationId") Long apmApplicationId,
			@RequestParam("referenceNumber") String referenceNumber,
			@RequestParam("additionalReferenceNumber") String additionalReferenceNumber) {
		this.getModel().bind(httpServletRequest);
		ObjectionDetailsModel objectionDetailsModel = this.getModel();
		ObjectionDetailsDto objectionDetailsDto = objectionDetailsModel.getObjectionDetailsDto();
		objectionDetailsDto.setOrgId(objectionDetailsModel.getOrgId());
		objectionDetailsDto.setObjectionDeptId(objectionDeptId);
		objectionDetailsDto.setServiceId(serviceId);
		objectionDetailsDto.setObjectionNumber(objectionNumber);
		objectionDetailsDto.setApmApplicationId(apmApplicationId);
		objectionDetailsDto.setObjectionReferenceNumber(referenceNumber);
		objectionDetailsDto.setObjectionAddReferenceNumber(additionalReferenceNumber);

		List<ObjectionDetailsDto> listObjectionDetails = iObjectionDetailsService.getObjectionList(objectionDetailsDto);
		if (listObjectionDetails != null && !listObjectionDetails.isEmpty()) {
			objectionDetailsModel.setListObjectionDetails(listObjectionDetails);
			return new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_SEARCH_URL, MainetConstants.FORM_NAME,
					getModel());
		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
	}
	/* end */

	/* Method to Save Objection Filed */
	@RequestMapping(params = "saveObjectionDetails", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveObjectiondetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		ObjectionDetailsModel model = this.getModel();
		ServiceMaster service = serviceMaster.getServiceMaster(model.getObjectionDetailsDto().getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		model.getObjectionDetailsDto().setDeptCode(service.getTbDepartment().getDpDeptcode());

		if (model.getObjectionDetailsDto().getDeptCode().contentEquals("RTI")) {
			ObjectionDetailsDto dto = null;
			try {
				dto = iObjectionDetailsService.fetchRtiAppDetailByRefNo(model.getObjectionDetailsDto());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (dto != null && dto.getApmApplicationId() == null) {
				model.addValidationError("Please enter valid Reference No");
				mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL,
						MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;

			}
			/*
			 * if (dto != null) { if (dto.getTitle() <= 0) {
			 * model.addValidationError("Dispatch not complete"); mv = new
			 * ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL,
			 * MainetConstants.FORM_NAME, getModel());
			 * mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
			 * getModel().getBindingResult()); return mv; } }
			 */
			if (dto != null && dto.getApmApplicationId() != null) {
				ObjectionDetailsDto objDto = iObjectionDetailsService.getObjectionDetailByAppId(
						UserSession.getCurrent().getOrganisation().getOrgid(),
						model.getObjectionDetailsDto().getServiceId(), dto.getApmApplicationId());
				if (objDto != null) {
					model.addValidationError("First Appeal already done");
					mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL,
							MainetConstants.FORM_NAME, getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
			}
		}
		// 97687 - Objection on mutation service after LOI payment
		if (service != null && StringUtils.isNotBlank(service.getSmShortdesc())
				&& service.getSmShortdesc().equalsIgnoreCase(MainetConstants.Property.MOS)) {
			RequestDTO reqDto = new RequestDTO();
			setRequestApplicantDetails(reqDto, model.getObjectionDetailsDto());
			Long applicationNo = applicationService.createApplication(reqDto);
			if (applicationNo != null)
				model.getObjectionDetailsDto().setApmApplicationId(applicationNo);
		}

		// Defect #34042 -->RTI first Appeal Issue
		if (model.getObjectionDetailsDto().getDeptCode().contentEquals("RTI")
				&& (service.getSmShortdesc().contentEquals("RAF") || (service.getSmShortdesc().contentEquals("RFA")))) {
			getDateByRefNo(model.getObjectionDetailsDto());
		}
		if (StringUtils.isNotBlank(model.getObjectionDetailsDto().getValidDate())
				&& model.getObjectionDetailsDto().getDeptCode().contentEquals("RTI")) {
			// check condition in case dispatch already done Defect#34042
			if (model.getObjectionDetailsDto().getDeliveryDate() != null
					&& (model.getObjectionDetailsDto().getErrorList() != null
							&& !model.getObjectionDetailsDto().getErrorList().isEmpty())) {
				model.addValidationError(model.getObjectionDetailsDto().getErrorList().get(0));
			} else {
				model.addValidationError(
						("You cannot file RTI First appeal till: ") + model.getObjectionDetailsDto().getValidDate());
			}
			// model.addValidationError(("rti.first.appeal") +
			// model.getObjectionDetailsDto().getValidDate());
			mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
					getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
					getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}

	}
	/* end */

	/* Method for save RTS related objection */
	@RequestMapping(params = "saveRTSObjectionDetails", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveRTSObjectionDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
				getModel());
		ObjectionDetailsModel model = this.getModel();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp lookupFRTSObj = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.RightToService.FIRST_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
				organisation);
		// 1st check reference no is valid or not for 1st/2nd Appeal
		if (model.getObjectionDetailsDto().getObjectionOn() == lookupFRTSObj.getLookUpId()) {
			// 1st appeal need numeric application id
			if (!StringUtils.isNumeric(model.getObjectionDetailsDto().getObjectionReferenceNumber())) {
				// throw invalid application id for 1st appeal
				/*
				 * model.addValidationError(
				 * model.getObjectionDetailsDto().getObjectionReferenceNumber() +
				 * " is invalid for first appeal");
				 */
				model.addValidationError(getApplicationSession().getMessage("rts.validn.firstAppealNoInvalid"));

				return throwValidationMsg(mv);
			}
		} else {
			if (StringUtils.isNumeric(model.getObjectionDetailsDto().getObjectionReferenceNumber())) {
				// 2nd appeal need alphanumeric
				model.addValidationError(getApplicationSession().getMessage("rts.validn.secondAppealNoInvalid"));
				return throwValidationMsg(mv);
			}
		}

		ServiceMaster service = serviceMaster.getServiceMaster(model.getObjectionDetailsDto().getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.getObjectionDetailsDto().setDeptCode(service.getTbDepartment().getDpDeptcode());
		// validation for 1st/2nd Appeal valid or not and already reference no exist
		// than throw MSG

		ServiceMaster frtsService = tbServicesMstService.findShortCodeByOrgId(
				MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (model.getObjectionDetailsDto().getDeptCode().contentEquals("RTS")
				&& model.getObjectionDetailsDto().getObjectionOn() == lookupFRTSObj.getLookUpId()) {
			// first appeal process
			// reference no <-> application id
			// 1st check application id is valid or not
			TbCfcApplicationMstEntity cfcApplication = cfcService.getCFCApplicationByApplicationId(
					Long.valueOf(model.getObjectionDetailsDto().getObjectionReferenceNumber()),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (cfcApplication == null) {
				// throw validation this application id is already FRTS punch
				model.addValidationError("this application not valid for first appeal entry");
				// return throwValidationMsg(mv);//TODO: remove told by SAMADHAN sir
			}

			ObjectionDetailsDto objDto = iObjectionDetailsService.getObjectionDetailByIds(
					UserSession.getCurrent().getOrganisation().getOrgid(), frtsService.getSmServiceId(),
					Long.valueOf(model.getObjectionDetailsDto().getObjectionReferenceNumber()),
					lookupFRTSObj.getLookUpId());
			if (objDto.getObjectionId() != null) {
				// throw validation this application id is already FRTS punch
				model.addValidationError(getApplicationSession().getMessage("rts.validn.applnAlreadyReg"));
				return throwValidationMsg(mv);
			}
			// Rule for 1st Appeal
			if (cfcApplication != null) {
				if (!(dateSatisfy(cfcApplication.getApmApplicationDate(), service.getSmServiceDuration(),
						Integer.valueOf(lookupFRTSObj.getOtherField())))) {
					model.addValidationError(getApplicationSession().getMessage("rts.validn.applnInvalidDate"));
					return throwValidationMsg(mv);
				}
			}
			model.getObjectionDetailsDto()
					.setApmApplicationId(Long.valueOf(model.getObjectionDetailsDto().getObjectionReferenceNumber()));
			model.getObjectionDetailsDto().setServiceId(frtsService.getSmServiceId());

		} else {
			LookUp lookupSRTSObj = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.RightToService.SECOND_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
					organisation);
			// reference no <-> objection no
			ObjectionDetailsDto objection = iObjectionDetailsService.getObjectionDetailByObjNo(
					UserSession.getCurrent().getOrganisation().getOrgid(),
					model.getObjectionDetailsDto().getObjectionReferenceNumber());

			// check 1st appeal punch or not for this application
			if (objection == null) {
				// invalid for second appeal should be in objection table as 1st appeal
				// throw validation this application is not in objection table
				model.addValidationError(model.getObjectionDetailsDto().getObjectionReferenceNumber()
						+ getApplicationSession().getMessage(" rts.validn.invalidSecond"));
				return throwValidationMsg(mv);
			}
			if (lookupSRTSObj.getLookUpId() == objection.getObjectionOn()) {
				// invalid for second appeal
				// already punch
				// throw validation this application id is already SRTS punch
				model.addValidationError(model.getObjectionDetailsDto().getObjectionReferenceNumber()
						+ getApplicationSession().getMessage("rts.validn.applnAlreadyRegSecond"));
				return throwValidationMsg(mv);
			}

			// Rule for Second Appeal
			int lookupDays = Integer.valueOf(lookupSRTSObj.getOtherField());
			if (objection.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)
					&& (objection.getUpdatedDate() != null && !(dateSatisfy(objection.getUpdatedDate(), lookupDays)))) {
				// throw MSG not valid
				model.addValidationError(getApplicationSession().getMessage("rts.validn.applnInvalidDateSecond"));
				return throwValidationMsg(mv);
			} else if (objection.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.PENDING)
					&& !(dateSatisfy(objection.getCreatedDate(), frtsService.getSmServiceDuration(), lookupDays))) {
				model.addValidationError(getApplicationSession().getMessage("rts.validn.applnInvalidDateSecond"));
				return throwValidationMsg(mv);
			} else if (objection.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
					&& (objection.getUpdatedDate() != null && !(dateSatisfy(objection.getUpdatedDate(), lookupDays)))) {
				model.addValidationError(getApplicationSession().getMessage("rts.validn.applnInvalidDateSecond"));
				return throwValidationMsg(mv);
			}
			ServiceMaster smrtsService = tbServicesMstService.findShortCodeByOrgId(
					MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL,
					UserSession.getCurrent().getOrganisation().getOrgid());
			// D#76731
			model.getObjectionDetailsDto().setApmApplicationId(objection.getApmApplicationId());
			model.getObjectionDetailsDto().setServiceId(smrtsService.getSmServiceId());
		}
		if (model.saveRTSObjectionForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
					getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}

	}

	private boolean dateSatisfy(Date applicationDate, Long smServDuration, int lookupDays) {
		// make from Date and to Date by using no of days
		if (smServDuration != null) {
			Date fromDate = Utility.getAddedDateBy2(applicationDate, smServDuration.intValue());
			Date toDate = Utility.getAddedDateBy2(fromDate, lookupDays);
			return Utility.compareDate(fromDate, new Date()) && Utility.compareDate(new Date(), toDate);
		} else {
			return false;
		}
	}

	private boolean dateSatisfy(Date compareDate, int days) {
		// within date range satisfy
		return Utility.compareDate(new Date(), Utility.getAddedDateBy2(compareDate, days));
	}

	// method for throw validation msg
	private ModelAndView throwValidationMsg(ModelAndView mv) {
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	/* Method to get Objection charges */
	@RequestMapping(params = "saveObjectionOrGetCharges", method = RequestMethod.POST)
	public ModelAndView saveObjectionOrGetCharges(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Organisation org) {
		getModel().bind(httpServletRequest);
		ObjectionDetailsModel model = this.getModel();
		ObjectionDetailsDto objectionDetailsDto = model.getObjectionDetailsDto();
		objectionDetailsDto.setOrgId(model.getOrgId());
		CommonChallanDTO commonChallanDTO = iObjectionDetailsService.getCharges(objectionDetailsDto);
		if (commonChallanDTO != null && commonChallanDTO.getAmountToShow() != null) {
			objectionDetailsDto.setCharges(commonChallanDTO.getAmountToShow());
			model.setOfflineDTO(commonChallanDTO);
			if (commonChallanDTO.getAmountToShow() != 0.0) {
				model.setPaymentCharge(MainetConstants.FlagY);
			}
		}
		
		return new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
				getModel());

	}
	/* End */

	/* For Getting Location by Department Name */
	@RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
	@ResponseBody
	public Set<LookUp> getLocationByDepartment(@RequestParam(value = "objectionDeptId", required = true) Long deptId) {
		final ObjectionDetailsDto dto = this.getModel().getObjectionDetailsDto();
		dto.setObjectionDeptId(deptId);
		Set<LookUp> locList = iObjectionDetailsService.getLocationByDepartmentInLookup(dto);
		this.getModel().setLocations(locList);
		return locList;
	}
	/* end */

	/**
	 * used to search records by filter criteria by objectionDeptId with
	 * organization id
	 * 
	 * @param request
	 * @param objectionDeptId
	 * @param serviceId
	 * 
	 * @return List<ObjectionDetailsDto> if record found else return empty dto
	 */

	@RequestMapping(params = "searchDetails", method = RequestMethod.POST)
	public @ResponseBody List<ObjectionDetailsDto> getObjectionDetail(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "objectionDeptId", required = false) Long objectionDeptId,
			@RequestParam(value = "serviceId", required = false) Long serviceId,
			@RequestParam(value = "refNo", required = false) String refNo,
			@RequestParam(value = "objectionOn", required = false) Long objectionOn) {
		return iObjectionDetailsService.searchObjectionDetails(UserSession.getCurrent().getOrganisation().getOrgid(),
				objectionDeptId, serviceId, refNo, objectionOn);

	}

	/**
	 * used to view records
	 * 
	 * @param request
	 * @param ObjId
	 * @param type(View Mode)
	 * 
	 * @return List<ObjectionDetailsDto> if record found else return empty dto
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
	public @ResponseBody ModelAndView objectionForm(
			@RequestParam(value = "objectionId", required = false) Long objectionId,
			@RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type) {
		ObjectionDetailsModel model = this.getModel();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ObjectionDetailsDto objDto = iObjectionDetailsService
				.getObjectionDetailByObjId(UserSession.getCurrent().getOrganisation().getOrgid(), objectionId);
		this.getModel().setObjectionDetailsDto(objDto);
		ServiceMaster service = serviceMaster.getServiceMaster(objDto.getServiceId(), orgId);
		objDto.setDeptName(service.getTbDepartment().getDpDeptdesc());
		objDto.setServiceName(service.getSmServiceName());
		this.getModel().setDocumentList(
				iChecklistVerificationService.getDocumentUploadedByRefNo(objDto.getObjectionNumber(), orgId));
		this.getModel().setObjectionDetailsDto(objDto);

		// objDto.setLocName(iLocationMasService.getLocationNameById(objDto.getCodIdOperLevel1(),
		// orgId));

		objDto.setLocName(iLocationMasService.getLocationNameById(objDto.getLocId(), orgId));

		return new ModelAndView("objectionViewDetails", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
	public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ObjectionDetailsModel model = this.getModel();
		return new ModelAndView("BackToObjectionSummary", MainetConstants.FORM_NAME, model);
	}

	// Defect #34042 -->to fetch application details by application refNo
	@RequestMapping(params = "getApplicationDateByRefNo", method = RequestMethod.POST)
	public void getDateByRefNo(ObjectionDetailsDto objectionDetailsDto) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		objectionDetailsDto.setOrgId(orgId);
		iObjectionDetailsService.fetchRtiAppDetailByRefNo(objectionDetailsDto);

		// added for fetching first appeal reminder days dynamicaally
		final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(
				PrefixConstants.LQP_PREFIX.ALERT_REMINDER_DAYS, MainetConstants.RTISERVICE.FIRST_APPEAL_REMINDER,
				langId, org);
		Long getNoOfDays = Long.parseLong(lookUp.getLookUpCode()); // add 30 days

		// In case of getting information but not satisfied in this
		Date applicableDate1 = new Date();
		if (objectionDetailsDto.getDeliveryDate() != null) {
			applicableDate1 = new Date(
					objectionDetailsDto.getDeliveryDate().getTime() + (getNoOfDays * 1000 * 60 * 60 * 24));
			List<String> l = new ArrayList<String>();

			if (!Utility.compareDate(new Date(), applicableDate1)) {
				objectionDetailsDto
						.setValidDate((new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(applicableDate1)));
				l.add(ApplicationSession.getInstance().getMessage("obj.first.appeal.alert"));
				objectionDetailsDto.setErrorList(l);

				return;
			}
		} else {
			// for getting date after 30 days from date of application submission
			Date applicableDate = new Date(
					objectionDetailsDto.getApplicationDate().getTime() + (getNoOfDays * 1000 * 60 * 60 * 24));
			Date applicableDate2 = new Date(applicableDate.getTime() - (1 * 1000 * 60 * 60 * 24));
			Date applicableDate3 = new Date(applicableDate.getTime() + (1 * 1000 * 60 * 60 * 24));
			int holiday = 0;
			// for checking last day is holiday or not
			holiday = holiMasDao.getHolidayDetailsList(applicableDate2, applicableDate3, orgId).size();
			if (holiday == 1) {
				applicableDate = new Date(applicableDate.getTime() + (1000 * 60 * 60 * 24));
			}

			if (Utility.compareDate(new Date(), applicableDate)) {
				objectionDetailsDto
						.setValidDate((new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(applicableDate)));
			}
		}

	}

	@RequestMapping(params = "getDispatchDetails", method = RequestMethod.POST)
	@ResponseBody
	public ObjectionDetailsDto getApplicationDetails(
			@RequestParam(value = "objectionReferenceNumber", required = true) String objectionReferenceNumber,
			@RequestParam(value = "deptCode") String deptCode) throws Exception {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ObjectionDetailsDto dto = new ObjectionDetailsDto();
		dto.setObjectionReferenceNumber(objectionReferenceNumber);
		dto.setOrgId(orgId);
		Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(deptCode);
		dto.setObjectionDeptId(deptId);

		return iObjectionDetailsService.fetchRtiAppDetailByRefNo(dto);

	}

	// Defect #112673
	@RequestMapping(params = "saveLicenseObjectionDetails", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveLicenseObjectionDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
				getModel());
		ObjectionDetailsModel model = this.getModel();
		// check licno valid or not
		if (StringUtils.isNumeric(model.getObjectionDetailsDto().getObjectionReferenceNumber())) {
			model.addValidationError(getApplicationSession().getMessage("validate.license.no"));
			return throwValidationMsg(mv);
		}
		if (model.saveLicenseObjectionForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
					getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}

	}

	private void setRequestApplicantDetails(RequestDTO reqDto, ObjectionDetailsDto objDto) {
		final UserSession session = UserSession.getCurrent();
		reqDto.setfName(objDto.getfName());
		reqDto.setDeptId(objDto.getObjectionDeptId());
		reqDto.setOrgId(session.getOrganisation().getOrgid());
		reqDto.setServiceId(objDto.getServiceId());
		reqDto.setLangId(Long.valueOf(session.getLanguageId()));
		reqDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		reqDto.setReferenceId(objDto.getObjectionNumber());
	}

	@ResponseBody
	@RequestMapping(params = "getFlatList", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("refNo") String refNo,
			@RequestParam("serviceId") Long serviceId, @RequestParam("objectionDeptId") Long objectionDeptId,
			HttpServletRequest request) {
		this.getModel().bind(request);
		ObjectionDetailsModel model = this.getModel();
		List<String> flatNoList = new ArrayList<>();
		flatNoList = iObjectionDetailsService.getFlatListByRefNo(refNo, serviceId, objectionDeptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setFlatNoList(flatNoList);
		return flatNoList;
	}

	@RequestMapping(method = RequestMethod.POST, params = "printObjAcknowledgement")
	public ModelAndView printObjAcknowledgement(HttpServletRequest httpServletRequest) {
		LOGGER.info("Start method printObjAcknowledgement() ");
		try {
			getModel().bind(httpServletRequest);
			String objectionOn = CommonMasterUtility
					.getNonHierarchicalLookUpObject(getModel().getObjectionDetailsDto().getObjectionOn(),
							UserSession.getCurrent().getOrganisation())
					.getDescLangFirst();
			httpServletRequest.setAttribute("objectionOn", objectionOn);
			return new ModelAndView("PrintObjAcknowledgement", MainetConstants.CommonConstants.COMMAND, getModel());
		} catch (Exception exception) {
			LOGGER.error("Exception found in printObjAcknowledgement  method : ", exception);
		}
		return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
	}

	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("FirstAppealChargesDetail", MainetConstants.CommonConstants.COMMAND, getModel());
	}

}
