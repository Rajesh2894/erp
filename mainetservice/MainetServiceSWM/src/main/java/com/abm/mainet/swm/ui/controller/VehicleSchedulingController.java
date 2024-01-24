package com.abm.mainet.swm.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.CalanderDTO;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.ISLRMEmployeeMasterService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.model.VehicleSchedulingModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/CollectionScheduling.html")
public class VehicleSchedulingController extends AbstractFormController<VehicleSchedulingModel> {

    /**
     * IVehicleSchedule Service
     */
    @Autowired
    private IVehicleScheduleService vehicleScheduleservice;

    /**
     * IBeatMaster Service
     */
    @Autowired
    private IBeatMasterService routeMasterService;

    /**
     * IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * ISLRMEmployeeMasterService Service
     */
    @Autowired
    private ISLRMEmployeeMasterService employeeService;

    /**
     * index It will return default Home Page of Vehicle Sheduling
     * @param httpServletRequest
     * @return ModelAndView
     */

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("CollectionScheduling.html");
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(null,
                null, UserSession.getCurrent().getOrganisation().getOrgid()));
        setVeheicleDetails();
        return defaultResult();
    }

    private void setVeheicleDetails() {
        Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        this.getModel().getVehicleScheduleDtos().forEach(schedule -> {
            schedule.setVeDesc(CommonMasterUtility.getCPDDescription(schedule.getVeVetype(), MainetConstants.BLANK));
            schedule.setVeRegnNo(veheicleMap.get(schedule.getVeId()));
        });
    }

    /**
     * add Vehicle Scheduling
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.SolidWasteManagement.ADD_VEHICLESCHEDULING)
    public ModelAndView addVehicleScheduling(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.CREATE);

        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));

        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT", UserSession.getCurrent().getOrganisation()));
        this.getModel()
                .setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.SolidWasteManagement.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    public String timeToStringConvert(Date date) {
        String dateString = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        dateString = sdf.format(date);
        return dateString;
    }

    /**
     * view Vehicle Scheduling
     * @param mode
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.VIEW_VEHICLESCHEDULING)
    public ModelAndView viewVehicleScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.VIEW);
        this.getModel().setVehicleScheduleDto(vehicleScheduleservice.getVehicleScheduleByVehicleScheduleId(id));
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleScheduleDto(vehicleScheduleservice.getVehicleScheduleByVehicleScheduleId(id));
        this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT", UserSession.getCurrent().getOrganisation()));
        this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().forEach(schDet -> {
            List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
            schDet.setStartime(timeToStringConvert(schDet.getVesStartime()));
            schDet.setEndtime(timeToStringConvert(schDet.getVesEndtime()));
            schDet.setSheduleDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) schDet.getVeScheduledate()));
            if (schDet.getEmpId() != null) {
                String[] str = schDet.getEmpId().split(",");
                for (int i = 0; i < str.length; i++) {
                    employeeList.add(employeeService.searchEmployeeDetails(Long.parseLong(str[i]),
                            UserSession.getCurrent().getOrganisation().getOrgid()));
                }
            }
            schDet.setEmployeeList(employeeList);
        });
        return new ModelAndView(MainetConstants.SolidWasteManagement.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * edit Vehicle Scheduling
     * @param mode
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.EDIT_VEHICLESCHEDULING)
    public ModelAndView editVehicleScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) Long id,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.EDIT);
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleScheduleDto(vehicleScheduleservice.getVehicleScheduleByVehicleScheduleId(id));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(
                this.getModel().getVehicleScheduleDto().getVeVetype(), null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT", UserSession.getCurrent().getOrganisation()));
        this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().forEach(schDet -> {
            List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
            schDet.setStartime(timeToStringConvert(schDet.getVesStartime()));
            schDet.setEndtime(timeToStringConvert(schDet.getVesEndtime()));
            schDet.setSheduleDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) schDet.getVeScheduledate()));
            if (schDet.getEmpId() != null) {
                String[] str = schDet.getEmpId().split(",");
                for (int i = 0; i < str.length; i++) {
                    employeeList.add(employeeService.searchEmployeeDetails(Long.parseLong(str[i]),
                            UserSession.getCurrent().getOrganisation().getOrgid()));
                }
            }
            schDet.setEmployeeList(employeeList);
        });

        return new ModelAndView(MainetConstants.SolidWasteManagement.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * delete Vehicle Scheduling
     * @param vesId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.DELETE_VEHICLESCHEDULING)
    public ModelAndView deleteVehicleScheduling(@RequestParam("vesId") Long vesId,
            final HttpServletRequest httpServletRequest) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleScheduleservice.deleteVehicleSchedule(vesId, emp.getEmpId(), emp.getEmppiservername());
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(null,
                null, UserSession.getCurrent().getOrganisation().getOrgid()));
        setVeheicleDetails();
        return new ModelAndView(MainetConstants.SolidWasteManagement.VEHICLESCHEDULING_SEARCH, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * search Vehicle Scheduling
     * @param veId
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.SEARCH_VEHICLESCHEDULING)
    public ModelAndView searchVehicleScheduling(@RequestParam(value = "veId") final Long veId) {
        if (veId != null) {
            this.getModel().getVehicleScheduleDto().setVeRegnNo(veId.toString());
        }
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.SEARCH);
        this.getModel()
                .setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(null,
                        veId, UserSession.getCurrent().getOrganisation().getOrgid()));
        setVeheicleDetails();
        return new ModelAndView(MainetConstants.SolidWasteManagement.VEHICLESCHEDULING_GRID, MainetConstants.FORM_NAME,
                this.getModel());
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

    /**
     * empScheduleLoad
     * @param httpServletRequest
     * @param veVetype
     * @param veid
     * @return
     */
    @RequestMapping(params = "getCalData", method = RequestMethod.POST)
    public @ResponseBody List<CalanderDTO> empScheduleLoad(final HttpServletRequest httpServletRequest,
            @RequestParam(required = false) Long veVetype, @RequestParam(required = false) Long veid) {
        // sessionCleanup(httpServletRequest);
        this.getModel().setBeatMasterList(
                routeMasterService.serchRoute(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> beatMaster = this.getModel().getBeatMasterList().stream()
                .collect(Collectors.toMap(BeatMasterDTO::getBeatId, BeatMasterDTO::getBeatNo));
        Map<Long, String> beatMaster1 = this.getModel().getBeatMasterList().stream()
                .collect(Collectors.toMap(BeatMasterDTO::getBeatId, BeatMasterDTO::getBeatName));
        List<VehicleScheduleDTO> vehicleList = vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(veVetype,
                veid, UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        this.getModel().getVehicleScheduleDtos().forEach(schedule -> {
            schedule.setVeDesc(CommonMasterUtility.getCPDDescription(schedule.getVeVetype(), MainetConstants.BLANK));
            schedule.setVeRegnNo(veheicleMap.get(schedule.getVeId()));
        });
        List<CalanderDTO> calanderList = new ArrayList<>();
        VehicleScheduleDTO vehicleDet = new VehicleScheduleDTO();
        List<VehicleScheduleDTO> vehicleDetList = new ArrayList<>();
        for (VehicleScheduleDTO veclist : vehicleList) {
            vehicleDet.setVeId(veclist.getVeId());
            vehicleDet.setVesFromdt(veclist.getVesFromdt());
            vehicleDet.setVesTodt(veclist.getVesTodt());
            veclist.getTbSwVehicleScheddets().forEach(d -> {
                vehicleDet.setVeScheduledate(d.getVeScheduledate());
                vehicleDet.setBeatNo(beatMaster.get(d.getBeatId()));
                vehicleDet.setBeatName(beatMaster1.get(d.getBeatId()));
                vehicleDet.setBeatNoAndbeatName(vehicleDet.getBeatNo() + " " + vehicleDet.getBeatName());
                vehicleDet.setVehStartTime(d.getVesStartime());
                String startTime = timeToDateConvert(d.getVesStartime());
                String endTime = timeToDateConvert(d.getVesEndtime());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:a");
                Date sartDate = null;
                Date endDate = null;
                try {
                    sartDate = dateFormat.parse(vehicleDet.getVeScheduledate() + "T" + startTime);
                    endDate = dateFormat.parse(vehicleDet.getVeScheduledate() + "T" + endTime);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                CalanderDTO calanderDTO = new CalanderDTO(vehicleDet.getVeId(), sartDate,
                        vehicleDet.getBeatNoAndbeatName(), "bg-black",
                        endDate, "");
                vehicleDetList.add(vehicleDet);
                calanderList.add(calanderDTO);
            });

        }
        return calanderList;
    }

    public String timeToDateConvert(Date date) {
        String dateString = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm:a");
        dateString = sdf.format(date);
        return dateString;
    }

}
