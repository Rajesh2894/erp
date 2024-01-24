package com.abm.mainet.securitymanagement.ui.model;

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
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.service.IEmployeeSchedulingService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class EmployeeSchedulingModel extends AbstractFormModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6314112611432537360L;
	EmployeeSchedulingDTO employeeSchedulingDTO = new EmployeeSchedulingDTO();
	List<EmployeeSchedulingDTO> employeeSchedulingDTOList = new ArrayList<>();
	private List<TbLocationMas> location=new ArrayList<TbLocationMas>();
	private List<EmployeeSchedulingDTO> empNameList=new ArrayList<EmployeeSchedulingDTO>();
	private List<TbAcVendormaster> vendorList=new ArrayList<TbAcVendormaster>();
	private String saveMode;
	private List<LookUp> lookup= new ArrayList<LookUp>();

	@Autowired
	IEmployeeSchedulingService employeeSchedulingService;

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();

		if (employeeSchedulingDTO.getEmplScdlId() == null) {
			employeeSchedulingDTO.setOrgid(orgId);
			employeeSchedulingDTO.setCreatedBy(createdBy);
			employeeSchedulingDTO.setCreatedDate(createdDate);
			employeeSchedulingDTO.setLgIpMac(lgIpMac);

			List<EmployeeSchedulingDTO> staffList = employeeSchedulingService
					.checkIfStaffExists(employeeSchedulingDTOList, employeeSchedulingDTO);
			Long countNew = 0l;
			String messageNew = null;
			for (int i = 0; i < staffList.size(); i++) {
				if (staffList.get(i).getMessageDate() == "true") {
					messageNew = "t";
					addValidationError(getAppSession().getMessage("DeploymentOfStaffDTO.Validation.fromToAndAppointmentDate"));
				} else if (staffList.get(i).getCount() != null && staffList.get(i).getCount() != 0) {
					countNew = 1l;
					addValidationError(getAppSession().getMessage("DeploymentOfStaffDTO.Validation.presentEmployee"));
				}
			}
			if (countNew != 1l && messageNew != "t") {
				List<EmployeeSchedulingDTO> vp=checkStaffWeekoffDay(employeeSchedulingDTO, employeeSchedulingDTOList);
				employeeSchedulingService.save(employeeSchedulingDTOList,employeeSchedulingDTO,vp);
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("Saved Data Successfully"));
			}
			for(int i = 0; i < staffList.size(); i++) {			
				employeeSchedulingDTOList.get(i).setContStaffIdNo(staffList.get(i).getContStaffIdNo() + "+" +staffList.get(i).getDayPrefixId());
			
			}
		}
		if (hasValidationErrors()) {
			return false;
		} else {
			return true;
		}
	}

	private List<EmployeeSchedulingDTO> checkStaffWeekoffDay(EmployeeSchedulingDTO employeeSchedulingDTO,
			List<EmployeeSchedulingDTO> employeeSchedulingDTOList) {

		List<EmployeeSchedulingDTO> employeeSchedulingDTOListNew = new ArrayList<>();
		Instant instantFrom = employeeSchedulingDTO.getContStaffSchFrom().toInstant();
		ZonedDateTime zdtFrom = instantFrom.atZone(ZoneId.systemDefault());
		java.time.LocalDate dateFrom = zdtFrom.toLocalDate();
		Instant instantTo = employeeSchedulingDTO.getContStaffSchTo().toInstant();
		ZonedDateTime zdtTo = instantTo.atZone(ZoneId.systemDefault());
		java.time.LocalDate dateTo = zdtTo.toLocalDate();
		
		long diffInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
		List<HolidayMasterDto> dtoList = employeeSchedulingService.findHolidaysByYear(
				employeeSchedulingDTO.getContStaffSchFrom(), employeeSchedulingDTO.getContStaffSchTo(),
				employeeSchedulingDTO.getOrgid());

		List<Date> scheDate = new ArrayList<>();
		 List<EmployeeSchedulingDTO> vp=new ArrayList<EmployeeSchedulingDTO>();
		for (EmployeeSchedulingDTO vescdet : employeeSchedulingDTOList) {
			EmployeeSchedulingDTO v1=new EmployeeSchedulingDTO();
			List<EmployeeSchedulingDetDTO> es = new ArrayList<>();
			for (int i = 0; i <= diffInDays; i++) {
				EmployeeSchedulingDetDTO employeeDetDTO = new EmployeeSchedulingDetDTO();
				BeanUtils.copyProperties(vescdet, employeeDetDTO);
				LocalDate d = null;
				String[] neWweekDay = null;
				String weekDays = vescdet.getDayDesc().toUpperCase();
				if (weekDays != null) {
					neWweekDay = weekDays.split(",");
				}
				d = dateFrom.plus(i, ChronoUnit.DAYS);
				DayOfWeek dayOfWeek = d.getDayOfWeek();
				String DayOfWeekName = dayOfWeek.name();

				if (weekDays != null) {
					for (int j = 0; j < neWweekDay.length; j++) {
						String str = neWweekDay[j];
						Long checkValue = 0l;
						for (int k = 0; k < dtoList.size(); k++) {
							LocalDate localDate=dateToLocaldate(dtoList.get(k).getHoDate());
							if (localDate.equals(d)) {
								checkValue++;
							}
						}
						if (str.compareTo(DayOfWeekName) != 0 && checkValue == 0) {
							Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
							employeeDetDTO.setShiftDate(date1);
							ShiftMaster master = employeeSchedulingService.findShiftById(vescdet.getCpdShiftId(),employeeSchedulingDTO.getOrgid());
							Date fromTime = stringToTimeConvet(master.getFromTime());
							Date toTime = stringToTimeConvet(master.getToTime());
							employeeDetDTO.setEmpTypeId(employeeSchedulingDTO.getEmpTypeId());
							employeeDetDTO.setStartimeShift(fromTime);
							employeeDetDTO.setEndtimeShift(toTime);
							employeeDetDTO.setShiftDay(DayOfWeekName);
							es.add(employeeDetDTO);
							scheDate.add(date1);
						}
					}
				}
			}
			employeeSchedulingDTO.setEmplDetDto(es);
			v1.setEmplDetDto(es);
			vp.add(v1);
			BeanUtils.copyProperties(employeeSchedulingDTO, v1);
			employeeSchedulingDTOListNew.addAll(vp);
			employeeSchedulingDTO.setSheduleDate(scheDate);
		}
		return vp;
		
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
	
	public LocalDate dateToLocaldate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}

	public EmployeeSchedulingDTO getEmployeeSchedulingDTO() {
		return employeeSchedulingDTO;
	}

	public void setEmployeeSchedulingDTO(EmployeeSchedulingDTO employeeSchedulingDTO) {
		this.employeeSchedulingDTO = employeeSchedulingDTO;
	}

	public List<EmployeeSchedulingDTO> getEmployeeSchedulingDTOList() {
		return employeeSchedulingDTOList;
	}

	public void setEmployeeSchedulingDTOList(List<EmployeeSchedulingDTO> employeeSchedulingDTOList) {
		this.employeeSchedulingDTOList = employeeSchedulingDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<TbLocationMas> getLocation() {
		return location;
	}

	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}

	public List<EmployeeSchedulingDTO> getEmpNameList() {
		return empNameList;
	}

	public void setEmpNameList(List<EmployeeSchedulingDTO> empNameList) {
		this.empNameList = empNameList;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public List<LookUp> getLookup() {
		return lookup;
	}

	public void setLookup(List<LookUp> lookup) {
		this.lookup = lookup;
	}

	
	
}
