package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.ExecuteChangeOfOwnerModel;
import com.abm.mainet.water.ui.model.ExecuteDisConnectionProcessModel;

@Controller
@RequestMapping("/ExecuteDisConnectionProcess.html")
public class ExecuteDisConnectionProcessController extends AbstractFormController<ExecuteDisConnectionProcessModel> {

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private ICFCApplicationMasterService iCFCApplicationMasterService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		final Long applicationId = Long.valueOf(httpServletRequest.getParameter("applId"));
		getModel().setApplicationId(applicationId);
		final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
		getModel().setServiceId(serviceId);
		getModel().setLevelId(Long.valueOf(httpServletRequest.getParameter("labelId")));
		getModel().setLevelValue(httpServletRequest.getParameter("labelVal"));
		getModel().setLevel(Long.valueOf(httpServletRequest.getParameter("level")));
		final UserSession session = UserSession.getCurrent();

		setServiceDetail(getModel(), session);
		setApplicantDetails(getModel(), applicationId, session);
		getModel().setCommonDto(applicationId, session.getOrganisation().getOrgid());

		return super.index();

	}

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView showDetails(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam("appNo") final Long applicationId) {
		getData(applicationId, actualTaskId,httpServletRequest);
		return new ModelAndView("ExecuteDisConnectionProcessValidn", MainetConstants.CommonConstants.COMMAND,
				getModel());

	}
	
	@Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {	
			getData(Long.valueOf(applicationId), taskId, httpServletRequest);
        return new ModelAndView("ExecuteDisConnectionProcessView", MainetConstants.FORM_NAME, getModel());
	}
	
	public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		getModel().setHomeGrid(true);
		final UserSession session = UserSession.getCurrent();
		getModel().setApplicationId(applicationId);
		setServiceDetail(getModel(), session);
		setApplicantDetails(getModel(), applicationId, session);
		getModel().setCommonDto(applicationId, session.getOrganisation().getOrgid());
		getModel().setTaskId(actualTaskId);
	}

	private void setServiceDetail(final ExecuteDisConnectionProcessModel model, final UserSession session) {
		final ServiceMaster serviceMst = serviceMaster.getServiceByShortName(WaterServiceShortCode.WATER_DISCONNECTION,
				session.getOrganisation().getOrgid());
		if (serviceMst != null) {
			model.setServiceId(serviceMst.getSmServiceId());
			if (session.getLanguageId() == MainetConstants.ENGLISH) {
				model.setServiceName(serviceMst.getSmServiceName());
			} else {
				model.setServiceName(serviceMst.getSmServiceNameMar());
			}
		}
	}

	private void setApplicantDetails(final ExecuteDisConnectionProcessModel model, final Long applicationId,
			final UserSession session) {
		final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
				.getCFCApplicationByApplicationId(applicationId, session.getOrganisation().getOrgid());
		if (applicationMaster != null) {

			String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
			userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
			userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmLname();
			model.setApplicanttName(userName);
			model.setApplicationDate(applicationMaster.getApmApplicationDate());
		}
	}
	
	 @RequestMapping(params = "saveform", method = RequestMethod.POST)
	    public ModelAndView saveform(final HttpServletRequest httpServletRequest) throws Exception {
	        bindModel(httpServletRequest);
	        final ExecuteDisConnectionProcessModel model = getModel();
	        if (model.saveForm()) {
	            if ((model.getSuccessMessage() != null) && !model.getSuccessMessage().isEmpty()) {
	            		return jsonResult(JsonViewObject
	        					.successResult(ApplicationSession.getInstance().getMessage("Your.application.for.Water.Disconnection.Updated.successfully"))); 
	            	
	            } else {
	                return jsonResult(
	                        JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
	            }
	        }

	        return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
	                : customDefaultMyResult(model.getCustomViewName());

	    }
}
