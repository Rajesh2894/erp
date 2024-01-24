package com.abm.mainet.disastermanagement.ui.controller;

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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.ui.model.ComplainRegisterModel;

@Controller
@RequestMapping(value = "/ComplainRegister.html")
public class ComplainRegisterController extends AbstractFormController<ComplainRegisterModel> {

	
	@Autowired
	private IFileUploadService fileUpload;
	  
	@Autowired
	private IEmployeeService iEmployeeService;
	
	@Autowired
    private DesignationService designationService;
	
	@Autowired
    private DepartmentService departmentService;
	
	 @Autowired
	 private ILocationMasService iLocationMasService;
		
	
	/**
	 * It will return page of Complain Register
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model,final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("ComplainRegister.html");
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		httpServletRequest.setAttribute("locations", loadLocation());
		Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("WO", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbLocationMas> locations=(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
		httpServletRequest.setAttribute("locationCat", locations);
		List<DesignationBean> designlist = designationService.getDesignByOrgId(orgid);
		httpServletRequest.setAttribute("designlist", designlist);
		return defaultResult();
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

	/**
	 * @return Location List
	 */
	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
    
    @RequestMapping(params = "getEmployeeByDept", method = RequestMethod.POST)
	public @ResponseBody List<Employee> getEmployeeDetailsByDeptId(@RequestParam("deptId") final Long deptId,@RequestParam("designId") final Long designId, final HttpServletRequest request) {
    	 getModel().bind(request);
    	 //List<Employee> employeeList = iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId());
    	 List<Employee> employeeList = iEmployeeService.findAllEmployeeByDesgIdDept(UserSession.getCurrent().getOrganisation().getOrgid(),designId,deptId);
    	 this.getModel().setEmployeList(employeeList);
 		this.getModel().setCommonHelpDocs("ComplainRegister.html");
		return employeeList;
	}

   
    @RequestMapping(params = "getDesgBasedOnDept", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getDesgBasedOnDept(@RequestParam("deptId") final Long deptId) {
		List<LookUp> dsglookupList = departmentService.getAllDesgBasedOnDept(deptId, UserSession.getCurrent().getOrganisation().getOrgid());
		return dsglookupList;
	}	
	
	
    @RequestMapping(params = "getDepBasedOnLoc", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getDepBasedOnLoc(@RequestParam("locId") final Long locId) {
		List<LookUp> departments = departmentService.finDeptListForLoc(locId);
		return departments;
	}	
	
	
	
	
	
	
	
}
