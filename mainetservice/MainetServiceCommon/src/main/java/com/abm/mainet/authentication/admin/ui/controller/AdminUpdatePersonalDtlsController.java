package com.abm.mainet.authentication.admin.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminUpdatePersonalDtlsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AdminUpdatePersonalDtls.html")
public class AdminUpdatePersonalDtlsController extends AbstractFormController<AdminUpdatePersonalDtlsModel> {

    @Autowired
    private IEmployeeService iEmployeeService;

    /*@RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {
        sessionCleanup(request);
        return new ModelAndView(MainetConstants.AdminUpdatePersonalDtls.ADMMIN_UPDATE_PERSONAL_DTLS, MainetConstants.FORM_NAME, new AdminUpdatePersonalDtlsModel());
    }*/
    
    @ResponseBody
    @RequestMapping(params = "showDetails", method = RequestMethod.GET)
    public ModelAndView showDetails(@RequestParam("emploginname") final String empLoginName,
            final HttpServletRequest httpServletRequest) {
    	List<Employee> employee = iEmployeeService.getEmployeeListByLoginName(empLoginName, UserSession.getCurrent().getOrganisation(), MainetConstants.AdminUpdatePersonalDtls.ACTIVE);
    	AdminUpdatePersonalDtlsModel model = this.getModel();
    	if (employee.size()>=1) {
    		model.setMobileNumber(employee.get(0).getEmpmobno());
			model.setEmpFName(employee.get(0).getEmpname());
			model.setEmpMName(employee.get(0).getEmpmname());
			model.setEmpLName(employee.get(0).getEmplname());
			model.setOldMobileNumber(employee.get(0).getEmpmobno());
		}
		return new ModelAndView(MainetConstants.AdminUpdatePersonalDtls.ADMMIN_UPDATE_PERSONAL_DTLS, MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "updatePersonalDtls", method = RequestMethod.POST)
    public @ResponseBody String updatePersonalDtls(@RequestParam("mobileNumber") final Long mobileNumber,@RequestParam("empFName") final String empFName,@RequestParam("empMName") final String empMName,@RequestParam("empLName") final String empLName) {
       //Validation for empty values
    	if(mobileNumber==null || empFName == null || empLName ==null || empFName.trim().isEmpty() || empLName.trim().isEmpty()) {
    	   return  ApplicationSession.getInstance().getMessage(MainetConstants.AdminUpdatePersonalDtls.ENTER_MANDATORY_FIELDS);
       }
    	//If mobile number is not changed, update name
       if(this.getModel().getOldMobileNumber().equals(mobileNumber.toString())) {
    	   List<Employee> employee = iEmployeeService.getEmployeeByEmpMobileNo(mobileNumber.toString(), null, MainetConstants.AdminUpdatePersonalDtls.ACTIVE);
    	   employee.stream().forEach(e ->{
        	   e.setEmpname(empFName);
        	   e.setEmpmname(empMName);
        	   e.setEmplname(empLName);
        	   iEmployeeService.updateEmpDetails(e);
           });
       }else {
    	   //if mobile number is changed, check if mobile number is existing, if its existing then throw error, otherwise update mobile number and name
    	   List<Employee> employee = iEmployeeService.getEmployeeByEmpMobileNo(mobileNumber.toString(), UserSession.getCurrent().getOrganisation(), MainetConstants.AdminUpdatePersonalDtls.ACTIVE);
    	   if(employee.size()>0) {
    		   return ApplicationSession.getInstance().getMessage(MainetConstants.AdminUpdatePersonalDtls.MOBILE_NUMBER_ALREADY_EXISTS);
    	   }else {
    		   employee = iEmployeeService.getEmployeeByEmpMobileNo(this.getModel().getOldMobileNumber(), null, MainetConstants.AdminUpdatePersonalDtls.ACTIVE);
    	       employee.stream().forEach(e ->{
    	    	   e.setEmpmobno(mobileNumber.toString());
    	    	   e.setEmpname(empFName);
    	    	   e.setEmpmname(empMName);
    	    	   e.setEmplname(empLName);
    	    	   iEmployeeService.updateEmpDetails(e);
    	       });
    	   }
       }
      
       return MainetConstants.AdminUpdatePersonalDtls.SUCCESS; 
    }


}
