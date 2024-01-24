package com.abm.mainet.care.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.GrievanceComplaintStatusModel;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/GrievanceComplaintStatus.html")
public class GrievanceComplaintStatusController extends AbstractFormController<GrievanceComplaintStatusModel> {

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

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewCompalintSearchForm(HttpServletRequest request) {
		sessionCleanup(request);
		fileUploadService.sessionCleanUpForFileUpload();
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LinkedHashSet<LookUp> departments = new LinkedHashSet<>();
		List<ComplaintGrid> complaintGrid = iComplaintService.findAllComplaintRecords(orgId, MainetConstants.MENU.Y);
		Set<ComplaintGrid> depts = new LinkedHashSet<ComplaintGrid>(complaintGrid);
        
        if(CollectionUtils.isNotEmpty(depts)) {
            depts.stream().sorted(Comparator.comparing(ComplaintGrid::getDeptName)).forEach(list ->{
            LookUp detData = new LookUp();
            detData.setDescLangFirst(list.getDeptName());
            detData.setDescLangSecond(list.getDeptNameReg());
            detData.setLookUpId(list.getDeptId());
            departments.add(detData);
        });
		}
        departments.forEach(a->{
            logger.info("Sorted Dept: " + a.getDescLangFirst());
        });
		this.getModel().setDepartments(departments);
		getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.GRIEVANCECOMPLAINTSTATUS);
		return new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_SEARCH_STATUS_FORM,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_COMPLAINTTYPES, method = RequestMethod.POST)
	public ModelAndView getComplaintTypes(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
		orgId = (orgId == null) ? UserSession.getCurrent().getOrganisation().getOrgid() : orgId;
		ModelAndView modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_COMPLAINTTYPES,
				MainetConstants.CommonConstants.COMMAND, getModel());
		TbDepartment dept = tbDepartmentService.findById(deptId);
		if (dept != null) {
			if (dept.getDpDeptcode().equalsIgnoreCase("CFC")) {
				Set<LookUp> complaints = new HashSet<>();
				List<TbServicesMst> serviceList = tbServicesMstService.findByDeptId(deptId, orgId);
				if (!CollectionUtils.isEmpty(serviceList)) {
					serviceList.forEach(c -> {
						LookUp detData = new LookUp();
						detData.setDescLangFirst(c.getSmServiceName());
						detData.setDescLangSecond(c.getSmServiceNameMar());
						detData.setLookUpId(c.getSmServiceId());
						complaints.add(detData);
					});
				}
				this.getModel().setComplaintTypes(complaints);
			} else {
				Set<LookUp> complaints = new LinkedHashSet<>();
				Set<DepartmentComplaintTypeDTO> complaintTypes = careRequestService
						.getDepartmentComplaintTypeByDepartmentId(deptId, orgId);

				Set<DepartmentComplaintTypeDTO> depts = new LinkedHashSet<DepartmentComplaintTypeDTO>(complaintTypes);

				if (depts != null && !depts.isEmpty()) {
					depts.stream().sorted(Comparator.comparing(DepartmentComplaintTypeDTO::getComplaintDesc))
							.forEach(c -> {
								LookUp detData = new LookUp();
								detData.setDescLangFirst(c.getComplaintDesc());
								detData.setDescLangSecond(c.getComplaintDescReg());
								detData.setLookUpId(c.getCompId());
								complaints.add(detData);
							});
				}
				this.getModel().setComplaintTypes(complaints);
			}
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.ServiceCareCommon.SEARCH_GRIEVANCE, method = RequestMethod.POST)
	public ModelAndView viewCompalint(@RequestParam("fromDate") Date fromDate, @RequestParam("toDate") Date toDate,
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
		filter.setDepartmentComplaint(departmentComplaint);
		filter.setComplaintTypeId(complaintType);
		filter.setStatus(status);
		filter.setOrgId(orgId);
		//#142541
		if (fromDate!=null && Utility.compareDate(new Date(),fromDate)){
            getModel().addValidationError(getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.VALID_FROM_DATE));
			return modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_SEARCH_STATUS_FORM);
		}
		else if(toDate!=null && Utility.compareDate(new Date(),toDate) ){
			getModel().addValidationError(getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.VALID_TO_DATE));
			return modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_SEARCH_STATUS_FORM);
		}
		else
		{
			filter.setFromDate(fromDate);
			filter.setToDate(toDate);
		}
		
		careRequests = careRequestService.findComplaintDetails(filter);
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
			//#158820
			Long dpDeptid = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
			
			TbDepartment department = tbDepartmentService.findById(dpDeptid);
			
			if (null!=department && department.getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER)) {
				this.getModel().setLoggedInEmpDept("CFC");
			}
			
			model.setCareRequests(careRequestsList);			
			modelAndView = new ModelAndView("GrievanceSearchStatusValidn",MainetConstants.CommonConstants.COMMAND, getModel());
		} else {
			getModel().addValidationError(
					getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
			modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_SEARCH_STATUS_FORM);
		}
		this.getModel().getCareRequest().setFromDate(fromDate);
		this.getModel().getCareRequest().setToDate(toDate);
		this.getModel().getCareRequest().setStatus(status);
		this.getModel().getCareRequest().setSearchString(searchString);
		this.getModel().getCareRequest().setDepartmentComplaint(departmentComplaint);
		this.getModel().getCareRequest().setComplaintType(complaintType);
		return modelAndView;
	}

	@RequestMapping(params = "viewComplaintDetails", method = RequestMethod.POST)
	public ModelAndView viewCompalint(
			@RequestParam(MainetConstants.ServiceCareCommon.SEARCH_STRING) String searchString,
			HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		ModelAndView modelAndView = null;
		bindModel(request);
		GrievanceComplaintStatusModel model = this.getModel();
		List<CareRequestDTO> careRequests = null;
		ComplaintSearchDTO filter = new ComplaintSearchDTO();
		String kdmcEnv = MainetConstants.N_FLAG;
		String tsclEnv = MainetConstants.N_FLAG;
		filter.setMobileNumber(searchString);
		filter.setComplaintId(searchString);

		if (UserSession.getCurrent().getOrganisation().getDefaultStatus()
				.equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
			careRequests = careRequestService.findComplaint(filter);
		} else {
			filter.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			careRequests = careRequestService.findComplaint(filter);
		}
		CareRequestDTO careRequestDTO = careRequests.get(0);
		CareRequest careRequest = careRequestService.findByApplicationId(careRequestDTO.getApplicationId());
		model.getCareRequest().setApplicationId(careRequest.getApplicationId());
		ComplaintAcknowledgementModel complaintAcknowledgementModel = careRequestService
				.getComplaintAcknowledgementModel(careRequest, UserSession.getCurrent().getLanguageId());
		model.setComplaintAcknowledgementModel(complaintAcknowledgementModel);
		modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_DETAILS,
				MainetConstants.CommonConstants.COMMAND, getModel());
		if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid()))
			kdmcEnv = MainetConstants.Y_FLAG;
		if(CareUtility.isENVCodePresent(MainetConstants.ENV_TSCL, UserSession.getCurrent().getOrganisation().getOrgid()))
			tsclEnv = MainetConstants.Y_FLAG;
		
		modelAndView.addObject("kdmcEnv", kdmcEnv);
		modelAndView.addObject("tsclEnv", tsclEnv);
		return modelAndView;

	}

}
