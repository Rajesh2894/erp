package com.abm.mainet.swm.ui.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.CalanderDTO;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.IEmployeeScheduleService;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.ISLRMEmployeeMasterService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.SanitaryStaffSchedulingModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/SanitaryStaffScheduling.html")
public class SanitaryStaffSchedulingController extends AbstractFormController<SanitaryStaffSchedulingModel> {

    /**
     * The IEmployee Schedule Service
     */
    @Autowired
    private IEmployeeScheduleService employeeScheduleService;
    /**
     * The IMRFMaster Service
     */
    @Autowired
    private IMRFMasterService imRFMasterService;

    /**
     * The IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * The ILocationMas Service
     */
    @Autowired
    private ILocationMasService iLocationMasService;

    /**
     * The IBeatMaster Service
     */
    @Autowired
    private IBeatMasterService routeMasterService;

    /**
     * The IEmployee Service
     */
    @Autowired
    private ISLRMEmployeeMasterService employeeService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("SanitaryStaffScheduling.html");
        defaultLoad(httpServletRequest);
        return defaultResult();
    }

    private void defaultLoad(final HttpServletRequest httpServletRequest) {
        this.getModel()
                .setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        List<EmployeeScheduleDTO> employeeScheList = employeeScheduleService.search(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        EmployeeScheduleDTO empdet = null;
        List<EmployeeScheduleDTO> emplist = new ArrayList<>();
        for (EmployeeScheduleDTO list1 : employeeScheList) {
            empdet = new EmployeeScheduleDTO();
            empdet.setEmsId(list1.getEmsId());
            empdet.setEmsType(list1.getEmsType());
            empdet.setEstfromdate(new SimpleDateFormat("dd/MM/yyyy").format((Date) list1.getEmsFromdate()));
            empdet.setEstTodate(new SimpleDateFormat("dd/MM/yyyy").format((Date) list1.getEmsTodate()));
            empdet.setEmsReocc(list1.getEmsReocc());
            emplist.add(empdet);
        }
        this.getModel().setEmployeeScheduleList(emplist);
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST,
            RequestMethod.GET }, params = MainetConstants.SolidWasteManagement.ADD_SANITARYSTAFFSCHEDULING)
    public ModelAndView addVehicleScheduling(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.CREATE);
        // CommonMasterUtility.getLookUps("COT", UserSession.getCurrent().getOrganisation())
        this.getModel().setEmployeeScheduleList(employeeScheduleService.search(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.SolidWasteManagement.SANITARYSTAFFSCHEDULING_FORM,
                MainetConstants.FORM_NAME,
                this.getModel());
    }

    public String timeToStringConvert(Date date) {
        String dateString = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        dateString = sdf.format(date);
        return dateString;
    }

    /**
     * @param action
     * @param request
     * @return
     */
    @RequestMapping(params = "getSanitaryStaff", method = RequestMethod.POST)
    public ModelAndView getsant(@RequestParam("actionParam") final String action, final HttpServletRequest request) {
        this.getModel().setSantType(action);
        setSummaryPageData(request);
        this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setEmsdCollTypeList(CommonMasterUtility.getLookUps("TSK", UserSession.getCurrent().getOrganisation()));
        return new ModelAndView("SanitaryDisposaltable", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param httpServletRequest
     */
    private void loadLocationData(final HttpServletRequest httpServletRequest) {
        // sessionCleanup(httpServletRequest);
        this.getModel().setLocList(iLocationMasService
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * @param httpServletRequest
     */
    private void setSummaryPageData(final HttpServletRequest httpServletRequest) {
        loadLocationData(httpServletRequest);
        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocNameEng));
        this.getModel().getRouteMasterList().forEach(master -> {
            master.setBeatEndPointName(locationMap.get(master.getBeatEndPoint()));
        });
    }

    /**
     * @param request
     * @param emsId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editSanitaryStaffScheduling", method = RequestMethod.POST)
    public ModelAndView editSanitaryStaff(final HttpServletRequest request, @RequestParam Long emsId) {
        sessionCleanup(request);
        this.getModel().setEmployeeScheduleDto(employeeScheduleService.getByEmployeeScheduleId(emsId));
        this.getModel().getEmployeeScheduleDto().getTbSwEmployeeScheddets().forEach(sd -> {
            sd.setStartTime(timeToStringConvert(sd.getEmsdStarttime()));
            sd.setEndTime(timeToStringConvert(sd.getEmsdEndtime()));
            sd.setScheduleDate((new SimpleDateFormat("dd/MM/yyyy").format((Date) sd.getEsdScheduledate())));
            this.getModel().getEmployeeScheduleDto().setWeekdays(sd.getEmsdDay());
        });
        String scheduleType = this.getModel().getEmployeeScheduleDto().getEmsType();
        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        this.getModel().setSantType(scheduleType);
        setSummaryPageData(request);
        this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setEmsdCollTypeList(CommonMasterUtility.getLookUps("TSK", UserSession.getCurrent().getOrganisation()));
        return new ModelAndView("SanitaryDisposaldisplay", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param request
     * @param emsId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewSanitaryStaffScheduling", method = RequestMethod.POST)
    public ModelAndView ViewSanitaryStaff(final HttpServletRequest request, @RequestParam Long emsId) {
        sessionCleanup(request);
        this.getModel().setEmployeeScheduleDto(employeeScheduleService.getByEmployeeScheduleId(emsId));
        this.getModel().getEmployeeScheduleDto().getTbSwEmployeeScheddets().forEach(sd -> {
            sd.setStartTime(timeToStringConvert(sd.getEmsdStarttime()));
            sd.setEndTime(timeToStringConvert(sd.getEmsdEndtime()));
            sd.setScheduleDate((new SimpleDateFormat("dd/MM/yyyy").format((Date) sd.getEsdScheduledate())));
        });
        String scheduleType = this.getModel().getEmployeeScheduleDto().getEmsType();
        this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        this.getModel().setSantType(scheduleType);
        setSummaryPageData(request);
        this.getModel()
        .setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setEmsdCollTypeList(CommonMasterUtility.getLookUps("TSK", UserSession.getCurrent().getOrganisation()));
        return new ModelAndView("SanitaryDisposaldisplay", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param request
     * @param emsId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteSanitaryStaffScheduling", method = RequestMethod.POST)
    public ModelAndView deleteSanitaryStaff(final HttpServletRequest request, @RequestParam Long emsId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        employeeScheduleService.delete(emsId, emp.getEmpId(), emp.getEmppiservername());
        defaultLoad(request);
        return new ModelAndView("SanitaryStaffSchedulingSummary", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param empid
     * @param fromDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchSanitaryStaffScheduling", method = RequestMethod.POST)
    public ModelAndView searchSanitaryStaff(@RequestParam(value = "empid") final Long empid,
            @RequestParam(value = "fromDate") final Date fromDate) {
        this.getModel()
        .setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> employeeMap = employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()).stream().collect(Collectors.toMap(SLRMEmployeeMasterDTO::getEmpId, SLRMEmployeeMasterDTO::getFullName));
        this.getModel().getEmployeeScheduleDto().setEmpName(employeeMap.get(empid));
        List<EmployeeScheduleDTO> employeeScheList = employeeScheduleService.search(empid, fromDate, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        EmployeeScheduleDTO empdet = null;
        List<EmployeeScheduleDTO> emplist = new ArrayList<>();
        for (EmployeeScheduleDTO list1 : employeeScheList) {
            empdet = new EmployeeScheduleDTO();
            empdet.setEmsId(list1.getEmsId());
            empdet.setEmsType(list1.getEmsType());
            empdet.setEstfromdate(new SimpleDateFormat("dd/MM/yyyy").format((Date) list1.getEmsFromdate()));
            empdet.setEstTodate(new SimpleDateFormat("dd/MM/yyyy").format((Date) list1.getEmsTodate()));
            empdet.setEmsReocc(list1.getEmsReocc());
            emplist.add(empdet);
        }
        this.getModel().setEmployeeScheduleList(emplist);
        return new ModelAndView("SanitaryStaffSchedulingGrid", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param httpServletRequest
     * @param empId
     * @return
     */
    @RequestMapping(params = "getCalData", method = RequestMethod.POST)
    public @ResponseBody List<CalanderDTO> empScheduleLoad(final HttpServletRequest httpServletRequest,
            @RequestParam(required = false) Long empId) {
        Map<Long, String> employeeMap =  employeeService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()).stream().collect(Collectors.toMap(SLRMEmployeeMasterDTO::getEmpId, SLRMEmployeeMasterDTO::getFullName));
        List<EmployeeScheduleDTO> employeeScheList = employeeScheduleService.search(empId, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        List<CalanderDTO> calanderList = new ArrayList<>();
        EmployeeScheduleDTO empdet = new EmployeeScheduleDTO();
        List<EmployeeScheduleDTO> empdetList = new ArrayList<>();
        for (EmployeeScheduleDTO emplist : employeeScheList) {
            emplist.getTbSwEmployeeScheddets().forEach(d -> {
                empdet.setEsdScheduledate(d.getEsdScheduledate());
                empdet.setEmpid(d.getEmpid());
                empdet.setEmpName(employeeMap.get(empdet.getEmpid()));
                String startTime = timeToDateConvert(d.getEmsdStarttime());
                String endTime = timeToDateConvert(d.getEmsdEndtime());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:a");
                Date sartDate = null;
                Date endDate = null;
                try {
                    sartDate = dateFormat.parse(empdet.getEsdScheduledate() + "T" + startTime);
                    endDate = dateFormat.parse(empdet.getEsdScheduledate() + "T" + endTime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                CalanderDTO calanderDTO = new CalanderDTO(empdet.getEmpid(), sartDate,
                        empdet.getEmpName(), "bg-black",
                        endDate, "");
                empdetList.add(empdet);
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