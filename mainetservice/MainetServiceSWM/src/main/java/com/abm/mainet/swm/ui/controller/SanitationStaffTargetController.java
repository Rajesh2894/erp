package com.abm.mainet.swm.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.ISanitationStaffTargetService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.SanitationStaffTargetModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleTarget.html")
public class SanitationStaffTargetController extends AbstractFormController<SanitationStaffTargetModel> {

    /**
     * The ISanitation Staff Target Service
     */
    @Autowired
    private ISanitationStaffTargetService sanitationStaffTargetService;

    /**
     * The IBeatMaster Service
     */
    @Autowired
    private IBeatMasterService routeMasterService;

    /**
     * The IVehicle Master Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("VehicleTarget.html");
        this.getModel().setSanitationStaffTargetDets(sanitationStaffTargetService.search(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        Map<Long, String> routeMap = this.getModel().getRoutelist().stream()
                .collect(Collectors.toMap(BeatMasterDTO::getBeatId, BeatMasterDTO::getRoNoandRoName));
        this.getModel().getSanitationStaffTargetDets().forEach(targt -> {
            targt.setTotal(0L);
            targt.getSanitationStaffTargetDet().forEach(details -> {
                targt.setTotal(targt.getTotal() + details.getSandVolume());
                details.setVehNumber(veheicleMap.get(details.getVehicleId()));
                details.setRouteName(routeMap.get(details.getRoId()));
                details.setVolume(new BigDecimal(details.getSandVolume().toString()).setScale(3, RoundingMode.HALF_EVEN));
            });
        });
        return index();
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(method = {
            RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.ADD_SANITATION_STAFF_TARGET)
    public ModelAndView addSanitationStaffTarget(final HttpServletRequest request) {
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        return new ModelAndView(MainetConstants.SolidWasteManagement.SANITATION_STAFF_TARGET_FORM,
                MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.EDIT_SANITATION_STAFF_TARGET)
    public ModelAndView editSanitationStaffTarget(@RequestParam Long id, final HttpServletRequest request) {
        this.getModel().setSanitationStaffTargetDET(sanitationStaffTargetService.getchildById(id));
        this.getModel().setSanitationStaffTargetDto(
                sanitationStaffTargetService.getById(this.getModel().getSanitationStaffTargetDET().getSanId()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
        return new ModelAndView(MainetConstants.SolidWasteManagement.SANITATION_STAFF_TARGET_FORM,
                MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = {
            RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.DELETE_SANITATION_STAFF_TARGET)
    public ModelAndView viewSanitationStaffTarget(@RequestParam Long id, final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
        this.getModel().setSanitationStaffTargetDET(sanitationStaffTargetService.getchildById(id));
        this.getModel().setSanitationStaffTargetDto(
                sanitationStaffTargetService.getById(this.getModel().getSanitationStaffTargetDET().getSanId()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.SolidWasteManagement.SANITATION_STAFF_TARGET_FORM,
                MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param fromdate
     * @param todate
     * @param request
     * @return
     */
    @RequestMapping(method = {
            RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.SEARCH_SANITATION_STAFF_TARGET)
    public ModelAndView searchSanitationStaffTarget(@RequestParam(required = false) Date fromdate,
            @RequestParam(required = false) Date todate, final HttpServletRequest request) {
        this.getModel().setCommonHelpDocs("VehicleTarget.html");
        this.getModel().setSanitationStaffTargetDets(sanitationStaffTargetService.search(null, fromdate, todate,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().getSanitationStaffTargetDto().setSanTgfromdt(fromdate);
        this.getModel().getSanitationStaffTargetDto().setSanTgtodt(todate);
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        Map<Long, String> routeMap = this.getModel().getRoutelist().stream()
                .collect(Collectors.toMap(BeatMasterDTO::getBeatId, BeatMasterDTO::getRoNoandRoName));
        this.getModel().getSanitationStaffTargetDets().forEach(targt -> {
            targt.setTotal(0L);
            targt.getSanitationStaffTargetDet().forEach(details -> {
                targt.setTotal(targt.getTotal() + details.getSandVolume());
                details.setVehNumber(veheicleMap.get(details.getVehicleId()));
                details.setRouteName(routeMap.get(details.getRoId()));
                details.setVolume(new BigDecimal(details.getSandVolume().toString()).setScale(3, RoundingMode.HALF_EVEN));
            });
        });
        return new ModelAndView(MainetConstants.SolidWasteManagement.SEARCH_SANITATION_STAFF_TARGET,
                MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "PrintSanitationStaffTarget")
    public ModelAndView printSanitationStaffTarget(@RequestParam Long id, final HttpServletRequest request) {
        this.getModel().setSanitationStaffTargetDto(sanitationStaffTargetService.getById(id));
        this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> routeMap = this.getModel().getRoutelist().stream()
                .collect(Collectors.toMap(BeatMasterDTO::getBeatId, BeatMasterDTO::getRoNoandRoName));
        this.getModel().getSanitationStaffTargetDto().getSanitationStaffTargetDet().forEach(targt -> {
            targt.setRouteName(routeMap.get(targt.getRoId()));
        });
        this.getModel().setSaveMode("P");
        return new ModelAndView("SanitationStaffTargetPrint", MainetConstants.FORM_NAME, this.getModel());
    }

}
