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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.BeatDetailDto;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDetDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.BeatMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/BeatMaster.html")
public class BeatMasterController extends AbstractFormController<BeatMasterModel> {

    @Autowired
    private IBeatMasterService routeMasterService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IMRFMasterService mRFMasterService;

    @Autowired
    private IVehicleMasterService VehicleMasterService;

    @Autowired
    private IVehicleScheduleService VehicleScheduleService;

    @Autowired
    IFileUploadService fileUpload;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        loadDefaultData(httpServletRequest);
        setSummaryPageData(httpServletRequest);
        this.getModel().getBeatMasterList();
        this.getModel().setCommonHelpDocs("BeatMaster.html");
        return index();
    }

    /**
     * @param httpServletRequest
     */
    private void setSummaryPageData(final HttpServletRequest httpServletRequest) {
        loadLocationData(httpServletRequest);
        loadDisposalSiteName(httpServletRequest);
        Map<Long, String> disposalMap = this.getModel().getMrfMasterList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        this.getModel().getBeatMasterList().forEach(master -> {
            master.setDeName(disposalMap.get(master.getMrfId()));
        });
        this.getModel().getBeatMasterList();

        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocNameEng));
        Map<Long, String> locationMap2 = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocNameReg));
        
        this.getModel().getBeatMasterList().forEach(master -> {
            master.setBeatEndPointName(locationMap.get(master.getBeatEndPoint()));
            master.setBeatStartPointName(locationMap.get(master.getBeatStartPoint()));
            master.setBeatEndPointNameReg(locationMap2.get(master.getBeatEndPoint()));
            master.setBeatStartPointNameReg(locationMap2.get(master.getBeatStartPoint()));
            
        });
    }

    /**
     * @param httpServletRequest
     */
    private void loadDefaultData(final HttpServletRequest httpServletRequest) {
        this.getModel().setBeatMasterList(
                routeMasterService.serchRoute(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * @param httpServletRequest
     */
    private void loadLocationData(final HttpServletRequest httpServletRequest) {
        this.getModel().setLocList(iLocationMasService
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * @param httpServletRequest
     */
    private void loadDisposalSiteName(final HttpServletRequest httpServletRequest) {
        this.getModel().setMrfMasterList(
                mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * add Route Master
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "addRouteMaster", method = RequestMethod.POST)
    public ModelAndView addRouteMaster(final HttpServletRequest request) {
        loadLocationData(request);
        loadDisposalSiteName(request);
        return new ModelAndView("addRouteMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * edit Route Master
     * @param request
     * 
     * 
     * @param roId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editRouteMaster", method = RequestMethod.POST)
    public ModelAndView editRouteMaster(final HttpServletRequest request, @RequestParam Long roId) {
        sessionCleanup(request);
        loadLocationData(request);
        loadDisposalSiteName(request);
        this.getModel().setBeatMasterDTO(routeMasterService.getRouteByRouteId(roId));
        
        BeatDetailDto beatDetailDto = null;
        List<BeatDetailDto> beatDetailList = new ArrayList<>();
        for (BeatDetailDto det : this.getModel().getBeatMasterDTO().getTbSwBeatDetail()) {
            beatDetailDto = new BeatDetailDto();
            beatDetailDto.setBeatAreaType(det.getBeatAreaType());
            beatDetailDto.setBeatAreaName(det.getBeatAreaName());
            beatDetailDto.setBeatHouseHold(det.getBeatHouseHold());
            beatDetailDto.setBeatShop(det.getBeatShop());
            beatDetailList.add(beatDetailDto);
        }
        this.getModel().setBeatDetailListDto(beatDetailList);
        
        this.getModel().setSaveMode("RouteMaster");

        return new ModelAndView("editRouteMaster/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * viewt Route Master
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewRouteMaster", method = RequestMethod.POST)
    public ModelAndView viewtRouteMaster(final HttpServletRequest request, @RequestParam Long roId) {
        sessionCleanup(request);
        loadLocationData(request);
        loadDisposalSiteName(request);
        this.getModel().setBeatMasterDTO(routeMasterService.getRouteByRouteId(roId));
        BeatDetailDto beatDetailDto = null;
        List<BeatDetailDto> beatDetailList = new ArrayList<>();
        for (BeatDetailDto det : this.getModel().getBeatMasterDTO().getTbSwBeatDetail()) {
            beatDetailDto = new BeatDetailDto();
            beatDetailDto.setBeatAreaType(det.getBeatAreaType());
            beatDetailDto.setBeatAreaName(det.getBeatAreaName());
            beatDetailDto.setBeatHouseHold(det.getBeatHouseHold());
            beatDetailDto.setBeatShop(det.getBeatShop());
            beatDetailList.add(beatDetailDto);
        }
        this.getModel().setBeatDetailListDto(beatDetailList);
        this.getModel().setSaveMode("RouteMaster");
        return new ModelAndView("viewRouteMaster/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * delete Route Master
     * @param request
     * @param roId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteRouteMaster", method = RequestMethod.POST)
    public ModelAndView deleteRouteMaster(final HttpServletRequest request, @RequestParam Long roId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        routeMasterService.deleteRoute(roId, emp.getEmpId(), emp.getEmppiservername());
        loadDefaultData(request);
        return new ModelAndView("RouteMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * search Route Master
     * @param request
     * @param routeName
     * @param routeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchRouteMaster", method = RequestMethod.POST)
    public ModelAndView searchRouteMaster(final HttpServletRequest request,
            @RequestParam(required = false) String routeName, @RequestParam(required = false) String routeNo) {
        sessionCleanup(request);
        this.getModel().getBeatMasterDTO().setBeatName(routeName);
        this.getModel().getBeatMasterDTO().setBeatNo(routeNo);
        this.getModel().setBeatMasterList(routeMasterService.serchRoute(routeName, routeNo,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        setSummaryPageData(request);
        return new ModelAndView("RouteMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * getMapData
     * @return
     */
    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public List<Object> getMapData() {
        List<Object> data = new ArrayList<Object>();
        Map<String, List<String>> newData = new HashMap<>();
        List<BeatMasterDTO> routemstr = routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> locationMap = this.getModel().getLocList().stream().filter(tr -> tr.getLatLong() != ",")
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLatLong));
        int i = 1;
        if (routemstr != null && !routemstr.isEmpty()) {
            for (BeatMasterDTO mstr : routemstr) {
                ArrayList<String> nodeList = new ArrayList<String>();
                if (mstr.getBeatStartPoint() != null && mstr.getBeatEndPoint() != null) {
                    nodeList.add(locationMap.get(mstr.getBeatStartPoint()));
                    nodeList.add(locationMap.get(mstr.getBeatEndPoint()));
                    newData.put(String.valueOf(i), nodeList);
                    i++;
                }
            }

        }
        data.add(newData);
        return data;
    }

    /**
     * get Vehicle Data
     * @return
     */
    @RequestMapping(params = "getvehicleData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public List<Object[]> getVehicleData() {
        List<Object[]> position = null;
        List<VehicleScheduleDTO> vehicle = VehicleScheduleService.searchVehicleScheduleByVehicleTypeAndVehicleNo(null,
                null, UserSession.getCurrent().getOrganisation().getOrgid());
        VehicleMasterDTO vehiclemaster;
        if (vehicle != null && !vehicle.isEmpty()) {
            position = new ArrayList<>();
            for (VehicleScheduleDTO mstr : vehicle) {
                vehiclemaster = VehicleMasterService.getVehicleByVehicleId(mstr.getVeId());
                final String[] mapData = new String[] { vehiclemaster.getVeNo(), mstr.getLatitude(),
                        mstr.getLongitude() };
                position.add(mapData);
            }
        }
        return position;
    }
}
