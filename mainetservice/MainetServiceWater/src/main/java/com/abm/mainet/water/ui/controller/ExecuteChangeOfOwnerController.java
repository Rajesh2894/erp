package com.abm.mainet.water.ui.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.service.ChangeOfOwnerShipService;
import com.abm.mainet.water.ui.model.ExecuteChangeOfOwnerModel;

/**
 * @author Vivek.Kumar
 * @since 15 June 2016
 */
@Controller
@RequestMapping("/ExecuteChangeOfOwner.html")
public class ExecuteChangeOfOwnerController extends AbstractFormController<ExecuteChangeOfOwnerModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteChangeOfOwnerController.class);

    @Autowired
    private ChangeOfOwnerShipService changeOfOwnerShipService;

    /**
     * application for Change of ownership will be completed here
     * 
     * @param appNo
     * @param request
     * @return
     */
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView executeChangeOfOwner(final HttpServletRequest request,
	    @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
	    @RequestParam("appNo") final Long applicationId) {
	
		try {
			getData(applicationId, actualTaskId, request);

		} catch (final Exception ex) {
			LOGGER.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
	return new ModelAndView("ChangeOfOwnerExecutionForm", MainetConstants.CommonConstants.COMMAND, getModel());

    }
    
    @Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {	
		try {
			getData(Long.valueOf(applicationId), taskId, httpServletRequest);
		} catch (final Exception ex) {
			LOGGER.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
        return new ModelAndView("ChangeOfOwnerExecutionFormView", MainetConstants.FORM_NAME, getModel());
	}
    
    public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
    	sessionCleanup(httpServletRequest);
    	getModel().bind(httpServletRequest);
    	getModel().setCommonDto(changeOfOwnerShipService.initializeChangeOfOwnerExecutionData(applicationId,
    		    UserSession.getCurrent()));
    	    getModel().getCommonDto().setApmApplicationId(applicationId);
    	    getModel().setTaskId(actualTaskId);
    }

    
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);
        final ExecuteChangeOfOwnerModel model = getModel();
        if (model.saveForm()) {
            if ((model.getSuccessMessage() != null) && !model.getSuccessMessage().isEmpty()) {
            	//For all environments Defect 148737
            		return new ModelAndView("ChangeInUsageWorkOrder", MainetConstants.CommonConstants.COMMAND,
							model); 
            } else {
                return jsonResult(
                        JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
            }
        }

        return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
                : customDefaultMyResult(model.getCustomViewName());

    }
    
    @ResponseBody
    @RequestMapping(params = "saveformForAligarh", method = RequestMethod.POST)
    public Map<String, Object> saveformForAligarh(final HttpServletRequest httpServletRequest) throws Exception {
    	getModel().bind(httpServletRequest);
	Map<String, Object> object = new LinkedHashMap<String, Object>();
	if (this.getModel().saveForm()) {
	    object.put("successFlag", "Y");
	} else {
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	}

	return object;
	}
}
