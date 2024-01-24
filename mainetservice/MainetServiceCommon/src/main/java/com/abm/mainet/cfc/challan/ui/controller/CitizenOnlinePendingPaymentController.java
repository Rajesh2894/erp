package com.abm.mainet.cfc.challan.ui.controller;

import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.CitizenOnlinePendingPaymentScheduler;
import com.abm.mainet.cfc.challan.ui.model.CitizenOnlinePendingPayment;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbScrutinyLabelsService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 03 April 2023
 */

@Controller
@RequestMapping(value = "/CitizenOnlinePendingPayment.html")
public class CitizenOnlinePendingPaymentController extends AbstractFormController<CitizenOnlinePendingPayment>{

	
	@Autowired
	private CitizenOnlinePendingPaymentScheduler citiPayScheduler;
	 

	@Resource
	private TbScrutinyLabelsService serviceMasterService;

	@Autowired
	private DepartmentService departService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().setChallanDTO(new ApplicationFormChallanDTO());
		Long deptId = departService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
		Long wtDeptId = departService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
				
		getModel().setServiceList(
				serviceMasterService.getAllServicesData(UserSession.getCurrent().getOrganisation().getOrgid()).stream()
						.filter(d -> d.getTriCod1()!=null && (d.getTriCod1().equals(deptId)|| d.getTriCod1().equals(wtDeptId))).collect(Collectors.toList()));
		return new ModelAndView("CitizenOnlinePendingPayment", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "initiatePendPayment", method = RequestMethod.POST)
	public ModelAndView getChecklistAndServiceCharge(final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		CitizenOnlinePendingPayment model = this.getModel();
		model.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		citiPayScheduler.runOnlinePendingPaymentDate(model);
		if (model.hasValidationErrors()) {
			//this.getModel().addValidationError("No record Found for selected criteria");
			ModelAndView modelAndView = new ModelAndView("CitizenOnlinePendingPaymentValidn", MainetConstants.FORM_NAME,
					this.getModel());

			modelAndView.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					model.getBindingResult());
			return modelAndView;
		}
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}
}
