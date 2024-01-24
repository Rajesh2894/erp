package com.abm.mainet.swm.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.PumpMasterDTO;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IPumpMasterService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.ExpenditureIncurredOnTransportationModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/ExpenditureIncurredOnTransportation.html")
public class ExpenditureIncurredOnTransportationController
        extends AbstractFormController<ExpenditureIncurredOnTransportationModel> {

    /**
     * The IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * The IVehicleSchedule Service
     */
    @Autowired
    private IVehicleScheduleService vehicleScheduleService;

    /**
     * The IPumpMaster Service
     */
    @Autowired
    private IPumpMasterService pumpMasterService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("ExpenditureIncurredOnTransportation.html");
        loadVehicle();
        setVehicleDetails();
        loadPumpname();
        setpumpName();
        return index();
    }

    private void setpumpName() {
        // TODO Auto-generated method stub
        Map<Long, String> pumpMap = this.getModel().getPumpMasterList().stream()
                .collect(Collectors.toMap(PumpMasterDTO::getPuId, PumpMasterDTO::getPuPumpname));
        if (CollectionUtils.isNotEmpty(this.getModel().getPumpMasterList())) {
            this.getModel().getPumpMasterList().forEach(master -> {
                master.setPuPumpname(pumpMap.get(master.getPuId()));
            });
        }
    }

    private void loadPumpname() {
        this.getModel().setPumpMasterList(pumpMasterService.serchPumpMasterByPumpType(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    private void setVehicleDetails() {
        Map<Long, String> locationMap = this.getModel().getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        this.getModel().getVehicleMasterList().forEach(master -> {
            master.setVeNo(locationMap.get(master.getVeId()));
        });
    }

    public void loadVehicle() {
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * fuel Expenditure
     * @param request
     * @param veVetype
     * @param veNo
     * @param fromdate
     * @param todate
     * @param pumpId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "Fueling", method = RequestMethod.POST)
    public ModelAndView fuelExpenditure(final HttpServletRequest request, @RequestParam Long veVetype,
            @RequestParam String veNo,
            @RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate,
            @RequestParam("pumpId") Long pumpId) {
        sessionCleanup(request);
        String redirectType = null;
        ExpenditureIncurredOnTransportationModel expInOccuredModel = this.getModel();
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        expInOccuredModel.getVehicleMasterDTO().setFromDate(fromdate);
        expInOccuredModel.getVehicleMasterDTO().setToDate(todate);
        expInOccuredModel.getVehicleMasterDTO().setExpenseType("Vehicle Fuel");
        VehicleFuellingDTO vehicleFuellingDTO = vehicleScheduleService.findFuelExpenditureDetails(OrgId, veVetype,
                Long.valueOf(veNo),
                Utility.stringToDate(fromdate), Utility.stringToDate(todate), pumpId);
        if (vehicleFuellingDTO != null) {
            expInOccuredModel.setVehicleFuellingdto(vehicleFuellingDTO);
            expInOccuredModel.getVehicleMasterDTO().setFlagMsg("Y");
            redirectType = "fuelExpenditureIncurred";
        } else {
            expInOccuredModel.setVehicleFuellingdto(vehicleFuellingDTO);
            expInOccuredModel.getVehicleMasterDTO().setFlagMsg("N");
            redirectType = "ExpenditureIncurredOnTransportationList";
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
        	this.getModel().setIsPSCLEnv(MainetConstants.Y_FLAG);
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, expInOccuredModel);
    }

    /**
     * maintenance Expenditure
     * @param request
     * @param veVetype
     * @param veNo
     * @param fromdate
     * @param todate
     * @param vemMetype
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "Maintenance", method = RequestMethod.POST)
    public ModelAndView maintenanceExpenditure(final HttpServletRequest request, @RequestParam Long veVetype,
            @RequestParam String veNo,
            @RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate,
            @RequestParam("vemMetype") Long vemMetype) {
        sessionCleanup(request);
        String redirectType = null;
        ExpenditureIncurredOnTransportationModel expInOccuredModel = this.getModel();
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long veId = veVetype;
        expInOccuredModel.getVehicleMasterDTO().setFromDate(fromdate);
        expInOccuredModel.getVehicleMasterDTO().setToDate(todate);
        expInOccuredModel.getVehicleMasterDTO().setExpenseType("Vehicle Maintenance");
        VehicleMaintenanceDTO vehicleMaintenanceDTO = vehicleScheduleService.findMaintenanceExpDetails(OrgId, veId,
                Long.valueOf(veNo), Utility.stringToDate(fromdate), Utility.stringToDate(todate), vemMetype);
        if (vehicleMaintenanceDTO != null) {
            expInOccuredModel.setVehicleMaintenanceDTO(vehicleMaintenanceDTO);
            expInOccuredModel.getVehicleMasterDTO().setFlagMsg("Y");
            redirectType = "maintenanceExpenditureIncurred";
        } else {
            expInOccuredModel.setVehicleMaintenanceDTO(vehicleMaintenanceDTO);
            expInOccuredModel.getVehicleMasterDTO().setFlagMsg("N");
            redirectType = "ExpenditureIncurredOnTransportationList";
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
        	this.getModel().setIsPSCLEnv(MainetConstants.Y_FLAG);
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, expInOccuredModel);
    }

    /**
     * serchVehicleNo
     * @param vehicleTypeId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
            final HttpServletRequest httpServletRequest) {
        List<VehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
        if (result != null && !result.isEmpty()) {
            result.forEach(vdata -> {
                data.put(vdata.getVeId(), vdata.getVeNo());
            });

        }
        return data;
    }

}
