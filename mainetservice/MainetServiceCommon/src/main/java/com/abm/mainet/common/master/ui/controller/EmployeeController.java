
package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.model.EmployeeResponse;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.MainetMultiTenantConnectionProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * Spring MVC controller for 'Employee' management.
 */
@Controller
@RequestMapping("/EmployeeMaster.html")
public class EmployeeController extends AbstractController {
	
    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "employee";
    private static final String MAIN_LIST_NAME = "list";

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "employee/form";
    private static final String JSP_LIST = "employee/list";

    private static final String ERROR_Value = "errorvalue";
    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "EmployeeMaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "EmployeeMaster.html?update";

    private static final String genderLookups_init = "genderLookups";

    private static final String titleLookups_init = "titleLookups";

    private static final String Group_Map = "groupMap";

    private static final String PATH = "filePath";

    private static final String IS_SUPERADMIN = "isSuperAdmin";
    private static final String IS_ADMIN	 = "isAdmin";
    private static final String HEAD_LIST = "headList";
	private static final Long GM_ID_ONE = 1l;

    // --- Main entity service
    @Autowired
    private IEmployeeService employeeService; // Injected by Spring

    // --- Other service(s)

    @Autowired
    private DesignationService designationService; // Injected by Spring

    @Autowired
    private ILocationMasService locationMasServiceImpl;

    @Autowired
    private TbDepartmentService tbDepartmentService; // Injected by Spring

    @Resource
    private IFileUploadService fileUpload;

    private List<EmployeeBean> list = null;

    @Autowired
    private GroupMasterService groupMasterService;
    
    @Autowired
    private TbOrganisationService organisationService;
    
    @Autowired
	private IOrganisationService iOrganisationService;

    // --------------------------------------------------------------------------------------
    /**
     * Default constructor
     */
    public EmployeeController() {
        super(EmployeeController.class, MAIN_ENTITY_NAME);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param employee
     */
    private void populateModel(final Model model, final EmployeeBean employee, final FormMode formMode) {

        final List<LookUp> genderLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.GENDER,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> titleLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.TITLE,
                UserSession.getCurrent().getOrganisation());
        final Map<Long, String> groupLookup = employeeService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
        List<Object[]> reportingHeadList = employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid());
        final List<TbDepartment> departmentlist = tbDepartmentService
                .findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
        Collections.sort(departmentlist);

