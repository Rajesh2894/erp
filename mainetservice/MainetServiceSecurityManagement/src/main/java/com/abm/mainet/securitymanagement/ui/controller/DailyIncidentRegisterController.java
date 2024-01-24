package com.abm.mainet.securitymanagement.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.DailyIncidentRegisterDTO;
import com.abm.mainet.securitymanagement.service.IDailyIncidentRegisterService;
import com.abm.mainet.securitymanagement.ui.model.DailyIncidentRegisterModel;

@Controller
@RequestMapping(value = "dailyIncidentRegister.html")
public class DailyIncidentRegisterController extends AbstractFormController<DailyIncidentRegisterModel> {

	@Autowired
	IDailyIncidentRegisterService iDailyIncidentRegisterService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	
	@Autowired
	private DepartmentService departmentService;
	
    @Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
    private IAttachDocsService attachDocsService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("dailyIncidentRegister.html");
		List<DailyIncidentRegisterDTO> incidents = new ArrayList<>(); 
				
		incidents = iDailyIncidentRegisterService.getAllRecords(UserSession.getCurrent().getOrganisation().getOrgid());
		
		//List<DailyIncidentRegisterDTO> incident = getIncident(incidents);
		model.addAttribute("incidents", incidents);
		return new ModelAndView("IncidentRegister", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "dailyIncident", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(final HttpServletRequest request) {
		this.sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		DailyIncidentRegisterModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		request.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid() , null));
         return new ModelAndView("DailyIncidentRegisterAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "viewDIR", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewIncidentReg(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long incidentId,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setDailyIncidentRegisterDTO(iDailyIncidentRegisterService.getIncidentById(incidentId));
		this.getModel().setSaveMode(mode);
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid() , null));
		final  List<AttachDocs> attachDocs = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SM" + MainetConstants.WINDOWS_SLASH+incidentId+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAttachDocsList(attachDocs);
		httpServletRequest.setAttribute("empIDs", this.getModel().getDailyIncidentRegisterDTO().getNameVisitingId());
		return new ModelAndView("DailyIncidentRegisterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "editDIR", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editIncidentReg(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long incidentId,
			final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setDailyIncidentRegisterDTO(iDailyIncidentRegisterService.getIncidentById(incidentId));
		this.getModel().setSaveMode(mode);
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid() , null));
		httpServletRequest.setAttribute("empIDs", this.getModel().getDailyIncidentRegisterDTO().getNameVisitingId());
		final  List<AttachDocs> attachDocs = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SM" + MainetConstants.WINDOWS_SLASH+incidentId+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("DailyIncidentRegisterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "searchIncident", method = RequestMethod.POST)
	public @ResponseBody List<DailyIncidentRegisterDTO> findIncidentReg(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<DailyIncidentRegisterDTO> daDtos = iDailyIncidentRegisterService.searchIncidentRegister(fromDate, toDate,
				orgid);
        
		return daDtos;

	}
		

}	  
	  
	  
	
	
	
	
	
	
	
	
	
	
	


