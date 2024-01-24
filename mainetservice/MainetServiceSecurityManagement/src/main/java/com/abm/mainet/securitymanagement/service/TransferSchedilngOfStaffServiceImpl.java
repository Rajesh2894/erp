package com.abm.mainet.securitymanagement.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.securitymanagement.dao.ITransferSchedulingOfStaffDao;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingHist;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.domain.TransferAndDutySchedulingOfStaff;
import com.abm.mainet.securitymanagement.domain.TransferAndDutySchedulingOfStaffHist;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.dto.TransferSchedulingOfStaffDTO;
import com.abm.mainet.securitymanagement.repository.DeploymentOfStaffRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingHistoryRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingRepository;
import com.abm.mainet.securitymanagement.repository.TransferAndDutySchHistoryRepository;
import com.abm.mainet.securitymanagement.repository.TransferAndDutySchRepository;

@Service
public class TransferSchedilngOfStaffServiceImpl implements ITransferSchedulingOfStaffService {

	@Autowired
	private ITransferSchedulingOfStaffDao transferSchedulingOfStaffDao;

	@Autowired
	private TransferAndDutySchRepository repository;

	@Autowired
	private TransferAndDutySchHistoryRepository hisRepository;

	@Autowired
	private EmployeeSchedulingHistoryRepository emplHisRepo;
	
	@Autowired
	private EmployeeSchedulingRepository emplRepo;

	@Autowired
	private DeploymentOfStaffRepository depRepo;

	@Autowired
    private IEmployeeSchedulingService employeeSchedulingService;

