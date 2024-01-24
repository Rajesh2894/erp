package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.IDepartmentUserLogReportService;
import com.abm.mainet.cms.ui.model.DepartmentUserLogReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.Utility;
@Controller
@RequestMapping("/DepartmentUserLogReport.html")
public class DepartmentUserLogReportController extends AbstractFormController<DepartmentUserLogReportModel> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IDepartmentUserLogReportService departmentUserLogReportService;
	
	    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView index(final HttpServletRequest httpServletRequest) {
	        sessionCleanup(httpServletRequest);
	        return super.index();
	    }
	    
	    @RequestMapping(params = MainetConstants.URL_EVENT.SERACH, method = RequestMethod.POST)
	    public ModelAndView getUserLogReport(final HttpServletRequest httpServletRequest,@RequestParam final Long org,
	    		@RequestParam final String section,@RequestParam final String fromDate,@RequestParam final String toDate) {
	        bindModel(httpServletRequest);
	        final DepartmentUserLogReportModel model = getModel();
	        model.setOrganisation(org);
	        
	        model.setSection(section);
	       if(fromDate==null || fromDate.isEmpty()){
	    	   model.addValidationError("From Date cannot be Empty");     
	       }
	       else {
	    	 model.setFromDate(Utility.stringToDate(fromDate));  
	       }
	       if(toDate==null || toDate.isEmpty()){
	    	 model.addValidationError("To Date cannot be Empty");    
	       }
	       else {
		     model.setToDate(Utility.stringToDate(toDate));  
		   }
	       if(!fromDate.isEmpty() && !toDate.isEmpty()){
	    	   int days = Days.daysBetween(new LocalDate(model.getFromDate()), new LocalDate(model.getToDate()))
	                    .getDays();
	            if (days < 0) {
	            	model.addValidationError("To Date cannot be less than From Date");  
	            }
	       }
	        if (!model.hasValidationErrors()) {
	             model.setDepartmentUserLogReportDTO(departmentUserLogReportService.getUserLogReport(model.getSection(),model.getFromDate(),model.getToDate(),model.getOrganisation()));
	        }

	        return defaultMyResult();
	    }


}
