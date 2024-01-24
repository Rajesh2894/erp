package com.abm.mainet.mrm.ui.controller;

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

import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.service.IAppointmentService;
import com.abm.mainet.mrm.ui.model.AppointmentModel;

@Controller
@RequestMapping("AppointmentReschedule.html")
public class AppointmentRescheduleController extends AbstractFormController<AppointmentModel> {

    @Autowired
    private IAppointmentService appointmentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);

        /*
         * List<AppointmentDTO> appointmentList = appointmentService.searchAppointments(Utility.getStartOfDay(new Date()),
         * UserSession.getCurrent().getOrganisation().getOrgid()); this.getModel().setAppointmentList(appointmentList);
         */
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchAppointments", method = RequestMethod.POST)
    public List<AppointmentDTO> searchAppointments(@RequestParam("appointmentDate") final Date appointmentDate,
            final HttpServletRequest request) {
        getModel().bind(request);
        return appointmentService.searchAppointments(appointmentDate,
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    @RequestMapping(params = "saveAppointmentResc", method = RequestMethod.POST)
    public ModelAndView saveAppointmentResc(HttpServletRequest request) {
        JsonViewObject responseObj = null;
        this.getModel().bind(request);

        AppointmentModel model = this.getModel();
        MarriageDTO marriageDTO = model.getMarriageDTO();
        AppointmentDTO appointmentDTO = marriageDTO.getAppointmentDTO();
        appointmentDTO.setMarId(marriageDTO);
        appointmentDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        appointmentDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        appointmentDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

        responseObj = JsonViewObject.successResult(model.updateAppointmentResc(marriageDTO));
        return jsonResult(responseObj);

    }

}
