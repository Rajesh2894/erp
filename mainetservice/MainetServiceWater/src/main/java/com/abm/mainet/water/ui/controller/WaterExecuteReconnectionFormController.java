
package com.abm.mainet.water.ui.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.WaterExecuteReconnectionFormModel;

/**
 * @author Arun.Chavda
 *
 */
@Controller
@RequestMapping("/waterExecuteReconnectionForm.html")
public class WaterExecuteReconnectionFormController extends AbstractFormController<WaterExecuteReconnectionFormModel> {

	private static Logger log = Logger.getLogger(WaterReconnectionFormController.class);

	@Autowired
	ICFCApplicationMasterService cfcService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView showReconnectionExecutionDetails(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam("appNo") final Long applicationId) {
		getData(applicationId, actualTaskId, httpServletRequest);
		return new ModelAndView("waterExecuteReconnectionForm", MainetConstants.CommonConstants.COMMAND, getModel());

	}
	
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getData(Long.valueOf(applicationId), taskId, httpServletRequest);
		return new ModelAndView("waterExecuteReconnectionFormView", MainetConstants.FORM_NAME, getModel());
	}

	public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final WaterExecuteReconnectionFormModel model = getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setOrgId(orgId);
		final TbCfcApplicationMstEntity cfcApplicationMst = cfcService.getCFCApplicationByApplicationId(applicationId,
				orgId);
		model.setApplicationId(cfcApplicationMst.getApmApplicationId());
		model.setApplicationDate(cfcApplicationMst.getApmApplicationDate());
		final Employee employee = UserSession.getCurrent().getEmployee();
		StringBuilder fullName = new StringBuilder();
		fullName.append(employee.getEmpname());
				
		if(StringUtils.isNotBlank(employee.getEmpmname())) {
			fullName.append(MainetConstants.WHITE_SPACE);
			fullName.append(employee.getEmpmname());
		}
		if(StringUtils.isNotBlank(employee.getEmplname())) {
			fullName.append(MainetConstants.WHITE_SPACE);
			fullName.append(employee.getEmplname());
		}
		model.setApprovedBy(fullName.toString());
		model.setEmpId(employee.getEmpId());
		model.setApprovalDate(new Date());
		model.setTaskId(actualTaskId);
		final ServiceMaster serviceMaster = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.WaterServiceShortCode.WATER_RECONN, orgId);
		model.setServiceId(serviceMaster.getSmServiceId());
		model.setServiceName(serviceMaster.getSmServiceName());

		if (cfcApplicationMst.getApmMname() != null) {
			model.setApplicantFullName(cfcApplicationMst.getApmMname() + MainetConstants.WHITE_SPACE
					+ cfcApplicationMst.getApmMname() + MainetConstants.WHITE_SPACE + cfcApplicationMst.getApmLname());
		} else {
			model.setApplicantFullName(
					cfcApplicationMst.getApmFname() + MainetConstants.WHITE_SPACE + cfcApplicationMst.getApmLname());
		}
	}
	/*
	 * @RequestMapping(params = "saveform", method = RequestMethod.POST) public
	 * ModelAndView saveform(final HttpServletRequest httpServletRequest) {
	 * bindModel(httpServletRequest); final WaterExecuteReconnectionFormModel model
	 * = getModel();
	 * 
	 * try { if (model.saveForm()) { return new
	 * ModelAndView("WaterReconnectionWorkOrder",
	 * MainetConstants.CommonConstants.COMMAND, model); } } catch (final Exception
	 * ex) { log.error("Error Occured During Save Data", ex); return
	 * jsonResult(JsonViewObject.failureResult(ex)); } return defaultMyResult(); }
	 */
}
