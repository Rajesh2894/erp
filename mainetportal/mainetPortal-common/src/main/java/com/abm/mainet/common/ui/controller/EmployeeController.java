
package com.abm.mainet.common.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.MainetConstants.OrgMaster;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.EmployeeResponse;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IGroupMasterService;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * Spring MVC controller for 'Employee' management.
 */
@Controller
@RequestMapping("/EmployeeMaster.html")
public class EmployeeController {

    private static final String GROUPMASTER_GR_CODE = "groupmaster.GrCode";
    private static final String DEPARTMENTLIST = "departmentlist";
    private static final String DESIGNLIST = "designlist";
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
    private static final String IS_SYSADMIN= "isSysAdmin";
    private final String entityName;
    private static final Logger LOG = Logger.getLogger(EmployeeController.class);

    // --- Main entity service
    @Autowired
    private IEmployeeService employeeService; // Injected by Spring

    // --- Other service(s)

    /*
     * @Autowired private IDesignationService designationService; // Injected by Spring
     */
    @Autowired
    private IDepartmentService tbDepartmentService; // Injected by Spring

    private List<EmployeeBean> list = null;

    @Autowired
    private IGroupMasterService groupMasterService;

    // --------------------------------------------------------------------------------------
    /**
     * Default constructor
     */
    public EmployeeController() {
        this.entityName = MAIN_ENTITY_NAME;
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * 
     * @param model
     * @param employee
     */
    private void populateModel(final Model model, final EmployeeBean employee, final FormMode formMode) {

        final List<LookUp> genderLookup = CommonMasterUtility.getLookUps(PrefixConstants.Common.GENDER,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> titleLookup = CommonMasterUtility.getLookUps(PrefixConstants.Common.TITLE,
                UserSession.getCurrent().getOrganisation());
        final Map<Long, String> groupLookup = employeeService
                .getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
        
        model.addAttribute(MainetConstants.FORM_NAME, model);
        model.addAttribute(genderLookups_init, genderLookup);
        model.addAttribute(titleLookups_init, titleLookup);
        model.addAttribute(Group_Map, groupLookup);
        model.addAttribute(MAIN_ENTITY_NAME, employee);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(OrgMaster.MODE, OrgMaster.MODE_CREATE); // The form is in "create" mode
            model.addAttribute(OrgMaster.SAVE_ACTION, SAVE_ACTION_CREATE);

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(OrgMaster.MODE, OrgMaster.MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(OrgMaster.SAVE_ACTION, SAVE_ACTION_UPDATE);

        }
    }

    // --------------------------------------------------------------------------------------
    // Request mapping
    // --------------------------------------------------------------------------------------
    /**
     * Shows a list with all the occurrences of Employee found in the database
     * 
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final ModelMap model) {
        final List<Department> departmentlist = tbDepartmentService.getDepartments(MainetConstants.IsLookUp.ACTIVE);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.addAttribute(DEPARTMENTLIST, departmentlist);

        Employee empSession = UserSession.getCurrent().getEmployee();
        ApplicationSession appSession = ApplicationSession.getInstance();
        final GroupMaster groupMaster = groupMasterService.findByGmId(empSession.getGmid(), orgId);
        if (groupMaster.getGrCode().equals(appSession.getMessage(GROUPMASTER_GR_CODE))) {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.NO);
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {

			list = employeeService.getAllDeptEmployee(orgId);
		} else {
			list = employeeService.getAllEmployeeBasedOnRoleCode(orgId);

		}
        
        List<GroupMaster> grMastList = groupMasterService.getAllGroupMast(orgId);
        
        list.forEach(emp->{
        	if(emp.getGmid() !=null) {
        		grMastList.forEach(g->{
        			if(emp.getGmid().longValue() == g.getGmId() ) {
        				emp.setGrCode( g.getGrCode());
        			}
        		});
        	}
        });       
        model.addAttribute(MainetConstants.FORM_NAME, model);
        return JSP_LIST;
    }

    @RequestMapping(params = "loadEmployeeGridData")
    public @ResponseBody EmployeeResponse loadEmployeeGridData(final HttpServletRequest request, final ModelMap model) {

        final EmployeeResponse empResponse = new EmployeeResponse();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.Common_Constant.PAGE));
        empResponse.setRows(list);
        empResponse.setTotal(list.size());
        empResponse.setRecords(list.size());
        empResponse.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, list);
        return empResponse;
    }

    @RequestMapping(params = "getEmployeeData", method = { RequestMethod.POST })
    public @ResponseBody String getEmployeeData(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId, @RequestParam("designId") final Long designId) {
        String message = "success";
        // --- Populates the model with a new instance

        list = employeeService.getEmployeeData(deptId, null, designId,
                Long.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
        if ((list.size() == 0) || list.isEmpty()) {
            message = "failure";
        }
        return message;
    }

    /**
     * Shows a form page in order to update an existing Employee
     * 
     * @param model Spring MVC model
     * @param empid primary key element
     * @return
     */
    @RequestMapping(params = "editEmployeeForm")
    public String formForUpdate(final Model model, @RequestParam("empId") final Long empid,
            @RequestParam("flag") final String flag) {
    	
    	Employee empSession = UserSession.getCurrent().getEmployee();
        final EmployeeBean employee = employeeService.findById(empid);
        final ApplicationSession appSession = ApplicationSession.getInstance();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String empScanSignPath = employee.getScansignature();
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			final GroupMaster groupMast = groupMasterService.findByGmId(empSession.getGmid(), orgId);
			if (groupMast != null && groupMast.getGrCode() != null
					&& groupMast.getGrCode().equals(appSession.getMessage(GROUPMASTER_GR_CODE))) {
				model.addAttribute(IS_SYSADMIN, MainetConstants.Common_Constant.YES);
			} else {
				model.addAttribute(IS_SYSADMIN, MainetConstants.Common_Constant.NO);
			}
		}
        
        final GroupMaster groupMaster = groupMasterService.findByGmId(employee.getGmid(), orgId);
        if (groupMaster !=null && groupMaster.getGrCode()!=null && groupMaster.getGrCode().equals(appSession.getMessage(GROUPMASTER_GR_CODE))) {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.YES);
        } else {
            model.addAttribute(IS_SUPERADMIN, MainetConstants.Common_Constant.NO);
        }
        employee.setEmpdob(employee.getEmpdob());
        employee.setEmpexpiredt(employee.getEmpexpiredt());
        if ((flag != null) && flag.equals(MainetConstants.Common_Constant.YES)) {
            employee.setModeFlag(MainetConstants.Common_Constant.YES);
        } else {
            employee.setModeFlag(MainetConstants.Common_Constant.NO);
        }

        if (empScanSignPath != null) {
            final int scanSignLen = empScanSignPath.length();
            employee.setEmpSignDocName(empScanSignPath.substring(
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR) + MainetConstants.NUMBERS.ONE,
                    scanSignLen));
            employee.setEmpSignDocPath(empScanSignPath.substring(MainetConstants.Common_Constant.INDEX.ZERO,
                    empScanSignPath.lastIndexOf(MainetConstants.FILE_PATH_SEPARATOR)));
        }

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
        if (null != employee.getEmpphotopath()) {
            employee.setFilePath(Utility.downloadedFileUrl(employee.getEmpphotopath(), outputPath,
                    FileNetApplicationClient.getInstance()));
        }
        if (null != employee.getScansignature()) {
            employee.setSignPath(Utility.downloadedFileUrl(employee.getScansignature(), outputPath,
                    FileNetApplicationClient.getInstance()));
        }
        if (null != employee.getEmpuiddocpath()) {
            employee.setUiddocPath(Utility.downloadedFileUrl(
                    employee.getEmpuiddocpath() + MainetConstants.FILE_PATH_SEPARATOR + employee.getEmpuiddocname(),
                    outputPath, FileNetApplicationClient.getInstance()));
        }

