
package com.abm.mainet.disastermanagement.ui.controller;

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
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.constant.DisasterConstant;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IComplainRegisterService;
import com.abm.mainet.disastermanagement.ui.model.DepartmentalScrutinyModel;

@Controller
@RequestMapping("/DepartmentalScrutiny.html")
public class DepartmentalScrutinyController extends AbstractFormController<DepartmentalScrutinyModel> {
	
	@Autowired
	private IComplainRegisterService iComplainRegisterService;
	
	@Autowired
    private IAttachDocsService iAttachDocsService;
	
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
    private DesignationService designationService;
	
	 @Autowired
	 private ILocationMasService iLocationMasService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("DepartmentalScrutiny.html");
		
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid(); 
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String formStatusStr = DisasterConstant.PENDING;
		
		List<ComplainRegisterDTO> complainReg = iComplainRegisterService.getcomplaintData(orgId, userId, deptId, formStatusStr);
		//List<ComplainRegisterDTO> complainReg = iComplainRegisterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("complainReg", complainReg);
		return new ModelAndView("DepartmentalScrutiny", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "searchComplain", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<ComplainRegisterDTO> searchComplain(@RequestParam("complainNo") final String complainNo, 
		   @RequestParam("frmDate") final Date frmDate, @RequestParam("toDate") final Date toDate, Model model) { 
	
		DepartmentalScrutinyModel modelComplainReg = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String formStatusStr = DisasterConstant.PENDING;
		List<ComplainRegisterDTO> complainReg= iComplainRegisterService.findByCompNoFrmDtToDt(complainNo, frmDate, toDate, orgId, formStatusStr, userId, deptId);
		model.addAttribute("complainReg", complainReg);
		modelComplainReg.setComplainRegisterDTOList(complainReg);
		return complainReg;
	}

	@RequestMapping(params = "editComplainReg", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editComplainReg(@RequestParam String mode, @RequestParam(MainetConstants.Common_Constant.ID) String complainId, final HttpServletRequest httpServletRequest ) {
		
		DepartmentalScrutinyModel modelComplainReg = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		modelComplainReg.setSaveMode(mode);
		ComplainRegisterDTO complainRegisterDTO = iComplainRegisterService.getComplainRegData(complainId, orgId,DisasterConstant.OPEN);
		httpServletRequest.setAttribute("locations", loadLocation());
		httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid() , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		
		//fetch uploaded document start
		//DisasterConstant.COMPLAIN_REG + MainetConstants.WINDOWS_SLASH + 
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setIdfId(DisasterConstant.COMPLAIN_REG + MainetConstants.WINDOWS_SLASH + complainRegisterDTO.getComplainNo().toString());
		List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), requestDTO.getIdfId());
		
		//fetch uploaded document end
		this.getModel().setAttachDocs(attachDocs);
		modelComplainReg.setComplainRegisterDTO(complainRegisterDTO);
		
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		httpServletRequest.setAttribute("locations", loadLocation());
		Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("WO", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbLocationMas> locations=(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
		httpServletRequest.setAttribute("locationCat", locations);
		List<DesignationBean> designlist = designationService.getDesignByOrgId(orgId);
		httpServletRequest.setAttribute("designlist", designlist);
		
		return new ModelAndView("ComplainRegisterApprovalForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	/**
	 * @return Department List
	 */
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); 
		return departments;
	}
	
	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
	
	
}
