package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbVisitorSchedule;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.TbVisitorScheduleService;
import com.abm.mainet.common.ui.model.InspectionResheduledModel;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/InspectionResheduleController.html")
public class InspectionResheduleController extends AbstractFormController<InspectionResheduledModel> {
	@Autowired
	private TbVisitorScheduleService tbVisitorScheduleService;

	@Autowired
	private ICFCApplicationMasterService iCFCApplicationMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		return defaultResult();
	}

	@ResponseBody
	@RequestMapping(params = "searchAppointments", method = RequestMethod.POST)
	public List<TbVisitorSchedule> searchAppointments(@RequestParam("appointmentDate") final Date appointmentDate,
			final HttpServletRequest request) {
		getModel().bind(request);
		List<TbVisitorSchedule> visListDet=new ArrayList<TbVisitorSchedule>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TbVisitorSchedule> visList = tbVisitorScheduleService.findAllByInspectionDate(appointmentDate,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (TbVisitorSchedule visDto : visList) {
			if (visDto.getVisApplicationId() != null) {
				TbCfcApplicationMstEntity cfcEnt = iCFCApplicationMasterService
						.getCFCApplicationByApplicationId(visDto.getVisApplicationId(), orgId);

				if (cfcEnt != null) {
					visDto.setVisWing(cfcEnt.getApmFname());
				}
				visListDet.add(visDto);
			}
		}
		return visListDet;

	}
	
    @RequestMapping(params = "saveAppointmentResc", method = RequestMethod.POST)
    public ModelAndView saveAppointmentResc(HttpServletRequest request) {
        JsonViewObject responseObj = null;
        this.getModel().bind(request);


        responseObj = JsonViewObject.successResult( this.getModel().saveOrUpdate());
        return jsonResult(responseObj);

    }
}
