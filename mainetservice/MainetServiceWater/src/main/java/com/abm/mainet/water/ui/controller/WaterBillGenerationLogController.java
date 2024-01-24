/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.dto.WaterBillGenerationMap;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.WaterBillGenerationLogModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/WaterBillGenerationLog.html")
public class WaterBillGenerationLogController extends AbstractFormController<WaterBillGenerationLogModel> {

    @RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
		WaterBillGenerationMap flowDto = new WaterBillGenerationMap();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WaterBillGenerationMap waterBillGenerationMap = ApplicationSession.getInstance().getWaterBillGenerationMapOrgId().get(orgId);
		if(waterBillGenerationMap != null) {
			flowDto = waterBillGenerationMap;
		}
		this.getModel().setWaterBillGenerationMap(flowDto);
		return new ModelAndView("WaterBillGenerationLog", MainetConstants.CommonConstants.COMMAND, getModel());
	}
}
