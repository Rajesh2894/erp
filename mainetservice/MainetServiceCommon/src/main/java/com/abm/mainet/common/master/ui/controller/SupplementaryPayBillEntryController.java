package com.abm.mainet.common.master.ui.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.ui.model.SupplementaryPayBillEntryModel;
import com.abm.mainet.common.service.ISupplementaryPayBillEntryService;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("/SupplementaryPayBillEntry.html")
public class SupplementaryPayBillEntryController extends AbstractFormController<SupplementaryPayBillEntryModel> {

	@Resource
	private ISupplementaryPayBillEntryService supplementaryPayBillEntryService;  
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("SupplementaryPayBillEntry.html");
		return index();
	}

	@RequestMapping(params = "addSupplementaryPayBill", method = RequestMethod.POST)
	public ModelAndView addSupplementaryPayBill(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		return new ModelAndView("SupplementaryPayBillEntryForm", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(params = "checkIsValidEmployee", method = RequestMethod.POST)
	public @ResponseBody boolean checkIsValidEmployee(@RequestParam String empId, final HttpServletRequest request) {
		// ulbCode == "3"; to be selected from form >> Temporary Hard coded 
		return supplementaryPayBillEntryService.checkIsValidEmployee(empId, "3");
	}


	@RequestMapping(params = "getEmployeeData", method = RequestMethod.POST)
	public @ResponseBody List<Object> getEmployeeData(@RequestParam String empId, @RequestParam String payMonth,
			@RequestParam String payYear, @RequestParam(required = false) String suppType, final HttpServletRequest request) {
		// ulbCode == "3"; to be selected from form >> Temporary Hard coded 
		return supplementaryPayBillEntryService.getEmployeeData(empId, payMonth, payYear, "3", suppType);
	}


	@RequestMapping(params = "getEmployeePayDetails", method = RequestMethod.POST)
	public @ResponseBody List<Object> getEmployeePayDetails(@RequestParam String empId, @RequestParam String payMonth,
			@RequestParam String payYear, @RequestParam(required = false) String suppType, @RequestParam String suppMonth, 
			@RequestParam String suppYear, @RequestParam String eLeave, @RequestParam String mLeave,
			@RequestParam String hpLeave, @RequestParam String workDays, final HttpServletRequest request) {
		// ulbCode == "3"; to be selected from form >> Temporary Hard coded 
		return supplementaryPayBillEntryService.getEmployeePayDetails(empId, payMonth, payYear, "3", suppType,
				suppMonth, suppYear, eLeave, mLeave, hpLeave,  workDays);
	}
	
	
}