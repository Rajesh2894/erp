package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.service.LocationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.service.IRouteInfoService;
import com.abm.mainet.swm.ui.model.RouteModel;

@Controller
@RequestMapping("/RouteInfo.html")
public class RouteController extends AbstractFormController<RouteModel> {

    @Autowired
    IRouteInfoService routeInfoService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        return defaultResult();
    }

    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public List<Object> getMapData() {
        List<Object> data = new ArrayList<Object>();
        Map<String, List<String>> routeDetailsMap = new HashMap<>();
        Map<Long, String> locationMap = locationService
                .getLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid(),
                        UserSession.getCurrent().getLanguageId())
                .stream().collect(Collectors.toMap(LocationDTO::getLocId, LocationDTO::getLatLong));

        int keyval = 1;
        List<BeatMasterDTO> routeMstr = routeInfoService
                .getAllRoute(UserSession.getCurrent().getOrganisation().getOrgid());
        if (routeMstr != null && !routeMstr.isEmpty()) {
            for (BeatMasterDTO mstr : routeMstr) {
                ArrayList<String> routeNodeList = new ArrayList<String>();
                if (mstr.getBeatStartPoint() != null && mstr.getBeatEndPoint() != null) {
                    routeNodeList.add(locationMap.get(mstr.getBeatStartPoint()));
                    routeNodeList.add(locationMap.get(mstr.getBeatEndPoint()));
                    routeDetailsMap.put(String.valueOf(keyval), routeNodeList);
                    keyval++;
                }
            }

        }
        data.add(routeDetailsMap);
        return data;
    }

    @RequestMapping(params = "getvehicleData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public List<Object[]> getVehicleData() {
        List<Object[]> vehicle = routeInfoService.searchVehicle(UserSession.getCurrent().getOrganisation().getOrgid());
        return vehicle;
    }

}
