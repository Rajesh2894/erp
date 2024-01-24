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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.service.IVehicleMaintenanceMasterService;
import com.abm.mainet.swm.ui.model.VehicleMaintenanceMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleMaintenanceMaster.html")
public class VehicleMaintenanceMasterController extends AbstractFormController<VehicleMaintenanceMasterModel> {

    /**
     * IVehicleMaintenanceMaster Service
     */
    @Autowired
    private IVehicleMaintenanceMasterService vehicleMaintenanceMasterService;

    /**
     * index
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setVehicleMaintenanceList(
                vehicleMaintenanceMasterService.serchVehicleMaintenance(null,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCommonHelpDocs("VehicleMaintenanceMaster.html");
        return index();
    }

    /**
     * addPopulationForm
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView addPopulationForm(final HttpServletRequest request) {
        return new ModelAndView("addVehicleMaintenanceMaster/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * editPopulationForm
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView editPopulationForm(final HttpServletRequest request, @RequestParam Long vemeId) {
        this.getModel().setVehicleMaintenanceMasterDTO(vehicleMaintenanceMasterService.getVehicleMaintenanceMaster(vemeId));
        return new ModelAndView("editVehicleMaintenanceMaster/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * deleteVehicleMaintenanceMaster
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView deleteVehicleMaintenanceMaster(final HttpServletRequest request, @RequestParam Long vemeId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleMaintenanceMasterService.deleteVehicleMaintenanceMaster(vemeId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setVehicleMaintenanceList(
                vehicleMaintenanceMasterService.serchVehicleMaintenance(null,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("searchVehicleMaintenanceMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * searchVehicleMaintenanceMaster
     * @param request
     * @param vehType
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView searchVehicleMaintenanceMaster(final HttpServletRequest request,
            @RequestParam(required = false) Long vehType) {
        sessionCleanup(request);
        this.getModel().getVehicleMaintenanceMasterDTO().setVeVetype(vehType);
        this.getModel().setVehicleMaintenanceList(
                vehicleMaintenanceMasterService.serchVehicleMaintenance(vehType,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("searchVehicleMaintenanceMaster", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * search Vehicle Maintenance Master
     * @param request
     * @param vemeId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewVehicleMaintenanceMaster", method = RequestMethod.POST)
    public ModelAndView viewVehicleMaintenanceMaster(final HttpServletRequest request, @RequestParam Long vemeId) {
        this.getModel().setVehicleMaintenanceMasterDTO(vehicleMaintenanceMasterService.getVehicleMaintenanceMaster(vemeId));
        return new ModelAndView("viewVehicleMaintenanceMaster/Form", MainetConstants.FORM_NAME, this.getModel());

    }

}
