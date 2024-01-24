package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.SweepingModel;

@Controller
@RequestMapping("SweepingReport.html")
public class SweepingController extends AbstractFormController<SweepingModel> {

    @Autowired
    private IVehicleScheduleService iVehicleScheduleService;

    @Autowired
    private IBeatMasterService routeMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("SweepingReport.html");
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "sweeping", method = RequestMethod.POST)
    public ModelAndView sweepingReportData(final HttpServletRequest request, @RequestParam("beatId") Long beatId,
            @RequestParam("monthNo") Long monthNo, @RequestParam("beatName") String beatName,
            @RequestParam("monthName") String monthName, @RequestParam("wasteTypeId") String wasteTypeId) {
        sessionCleanup(request);
        SweepingModel vehicleLogPage = this.getModel();
        VehicleScheduleDTO sweepingDetails = iVehicleScheduleService
                .findSweepingDetails(UserSession.getCurrent().getOrganisation().getOrgid(), beatId, monthNo, wasteTypeId);
        if (sweepingDetails != null) {
            vehicleLogPage.setVehicleScheduleDTO(sweepingDetails);
            vehicleLogPage.getVehicleScheduleDTO().setBeatName(beatName);
            vehicleLogPage.getVehicleScheduleDTO().setMonthName(monthName);
            if (wasteTypeId.equalsIgnoreCase("SS")) {
                vehicleLogPage.getVehicleScheduleDTO().setCollectionCode(wasteTypeId);
                vehicleLogPage.getVehicleScheduleDTO().setColleType("Street Sweeping");
            } else if (wasteTypeId.equalsIgnoreCase("DC")) {
                vehicleLogPage.getVehicleScheduleDTO().setCollectionCode(wasteTypeId);
                vehicleLogPage.getVehicleScheduleDTO().setColleType("Drainage Cleaning");
            }
        }
        return new ModelAndView("sweepingReportData", MainetConstants.FORM_NAME, vehicleLogPage);
    }
}
