package com.abm.mainet.swm.ui.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.ISLRMEmployeeMasterService;
import com.abm.mainet.swm.ui.model.SLRMEmployeeModel;

@Controller
@RequestMapping("/SLRMEmployeeMaster.html")
public class SLRMEmployeeController extends AbstractFormController<SLRMEmployeeModel>{
    
    @Autowired
    private IMRFMasterService mRFMasterService;
    
    @Autowired
    DesignationService designationService;
    
    @Autowired
    DepartmentService departmentService;     
    
    @Autowired
    ISLRMEmployeeMasterService sLRMEmployeeMasterService;
    
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {      
        sessionCleanup(httpServletRequest);
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setsLRMEmployeeMasterDtoList(sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> mrfCenterMap = this.getModel().getMrfMasterList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        Map<Long, String> desigMap = this.getModel().getDesignationList().stream()
                .collect(Collectors.toMap(DesignationBean::getDsgid, DesignationBean::getDsgname));
     
        
        this.getModel().getsLRMEmployeeMasterDtoList().forEach(emp -> {
            emp.setMrfName(mrfCenterMap.get(emp.getMrfId()));
            emp.setDesigName(desigMap.get(emp.getDesgId()));
            });        
        return defaultResult();
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "addEmployeeMaster")
    public ModelAndView addEmployeeMaster(final HttpServletRequest request) {
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
     	   this.getModel().setEnvFlag(true);
        }else {
        	this.getModel().setEnvFlag(false);
        }
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.CREATE);
        return new ModelAndView("SLRMEmployeeAdd", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "viewEmployeeMaster")
    public ModelAndView viewEmployeeMaster(@RequestParam Long empId,final HttpServletRequest request) {
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setsLRMEmployeeMasterDto(sLRMEmployeeMasterService.searchEmployeeDetails(empId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.VIEW);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
      	   this.getModel().setEnvFlag(true);
         }else {
         	this.getModel().setEnvFlag(false);
         }
        return new ModelAndView("SLRMEmployeeEdit", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "editEmployeeMaster")
    public ModelAndView editEmployeeMaster(@RequestParam Long empId,final HttpServletRequest request) {
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setsLRMEmployeeMasterDto(sLRMEmployeeMasterService.searchEmployeeDetails(empId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.EDIT);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
      	   this.getModel().setEnvFlag(true);
         }else {
         	this.getModel().setEnvFlag(false);
         }
        return new ModelAndView("SLRMEmployeeEdit", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "searchEmployeeMaster")
    public ModelAndView searchEmployeeMaster(@RequestParam Long mrfId,@RequestParam Long empUId, final HttpServletRequest request) {
        this.getModel().setsLRMEmployeeMasterDtoList(sLRMEmployeeMasterService.searchEmployeeList(null, empUId, mrfId, UserSession.getCurrent().getOrganisation().getOrgid()));
   
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));        
        Map<Long, String> mrfCenterMap = this.getModel().getMrfMasterList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        Map<Long, String> desigMap = this.getModel().getDesignationList().stream()
                .collect(Collectors.toMap(DesignationBean::getDsgid, DesignationBean::getDsgname));
        
        this.getModel().getsLRMEmployeeMasterDtoList().forEach(emp -> {
            emp.setMrfName(mrfCenterMap.get(emp.getMrfId()));
            emp.setDesigName(desigMap.get(emp.getDesgId()));
            });
        return new ModelAndView("SLRMEmployeeSearch", MainetConstants.FORM_NAME, this.getModel());
    }

}
