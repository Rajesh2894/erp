/**
 * 
 */
package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.dto.PropertyBillGenerationMap;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.ui.model.PropertyBillGenerationLogModel;

/**
 * @author Anwarul.Hassan
 *
 * @since 28-Apr-2020
 */
@Controller
@RequestMapping("PropertyBillGenerationLog.html")
public class PropertyBillGenerationLogController extends AbstractFormController<PropertyBillGenerationLogModel> {
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
    	
    	PropertyBillGenerationMap billGenerateMap = new PropertyBillGenerationMap();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PropertyBillGenerationMap propertyBillGenerationMap = ApplicationSession.getInstance().getPropBillGenerationMapOrgId().get(orgId);
		if(propertyBillGenerationMap != null) {
			billGenerateMap = propertyBillGenerationMap;
		}   	
        this.getModel().setPropertyBillGenerationMap(billGenerateMap);
        return new ModelAndView("PropertyBillGenerationLog",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }
}
