package com.abm.mainet.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;

@ControllerAdvice
public class MVCExceptionHandlerController {

    private final Logger LOG = Logger.getLogger(MVCExceptionHandlerController.class);

    @ExceptionHandler(value = FrameworkException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, FrameworkException e) throws Throwable {
	LOG.error(e);
	// If the exception is annotated with @ResponseStatus rethrow it and let
	// the framework handle it - like the OrderNotFoundException example
	// at the start of this post.
	// AnnotationUtils is a Spring Framework utility class.
	if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
	    throw e;

	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);

	// Otherwise setup and send the user to a default error-view.
	ModelAndView mav = new ModelAndView();
	mav.addObject("errCode", e.getErrCode());
	mav.addObject("errMsg", e.getMessage());
	mav.addObject("error", sw);
	mav.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
	return mav;
    }

}
