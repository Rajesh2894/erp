package com.abm.mainet.common.ui.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UserSession;

@Configuration
@ControllerAdvice
public class MVCExceptionHandlerController {

    private final Logger LOG = Logger.getLogger(MVCExceptionHandlerController.class);

    @ExceptionHandler(value = FrameworkException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, FrameworkException e) throws Throwable {
	LOG.error(e);
	UserSession userSession = UserSession.getCurrent();
	Organisation org = userSession.getOrganisation();
	String orgString = "Org[][]";
	if(org != null){
	    orgString = "Org["+org.getOrgid()+"]["+org.getONlsOrgname()+"]";
	}
	
	// Otherwise setup and send the user to a default error-view.
	ModelAndView mav = new ModelAndView();
	mav.addObject(MainetConstants.ERR_CODE,
		(e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()) + ", " + orgString);
	mav.addObject(MainetConstants.ERR_MSG, e.getMessage());
	mav.addObject(MainetConstants.ERROR, e);
	mav.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
	return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	LOG.error(e);
	ModelAndView mav = new ModelAndView();
	mav.addObject(MainetConstants.ERR_CODE,e.getMessage());
	mav.addObject(MainetConstants.ERR_MSG, e.getMessage());
	mav.addObject(MainetConstants.ERROR, e);
	mav.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
	return mav;
    }
}
