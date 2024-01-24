/**
 * 
 */
package com.abm.mainet.common.master.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.ui.model.CommonManualReceiptEntryModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 *@since 29 june 2020
 */
@Controller
@RequestMapping("/CommonManualReceiptEntry.html")
public class CommonManualReceiptEntryController extends AbstractFormController<CommonManualReceiptEntryModel>{

	@RequestMapping(method = {RequestMethod.POST})
	public ModelAndView index(HttpServletRequest request) {
		List<TbDepartment> deparmentList = ApplicationContextProvider.getApplicationContext()
				.getBean(TbDepartmentService.class)
				.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(deparmentList)) {
			this.getModel().setDeparatmentList(deparmentList);
		}
		return index();

	}
	
	 @RequestMapping(params = "redirectToDepartmentWiseBillPayment", method = {
			    RequestMethod.POST })
	public ModelAndView redirectToDepartmentWiseBillPayment(HttpServletRequest request) {
		return new ModelAndView("redirect:WaterBillPayment.html");
	}
}
