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
import com.abm.mainet.swm.dto.VehicleLogBookMainPageDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.VehicleLogBookMainPageModel;

@Controller
@RequestMapping("VehicleLogBookMainPageReport.html")
public class VehicleLogBookMainPageController extends AbstractFormController<VehicleLogBookMainPageModel> {

    @Autowired
    private ILogBookReportService iLogBookReportService;

    @Autowired
    private IVehicleMasterService vehicleMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("VehicleLogBookMainPageReport.html");
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

    @ResponseBody
    @RequestMapping(params = "vehicleLogBookMainPage", method = RequestMethod.POST)
    public ModelAndView vehiclelogbookmainpage(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo, @RequestParam Long veId, @RequestParam("monthName") String monthName) {
        sessionCleanup(request);
        String redirectType = null;
        VehicleLogBookMainPageModel vehicleLogPage = this.getModel();
        VehicleLogBookMainPageDTO veDetails;
        veDetails = iLogBookReportService
                .getVehicleMainPageByVeIdAndMonth(UserSession.getCurrent().getOrganisation().getOrgid(), veId, monthNo);
        if (veDetails != null) {
            this.getModel().setVehicleLogBookMainPageList(veDetails);
            this.getModel().getVehicleLogBookMainPageList().setStatusFlag("Y");
            vehicleLogPage.getVehicleLogBookMainPageList().setMonthName(monthName);
            redirectType = "vehiclelogbookmainpageReportData";
        } else {
            redirectType = "VehicleLogBookMainPageSummary";
            this.getModel().getVehicleLogBookMainPageList().setStatusFlag("N");
            loadVehicle();
            setVehicleDetails();
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, vehicleLogPage);
    }

}