	@Override
	@Transactional(readOnly = true)
	public List<TransferSchedulingOfStaffDTO> findStaffDetails(Long empTypeId,String empCode, Long vendorId, Long cpdShiftId,
			Long locId, Long orgId) {

		List<ContractualStaffMaster> empList = transferSchedulingOfStaffDao.findStaffDetails(empTypeId,empCode, vendorId,
				cpdShiftId, locId, orgId);
		List<TransferSchedulingOfStaffDTO> empDtoList = new ArrayList<TransferSchedulingOfStaffDTO>();

		empList.forEach(entity -> {
			TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO = new TransferSchedulingOfStaffDTO();
			BeanUtils.copyProperties(entity, transferSchedulingOfStaffDTO);
			transferSchedulingOfStaffDTO.setEmpTypeId(empTypeId);
			transferSchedulingOfStaffDTO.setContStaffSchFrom(null);
			transferSchedulingOfStaffDTO.setContStaffSchTo(null);
			transferSchedulingOfStaffDTO.setCpdShiftId(null);
			transferSchedulingOfStaffDTO.setLocId(null);
			empDtoList.add(transferSchedulingOfStaffDTO);
		});
		return empDtoList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TransferSchedulingOfStaffDTO> saveOrUpdate(
			List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList,
			TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO) {

		List<TransferSchedulingOfStaffDTO> transferDutySchedulingOfStaffDTOs = new ArrayList<TransferSchedulingOfStaffDTO>();
		
		transferSchedulingOfStaffDTOList.forEach(data -> {
			if (null != data.getMemberSelected() && data.getMemberSelected().equals("Y")) {
				TransferSchedulingOfStaffDTO dtos = new TransferSchedulingOfStaffDTO();
				
				List<HolidayMasterDto> dtoList = employeeSchedulingService
						.findHolidaysByYear(data.getContStaffSchFrom(), data.getContStaffSchTo(), data.getOrgid());
				Instant instantFrom = data.getContStaffSchFrom().toInstant();
				ZonedDateTime zdtFrom = instantFrom.atZone(ZoneId.systemDefault());
				java.time.LocalDate dateFrom = zdtFrom.toLocalDate();
				Instant instantTo = data.getContStaffSchTo().toInstant();
				ZonedDateTime zdtTo = instantTo.atZone(ZoneId.systemDefault());
				java.time.LocalDate dateTo = zdtTo.toLocalDate();
				long diffInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
				List<EmployeeSchedulingDetDTO> emplDetDtoListNew = new ArrayList<EmployeeSchedulingDetDTO>();
				for (int i = 0; i <= diffInDays; i++) {
					EmployeeSchedulingDetDTO employeeDetDTO = new EmployeeSchedulingDetDTO();
					BeanUtils.copyProperties(data, employeeDetDTO);
					LocalDate d = null;
					String[] neWweekDay = null;
					if(data.getDayDesc()!=null) {
					String weekDays = data.getDayDesc().toUpperCase();
					
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
								LocalDate localDate = dateToLocaldate(dtoList.get(k).getHoDate());
								if (localDate.equals(d)) {
									checkValue++;
								}
							}
							if (str.compareTo(DayOfWeekName) != 0 && checkValue == 0) {
								Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
								employeeDetDTO.setShiftDate(date1);
								ShiftMaster master = employeeSchedulingService.findShiftById(data.getCpdShiftId(),data.getOrgid());
								Date fromTime = stringToTimeConvet(master.getFromTime());
								Date toTime = stringToTimeConvet(master.getToTime());
								employeeDetDTO.setEmpTypeId(data.getEmpTypeId());
								employeeDetDTO.setStartimeShift(fromTime);
								employeeDetDTO.setEndtimeShift(toTime);
								employeeDetDTO.setShiftDay(DayOfWeekName);
								employeeDetDTO.setCreatedDate(transferSchedulingOfStaffDTO.getCreatedDate());
								employeeDetDTO.setCreatedBy(transferSchedulingOfStaffDTO.getCreatedBy());
								employeeDetDTO.setLgIpMac(transferSchedulingOfStaffDTO.getLgIpMac());
								emplDetDtoListNew.add(employeeDetDTO);
							}
						}
					}
					}
				}
				dtos.setEmplDetDtoList(emplDetDtoListNew);
				dtos.setContStaffName(data.getContStaffName());
				dtos.setContStaffIdNo(data.getContStaffIdNo());
				dtos.setEmpTypeId(data.getEmpTypeId());
				dtos.setDayPrefixId(data.getDayPrefixId());
				dtos.setContStaffSchFrom(data.getContStaffSchFrom());
				dtos.setContStaffSchTo(data.getContStaffSchTo());
				dtos.setCpdShiftId(data.getCpdShiftId());
				dtos.setLocId(data.getLocId());
				dtos.setVendorId(transferSchedulingOfStaffDTO.getVendorId());
				dtos.setReasonTransfer(transferSchedulingOfStaffDTO.getReasonTransfer());
				dtos.setRemarks(transferSchedulingOfStaffDTO.getRemarks());
				dtos.setOrgid(transferSchedulingOfStaffDTO.getOrgid());
				dtos.setCreatedBy(transferSchedulingOfStaffDTO.getCreatedBy());
				dtos.setCreatedDate(transferSchedulingOfStaffDTO.getCreatedDate());
				dtos.setLgIpMac(transferSchedulingOfStaffDTO.getLgIpMac());
				transferDutySchedulingOfStaffDTOs.add(dtos);
			}
		});
		
		List<EmployeeSchedulingDet> emplList=new ArrayList<EmployeeSchedulingDet>();
		transferDutySchedulingOfStaffDTOs.forEach(dto -> {
			
			if(!dto.getEmplDetDtoList().isEmpty()) {
				TransferAndDutySchedulingOfStaff entity = new TransferAndDutySchedulingOfStaff();
				TransferAndDutySchedulingOfStaffHist historyEntity = new TransferAndDutySchedulingOfStaffHist();
				EmployeeScheduling empSchEntity = new EmployeeScheduling();
				EmployeeSchedulingHist employeeSchedulingHist = new EmployeeSchedulingHist();

				BeanUtils.copyProperties(dto, empSchEntity);
				for(int i=0;i<dto.getEmplDetDtoList().size();i++ ) {
					EmployeeSchedulingDet emplEnt=new EmployeeSchedulingDet();
					emplEnt.setEmployeeScheduling(empSchEntity);
					BeanUtils.copyProperties(dto.getEmplDetDtoList().get(i), emplEnt);
					emplList.add(emplEnt);
				}
				empSchEntity.setEmployeeSchedulingdets(emplList);
				BeanUtils.copyProperties(dto, entity);
				entity.setEmployeeScheduling(empSchEntity);
				empSchEntity.setTransferId(entity);
				repository.save(entity);
				
				BeanUtils.copyProperties(dto, historyEntity);
				historyEntity.setTransferId(entity.getTransferId());
				hisRepository.save(historyEntity);

				BeanUtils.copyProperties(empSchEntity, employeeSchedulingHist);
				employeeSchedulingHist.setEmplScdlId(empSchEntity.getEmplScdlId());
				emplHisRepo.save(employeeSchedulingHist);
			}
		});
		return transferDutySchedulingOfStaffDTOs;
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
	
	@Override
	public List<TransferSchedulingOfStaffDTO> checkIfStaffExists(
			List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList,
			TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO) {

		transferSchedulingOfStaffDTOList.forEach(data -> {
			if (null != data.getMemberSelected() && data.getMemberSelected().equals("Y")) {

				if(data.getContStaffIdNo() !=null) {
					String s = CommonMasterUtility.getCPDDescription(data.getDayPrefixId(), MainetConstants.BLANK);
					data.setDayDesc(s);	
				}
				/* To check if staff exists start */
				Long count = 0l;
				data.setCount(count);
				List<EmployeeScheduling> staffDetail = depRepo.findStaffListByEmpId(data.getContStaffIdNo(),data.getVendorId(),
						transferSchedulingOfStaffDTO.getOrgid());
				for (int i = 0; i < staffDetail.size(); i++) {
					if ((staffDetail != null && staffDetail.get(i).getLocId().equals(data.getLocId())
							&& staffDetail.get(i).getCpdShiftId().equals(data.getCpdShiftId())
							&& (data.getContStaffSchFrom().compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0
									&& data.getContStaffSchFrom()
											.compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true)
							|| (data.getContStaffSchTo().compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0
									&& data.getContStaffSchTo()
											.compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true) {
						count++;
						data.setCount(count);
					}
				}
				/* To check if staff exists end */

				ContractualStaffMaster staffData=emplRepo.findByEmpIdAndVendor(data.getContStaffIdNo(),transferSchedulingOfStaffDTO.getVendorId(), data.getOrgid());
				if(staffData!=null) {
					if ((staffData.getContStaffAppointDate().after(data.getContStaffSchFrom()))
							|| (staffData.getContStaffAppointDate().after(data.getContStaffSchTo()))) {
						data.setMessageDate("true");
					}
				}
			}
		});
		return transferSchedulingOfStaffDTOList;
	}

	@Override
	public TransferSchedulingOfStaffDTO checkDateWithAppointDate(String contStaffIdNo, Long orgId) {
		ContractualStaffMaster staffData = repository.findEmpByEmpId(contStaffIdNo, orgId);
		TransferSchedulingOfStaffDTO transferDTO = new TransferSchedulingOfStaffDTO();
		BeanUtils.copyProperties(staffData, transferDTO);
		if (staffData.getContStaffAppointDate().after(transferDTO.getContStaffSchFrom())) {
			transferDTO.setMessageDate("true");
		}
		return transferDTO;
	}

}
