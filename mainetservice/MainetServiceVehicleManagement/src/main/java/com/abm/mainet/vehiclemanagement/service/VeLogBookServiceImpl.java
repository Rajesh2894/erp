package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dao.ILogBookDao;
import com.abm.mainet.vehiclemanagement.domain.VehicleLogBookDetails;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.repository.VeLogBookRepository;

@Service
public class VeLogBookServiceImpl implements ILogBookService {

	@Autowired
	private VeLogBookRepository logBookRepo;

	@Autowired
	private ILogBookDao iLogBookDao;

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	@Autowired
	private ISLRMEmployeeMasterService iSLRMEmployeeMasterService;

	@Override
	// @Transactional(rollbackFor = Exception.class)
	public VehicleLogBookDTO saveVehicleDetails(VehicleLogBookDTO vehicleLogBookDto) {
		VehicleLogBookDetails vlbook = new VehicleLogBookDetails();
		BeanUtils.copyProperties(vehicleLogBookDto, vlbook);
		vlbook.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		vlbook.setVeNo(vehicleLogBookDto.getVeNo());
		vlbook.setDriverId(vehicleLogBookDto.getDriverId());
		vlbook.setVeNo(vehicleLogBookDto.getVeNo());
		if(vehicleLogBookDto.getVehicleInTime().isEmpty() || vehicleLogBookDto.getVehicleInTime().equals("")) {
			//setting null because in dataBase coloum type is date if value is empty default saving as 00:00:00 and it is problem while editing
			vlbook.setVehicleInTime(null);
		}
		vlbook.setTypeOfVehicle(vehicleLogBookDto.getTypeOfVehicle());
		vlbook = logBookRepo.save(vlbook);
		BeanUtils.copyProperties(vlbook, vehicleLogBookDto);
		return vehicleLogBookDto;
	}

	@Override
	public List<VehicleLogBookDTO> getAllRecord(Long orgid) {
		List<VehicleLogBookDetails> listOccuranceBookEntities = logBookRepo.findByOrgid(orgid);
		// @SuppressWarnings("unused")
		// List<Object[]> listss=vehicleMasterService.getAllVehicles(orgid);
		List<VehicleLogBookDTO> dtoList = new ArrayList<VehicleLogBookDTO>();
		listOccuranceBookEntities.forEach(entity -> {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			BeanUtils.copyProperties(entity, dto);
			if (entity.getDriverId() != null) {
			SLRMEmployeeMasterDTO employee = ApplicationContextProvider.getApplicationContext().getBean(ISLRMEmployeeMasterService.class).searchEmployeeDetails(entity.getDriverId(), orgid);
			if(employee!=null && employee.getFullName()!=null) {
			dto.setDriverName(employee.getFullName());
			}
			}
			dto.setVeName(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeNo()));
			if (dto.getVehicleInTime() != null) {
				String[] arrOfStr = dto.getVehicleInTime().split(":");
				dto.setVehicleInTime(arrOfStr[0] + ":" + arrOfStr[1]);
			}
			if (dto.getVehicleOutTime() != null) {
				String[] arrOfStr1 = dto.getVehicleOutTime().split(":");
				dto.setVehicleOutTime(arrOfStr1[0] + ":" + arrOfStr1[1]);
			}
			dtoList.add(dto);
		});

		return dtoList;
	}

	public List<VehicleLogBookDTO> searchVehicleDetail(Date fromDate, Date toDate, Long veNo, Long orgid) {
		List<VehicleLogBookDTO> listLogBookDTOs = new ArrayList<VehicleLogBookDTO>();
		List<VehicleLogBookDetails> list = iLogBookDao.searchVehicleLogBook(fromDate, toDate, veNo, orgid);
		list.forEach(entity -> {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setDriverName(iSLRMEmployeeMasterService.getDriverFullNameById((entity.getDriverId())));
			dto.setVeName(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeNo()));
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;
	}

	@Override
	public VehicleLogBookDTO getVehicleById(Long veID) {
		VehicleLogBookDetails vehiclBook = logBookRepo.findOne(veID);
		VehicleLogBookDTO vehicleLogBookDTO = new VehicleLogBookDTO();
		BeanUtils.copyProperties(vehiclBook, vehicleLogBookDTO);

		return vehicleLogBookDTO;
	}

	@Override
	public List<VehicleLogBookDTO> getAllVehicles(Long orgid) {

		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<VehicleLogBookDTO> listDto = new ArrayList<VehicleLogBookDTO>();
		for (Object[] obj : list) {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();

			dto.setVeName(obj[0].toString());
			dto.setVehicleTypeDesc(obj[1].toString());
			// dto.setDriverId(obj[1].toString());
			if(obj[2]!=null && obj[3]!=null)
			dto.setDriverName(obj[2].toString() + " " + obj[3].toString());
			listDto.add(dto);
		}

		return listDto;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean veLogBookDupCheck(VehicleLogBookDTO velogBDTO) {
		 Long count = null;
		    boolean status = true;
		    if(velogBDTO.getVeID() == null) {
		    	velogBDTO.setVeID(Long.parseLong("0"));
        		}
		        count = logBookRepo.veLogBookDupCheck(velogBDTO.getOrgId(), velogBDTO.getVehicleOutTime(), velogBDTO.getVehicleInTime(), velogBDTO.getOutDate(), velogBDTO.getInDate(),velogBDTO.getVeNo(),velogBDTO.getVeID());//,velogBDTO.getDriverId());   
		        	if(velogBDTO.getVeID().equals(Long.parseLong("0"))) {
		        		velogBDTO.setVeID(null);
		        		}
		        	if(count > 0) {
		        		status = false;
		        	}
		    return status;
		    
	}
	
	
	@Override
	public List<VehicleLogBookDTO> getAllVehiclesWithoutEmp(Long orgid) {

		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<VehicleLogBookDTO> listDto = new ArrayList<VehicleLogBookDTO>();
		for (Object[] obj : list) {
			VehicleLogBookDTO dto = new VehicleLogBookDTO();

			dto.setVeName(obj[0].toString());
			dto.setVehicleTypeDesc(obj[1].toString());
			// dto.setDriverId(obj[1].toString());
			if(obj[2]!=null && obj[3]!=null)
			dto.setDriverName(obj[2].toString() + " " + obj[3].toString());
			listDto.add(dto);
		}

		return listDto;
	}
	
	
	@Override
	public List<VehicleLogBookDTO> getLogBookForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList, Long orgid) {
		List<VehicleLogBookDetails> logBookDetailList = logBookRepo.getLogBookForMaintenanceAlert(activeMaintMasVehicleIdList, orgid);
		List<VehicleLogBookDTO> logBookDTOList = new ArrayList<VehicleLogBookDTO>();
		logBookDetailList.forEach(logBookDetail -> {
			VehicleLogBookDTO logBookDTO = new VehicleLogBookDTO();
			BeanUtils.copyProperties(logBookDetail, logBookDTO);
			logBookDTOList.add(logBookDTO);
		});
		return logBookDTOList;
	}
	
	

}
