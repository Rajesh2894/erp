package com.abm.mainet.swm.ui.controller;

import java.util.Date;

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
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.VehicleDeployementPlanReportModel;

@Controller
@RequestMapping("/VehicleDeploymentPlanReport.html")
public class VehicleDeployementPlanReportController extends AbstractFormController<VehicleDeployementPlanReportModel> {

    @Autowired
    private IVehicleScheduleService iVehicleScheduleService;
    
    @Autowired
    private IVehicleMasterService vehicleMasterService;


    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest); 
        this.getModel().setVehicleMasterList(
                vehicleMasterService.searchVehicle(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCommonHelpDocs("VehicleDeploymentPlanReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "VDP", method = RequestMethod.POST)
    public ModelAndView vehicledeploymentplan(final HttpServletRequest request,@RequestParam("veId") Long veId,@RequestParam("date") Date date) {
        sessionCleanup(request);
        String redirectType = null;
        VehicleDeployementPlanReportModel Srm = this.getModel();
        VehicleScheduleDTO vehiclesch = iVehicleScheduleService.findVehicleSchdetailsByVehNo(UserSession.getCurrent().getOrganisation().getOrgid(), veId, date);
        if (vehiclesch != null) {
            this.getModel().setVehicleScheduleDTO(vehiclesch);
            Srm.getVehicleScheduleDTO().setFlagMsg("Y");
            Srm.getVehicleScheduleDTO().setVeScheduledate(date);
            this.getModel().setEmpUIdList(this.getModel().getVehicleScheduleDTO().getEmployeeUIdList().toArray());
            redirectType = "vehicledeploymentplanReport";
        } else {
            Srm.getVehicleScheduleDTO().setFlagMsg("N");
            redirectType = "VehicleDeployementPlanList";
        }
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicle(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
      
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, Srm);
    }

}
