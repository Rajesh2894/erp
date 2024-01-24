package com.abm.mainet.swm.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.dto.EmployeeScheduleDetailDTO;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IEmployeeScheduleService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class SanitaryStaffSchedulingModel extends AbstractFormModel {
    private static final long serialVersionUID = 1173589053257668697L;

    private String saveMode;
    EmployeeScheduleDTO employeeScheduleDto = new EmployeeScheduleDTO();
    private List<EmployeeScheduleDTO> employeeScheduleDtos = new ArrayList<>();
    private List<EmployeeScheduleDetailDTO> employeeScheduleDetailsList;
    private List<EmployeeScheduleDetailDTO> employeeScheduleDetails = new ArrayList<>();
    /* List<Object[]> employeeList = new ArrayList<>(); */
    private List<MRFMasterDto> mRFMasterDtoList = new ArrayList<>();

    private List<LookUp> emsdCollTypeList = new ArrayList<>();
    private List<LookUp> vehicleTypeList = new ArrayList<>();
    private List<LookUp> shiftTypeList = new ArrayList<>();
    private List<EmployeeScheduleDTO> employeeScheduleList = new ArrayList<>();
    private List<VehicleMasterDTO> vehicleMasterList = new ArrayList<>();
    private List<BeatMasterDTO> routeMasterList = new ArrayList<>();
    private List<TbLocationMas> locList = new ArrayList<>();
    List<SLRMEmployeeMasterDTO> employeeBeanList = new ArrayList<>();
    EmployeeScheduleDetailDTO employeeScheduleDetailDTO = null;
    private String santType;
    private long srNo;
    @Autowired
    private IEmployeeScheduleService employeeScheduleService;

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {
        case "SWZ":
            return "employeeScheduleDto.tbSwEmployeeScheddets[0].codWard";
        default:
            return null;

        }
    }

    @Override
    public boolean saveForm() {
        List<String> exist = null;
        boolean status = false;
        if (employeeScheduleDto.getEmsId() == null)
            checkWeeklyDay(employeeScheduleDto);
        List<EmployeeScheduleDetailDTO> detList = employeeScheduleDto.getTbSwEmployeeScheddets();
        if (detList != null && !detList.isEmpty()) {
            for (EmployeeScheduleDetailDTO employeeDetails : detList) {
                if (employeeDetails.getEmsdId() == null) {
                    employeeDetails.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    employeeDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    employeeDetails.setCreatedDate(new Date());
                    employeeDetails.setLgIpMac(Utility.getMacAddress());
                    employeeDetails.setEmsdStarttime(stringToTimeConvet(employeeDetails.getStartTime()));
                    employeeDetails.setEmsdEndtime(stringToTimeConvet(employeeDetails.getEndTime()));
                    employeeDetails.setEmpid(employeeDetails.getEmpid());
                    employeeDetails.setBeatId(employeeDetails.getBeatId());
                    employeeDetails.setTbSwEmployeeScheduling(employeeScheduleDto);
                } else {
                    employeeDetails.setEmsdStarttime(stringToTimeConvet(employeeDetails.getStartTime()));
                    employeeDetails.setEmsdEndtime(stringToTimeConvet(employeeDetails.getEndTime()));
                    employeeDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    employeeDetails.setUpdatedDate(new Date());
                    employeeDetails.setLgIpMacUpd(Utility.getMacAddress());
                    employeeDetails.setTbSwEmployeeScheduling(employeeScheduleDto);
                }

            }
        }

        if (employeeScheduleDto.getEmsId() != null) {
            employeeScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            employeeScheduleDto.setUpdatedDate(new Date());
            employeeScheduleDto.setLgIpMacUpd(Utility.getMacAddress());
            exist = employeeScheduleService.validateEmpScheduling(employeeScheduleDto);
            if (exist.isEmpty()) {
                employeeScheduleService.update(employeeScheduleDto);
                status = true;
            } else {
                addValidationError(
                        ApplicationSession.getInstance().getMessage("EmployeeScheduleDTO.validate") + " Level " + exist);
                status = false;
            }
            this.setSuccessMessage(getAppSession().getMessage("EmployeeScheduleDTO.edit"));
        } else {
            employeeScheduleDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            employeeScheduleDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            employeeScheduleDto.setCreatedDate(new Date());
            employeeScheduleDto.setLgIpMac(Utility.getMacAddress());
            exist = employeeScheduleService.validateEmpScheduling(employeeScheduleDto);
            if (exist.isEmpty()) {
                employeeScheduleService.save(employeeScheduleDto);
                this.setSuccessMessage(getAppSession().getMessage("sanitary.schedule.save.success"));
                status = true;
            } else {
                addValidationError(
                        ApplicationSession.getInstance().getMessage("EmployeeScheduleDTO.validate") + " Level " + exist);
                status = false;
            }

        }
        return status;
    }

    private void checkWeeklyDay(EmployeeScheduleDTO employeeScheduledet) {
        Instant instantFrom = employeeScheduledet.getEmsFromdate().toInstant();
        ZonedDateTime zdtFrom = instantFrom.atZone(ZoneId.systemDefault());
        java.time.LocalDate dateFrom = zdtFrom.toLocalDate();
        Instant instantTo = employeeScheduledet.getEmsTodate().toInstant();
        ZonedDateTime zdtTo = instantTo.atZone(ZoneId.systemDefault());
        java.time.LocalDate dateTo = zdtTo.toLocalDate();
        List<EmployeeScheduleDetailDTO> emp = new ArrayList<>();
        long diffInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
        LocalDate d = null;
        String weekDays = employeeScheduleDto.getWeekdays();
        String[] neWweekDay = weekDays.split(",");
        List<Date> scheDate = new ArrayList<>();
        for (EmployeeScheduleDetailDTO det : employeeScheduledet.getTbSwEmployeeScheddets()) {
            // days wise
            ArrayList<String> week = new ArrayList();
            if (employeeScheduledet.getEmsReocc().equals("D")) {
                for (int i = 0; i <= diffInDays; i++) {
                    employeeScheduleDetailDTO = new EmployeeScheduleDetailDTO();
                    BeanUtils.copyProperties(det, employeeScheduleDetailDTO);
                    d = dateFrom.plus(i, ChronoUnit.DAYS);
                    DayOfWeek dayOfWeek = d.getDayOfWeek();
                    int dayOfWeekIntValue = dayOfWeek.getValue();
                    week.add(String.valueOf(dayOfWeekIntValue));
                    Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    employeeScheduleDetailDTO.setEsdScheduledate(date1);
                    employeeScheduleDetailDTO.setEmpid(this.employeeScheduleDetailDTO.getEmpid());
                    emp.add(employeeScheduleDetailDTO);
                    scheDate.add(date1);
                }
                String result = String.join(",", week);
                employeeScheduleDto.setWeekdays(result);
                employeeScheduledet.setSheduleDate(scheDate);
                employeeScheduledet.setTbSwEmployeeScheddets(emp);
            }

            // Weekly Wise
            if (employeeScheduledet.getEmsReocc().equals("W") || employeeScheduledet.getEmsReocc().equals("M")
                    || employeeScheduledet.getEmsReocc().equals("Y")) {
                for (int i = 0; i <= diffInDays; i++) {
                    employeeScheduleDetailDTO = new EmployeeScheduleDetailDTO();
                    BeanUtils.copyProperties(det, employeeScheduleDetailDTO);
                    d = dateFrom.plus(i, ChronoUnit.DAYS);
                    DayOfWeek dayOfWeek = d.getDayOfWeek();
                    int dayOfWeekIntValue = dayOfWeek.getValue();
                    for (int j = 0; j < neWweekDay.length; j++) {
                        String str = neWweekDay[j];
                        if (str.equals("" + dayOfWeekIntValue)) {
                            Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            employeeScheduleDetailDTO.setEsdScheduledate(date1);
                            emp.add(employeeScheduleDetailDTO);
                            scheDate.add(date1);
                        }
                    }
                }
                employeeScheduledet.setSheduleDate(scheDate);
                employeeScheduledet.setTbSwEmployeeScheddets(emp);
            }
        }
    }

    public Date stringToTimeConvet(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;
        if (time != null)
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {

                e.printStackTrace();
            }
        return timeValue;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public EmployeeScheduleDTO getEmployeeScheduleDto() {
        return employeeScheduleDto;
    }

    public void setEmployeeScheduleDto(EmployeeScheduleDTO employeeScheduleDto) {
        this.employeeScheduleDto = employeeScheduleDto;
    }

    public List<EmployeeScheduleDTO> getEmployeeScheduleDtos() {
        return employeeScheduleDtos;
    }

    public void setEmployeeScheduleDtos(List<EmployeeScheduleDTO> employeeScheduleDtos) {
        this.employeeScheduleDtos = employeeScheduleDtos;
    }

    public List<LookUp> getEmsdCollTypeList() {
        return emsdCollTypeList;
    }

    public void setEmsdCollTypeList(List<LookUp> emsdCollTypeList) {
        this.emsdCollTypeList = emsdCollTypeList;
    }

    public String getSantType() {
        return santType;
    }

    public void setSantType(String santType) {
        this.santType = santType;

    }

    public List<EmployeeScheduleDetailDTO> getEmployeeScheduleDetailsList() {
        return employeeScheduleDetailsList;
    }

    public void setEmployeeScheduleDetailsList(List<EmployeeScheduleDetailDTO> employeeScheduleDetailsList) {
        this.employeeScheduleDetailsList = employeeScheduleDetailsList;
    }

    public List<EmployeeScheduleDTO> getEmployeeScheduleList() {
        return employeeScheduleList;
    }

    public void setEmployeeScheduleList(List<EmployeeScheduleDTO> employeeScheduleList) {
        this.employeeScheduleList = employeeScheduleList;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public List<BeatMasterDTO> getRouteMasterList() {
        return routeMasterList;
    }

    public void setRouteMasterList(List<BeatMasterDTO> routeMasterList) {
        this.routeMasterList = routeMasterList;
    }

    public List<EmployeeScheduleDetailDTO> getEmployeeScheduleDetails() {
        return employeeScheduleDetails;
    }

    public void setEmployeeScheduleDetails(List<EmployeeScheduleDetailDTO> employeeScheduleDetails) {
        this.employeeScheduleDetails = employeeScheduleDetails;
    }

    public List<SLRMEmployeeMasterDTO> getEmployeeBeanList() {
        return employeeBeanList;
    }

    public void setEmployeeBeanList(List<SLRMEmployeeMasterDTO> employeeBeanList) {
        this.employeeBeanList = employeeBeanList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public IEmployeeScheduleService getEmployeeScheduleService() {
        return employeeScheduleService;
    }

    public void setEmployeeScheduleService(IEmployeeScheduleService employeeScheduleService) {
        this.employeeScheduleService = employeeScheduleService;
    }

    public long getSrNo() {
        return srNo;
    }

    public void setSrNo(long srNo) {
        this.srNo = srNo;
    }

    public EmployeeScheduleDetailDTO getEmployeeScheduleDetailDTO() {
        return employeeScheduleDetailDTO;
    }

    public void setEmployeeScheduleDetailDTO(EmployeeScheduleDetailDTO employeeScheduleDetailDTO) {
        this.employeeScheduleDetailDTO = employeeScheduleDetailDTO;
    }

    public List<MRFMasterDto> getmRFMasterDtoList() {
        return mRFMasterDtoList;
    }

    public void setmRFMasterDtoList(List<MRFMasterDto> mRFMasterDtoList) {
        this.mRFMasterDtoList = mRFMasterDtoList;
    }

    public List<LookUp> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<LookUp> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public List<LookUp> getShiftTypeList() {
        return shiftTypeList;
    }

    public void setShiftTypeList(List<LookUp> shiftTypeList) {
        this.shiftTypeList = shiftTypeList;
    }

}