        final Map<Long, String> groupLookup = employeeService.getGroupList(orgId);
        populateModel(model, employee, FormMode.UPDATE);
        model.addAttribute(Group_Map, groupLookup);

        // setDesignationData(model, employee.getDpDeptid(), orgId);

        setEmployeeData(model, orgId);

        return JSP_FORM;
    }

    @RequestMapping(params = "addEmployeeData", method = { RequestMethod.POST })
    public String addEmployeeData(final HttpServletRequest request, final Model model) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        final EmployeeBean employee = new EmployeeBean();
        employee.setModeFlag(MainetConstants.MENU.A);
        final Department department = tbDepartmentService.getDepartment(MainetConstants.DEPARTMENT.CFC_CODE,
                MainetConstants.IsLookUp.ACTIVE);

        // final DesignationBean designation = designationService.findById(designId);

        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            employee.setDeptName(department.getDpDeptdesc());
        } else {
            employee.setDeptName(department.getDpNameMar());
        }

        employee.setDpDeptid(department.getDpDeptid());

        final List<LookUp> genderLookup = CommonMasterUtility.getLookUps(PrefixConstants.Common.GENDER,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> titleLookup = CommonMasterUtility.getLookUps(PrefixConstants.Common.TITLE,
                UserSession.getCurrent().getOrganisation());

        Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Map<Long, String> groupLookup = employeeService
                .getGroupList(orgid);

        // setDesignationData(model, department.getDpDeptid(), orgid);
        setEmployeeData(model, orgid);
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
          //  countEmployee = employeeService.validateEmployee(emploginname,
                  //  UserSession.getCurrent().getOrganisation().getOrgid());
            final List<Employee> employee1 = employeeService.getEmployeeListByLoginName(emploginname, UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO);
            if ((employee1 != null) && !employee1.isEmpty()) {
            	for (final Employee employee : employee1) {
                    if (employee.getEmplType() == null) {
                    	countEmployee++;
                    }
                }
              
            }
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return countEmployee;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param employee entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create") // GET or POST
    public @ResponseBody String create(@Valid @ModelAttribute("employee") final EmployeeBean employee,
            final BindingResult bindingResult, final Model model, final HttpServletRequest httpServletRequest) {
        try {
            if (!bindingResult.hasErrors()) {

                employee.setEmpdob(employee.getEmpdob());

                employee.setEmpname(employee.getEmpname().trim());
                employee.setEmpMName(employee.getEmpMName().trim());
                employee.setEmpLName(employee.getEmpLName().trim());

                final EmployeeBean employeeCreated = employeeService.create(employee, getDirectry(),
                        FileNetApplicationClient.getInstance());
                employeeCreated.setHasError(false);
                model.addAttribute(MAIN_ENTITY_NAME, employeeCreated);
                model.addAttribute(MainetConstants.FORM_NAME, model);
                model.addAttribute(ERROR_Value, "N");

                final List<Department> departmentlist = tbDepartmentService.getDepartments(MainetConstants.IsLookUp.ACTIVE);
                model.addAttribute(DEPARTMENTLIST, departmentlist);

                list = new ArrayList<>(0);

                return "success";
            }

            else {
                model.addAttribute(ERROR_Value, MainetConstants.Common_Constant.YES);
                employee.setHasError(true);
                populateModel(model, employee, FormMode.CREATE);
                return "fail";
            }
        } catch (final Exception e) {

            populateModel(model, employee, FormMode.CREATE);
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return "fail";
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param employee entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update")// GET or POST
    public @ResponseBody String update(@Valid @ModelAttribute("employee") final EmployeeBean employee,
            final BindingResult bindingResult,
            final Model model,
            final HttpServletRequest httpServletRequest) {
        try {
            if (!bindingResult.hasErrors()) {

                employee.setEmpdob(employee.getEmpdob());

                employee.setEmpname(employee.getEmpname().trim());
                employee.setEmpMName(employee.getEmpMName().trim());
                employee.setEmpLName(employee.getEmpLName().trim());

                final EmployeeBean employeeSaved = employeeService.updateEmployee(employee, getDirectry(),
                        FileNetApplicationClient.getInstance());
                employeeSaved.setHasError(false);
                model.addAttribute(MAIN_ENTITY_NAME, employeeSaved);

                model.addAttribute(MainetConstants.FORM_NAME, model);
                model.addAttribute(ERROR_Value, "N");
                LOG.info("Action 'update' : update done - redirect");
                final List<Department> departmentlist = tbDepartmentService.getDepartments(MainetConstants.IsLookUp.ACTIVE);
                model.addAttribute(DEPARTMENTLIST, departmentlist);

                list = new ArrayList<>(0);

                return "success";
            } else {
                employee.setHasError(true);
                model.addAttribute(ERROR_Value, MainetConstants.Common_Constant.YES);
                populateModel(model, employee, FormMode.UPDATE);
                return "fail";
            }
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            populateModel(model, employee, FormMode.UPDATE);
            return "fail";
        }
    }

    public String getDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + "EMP_MASTER" + File.separator
                + Utility.getTimestamp();
    }

    @RequestMapping(params = "getUploadedImage", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final String getdataOfUploadedImage(final HttpServletRequest httpServletRequest) {
        try {

            if ((FileUploadUtility.getCurrent().getFileMap() != null)
                    && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    if ((entry.getKey() != null) && (entry.getKey().longValue() == 0)) {
                        for (final File file : entry.getValue()) {
                            String fileName = null;
                            fileName = getPath(file, fileName);

                            return fileName;
                        }
                    }

                }
            }
            return MainetConstants.BLANK;
        } catch (final Exception e) {

            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return MainetConstants.BLANK;
    }

    private String getPath(final File file, String fileName) {
        try {
            final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                    MainetConstants.operator.FORWARD_SLACE);
            fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return fileName;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        return FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
                browserType);
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        return FileUploadUtility.getCurrent().getFileUploadList();

    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        JsonViewObject jsonViewObject = JsonViewObject.successResult();
        jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
        return jsonViewObject;
    }

    @RequestMapping(params = "Download", method = RequestMethod.POST)
    public ModelAndView download(@RequestParam("downloadLink") final String downloadLink,
            final HttpServletResponse httpServletResponse, final Model model) {
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
        try {
            final String downloadPath = Utility.downloadedFileUrl(downloadLink, outputPath,
                    FileNetApplicationClient.getInstance());
            model.addAttribute(PATH, downloadPath);
        } catch (final Exception ex) {
            return new ModelAndView("redirect:/404error.jsp.html");
        }

        return new ModelAndView("viewHelp", MainetConstants.FORM_NAME, model);
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param redirectAttributes
     * @param empid primary key element
     * @return
     */
    @RequestMapping(params = "delete", method = RequestMethod.POST) // GET or POST
    public @ResponseBody String delete(final HttpServletRequest httpServletRequest,
            @RequestParam("empId") final Long empid, final Employee employee, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        String errorMsg = null;

        try {
            final EmployeeBean emp = employeeService.findById(empid);

            final GroupMaster groupMaster = groupMasterService.findByGmId(emp.getGmid(), orgId);
            if (groupMaster.getGrCode().equals(appSession.getMessage(GROUPMASTER_GR_CODE))) {
                errorMsg = appSession.getMessage("emp.error.delete");
            } else {
                employeeService.deleteEmployee(empid, orgId);
            }

        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute(MAIN_ENTITY_NAME, employee);
        model.addAttribute(MainetConstants.FORM_NAME, model);
        return JSP_LIST;
    }

    @RequestMapping(params = "validateEmp", method = RequestMethod.POST)
    public @ResponseBody List<String> validateEmployee(@RequestParam("mobileNo") final String mobileNo,
            @RequestParam("uid") String uid, @RequestParam("pancardNo") final String pancardNo,
            @RequestParam("mode") final String mode, @RequestParam("empId") final Long empId) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        List<Employee> employee2 = null;
        List<Employee> employee3 = null;
        if (uid.equals(MainetConstants.BLANK)) {
            uid = null;
        }
       
        final List<String> errorList = new ArrayList<>();
        final List<Employee> employee1 = employeeService.getEmployeeByMobileNo(mobileNo, orgId);
        
      

        if (uid !=null && !(uid.trim().equalsIgnoreCase(""))) {
            employee2 = employeeService.getEmployeeByUid(uid, orgId);
        }
        if (pancardNo !=null && !(pancardNo.trim().equalsIgnoreCase(""))) {
        	employee3 = employeeService.getEmployeeByPancardNo(pancardNo, orgId);
        }

        if (mode.equals(MainetConstants.OrgMaster.MODE_CREATE)) {
            if ((employee1 != null) && !employee1.isEmpty()) {
            	for (final Employee employee : employee1) {
                    if (employee.getEmplType() == null) {
                    	  errorList.add(appSession.getMessage("emp.error.notValid.mobileNo"));
                    	  break;
                    }
                }
              
            }
            if (uid !=null && !(uid.trim().equalsIgnoreCase(""))  && (employee2 != null) && !employee2.isEmpty()) {
                errorList.add(appSession.getMessage("emp.error.notValid.uid"));
            }
            if (pancardNo !=null && !(pancardNo.trim().equalsIgnoreCase(""))  && (employee3 != null) && !employee3.isEmpty()) {
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
        return errorList;

    }

    private void setEmployeeData(final Model model, final Long orgId) {
        List<EmployeeBean> employeeList = employeeService.getAllEmployeeBasedOnRoleCode(orgId);
        model.addAttribute("employeeList", employeeList);
    }
    
    @RequestMapping(params = "validateEmplEmail") // GET or POST
    public @ResponseBody List<String> validateEmplEmail(final HttpServletRequest httpServletRequest,
            @RequestParam("empemail") final String empemail, @RequestParam("empId") final Long empId, final Model model) {
    	final List<String> errorList = new ArrayList<>();
    	final ApplicationSession appSession = ApplicationSession.getInstance();
        try {
            final List<Employee> employee1 = employeeService.getEmployeeByEmpEMail(empemail, UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.ZERO);
            if ((employee1 != null) && !employee1.isEmpty()) {
            	for (final Employee employee : employee1) {
                    if (employee.getEmplType() == null && (empId ==null || (empId!=null && employee.getEmpId() !=null && (empId.longValue() != employee.getEmpId().longValue())) ) ) {
                    	 errorList.add(appSession.getMessage("Employee.uniqueEMailId"));
                    	 break;
                    }
                }
              
            }
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return errorList;
    }
    @RequestMapping(params = "locale", method = RequestMethod.GET)
    public String welcome(@RequestParam("lang") final String requestLang, @RequestParam("url") final String url,
            final HttpServletRequest request, final HttpServletResponse response) {
        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, org.springframework.util.StringUtils.parseLocaleString(requestLang));
        UserSession.getCurrent().setLanguageId(getLanguage(requestLang));
        String finalurl = url.replace("mainetgw9Qk", "&");
        return "redirect:" + finalurl;
    }
    private int getLanguage(final String language) {
        if (MainetConstants.DEFAULT_LOCAL_REG_STRING.equals(language)) {
            return 2;
        } else if (MainetConstants.DEFAULT_LOCALE_STRING.equals(language)) {
            return 1;
        } else {
            return 1;
        }
    }

}
