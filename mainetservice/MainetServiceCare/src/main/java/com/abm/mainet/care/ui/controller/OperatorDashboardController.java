package com.abm.mainet.care.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.GrievanceComplaintStatusModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping({"/OperatorDashboardView.html"})
public class OperatorDashboardController extends AbstractFormController<GrievanceComplaintStatusModel> {

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	private ICareRequestService careRequestService;

	@Autowired
	private IComplaintTypeService iComplaintService;

	@Resource
	private TbDepartmentService tbDepartmentService;
	@Autowired
	TbServicesMstService tbServicesMstService;

	@RequestMapping(method = { RequestMethod.POST,RequestMethod.GET })
	public ModelAndView viewCompalintSearchFormForCareOperRole(HttpServletRequest request) {
		sessionCleanup(request);
		fileUploadService.sessionCleanUpForFileUpload();
		bindModel(request);
		ComplaintSearchDTO filter = new ComplaintSearchDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Set<LookUp> departments = new HashSet<>();
		List<CareRequestDTO> careRequests = null;
		filter.setOrgId(orgId);
		//filter.setComplaintId("06W5202100022");
		careRequests = careRequestService.findComplaintDetailsForCareOperatorRole(filter);
		this.getModel().setCareRequests(careRequests);
		List<ComplaintGrid> complaintGrid = iComplaintService.findAllComplaintRecords(orgId, MainetConstants.MENU.Y);
		if(CollectionUtils.isNotEmpty(complaintGrid)) {
		complaintGrid.forEach(list -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(list.getDeptName());
			detData.setDescLangSecond(list.getDeptNameReg());
			detData.setLookUpId(list.getDeptId());
			departments.add(detData);
		});
		}
		this.getModel().setDepartments(departments);
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.GRIEVANCECOMPLAINTSTATUS);
		return new ModelAndView(MainetConstants.ServiceCareCommon.OPERATOR_DASHBOARD_FORM,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.ServiceCareCommon.VIEW_DETAIL, method = {RequestMethod.POST})
	public ModelAndView viewCompalintDetailForOperatorRole(@RequestParam("fromDate") Date fromDate, @RequestParam("toDate") Date toDate,
			@RequestParam("departmentComplaint") Long departmentComplaint,
			@RequestParam("complaintType") Long complaintType, @RequestParam("searchString") String searchString,
			@RequestParam("status") String status, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		bindModel(request);
		ModelAndView modelAndView = null;
		GrievanceComplaintStatusModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<CareRequestDTO> careRequests = null;
		ComplaintSearchDTO filter = new ComplaintSearchDTO();
		filter.setMobileNumber(searchString);
		filter.setComplaintId(searchString);
		filter.setFromDate(fromDate);
		filter.setToDate(toDate);
		filter.setDepartmentComplaint(departmentComplaint);
		filter.setComplaintTypeId(complaintType);
		filter.setStatus(status);
		filter.setOrgId(orgId);
		careRequests = careRequestService.findComplaintDetailsForCareOperatorRole(filter);
		List<CareRequestDTO> careRequestsList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(careRequests)) {
			careRequests.forEach(dto -> {
				CareRequestDTO careRequestDTO = new CareRequestDTO();
				careRequestDTO.setComplaintId(dto.getComplaintId());
				careRequestDTO.setApplicationId(dto.getApplicationId());
				careRequestDTO.setDateOfRequest(dto.getDateOfRequest());
				careRequestDTO.setDepartmentComplaintDesc(dto.getDepartmentComplaintDesc());
				careRequestDTO.setDepartmentComplaintDescReg(dto.getDepartmentComplaintDescReg());
				careRequestDTO.setComplaintTypeDesc(dto.getComplaintTypeDesc());
				careRequestDTO.setComplaintTypeDescReg(dto.getComplaintTypeDescReg());
				careRequestDTO.setApmName(dto.getApmName());
				careRequestDTO.setStatus(dto.getStatus());
				careRequestsList.add(careRequestDTO);
			});
			model.setCareRequests(careRequestsList);			
			modelAndView = new ModelAndView("OPERATOR_DASHBOARD_VAL_FORM",MainetConstants.CommonConstants.COMMAND, getModel());
		} else {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.OPERATOR_DASHBOARD_FORM);
		}
		this.getModel().getCareRequest().setFromDate(fromDate);
		this.getModel().getCareRequest().setToDate(toDate);
		this.getModel().getCareRequest().setStatus(status);
		this.getModel().getCareRequest().setSearchString(searchString);
		this.getModel().getCareRequest().setDepartmentComplaint(departmentComplaint);
		this.getModel().getCareRequest().setComplaintType(complaintType);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.ServiceCareCommon.SEARCH_DETAIL, method = RequestMethod.POST)
	public ModelAndView viewCompalintListForOperatorRole(@RequestParam("fromDate") Date fromDate, @RequestParam("toDate") Date toDate,
		 @RequestParam("searchString") String searchString,
			@RequestParam("status") String status, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		bindModel(request);
		ModelAndView modelAndView = null;
		GrievanceComplaintStatusModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<CareRequestDTO> careRequests = null;
		ComplaintSearchDTO filter = new ComplaintSearchDTO();
		filter.setMobileNumber(searchString);
		filter.setComplaintId(searchString);
		filter.setFromDate(fromDate);
		filter.setToDate(toDate);
		filter.setStatus(status);
		filter.setOrgId(orgId);
		
		careRequests = careRequestService.findComplaintDetailsForCareOperatorRole(filter);
		if (CollectionUtils.isNotEmpty(careRequests)) {
			model.setCareRequests(careRequests);			
			modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.OPERATOR_DASHBOARD_VAL_FORM,MainetConstants.CommonConstants.COMMAND, getModel());
		} else {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.OPERATOR_DASHBOARD_FORM);
		}
		this.getModel().getCareRequest().setFromDate(fromDate);
		this.getModel().getCareRequest().setToDate(toDate);
		this.getModel().getCareRequest().setStatus(status);
		this.getModel().getCareRequest().setSearchString(searchString);
		return modelAndView;
	}

}
