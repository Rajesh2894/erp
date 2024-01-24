package com.abm.mainet.vehiclemanagement.ui.model;

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.BeatMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDetDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleScheduleService;
import com.abm.mainet.vehiclemanagement.ui.validator.VehicleScheduleValidator;

/**
 * @author Niraj.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleScheduleModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;
	private String saveMode;
	private VehicleScheduleDTO vehicleScheduleDto;
	private List<VehicleScheduleDTO> vehicleScheduleDtos = new ArrayList<>();
	private List<VehicleScheduleDetDTO> vehicleScheduleDetails = new ArrayList<>();
	private List<BeatMasterDTO> routeMasterList = new ArrayList<>();
	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();
	private List<LookUp> vesCollTypeList = new ArrayList<>();
	private VehicleScheduleDetDTO vehicleScheduleDetDTO = null;
	List<SLRMEmployeeMasterDTO> employeeBeanList = new ArrayList<>();
	List<BeatMasterDTO> beatMasterList;
	List<GenVehicleMasterDTO> genVehicleMasterList;
    List<EmployeeBean> employeeList = new ArrayList<>();
    List<Employee> employeList = new ArrayList<>();	
	private Long DeptId; 
	List<Object[]> empList;
	
	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	@Autowired
	private IVehicleScheduleService vehicleScheduleService;

	@Override
	public boolean saveForm() {		
		boolean status = false;
		if(saveMode == "E" && (vehicleScheduleDto.getIsDeleted() !=null && vehicleScheduleDto.getIsDeleted().equals(Constants.BULK_DELETE) )) {
			return bulkDeleteVehicleScheduling();	
		}
	
		if (saveMode != "E")
			validateBean(vehicleScheduleDto, VehicleScheduleValidator.class);
		
		if (hasValidationErrors()) {
			return false;
		} else {
			if (saveMode != "E") {
				checkVehicleSheduleDay(vehicleScheduleDto);
			}
			
			if(maserAndScheduleToAndFromDateCheck() == false) {
				return false;
			}
			
				List<VehicleScheduleDetDTO> detList = vehicleScheduleDto.getTbSwVehicleScheddets();
				if (detList != null && !detList.isEmpty()) {
				for (VehicleScheduleDetDTO vehicleDetails : detList) {	
					 if(saveMode == "E") {
						if(scheduleAndScheduleDetailsToAndFromDateCheck(vehicleDetails) == false) {
							return false;
						}
					  }
					 vehicleScheduleDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					if (vehicleDetails.getVesdId() == null) {
						vehicleDetails.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
						vehicleDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						vehicleDetails.setCreatedDate(new Date());
						vehicleDetails.setLgIpMac(Utility.getMacAddress());
						vehicleDetails.setVesStartime(stringToTimeConvet(vehicleDetails.getStartime()));
						vehicleDetails.setVesEndtime(stringToTimeConvet(vehicleDetails.getEndtime()));
						vehicleDetails.setEmpId(vehicleDetails.getEmpId());
						vehicleDetails.setOccEmpName(vehicleDetails.getOccEmpName());
						vehicleDetails.setIsDeleted(MainetConstants.FlagN);
						/* vehicleDetails.setVesWeekday(vehicleScheduleDto.getVesWeekday()); */
						vehicleDetails.setTbSwVehicleScheduling(vehicleScheduleDto);
					}else {

						vehicleDetails.setVesStartime(stringToTimeConvet(vehicleDetails.getStartime()));
						vehicleDetails.setVesEndtime(stringToTimeConvet(vehicleDetails.getEndtime()));
						vehicleDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						vehicleDetails.setEmpId(vehicleDetails.getEmpId());
						vehicleDetails.setUpdatedDate(new Date());
						vehicleDetails.setLgIpMacUpd(Utility.getMacAddress());
						vehicleDetails.setIsDeleted(MainetConstants.FlagN);
						vehicleDetails.setTbSwVehicleScheduling(vehicleScheduleDto);
					}

				}
				/*
				 * String dupCheck =
				 * vehicleScheduleService.vehicleScheduleValidate(vehicleScheduleDto); if(
				 * !(dupCheck.equals(MainetConstants.FlagN))){
				 * addValidationError(ApplicationSession.getInstance().
				 * getMessage("vehicle.schedule.already.exists ")+dupCheck); return false; }
				 */
			}

			if (vehicleScheduleDto.getVesId() == null) {
				vehicleScheduleDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				vehicleScheduleDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				vehicleScheduleDto.setCreatedDate(new Date());
				vehicleScheduleDto.setLgIpMac(Utility.getMacAddress());
				vehicleScheduleDto.setIsDeleted(MainetConstants.FlagN);

			} else {
				vehicleScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				vehicleScheduleDto.setUpdatedDate(new Date());
				vehicleScheduleDto.setLgIpMacUpd(Utility.getMacAddress());
				
			}
			
			/*List<GenVehicleMasterDTO> checkList =	vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(vehicleScheduleDto.getVeId(),null,null,null,MainetConstants.FlagY,UserSession.getCurrent().getOrganisation().getOrgid());
   			if( (checkList.size()) <= 0) {
   				addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.rent.time.completed"));
			return	status = false;
   			}
			
			if( vehicleScheduleService.vehicleScheduleValidate(vehicleScheduleDto) == false){
					status = false;
			} else {
				status = true;
			};*/
			
	
			////if (status) {
				if (vehicleScheduleDto.getVesId() == null) {
					vehicleScheduleService.saveVehicleSchedule(vehicleScheduleDto);
					this.setSuccessMessage(getAppSession().getMessage("vehiecle.schedule.save.success.msg"));
				} else {
					vehicleScheduleService.updateVehicleSchedule(vehicleScheduleDto);
					setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.edit.success.msg"));
				}
				
				/*
				 * setSuccessMessage(ApplicationSession.getInstance().getMessage(
				 * "vehicleScheduleDto.add.success")); status = true;
				 */
				
			////} else {
			////	addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.exists"));
			////	status = false;
				
				// vehicleScheduleDto.getTbSwVehicleScheddets().clear();
			////}
		}
		
		return true;
	}

	boolean bulkDeleteVehicleScheduling(){
			if( vehicleScheduleDto.getTbSwVehicleScheddets() !=null || !vehicleScheduleDto.getTbSwVehicleScheddets().isEmpty()) {
				
				List<VehicleScheduleDetDTO> sortedList = new ArrayList<VehicleScheduleDetDTO>();
				for (VehicleScheduleDetDTO delDet : vehicleScheduleDto.getTbSwVehicleScheddets()) {	
					if(delDet.getIsDeleted() != null &&  delDet.getIsDeleted().contentEquals(MainetConstants.FlagD)) {
						delDet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						delDet.setUpdatedDate(new Date());
						delDet.setLgIpMacUpd(Utility.getMacAddress());
						delDet.setIsDeleted(MainetConstants.FlagY); 
						 sortedList.add(delDet);
					}
				}
				if(sortedList.size() == 0) {
					addValidationError(ApplicationSession.getInstance().getMessage("vehicle.schedule.select.one"));
					return false;
				}
				if (sortedList.size() == vehicleScheduleDto.getTbSwVehicleScheddets().size()) {
					addValidationError(ApplicationSession.getInstance().getMessage("vehicle.schedule.all.not.deleted"));
					return false;
					//vehicleScheduleDto.setIsDeleted(MainetConstants.FlagY);
				}else {
					vehicleScheduleDto.setIsDeleted(MainetConstants.FlagN);
				}
					vehicleScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					vehicleScheduleDto.setUpdatedDate(new Date());
					vehicleScheduleDto.setLgIpMacUpd(Utility.getMacAddress());
					vehicleScheduleDto.setTbSwVehicleScheddets(sortedList);
			
				vehicleScheduleService.updateVehicleSchedule(vehicleScheduleDto);
				//setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.delete.success.msg"));
				return true;	
			}else {
				addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.delete.error.msg"));
				return false;
			}	
		
	}
	
	
	public boolean maserAndScheduleToAndFromDateCheck() {;
	List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(vehicleScheduleDto.getVeId(),vehicleScheduleDto.getVeVetype(),null,null,null,null,UserSession.getCurrent().getOrganisation().getOrgid());	      
	if(result != null && result.size() > 0  ) {
		if(result.get(0).getVeFlag().equals(MainetConstants.FlagN)) {
		Date masterFromDate = result.get(0).getVeRentFromdate();
		Date masterToDate = result.get(0).getVeRentTodate();
		if(!((vehicleScheduleDto.getVesFromdt().equals(masterFromDate) || vehicleScheduleDto.getVesFromdt().after(masterFromDate)) &&
				 (vehicleScheduleDto.getVesTodt().equals(masterToDate) || vehicleScheduleDto.getVesTodt().before(masterToDate)  ) 
				)) {
			  addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.rent.time.completed"));
			return false;
		}
	}
	}
	return true;
	}
	
	
	
	
	
	public boolean scheduleAndScheduleDetailsToAndFromDateCheck(VehicleScheduleDetDTO vehicleDetails ) {

		try {
			Date schDate =null;
			if(vehicleDetails.getSheduleDate() == null) {
				 schDate =vehicleDetails.getVeScheduledate();
			}else {
				 schDate = new SimpleDateFormat("dd/MM/yyyy").parse(vehicleDetails.getSheduleDate());
				 vehicleDetails.setVeScheduledate(schDate);
			}
			
			if( (schDate.after(vehicleScheduleDto.getVesFromdt()) || schDate.equals(vehicleScheduleDto.getVesFromdt()))
				&& (schDate.before(vehicleScheduleDto.getVesTodt()) ||schDate.equals(vehicleScheduleDto.getVesTodt())) 
					) {
				vehicleDetails.setVesStartime(stringToTimeConvet(vehicleDetails.getStartime()));
				vehicleDetails.setVesEndtime(stringToTimeConvet(vehicleDetails.getEndtime()));
				vehicleDetails.setVesWeekday(new Integer(vehicleDetails.getVeScheduledate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue()).toString());
			}else {
				  addValidationError(ApplicationSession.getInstance().getMessage("vehicleScheduleDto.schDate.bw.from.to.date"));
				return false;
			}
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return true;
	}
	
	
	public void checkVehicleSheduleDay(VehicleScheduleDTO vehScheduleDet) {
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
					vehicleScheduleDetDTO.setVesWeekday(new Integer(dayOfWeekIntValue).toString());
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
								vehicleScheduleDetDTO.setVesWeekday(new Integer(dayOfWeekIntValue).toString());
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

	// validation on collection type and time interval bassed on vehicle type and
	// vehicle nor
	private void checkForValidCollectionAndTime(VehicleScheduleDTO vehicleScheduleDto) {
		List<VehicleScheduleDTO> dto = vehicleScheduleService.searchVehicleScheduleByVehicleTypeAndVehicleNo(
				vehicleScheduleDto.getVeVetype(), vehicleScheduleDto.getVeId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (dto != null && !dto.isEmpty()) {
			for (VehicleScheduleDTO vdto : dto) {
				for (VehicleScheduleDetDTO schedule : vdto.getTbSwVehicleScheddets()) {
					for (VehicleScheduleDetDTO detDtofor : vehicleScheduleDto.getTbSwVehicleScheddets()) {
						/*
						 * if (schedule.getBeatId().equals(detDtofor.getBeatId()) &&
						 * schedule.getVesCollType().equals(detDtofor.getVesCollType()))
						 */
							Date from = stringToTimeConvet(detDtofor.getStartime());
							Date to = stringToTimeConvet(detDtofor.getEndtime());
							if (((from.before(schedule.getVesStartime()) && from.before(schedule.getVesEndtime()))
									|| (from.after(schedule.getVesStartime()) && from.after(schedule.getVesEndtime())))
									&& ((to.before(schedule.getVesStartime()) && to.before(schedule.getVesEndtime()))
											|| (to.after(schedule.getVesStartime())
													&& to.after(schedule.getVesEndtime())))) {
							} else {
								if (saveMode != "E")
									addValidationError("Record already present.");
							}
						
					}

				}
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

	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
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

	public List<GenVehicleMasterDTO> getGenVehicleMasterList() {
		return genVehicleMasterList;
	}

	public void setGenVehicleMasterList(List<GenVehicleMasterDTO> genVehicleMasterList) {
		this.genVehicleMasterList = genVehicleMasterList;
	}

	public List<EmployeeBean> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeBean> employeeList) {
		this.employeeList = employeeList;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public List<Employee> getEmployeList() {
		return employeList;
	}

	public void setEmployeList(List<Employee> employeList) {
		this.employeList = employeList;
	}

	public List<Object[]> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Object[]> empList) {
		this.empList = empList;
	}	

	
	
	
}










