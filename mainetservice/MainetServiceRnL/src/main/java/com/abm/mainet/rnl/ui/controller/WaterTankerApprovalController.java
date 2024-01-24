package com.abm.mainet.rnl.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;

import com.abm.mainet.common.integration.dms.service.IFileUploadService;

import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;

import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.ui.model.EstateBookingModel;


@Controller
@RequestMapping("/WaterTankerApproval.html")
public class WaterTankerApprovalController extends AbstractFormController<EstateBookingModel> {
	private final Logger LOGGER = Logger.getLogger(WaterTankerBookingController.class);

	@Autowired
	private IEstateBookingService iEstateBookingService;

	@Autowired
	private IEstatePropertyService iEstatePropertyService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private DepartmentJpaRepository deptRepo;
	
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
	public ModelAndView executeAgencyRegistration(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
			@RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
			final HttpServletRequest request) {
		try {
			getData(applicationId, taskId, actualTaskId, request);

		} catch (Exception exception) {
			logger.error("Error While Rendoring the form", exception);
			return defaultExceptionFormView();
		}
		ServiceMaster service = serviceMaster.getServiceMaster(Long.valueOf(taskId),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (service != null) {
			LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
					PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM, UserSession.getCurrent().getOrganisation());
			getModel().setRemarkList(
					tbApprejMasService.findByRemarkType(Long.valueOf(taskId), artTypeLookUp.getLookUpId()));
		}
		
		Long deptId = deptRepo.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);

		List<Object[]> empList = iEmployeeService
				.findActiveEmployeeByDeptId(deptId,UserSession.getCurrent().getOrganisation().getOrgid());

		for (Object[] objects : empList) {
			StringBuilder name = new StringBuilder();
			name.append(objects[1].toString());
			if (objects[2] != null) {
				name.append(MainetConstants.WHITE_SPACE);
				name.append(objects[2].toString());
			}
			if (objects[3] != null) {
				name.append(MainetConstants.WHITE_SPACE);
				name.append(objects[3].toString());
			}
			objects[1] = name;
		}

		this.getModel().setDriverName(empList);
		getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		if(getModel().getLevelcheck() == 2) {
			this.getModel().getDriverData();
		}
		return new ModelAndView(MainetConstants.WaterTankerBooking.WATER_TANKER_APPROVAL,
				MainetConstants.CommonConstants.COMMAND, getModel());

	}

	public void getData(Long applicationId, String taskId, Long actualTaskId, HttpServletRequest httpServletRequest)
			throws Exception {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);

		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceDto = serviceMaster.getServiceByShortName(MainetConstants.RNL_WATER_TANKER_BOOKING, orgId);
		if (serviceDto != null) {
			this.getModel().setServiceMaster(serviceDto);
			this.getModel().setServiceId(serviceDto.getSmServiceId());
			this.getModel().setServiceName(serviceDto.getSmServiceName());
		}
		Long propId = iEstateBookingService.findPropIdByAppId(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().getBookingReqDTO().setEstatePropResponseDTO(estatePropResponseDTO);
		this.getModel().setEventDTOsList(iEstatePropertyService.getPropEventListBypropId(propId));
		this.getModel().setApmApplicationId(applicationId);
		this.getModel().populateApplicationData(applicationId);
		this.getModel().setPayableFlag(MainetConstants.FlagN);
		this.getModel().setPropId(propId);
		this.getModel().getBookingReqDTO().getEstatePropResponseDTO()
				.setShiftDTOsList(estatePropResponseDTO.getShiftDTOsList());
	}

	@Override
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = RequestMethod.POST)
	public ModelAndView saveform(HttpServletRequest request) {
		JsonViewObject responseObj = null;
		this.getModel().bind(request);
		EstateBookingModel model = this.getModel();
		BookingReqDTO bookingReqDTO = this.getModel().getBookingReqDTO();
		model.getWorkflowActionDto().setDecision("APPROVED");
		RequestDTO requestDto = new RequestDTO();
		requestDto.setReferenceId(String.valueOf(model.getApmApplicationId()));
		requestDto.setDepartmentName(MainetConstants.RnLCommon.RL_SHORT_CODE);
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setServiceId(model.getServiceId());
		requestDto.setDeptId(model.getServiceMaster().getTbDepartment().getDpDeptid());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.setApplicationId(model.getApmApplicationId());
		boolean lastApproval = workFlowTypeService
				.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
		if (lastApproval
				&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)
				&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
						MainetConstants.FlagN)) {
			model.getWorkflowActionDto().setComments(bookingReqDTO.getTankerBookingDetailsDTO().getReturnRemark());
			if (model.closeWorkFlowTask()) {
				final UserSession session = UserSession.getCurrent();
				bookingReqDTO.getTankerBookingDetailsDTO().setUpdatedDate(new Date());
	            bookingReqDTO.getTankerBookingDetailsDTO().setUpdatedBy(session.getEmployee().getEmpId());
				iEstateBookingService.saveReturnDetail(bookingReqDTO);
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("Return Details Added SuccessFully"));
			} else {
				responseObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("Return Details Failed"));
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.APPROVED)) {
			model.getWorkflowActionDto().setComments(bookingReqDTO.getTankerBookingDetailsDTO().getRemark());
			boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
			final UserSession session = UserSession.getCurrent();
			bookingReqDTO.getTankerBookingDetailsDTO().setOrgId(session.getOrganisation().getOrgid());
			bookingReqDTO.getTankerBookingDetailsDTO().setLgIpMacUp(model.getClientIpAddress());
            bookingReqDTO.getTankerBookingDetailsDTO().setCreatedDate(new Date());
            bookingReqDTO.getTankerBookingDetailsDTO().setCreatedBy(session.getEmployee().getEmpId());
            bookingReqDTO.getTankerBookingDetailsDTO().setLgIpMac(model.getClientIpAddress());
            bookingReqDTO.getTankerBookingDetailsDTO().setLangId((long) session.getLanguageId());
			bookingReqDTO = iEstateBookingService.saveDriverDetail(bookingReqDTO);
			if (approvalFlag) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("Driver Details Added SuccessFully"));
			} else {
				responseObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("Driver Details Failed"));
			}
		}
		return jsonResult(responseObj);
	}

}
