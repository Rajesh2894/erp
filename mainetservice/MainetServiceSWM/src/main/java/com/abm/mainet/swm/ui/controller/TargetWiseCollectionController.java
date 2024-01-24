package com.abm.mainet.swm.ui.controller;

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
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.ISanitationStaffTargetService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.TargetWiseCollectionModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/TargetWiseCollection.html")
public class TargetWiseCollectionController extends AbstractFormController<TargetWiseCollectionModel> {
    /**
     * The IVehicle Master Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * The ISanitationStaffTarget Service
     */
    @Autowired
    private ISanitationStaffTargetService iSanitationStaffTargetService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("TargetWiseCollection.html");
        loadVehicle();
        setVehicleDetails();
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
     * @param request
     * @param veId
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView dayWiseDumpingSummary(final HttpServletRequest request, @RequestParam Long veId,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String redirectType = null;
        TargetWiseCollectionModel targetWiseCollectionModel = this.getModel();
        SanitationStaffTargetDTO vehicleTargetDTO = new SanitationStaffTargetDTO();
        vehicleTargetDTO = iSanitationStaffTargetService.findVehicleTargetDatils(OrgId, veId, Utility.stringToDate(fromDate),
                Utility.stringToDate(toDate));
        if (vehicleTargetDTO != null) {
            vehicleTargetDTO.setFromDate(fromDate);
            vehicleTargetDTO.setToDate(toDate);
            targetWiseCollectionModel.getVehicleTargetDTO().setFlagMsg("Y");
            targetWiseCollectionModel.setVehicleTargetDTO(vehicleTargetDTO);
            redirectType = "TargetWiseCollectionSummary";
        } else {
            targetWiseCollectionModel.getVehicleTargetDTO().setFlagMsg("N");
            targetWiseCollectionModel.setVehicleTargetDTO(vehicleTargetDTO);
            redirectType = "TargetWiseCollectionLists";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, targetWiseCollectionModel);

    }
}
