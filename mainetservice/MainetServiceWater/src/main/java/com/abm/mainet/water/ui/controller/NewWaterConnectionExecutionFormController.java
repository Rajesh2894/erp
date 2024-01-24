/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.NewWaterConnectionExecutionFormModel;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Controller
@RequestMapping("/NewWaterConnectionExecutionForm.html")
public class NewWaterConnectionExecutionFormController
		extends AbstractFormController<NewWaterConnectionExecutionFormModel> {

	@Autowired
	NewWaterConnectionService waterService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private ICFCApplicationMasterService iCFCApplicationMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		bindModel(httpServletRequest);
		final NewWaterConnectionExecutionFormModel model = getModel();
		final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
		final UserSession session = UserSession.getCurrent();
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		model.setLevelId(lableId);
		final String lableValue = httpServletRequest.getParameter("labelVal");
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		lableDTO.setApplicationId(appId);
		model.setServiceId(serviceId);
		model.setApplicationId(appId);
		setServiceDetail(getModel(), session);
		setApplicantDetails(getModel(), appId, session);
		lableDTO.setUserId(session.getEmployee().getEmpId());
		lableDTO.setOrgId((session.getOrganisation().getOrgid()));
		lableDTO.setLangId((long) session.getLanguageId());
		lableDTO.setLableId(lableId);
		lableDTO.setLableValue(lableValue);
		lableDTO.setLevel(level);
		return super.index();

	}

	private void setServiceDetail(final NewWaterConnectionExecutionFormModel model, final UserSession session) {
		final ServiceMaster serviceMst = serviceMaster.getServiceByShortName("WNC",
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

	private void setApplicantDetails(final NewWaterConnectionExecutionFormModel model, final Long applicationId,
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

	@ResponseBody
	@RequestMapping(params = "saveNewWater", method = RequestMethod.POST)
	public String saveNewWater(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		NewWaterConnectionExecutionFormModel model = getModel();

		if (model.saveForm()) {

		}
		return "Connection No.= "+ " "+model.getCsmrInfo().getCsCcn();

	}

}
