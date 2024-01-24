package com.abm.mainet.swm.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.VehicleScheduleReportModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleScheduleReport.html")
public class VehicleScheduleReportController extends AbstractFormController<VehicleScheduleReportModel> {
    /**
     * IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * IVehicleSchedule Service
     */
    @Autowired
    private IVehicleScheduleService vehicleScheduleService;

    /**
     * index
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        loadVehicle();
        setVehicleDetails();
        this.getModel().setCommonHelpDocs("VehicleScheduleReport.html");
        return index();
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
     * Vehicle Schedule Report Summary
     * @param request
     * @param veVetype
     * @param veNo
     * @param fromdate
     * @param todate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView VehicleScheduleReportSummary(final HttpServletRequest request, @RequestParam Long veVetype,
            @RequestParam String veNo,
            @RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) {
        sessionCleanup(request);
        VehicleScheduleReportModel vsrm = this.getModel();
        String redirectType = null;
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long veId = veVetype;
        vsrm.getVehicleMasterDTO().setFromDate(fromdate);
        vsrm.getVehicleMasterDTO().setToDate(todate);
        vsrm.getVehicleMasterDTO().setVeId(veVetype);
        VehicleScheduleDTO vehicledetList = vehicleScheduleService.findVehicleScheduleDetails(OrgId, veId, Long.valueOf(veNo),
                Utility.stringToDate(fromdate), Utility.stringToDate(todate));
        if (vehicledetList != null) {
            vsrm.getVehicleMasterDTO().setFlagMsg("Y");
            if (vehicledetList.getVehicleScheduleList() != null && !vehicledetList.getVehicleScheduleList().isEmpty()) {
                vsrm.setVehicleScheduledto(vehicledetList);
                if (veVetype != 0) {
                    for (VehicleScheduleDTO det : this.getModel().getVehicleScheduledto().getVehicleScheduleList()) {
                        vsrm.getVehicleMasterDTO().setVname(det.getVeDesc());
                        vsrm.getVehicleMasterDTO().setVeNo(det.getVeRegnNo());
                        break;
                    }
                }
            }
            redirectType = "VehicleScheduleReportSummary";
        } else {
            vsrm.getVehicleMasterDTO().setFlagMsg("N");
            loadVehicle();
            setVehicleDetails();
            redirectType = "VehicleScheduleReportList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, vsrm);
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
