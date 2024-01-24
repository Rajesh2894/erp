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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDetDTO;
import com.abm.mainet.swm.service.IVehicleScheduleService;
import com.abm.mainet.swm.ui.validator.VehicleSchedulingValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleSchedulingModel extends AbstractFormModel {
    private static final long serialVersionUID = 1L;
    private String saveMode;
    private VehicleScheduleDTO vehicleScheduleDto;
    private List<VehicleScheduleDTO> vehicleScheduleDtos = new ArrayList<>();
    private List<VehicleScheduleDetDTO> vehicleScheduleDetails = new ArrayList<>();
    private List<BeatMasterDTO> routeMasterList = new ArrayList<>();
    private List<VehicleMasterDTO> vehicleMasterList = new ArrayList<>();
    private List<LookUp> vesCollTypeList = new ArrayList<>();
    private VehicleScheduleDetDTO vehicleScheduleDetDTO = null;
    List<SLRMEmployeeMasterDTO> employeeBeanList = new ArrayList<>();
    List<BeatMasterDTO> beatMasterList;
    @Autowired
    private IVehicleScheduleService vehicleScheduleService;

    @Override
    public boolean saveForm() {
        boolean status = false;
        if (saveMode != "E")
            validateBean(vehicleScheduleDto, VehicleSchedulingValidator.class);
        if (!hasValidationErrors()) {
            checkForValidCollectionAndTime(vehicleScheduleDto);
        }
        if (hasValidationErrors()) {
            return false;
        } else {
            if (saveMode != "E") {
                checkVehicleSheduleDay(vehicleScheduleDto);
            }
            List<VehicleScheduleDetDTO> detList = vehicleScheduleDto.getTbSwVehicleScheddets();
            if (detList != null && !detList.isEmpty()) {
                for (VehicleScheduleDetDTO vehicleDetails : detList) {
                    if (vehicleDetails.getVesdId() == null) {
                        vehicleDetails.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        vehicleDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        vehicleDetails.setCreatedDate(new Date());
                        vehicleDetails.setLgIpMac(Utility.getMacAddress());
                        vehicleDetails.setVesStartime(stringToTimeConvet(vehicleDetails.getStartime()));
                        vehicleDetails.setVesEndtime(stringToTimeConvet(vehicleDetails.getEndtime()));
                        vehicleDetails.setEmpId(vehicleDetails.getEmpId());
                        /* vehicleDetails.setVesWeekday(vehicleScheduleDto.getVesWeekday()); */
                        vehicleDetails.setTbSwVehicleScheduling(vehicleScheduleDto);
                    } else {

                        vehicleDetails.setVesStartime(stringToTimeConvet(vehicleDetails.getStartime()));
                        vehicleDetails.setVesEndtime(stringToTimeConvet(vehicleDetails.getEndtime()));
                        vehicleDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        vehicleDetails.setUpdatedDate(new Date());
                        vehicleDetails.setLgIpMacUpd(Utility.getMacAddress());
                        vehicleDetails.setTbSwVehicleScheduling(vehicleScheduleDto);
                    }

                }
            }

            if (vehicleScheduleDto.getVesId() == null) {
                vehicleScheduleDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleScheduleDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleScheduleDto.setCreatedDate(new Date());
                vehicleScheduleDto.setIsActive(MainetConstants.Y_FLAG);
                vehicleScheduleDto.setLgIpMac(Utility.getMacAddress());

            } else {
                vehicleScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleScheduleDto.setUpdatedDate(new Date());
                vehicleScheduleDto.setLgIpMacUpd(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.edit.success"));
            }
            if (saveMode != "E") {
                status = vehicleScheduleService.vehicleScheduleValidate(vehicleScheduleDto);
            } else {
                status = true;
            }
            if (status) {
                if (vehicleScheduleDto.getVesId() == null) {
                    vehicleScheduleService.saveVehicleSchedule(vehicleScheduleDto);
                } else {
                    vehicleScheduleService.updateVehicleSchedule(vehicleScheduleDto);
                }
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.add.success"));
                status = true;
            } else {
                addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.exists"));
                status = false;
                // vehicleScheduleDto.getTbSwVehicleScheddets().clear();
            }
        }
        this.setSuccessMessage(getAppSession().getMessage("vehiecle.schedule.save.success"));
        return status;
    }

    private void checkVehicleSheduleDay(VehicleScheduleDTO vehScheduleDet) {
        // TODO Auto-generated method stub
        Instant instantFrom = vehScheduleDet.getVesFromdt().toInstant();
        ZonedDateTime zdtFrom = instantFrom.atZone(ZoneId.systemDefault());
        java.time.LocalDate dateFrom = zdtFrom.toLocalDate();
        Instant instantTo = vehScheduleDet.getVesTodt().toInstant();
        ZonedDateTime zdtTo = instantTo.atZone(ZoneId.systemDefault());
        java.time.LocalDate dateTo = zdtTo.toLocalDate();
        List<VehicleScheduleDetDTO> ves = new ArrayList<>();
        long diffInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
        LocalDate d = null;
        String[] neWweekDay = null;
        String weekDays = vehScheduleDet.getVesWeekday();
        if (weekDays != null) {
            neWweekDay = weekDays.split(",");
        }
        List<Date> scheDate = new ArrayList<>();
        for (VehicleScheduleDetDTO vescdet : vehicleScheduleDto.getTbSwVehicleScheddets()) {
            ArrayList<String> week = new ArrayList();
            if (vehicleScheduleDto.getVesReocc().equals("D")) {
                for (int i = 0; i <= diffInDays; i++) {
                    vehicleScheduleDetDTO = new VehicleScheduleDetDTO();
                    BeanUtils.copyProperties(vescdet, vehicleScheduleDetDTO);
                    d = dateFrom.plus(i, ChronoUnit.DAYS);
                    DayOfWeek dayOfWeek = d.getDayOfWeek();
                    int dayOfWeekIntValue = dayOfWeek.getValue();
                    week.add(String.valueOf(dayOfWeekIntValue));
                    Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    vehicleScheduleDetDTO.setVeScheduledate(date1);
                    vehicleScheduleDetDTO.setBeatId(this.vehicleScheduleDetDTO.getBeatId());
                    vehicleScheduleDetDTO.setEmpId(this.vehicleScheduleDetDTO.getEmpId());
                    ves.add(vehicleScheduleDetDTO);
                    scheDate.add(date1);
                }
                String result = String.join(",", week);
                vehicleScheduleDto.setVesWeekday(result);
                vehScheduleDet.setTbSwVehicleScheddets(ves);
                vehScheduleDet.setSheduleDate(scheDate);
            } else if (vehicleScheduleDto.getVesReocc().equals("W") || vehicleScheduleDto.getVesReocc().equals("M")
                    || vehicleScheduleDto.getVesReocc().equals("Y")) {
                for (int i = 0; i <= diffInDays; i++) {
                    vehicleScheduleDetDTO = new VehicleScheduleDetDTO();
                    BeanUtils.copyProperties(vescdet, vehicleScheduleDetDTO);
                    d = dateFrom.plus(i, ChronoUnit.DAYS);
                    DayOfWeek dayOfWeek = d.getDayOfWeek();
                    int dayOfWeekIntValue = dayOfWeek.getValue();
                    if (weekDays != null) {
                        for (int j = 0; j < neWweekDay.length; j++) {
                            String str = neWweekDay[j];
                            if (str.equals("" + dayOfWeekIntValue)) {
                                Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                vehicleScheduleDetDTO.setVeScheduledate(date1);
                                ves.add(vehicleScheduleDetDTO);
                                scheDate.add(date1);
                            }
                        }
                    }
                }
                vehScheduleDet.setTbSwVehicleScheddets(ves);
                vehScheduleDet.setSheduleDate(scheDate);
            }
        }

    }

    // validation on collection type and time interval bassed on vehicle type and vehicle nor
    private void checkForValidCollectionAndTime(VehicleScheduleDTO vehicleScheduleDto) {
    	boolean validationFlag = false;
    	
    	/*Check whether employee is alredy assign for given day and time*/
		List<VehicleScheduleDTO> vehicleScheduleDTO = vehicleScheduleService.getVehicleScheduleByFromDtAndToDt(
				vehicleScheduleDto.getVesFromdt(), vehicleScheduleDto.getVesTodt(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		outerLoop1:
		for (VehicleScheduleDTO vs : vehicleScheduleDTO) {
			for (VehicleScheduleDetDTO vsd : vs.getTbSwVehicleScheddets()) {
				for (VehicleScheduleDetDTO vsdDto : vehicleScheduleDto.getTbSwVehicleScheddets()) {
					Date fromTime = stringToTimeConvet(vsdDto.getStartime());
					Date toTime = stringToTimeConvet(vsdDto.getEndtime());
					if (vsdDto.getEmpId() != null){
						for (String empId : vsdDto.getEmpId().split(",")) {
							if ((vsd.getEmpId() != null && vsd.getEmpId().equals(empId))
									&& !(fromTime.after(vsd.getVesEndtime()) || toTime.before(vsd.getVesStartime()))) {
								if (saveMode != "E")
								validationFlag = true;
								break outerLoop1;
							}
						}
					}
				}
			}
		}
		if (!validationFlag) {
			List<VehicleScheduleDTO> dto = vehicleScheduleService.searchVehicleScheduleByVehicleTypeAndVehicleNoAndDate(
					vehicleScheduleDto.getVeVetype(), vehicleScheduleDto.getVeId(),
					UserSession.getCurrent().getOrganisation().getOrgid(), vehicleScheduleDto.getVesFromdt(),
					vehicleScheduleDto.getVesTodt());

			if (dto != null && !dto.isEmpty()) {
				outerLoop2:
				for (VehicleScheduleDTO vdto : dto) {
					for (VehicleScheduleDetDTO schedule : vdto.getTbSwVehicleScheddets()) {
						for (VehicleScheduleDetDTO detDtofor : vehicleScheduleDto.getTbSwVehicleScheddets()) {
							if (schedule.getBeatId().equals(detDtofor.getBeatId())
									&& schedule.getVesCollType().equals(detDtofor.getVesCollType())) {
								Date from = stringToTimeConvet(detDtofor.getStartime());
								Date to = stringToTimeConvet(detDtofor.getEndtime());
								if (((from.before(schedule.getVesStartime()) && from.before(schedule.getVesEndtime()))
										|| (from.after(schedule.getVesStartime())
												&& from.after(schedule.getVesEndtime())))
										&& ((to.before(schedule.getVesStartime())
												&& to.before(schedule.getVesEndtime()))
												|| (to.after(schedule.getVesStartime())
														&& to.after(schedule.getVesEndtime())))
										&& (detDtofor.getEmpId() != schedule.getEmpId())) {
								} else {
									if (saveMode != "E")
										validationFlag = true;
									break outerLoop2;
								}
							}
						}
					}
				}
			}
		}
        if(validationFlag)
        	addValidationError("Record already present.");
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

    public List<VehicleScheduleDetDTO> getVehicleScheduleDetails() {
        return vehicleScheduleDetails;
    }

    public void setVehicleScheduleDetails(List<VehicleScheduleDetDTO> vehicleScheduleDetails) {
        this.vehicleScheduleDetails = vehicleScheduleDetails;
    }

    public IVehicleScheduleService getVehicleScheduleService() {
        return vehicleScheduleService;
    }

    public void setVehicleScheduleService(IVehicleScheduleService vehicleScheduleService) {
        this.vehicleScheduleService = vehicleScheduleService;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public VehicleScheduleDTO getVehicleScheduleDto() {
        return vehicleScheduleDto;
    }

    public void setVehicleScheduleDto(VehicleScheduleDTO vehicleScheduleDto) {
        this.vehicleScheduleDto = vehicleScheduleDto;
    }

    public List<VehicleScheduleDTO> getVehicleScheduleDtos() {
        return vehicleScheduleDtos;
    }

    public void setVehicleScheduleDtos(List<VehicleScheduleDTO> vehicleScheduleDtos) {
        this.vehicleScheduleDtos = vehicleScheduleDtos;
    }

    public List<BeatMasterDTO> getRouteMasterList() {
        return routeMasterList;
    }

    public void setRouteMasterList(List<BeatMasterDTO> routeMasterList) {
        this.routeMasterList = routeMasterList;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public List<LookUp> getVesCollTypeList() {
        return vesCollTypeList;
    }

    public void setVesCollTypeList(List<LookUp> vesCollTypeList) {
        this.vesCollTypeList = vesCollTypeList;
    }

    public VehicleScheduleDetDTO getVehicleScheduleDetDTO() {
        return vehicleScheduleDetDTO;
    }

    public void setVehicleScheduleDetDTO(VehicleScheduleDetDTO vehicleScheduleDetDTO) {
        this.vehicleScheduleDetDTO = vehicleScheduleDetDTO;
    }

    public List<BeatMasterDTO> getBeatMasterList() {
        return beatMasterList;
    }

    public void setBeatMasterList(List<BeatMasterDTO> beatMasterList) {
        this.beatMasterList = beatMasterList;
    }

    public List<SLRMEmployeeMasterDTO> getEmployeeBeanList() {
        return employeeBeanList;
    }

    public void setEmployeeBeanList(List<SLRMEmployeeMasterDTO> employeeBeanList) {
        this.employeeBeanList = employeeBeanList;
    }

}