        /*
         * List<EmployeeBean> employeeList = employeeService.getAllActiveEmployee();
         * Set<String> regisMobiNos = employeeList.stream().map(EmployeeBean::getEmpmobno).filter(Objects::nonNull)
         * .map(String::trim)
         * .collect(Collectors.toSet());
         * model.addAttribute("regisMobiNos", regisMobiNos);
         */
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
    		model.addAttribute("envFlag", MainetConstants.FlagY);
        }else {
    	    model.addAttribute("envFlag", MainetConstants.FlagN);
    	}
        //get organization list for sfac project
        List<TbOrganisation> orgList = organisationService.fetchOrgListBasedOnLoginOrg();
  
        model.addAttribute("orgList", orgList);
        model.addAttribute("departmentlist", departmentlist);
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
        model.addAttribute(genderLookups_init, genderLookup);
        model.addAttribute(titleLookups_init, titleLookup);
        model.addAttribute(Group_Map, groupLookup);
        model.addAttribute(MAIN_ENTITY_NAME, employee);
        model.addAttribute(HEAD_LIST, reportingHeadList);

        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);

        }
    }

    // --------------------------------------------------------------------------------------
    // Request mapping
    // --------------------------------------------------------------------------------------
    /**
     * Shows a list with all the occurrences of Employee found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final ModelMap model, HttpServletRequest request) {
        final List<TbDepartment> departmentlist = tbDepartmentService
                .findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
        fileUpload.sessionCleanUpForFileUpload();
        Collections.sort(departmentlist);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.addAttribute("departmentlist", departmentlist);
        
        List<TbLocationMas> locationlist = locationMasServiceImpl.getAllLocationByOrgId(orgId);
        model.addAttribute("locationlist", locationlist);
         
        List<DesignationBean> designlist = designationService.getDesignByOrgId(orgId);
        Collections.sort(designlist);
        model.addAttribute("designlist", designlist);
        helpDoc("EmployeeMaster.html", (Model) model);
        Employee empSession = UserSession.getCurrent().getEmployee();
        ApplicationSession appSession = ApplicationSession.getInstance();
        final GroupMaster groupMaster = groupMasterService.findByGmId(empSession.getGmid(), orgId);
        if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.NO);
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
    		model.addAttribute("envFlag", MainetConstants.FlagY);
    	}else {
    	    model.addAttribute("envFlag", MainetConstants.FlagN);
    	}
        //get organization list for  project
        List<TbOrganisation> orgList = organisationService.fetchOrgListBasedOnLoginOrg();
         model.addAttribute("orgList", orgList);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)
				&& UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.NPMA)) {
			list = employeeService.getAllActiveEmployee();
			 final Map<Long, String> groupLookup = employeeService.fetchAllgroupList();
		        model.addAttribute(Group_Map, groupLookup);
		}else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)){
			final Map<Long, String> groupLookup = employeeService.getGroupList(orgId);
			
			list = employeeService.getAllEmployeeWithRole(orgId, groupLookup, UserSession.getCurrent().getRoleCode());
			List<LookUp> zonelist = CommonMasterUtility.getLevelData("DDZ", 1,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> wardList = CommonMasterUtility.getLevelData("DDZ", 2,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> sectorTehsilList = CommonMasterUtility.getLevelData("DDZ", 3,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> sectorList = CommonMasterUtility.getLevelData("DDZ", 4,
					UserSession.getCurrent().getOrganisation());
			wardList = wardList.stream().sorted((i1, i2) -> i1.getLookUpDesc().compareTo(i2.getLookUpDesc())).collect(Collectors.toList());
			//Collections.sort(wardList, LookUp.wardNameComparator);
			
			model.addAttribute("zoneList",zonelist);
			model.addAttribute("wardList",wardList);
			model.addAttribute("sectorTehsilList",sectorTehsilList);
			model.addAttribute("sectorList",sectorList);

			model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
			model.addAttribute(MAIN_LIST_NAME, list);
			model.addAttribute("searchList", list);
			request.getSession().setAttribute("searchList", list);
			//model.addAttribute("YNC", yncLookup);
			model.addAttribute("orgDesig", appSession.getMessage("organisation.designation"));
		}
		else {
			list = employeeService.getAllEmployee(orgId);
			model.addAttribute(MAIN_LIST_NAME, list);
        final Map<Long, String> groupLookup = employeeService.getGroupList(orgId);
        model.addAttribute(Group_Map, groupLookup);
		}
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
        return JSP_LIST;
    }

    @RequestMapping(params = "loadEmployeeGridData")
    public @ResponseBody EmployeeResponse loadEmployeeGridData(final HttpServletRequest request, final ModelMap model) {

        final EmployeeResponse empResponse = new EmployeeResponse();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        empResponse.setRows(list);
        empResponse.setTotal(list.size());
        empResponse.setRecords(list.size());
        empResponse.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, list);
        return empResponse;
    }

    @RequestMapping(params = "getEmployeeData", method = { RequestMethod.POST })
    public @ResponseBody String getEmployeeData(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId,
            @RequestParam("locId") final Long locId,
            @RequestParam("designId") final Long designId,
            @RequestParam("gmid") final  Long gmid) {
        String message = "success";
        // --- Populates the model with a new instance

        list = employeeService.getEmployeeData(deptId, locId, designId,
                Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()),gmid);
        if ((list.size() == 0) || list.isEmpty()) {
            message = "failure";
        }
        return message;
    }
    
    @RequestMapping(params = "getEmployeeDataForSfac", method = { RequestMethod.POST })
    public @ResponseBody String getEmployeeDataSfac(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId,
            @RequestParam("locId") final Long locId,
            @RequestParam("designId") final Long designId,
            @RequestParam("gmid") final  Long gmid, 
            @RequestParam("orgid") final  Long orgid,@RequestParam("masId") final  Long masId) {
        String message = "success";
        // --- Populates the model with a new instance

        list = employeeService.getEmployeeDataForSFAC(deptId, locId, designId,orgid,gmid,masId);
        if ((list.size() == 0) || list.isEmpty()) {
            message = "failure";
        }
        return message;
    }

    @RequestMapping(params = "locationList", method = {
            RequestMethod.POST }, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody List<TbLocationMas> getLocationData(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId) {

        List<TbLocationMas> locationlist = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // --- Populates the model with a new instance
        try {
            locationlist = locationMasServiceImpl.getlocationByDeptId(Long.valueOf(deptId), orgId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return locationlist;

    }

    @RequestMapping(params = "designationList", method = { RequestMethod.POST })
    public @ResponseBody List<DesignationBean> getDesignationData(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId) {
        List<DesignationBean> designlist = null;
        try {
            // --- Populates the model with a new instance
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            designlist = designationService.getDesignByDeptId(deptId, orgId);
            Collections.sort(designlist);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return designlist;
    }

    /**
     * Shows a form page in order to update an existing Employee
     * @param model Spring MVC model
     * @param empid primary key element
     * @return
     */
    @RequestMapping(params = "editEmployeeForm")
    public String formForUpdate(final Model model, @RequestParam("empId") final Long empid,
            @RequestParam("flag") final String flag) {

        final EmployeeBean employee = employeeService.findById(empid);
    
        final ApplicationSession appSession = ApplicationSession.getInstance();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String empScanSignPath = employee.getScansignature();

        final GroupMaster groupMaster = groupMasterService.findByGmId(employee.getGmid(), orgId);
        if (groupMaster != null && groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.NO);
        }
        
        final GroupMaster groupMaster1 = groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(), orgId);
        if (groupMaster1.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.NO);
        }
        final String dob = Utility.dateToString(employee.getEmpdob());
        employee.setEmpDOB(dob);
        final String expDt = Utility.dateToString(employee.getEmpexpiredt());
        employee.setEmpExpDate(expDt);
        if ((flag != null) && flag.equals(MainetConstants.Common_Constant.YES)) {
            employee.setModeFlag(MainetConstants.Common_Constant.YES);
        } else {
            employee.setModeFlag(MainetConstants.Common_Constant.NO);
        }
        //123332 to set flag based on logged user is super Admin or not
        Employee emp = UserSession.getCurrent().getEmployee();
        final GroupMaster grpmaster = groupMasterService.findByGmId(emp.getGmid(), orgId);
        if (grpmaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))){
        	employee.setEmpRoleFlag(MainetConstants.FlagY);
        }else {
        	employee.setEmpRoleFlag(MainetConstants.FlagN);
        }
        //  to get designation and location list on load of edit form
        if (employee.getDpDeptid() != null) {
        List<TbLocationMas> locationlist  = locationMasServiceImpl.getlocationByDeptId(Long.valueOf(employee.getDpDeptid()), orgId);
        model.addAttribute("locationlist", locationlist);
        List<DesignationBean> designlist  = designationService.getDesignByDeptId(employee.getDpDeptid(), orgId);
        model.addAttribute("designlist", designlist);
        }
        if (empScanSignPath != null) {
            final int scanSignLen = empScanSignPath.length();
            employee.setEmpSignDocName(empScanSignPath.substring(
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR) + MainetConstants.NUMBERS.ONE, scanSignLen));
            employee.setEmpSignDocPath(empScanSignPath.substring(MainetConstants.NUMBERS.ZERO,
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR)));
        }

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        employee.setFilePath(
                Utility.downloadedFileUrl(employee.getEmpphotoPath(), outputPath, FileNetApplicationClient.getInstance()));
        employee.setSignPath(
                Utility.downloadedFileUrl(employee.getScansignature(), outputPath, FileNetApplicationClient.getInstance()));
        employee.setUiddocPath(Utility.downloadedFileUrl(
                employee.getEmpuiddocPath() + MainetConstants.FILE_PATH_SEPARATOR + employee.getEmpUidDocName(), outputPath,
                FileNetApplicationClient.getInstance()));
        final Map<Long, String> groupLookup = employeeService.getGroupList(orgId);
        populateModel(model, employee, FormMode.UPDATE);
        model.addAttribute(Group_Map, groupLookup);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
			if (flag.equals(MainetConstants.FlagN) || flag.equals(MainetConstants.FlagY)) {
				List<TbOrganisation> orgList = organisationService.findAll();
				model.addAttribute("orgList", orgList);
			}
			if (employee.getMasId() != null) {
				String masterName = employeeService.getMasterName(employee.getOrgid(), employee.getMasId());
				employee.setMastName(masterName);
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
				if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.NPMA)) {
					list = employeeService.getAllActiveEmployee();
					final Map<Long, String> groupLookupList = employeeService.fetchAllgroupList();
					model.addAttribute(Group_Map, groupLookupList);
					List<Object[]> reportingHead = employeeService.findAllEmpForReporting();
					model.addAttribute(HEAD_LIST, reportingHead);
				} else {
					final Map<Long, String> groupLookupList = employeeService.getGroupList(employee.getOrgid());
					List<Object[]> reportingHead = employeeService.findAllEmpByOrg(employee.getOrgid());
					model.addAttribute(Group_Map, groupLookupList);
					model.addAttribute(HEAD_LIST, reportingHead);
				}
			}
		}
        return JSP_FORM;
    }

    @RequestMapping(params = "addEmployeeData", method = { RequestMethod.POST })
    public String addEmployeeData(final HttpServletRequest request, final Model model) {
        fileUpload.sessionCleanUpForFileUpload();
        final EmployeeBean employee = new EmployeeBean();
        employee.setModeFlag(MainetConstants.FlagA);
        final ApplicationSession appSession = ApplicationSession.getInstance();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final GroupMaster groupMaster = groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(), orgId);
        if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.NO);
        }
        
        final List<LookUp> genderLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.GENDER,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> titleLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.TITLE,
                UserSession.getCurrent().getOrganisation());

        final Map<Long, String> groupLookup = employeeService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
        // 123332 to get designation list on load of add form
        List<DesignationBean> designlist  = designationService.getDesignByOrgId(orgId);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
    		model.addAttribute("envFlag", MainetConstants.FlagY);
    	}else {
    	    model.addAttribute("envFlag", MainetConstants.FlagN);
    	}
        model.addAttribute("designlist", designlist);
        model.addAttribute(genderLookups_init, genderLookup);
        model.addAttribute(titleLookups_init, titleLookup);
        model.addAttribute(Group_Map, groupLookup);
        populateModel(model, employee, FormMode.CREATE);
        return JSP_FORM;
        

      
    }

    @RequestMapping(params = "validateEmployee") // GET or POST
    public @ResponseBody int validateEmployee(final HttpServletRequest httpServletRequest,
            @RequestParam("emploginname") final String emploginname, final Model model) {
        int countEmployee = 0;
        try {
            countEmployee = employeeService.validateEmployee(emploginname, UserSession.getCurrent().getOrganisation().getOrgid());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return countEmployee;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param employee entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create") // GET or POST
    public ModelAndView create(@Valid @ModelAttribute("employee") final EmployeeBean employee, final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        try {
            if (!bindingResult.hasErrors()) {
                employee.setEmpname(employee.getEmpname().trim());
                employee.setEmpmname(employee.getEmpmname().trim());
                employee.setEmplname(employee.getEmplname().trim());
                employee.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                final EmployeeBean employeeCreated =  employeeService.create(employee, getDirectry(),
                        FileNetApplicationClient.getInstance(), UserSession.getCurrent().getOrganisation().getOrgid(),
                        UserSession.getCurrent().getEmployee().getEmpId());
                employeeCreated.setHasError(false);
                model.addAttribute(MAIN_ENTITY_NAME, employeeCreated);

                model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
                model.addAttribute(ERROR_Value, "N");
                final List<TbDepartment> departmentlist = tbDepartmentService.findActualDepartment();
                Collections.sort(departmentlist);
                model.addAttribute("departmentlist", departmentlist);
                list = new ArrayList<>(0);

                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, "success");
            }

            else {
                model.addAttribute(ERROR_Value, MainetConstants.Common_Constant.YES);
                employee.setHasError(true);
                // populateModel(model, employee, FormMode.CREATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {

            // populateModel(model, employee, FormMode.CREATE);
            e.printStackTrace();
            // return new ModelAndView(JSP_FORM);
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param employee entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update") // GET or POST
    public ModelAndView update(@Valid final EmployeeBean employee, final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest) {
        try {
            if (!bindingResult.hasErrors()) {

                final Date datedob = UtilityService.convertStringDateToDateFormat(employee.getEmpDOB());
                employee.setEmpdob(datedob);

                employee.setEmpname(employee.getEmpname().trim());
                employee.setEmpmname(employee.getEmpmname().trim());
                employee.setEmplname(employee.getEmplname().trim());

                employee.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
                employee.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                final EmployeeBean employeeSaved = employeeService.updateEmployee(employee, getDirectry(),
                        FileNetApplicationClient.getInstance());
                employeeSaved.setHasError(false);
                model.addAttribute(MAIN_ENTITY_NAME,
                        employeeSaved);

                model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
                model.addAttribute(ERROR_Value, "N");
                log("Action 'update' : update done - redirect");
                final List<TbDepartment> departmentlist = tbDepartmentService.findActualDepartment();
                Collections.sort(departmentlist);
                model.addAttribute("departmentlist", departmentlist);

                list = new ArrayList<>(0);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, "success");
            } else {
                // employee.setHasError(true);
                // model.addAttribute(ERROR_Value, MainetConstants.Common_Constant.YES);
                // populateModel(model, employee, FormMode.UPDATE);
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // populateModel(model, employee, FormMode.UPDATE);

            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
    }

    public String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "EMP_MASTER"
                + File.separator + Utility.getTimestamp();
    }

    @RequestMapping(params = "getUploadedImage", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final String getdataOfUploadedImage(final HttpServletRequest httpServletRequest) {
        try {

            if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if ((entry.getKey() != null) && (entry.getKey().longValue() == 0)) {
                        for (final File file : entry.getValue()) {
                            String fileName = null;
                            try {
                                final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                                        MainetConstants.operator.FORWARD_SLACE);
                                fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }

                            return fileName;
                        }
                    }

                }
            }
            return MainetConstants.BLANK;
        } catch (final Exception e) {

            e.printStackTrace();
        }
        return MainetConstants.BLANK;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode, browserType);
        return jsonViewObject;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        JsonViewObject jsonViewObject = JsonViewObject.successResult();
        jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
        return jsonViewObject;
    }

    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse,
            final Model model) {
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        try {
            final String downloadPath = Utility.downloadedFileUrl(downloadLink, outputPath,
                    FileNetApplicationClient.getInstance());
            model.addAttribute(PATH, downloadPath);
        } catch (final Exception ex) {
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView("viewHelp", MainetConstants.CommonConstants.COMMAND, model);
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param redirectAttributes
     * @param empid primary key element
     * @return
     */
    @RequestMapping(params = "delete", method = RequestMethod.POST) // GET or POST
    public @ResponseBody String delete(final HttpServletRequest httpServletRequest, @RequestParam("empId") final Long empid,
            final Employee employee, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        String errorMsg = null;

        try {
            final EmployeeBean emp = employeeService.findById(empid);

            final GroupMaster groupMaster = groupMasterService.findByGmId(emp.getGmid(), orgId);
            if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
                errorMsg = appSession.getMessage("emp.error.delete");
            } else {
                employeeService.deleteEmployee(empid, orgId);
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute(MAIN_ENTITY_NAME, employee);
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
        return JSP_LIST;
    }

    /*
     * @RequestMapping(params = "employeeLocation", method = RequestMethod.POST) public @ResponseBody LocationMasEntity
     * getEmployeeLocation(@RequestParam("employeeName") final String empName,
     * @RequestParam("orgId") final Long orgId) { LocationMasEntity locationMas = null; try { locationMas =
     * employeeService.findEmployeeLocation(empName, orgId); } catch (final Exception e) { e.printStackTrace(); } return
     * locationMas; }
     */

    @RequestMapping(params = "validateEmp", method = RequestMethod.POST)
    public @ResponseBody List<String> validateEmployee(@RequestParam("mobileNo") final String mobileNo,
            @RequestParam("uid") String uid,
            @RequestParam("pancardNo") final String pancardNo, @RequestParam("mode") final String mode,
            @RequestParam("empId") final Long empId,
            @RequestParam("gmid") final Long gmid) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        List<Employee> employee2 = null;
        List<Employee> employee3 = null;
         if (uid.equals(MainetConstants.BLANK)) {
            uid = null;
        }
        final List<String> errorList = new ArrayList<>();
      
        final List<Employee> employee1 = employeeService.getEmployeeByMobileNo(mobileNo, orgId);
        //Defect #115543-> PanCardNo field made as optional 
        if (StringUtils.isNotEmpty(pancardNo)) {
        	employee3  = employeeService.getEmployeeByPancardNo(pancardNo, orgId);
        }
     

        if (uid != null) {
            employee2 = employeeService.getEmployeeByUid(uid, orgId);
        }
        /*
         * if ((pancardNo == null) || pancardNo.equals(MainetConstants.BLANK)) {
         * errorList.add(appSession.getMessage("emp.error.notValid.pancardNo")); }
         */

        if (mode.equals(MainetConstants.Actions.CREATE)) {
            if ((employee1 != null) && !employee1.isEmpty()) {
                errorList.add(appSession.getMessage("emp.error.notValid.mobileNo"));
            }
            if ((employee2 != null) && !employee2.isEmpty()) {
                errorList.add(appSession.getMessage("emp.error.notValid.uid"));
            }
          
            if ((employee3 != null) && !employee3.isEmpty()) {
                errorList.add(appSession.getMessage("emp.error.notValid.pancardNo"));
            }
        } else {
            // mobileNo Validation
            if ((employee1 != null) && !employee1.isEmpty()) {
                Boolean isDuplicateEmpMobile = false;
                for (final Employee emp : employee1) {
                    if (!empId.equals(emp.getEmpId())) {
                        isDuplicateEmpMobile = true;
                        break;
                    }
                }
                if (isDuplicateEmpMobile) {
                    errorList.add(appSession.getMessage("emp.error.notValid.mobileNo"));
                }
            }

            // Uid Validation
            if ((employee2 != null) && !employee2.isEmpty()) {
                Boolean isDuplicateUid = false;
                for (final Employee emp : employee2) {
                    if (!empId.equals(emp.getEmpId())) {
                        isDuplicateUid = true;
                        break;
                    }
                }
                if (isDuplicateUid) {
                    errorList.add(appSession.getMessage("emp.error.notValid.uid"));
                }
            }

            // Pancard Validation
            if ((employee3 != null) && !employee3.isEmpty()) {
                Boolean isduplicatePancard = false;
                for (final Employee emp : employee3) {
                    if (!empId.equals(emp.getEmpId())) {
                        isduplicatePancard = true;
                        break;
                    }
                }
                if (isduplicatePancard) {
                    errorList.add(appSession.getMessage("emp.error.notValid.pancardNo"));
                }
            }
        }
        //User Story #31123
        if (gmid !=0  && groupMasterService.findByGmId(gmid, orgId).getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            final int count = employeeService.getEmpCountByGmIdAndOrgId(orgId, gmid);
        	if(count >= 1) {
            	errorList.add(appSession.getMessage("emp.error.notValid.gmid"));
            }
        }
        return errorList;

    }

    /**
     * it is used to fetch user details by mobile number. if employee is registered for mobile number than employee details will
     * get populated in read only mode
     * @param empMobNo
     * @param model
     * @return if employee present than return employee object with details else return empty object
     */
    @RequestMapping(params = "getRegiUserData", method = RequestMethod.POST)
    public String getEmployeeDetails(@RequestParam(value = "empMobNo") final String empMobNo, final Model model) {
        EmployeeBean employee = new EmployeeBean();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final GroupMaster groupMaster = groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(), orgId);
        if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.NO);
        }
        final List<EmployeeBean> employeeList = employeeService.getActiveEmployeeByEmpMobileNo(empMobNo);
        if (employeeList != null && !employeeList.isEmpty()) {
            EmployeeBean savedEmployee = employeeList.get(0);
            BeanUtils.copyProperties(savedEmployee, employee);
            setRegisteredEmployeeDetails(empMobNo, employee, savedEmployee);
        }
        employee.setModeFlag(MainetConstants.FlagA);
        populateModel(model, employee, FormMode.CREATE);
        return JSP_FORM;
        
    }

    // set registered employee details to new bean
    private void setRegisteredEmployeeDetails(final String empMobNo, EmployeeBean employee, EmployeeBean savedEmployee) {
        final String empScanSignPath = savedEmployee.getScansignature();
        if (empScanSignPath != null) {
            final int scanSignLen = empScanSignPath.length();
            employee.setEmpSignDocName(empScanSignPath.substring(
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR) + MainetConstants.NUMBERS.ONE,
                    scanSignLen));
            employee.setEmpSignDocPath(empScanSignPath.substring(MainetConstants.NUMBERS.ZERO,
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR)));
        }

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        employee.setFilePath(
                Utility.downloadedFileUrl(savedEmployee.getEmpphotoPath(), outputPath,
                        FileNetApplicationClient.getInstance()));
        employee.setSignPath(
                Utility.downloadedFileUrl(savedEmployee.getScansignature(), outputPath,
                        FileNetApplicationClient.getInstance()));
        employee.setUiddocPath(Utility.downloadedFileUrl(
                savedEmployee.getEmpuiddocPath() + MainetConstants.FILE_PATH_SEPARATOR + savedEmployee.getEmpUidDocName(),
                outputPath,
                FileNetApplicationClient.getInstance()));

        // flag to identify user is already registered
        employee.setAlreadResisteredUser("Y");

        final String dob = Utility.dateToString(savedEmployee.getEmpdob());
        employee.setEmpDOB(dob);
        // set registered employee hidden field data for disabled mode
        employee.setEmpmobno(empMobNo);
        employee.setRegiCpdTtlId(savedEmployee.getCpdTtlId());
        employee.setRegiEmpDOB(dob);
        employee.setRegiEmpGender(savedEmployee.getEmpGender());

        // set details null for new user
        employee.setEmpId(null);
        employee.setDepid(null);
        employee.setDpDeptid(null);
        employee.setDsgid(null);
        employee.setReportingManager(null);
        employee.setGmid(null);
    }
    @RequestMapping(params = "getUserName") // GET or POST
    public @ResponseBody String getUserName(final HttpServletRequest httpServletRequest,
            @RequestParam("deptId") final Long deptId,@RequestParam("designId") final Long designId) {
    	 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	 Optional<DesignationBean> desgBean=designationService.getDesignByDeptId(deptId, orgId).parallelStream().filter(f->f!=null && f.getDsgid().equals(designId)).findAny();
    String desgShortCode=null;
    	 if(desgBean.isPresent()) {
    		 DesignationBean bean =(DesignationBean)desgBean.get();
    		 desgShortCode=bean.getDsgshortname();
    	 }
    	 String deptShortCode=tbDepartmentService.findById(deptId).getDpDeptcode();
    	 String userName=null;
        try {
        	Long seq=ApplicationContextProvider.getApplicationContext()
			.getBean(SeqGenFunctionUtility.class).getNumericSeqNo("COMMON",
					MainetConstants.COMMON.EMPLOYEE,
					MainetConstants.COMMON.CENTRALENO,  orgId, "CNT", deptId.toString(), 1l,
					99999999l);
        String	no = String.format(MainetConstants.CommonMasterUi.PADDING_THREE,
					Integer.parseInt(seq.toString()));
        userName=desgShortCode+deptShortCode+no;
        	
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return userName;
    }
    
    
    @RequestMapping(params = "getMasterDetail", method = { RequestMethod.POST })
    public @ResponseBody List<CommonMasterDto> getMasterDetail(final HttpServletRequest request, final Model model,
            @RequestParam("orgid") final Long orgid) {
        List<CommonMasterDto> masterDtoList = null;
        try {
            masterDtoList = employeeService.getMasterDetail(orgid);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return masterDtoList;
    }
    
    @RequestMapping(params = "getDeptIdByOrgIdForSFAC", method = { RequestMethod.POST })
    private @ResponseBody Long deptId(final HttpServletRequest request, final Model model,
            @RequestParam("orgid") final Long orgid) {
    	 Organisation orgnisation = ApplicationContextProvider.getApplicationContext()
    				.getBean(IOrganisationService.class).getOrganisationById(orgid);
    	 Long deptId = ApplicationContextProvider.getApplicationContext()
 				.getBean(TbDepartmentService.class).getDepartmentIdByDeptCode(orgnisation.getOrgShortNm());
		 return deptId;
    }
    
    @RequestMapping(params = "addEmployeeDataTCP", method = { RequestMethod.POST })
	public String addEmployeeDataTCP(final HttpServletRequest request, final Model model) {
		fileUpload.sessionCleanUpForFileUpload();
		final EmployeeBean employee = new EmployeeBean();
	
		employee.setModeFlag(MainetConstants.FlagA);
		final ApplicationSession appSession = ApplicationSession.getInstance();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final GroupMaster groupMaster = groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(), orgId);
		if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
			model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute(IS_ADMIN, MainetConstants.Common_Constant.NO);
		}

		final List<LookUp> genderLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.GENDER,
				UserSession.getCurrent().getOrganisation());
		final List<LookUp> titleLookup = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.TITLE,
				UserSession.getCurrent().getOrganisation());

		final Map<Long, String> groupLookup = employeeService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
		// 123332 to get designation list on load of add form
		List<DesignationBean> designlist  = designationService.getDesignByDeptId(null,orgId);
		final List<LookUp> yncLookup = CommonMasterUtility.getLookUps("GRA",
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("YNC", yncLookup);
		model.addAttribute("designlist", designlist);
		model.addAttribute(genderLookups_init, genderLookup);
		model.addAttribute(titleLookups_init, titleLookup);
		model.addAttribute(Group_Map, groupLookup);
		populateModel(model, employee, FormMode.CREATE);
		return JSP_FORM;



	}
    
    @RequestMapping(params = "saveEmployeeGrid")
	@ResponseBody
	public boolean saveEmployeeGrid(final Model model, @RequestParam("empId") final Long empid,
			@RequestParam("name") final String name , @RequestParam("mob") final String mob, @RequestParam("oldno") final String oldNo,
			@RequestParam(value = "hidezone", required = false) String hideZone, @RequestParam(value = "hideward", required = false) String hideWard,
			@RequestParam(value = "hidTehsil", required = false) String hidTehsil, @RequestParam(value = "hidSector", required = false) String hidSector,
			@RequestParam(value = "zone", required = false) String zone, @RequestParam(value = "ward", required = false) String ward,
			@RequestParam(value = "sectorTehsil", required = false) String sectorTehsil, @RequestParam(value = "sector", required = false) String sector,
			@RequestParam(value = "isCCS", required = false) Long isCCS) {
		
		
			final Organisation org = UserSession.getCurrent().getOrganisation();
			return employeeService.saveEmpGridWithWardZone(empid, name, mob,oldNo,zone,ward,sectorTehsil, sector,org,hideZone, hideWard, hidTehsil, hidSector);
		}
		
		
    
    @RequestMapping(params = "getEmployeeDataTCP", method = { RequestMethod.POST })
	public  String getEmployeeData(final HttpServletRequest request, final Model model,
			@RequestParam("deptId") final Long deptId,
			@RequestParam("locId") final Long locId,
			@RequestParam("designId") final Long designId,
			@RequestParam("gmid") final  Long gmid,
			@RequestParam(value = "zone", required = false) final  Long zone,
			@RequestParam(value = "empId", required = false) final  Long empId) {
		String message = "success";
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// --- Populates the model with a new instance
		final Map<Long, String> groupLookup = employeeService.getGroupList(orgId);
		model.addAttribute(Group_Map, groupLookup);
		/*List<EmployeeBean> listSearch = employeeService.getAllEmployee(orgId);
		Collections.sort(listSearch, EmployeeBean.empNameComparator);*/
		list = employeeService.getEmployeeDataWithRole(deptId, locId, designId,
				Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()),gmid, groupLookup, zone , empId, UserSession.getCurrent().getRoleCode());
		if ((list.size() == 0) || list.isEmpty()) {
			message = "failure";
		}
		model.addAttribute(MAIN_LIST_NAME, list);
		List<EmployeeBean> searchList = (List<EmployeeBean>) request.getSession().getAttribute("searchList");
		model.addAttribute("searchList", searchList);
		 List<TbDepartment> departmentlist = tbDepartmentService
				.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
		
		List<LookUp> zoneList = CommonMasterUtility.getLevelData("DDZ", 1,
				UserSession.getCurrent().getOrganisation());
		zoneList = zoneList.stream().sorted((i1, i2) -> i1.getLookUpDesc().compareTo(i2.getLookUpDesc())).collect(Collectors.toList());
		/* Collections.sort(wardList, LookUp.wardNameComparator); */
		model.addAttribute("zoneList",zoneList);
		
		

		List<TbLocationMas> locationlist = locationMasServiceImpl.getAllLocationByOrgId(orgId);
		model.addAttribute("locationlist", locationlist);

		List<DesignationBean> designlist = designationService.getDesignByDeptId(null,orgId);
		
		helpDoc("EmployeeMaster.html", (Model) model);
		Employee empSession = UserSession.getCurrent().getEmployee();
		ApplicationSession appSession = ApplicationSession.getInstance();
		final GroupMaster groupMaster = groupMasterService.findByGmId(empSession.getGmid(), orgId);
		if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode")) || groupMaster.getGrCode().equalsIgnoreCase("CCS_ADMIN") || groupMaster.getGrCode().equalsIgnoreCase("RTI_ADMIN")) {
			model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.NO);
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
			model.addAttribute("envFlag", MainetConstants.FlagY);
		}else {
			model.addAttribute("envFlag", MainetConstants.FlagN);
		}
		//get organization list for  project
		List<TbOrganisation> orgList = organisationService.fetchOrgListBasedOnLoginOrg();
		if(groupMaster.getGrCode().equalsIgnoreCase("CCS_ADMIN") || groupMaster.getGrCode().equalsIgnoreCase("RTI_ADMIN")) {
			departmentlist = departmentlist.stream().filter(d->searchList.stream().anyMatch(e->e.getDpDeptid().equals(d.getDpDeptid()))).collect(Collectors.toList());
			designlist = designlist.stream().filter(d->searchList.stream().anyMatch(e->e.getDsgid().equals(d.getDsgid()))).collect(Collectors.toList());
			
		
		}
		
		Collections.sort(designlist);
		model.addAttribute("designlist", designlist);
		
		Collections.sort(departmentlist);
		model.addAttribute("departmentlist", departmentlist);


		model.addAttribute("orgList", orgList);


		

		model.addAttribute(MainetConstants.CommonConstants.COMMAND, model);
		model.addAttribute("orgDesig", appSession.getMessage("organisation.designation"));
		return "employee/list_form";
	}
    
    @RequestMapping(params = "getZoneList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getZoneList(@RequestParam("zone[]") List<Long> zone, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("DDZ", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp ->  zone.contains(lookUp.getLookUpParentId()))
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			
			return lookUpList1;

		}
	}
    
    @RequestMapping(params = "getSectorList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getSectorList(@RequestParam("ward[]") List<Long> ward, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("DDZ", 3,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp ->  ward.contains(lookUp.getLookUpParentId()))
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			
			return lookUpList1;

		}
	}
    
    @RequestMapping(params = "getLevel4List", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getLevel4List(@RequestParam("tehsil[]") List<Long> tehsil, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("DDZ", 4,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp ->  tehsil.contains(lookUp.getLookUpParentId()))
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			
			return lookUpList1;

		}
	}
}
