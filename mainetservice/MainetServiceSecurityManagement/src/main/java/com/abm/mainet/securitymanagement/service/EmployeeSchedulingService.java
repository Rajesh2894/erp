package com.abm.mainet.securitymanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dao.IEmployeeSchedulingDao;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingHist;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.mapper.EmployeeSchedulingMapper;
import com.abm.mainet.securitymanagement.repository.ContractualStaffMasterRepository;
import com.abm.mainet.securitymanagement.repository.DeploymentOfStaffRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingDelRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingHistoryRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingRepository;
import com.abm.mainet.securitymanagement.repository.TransferAndDutySchRepository;

@Service
public class EmployeeSchedulingService implements IEmployeeSchedulingService {

	@Autowired
	private EmployeeSchedulingRepository repository;

	@Autowired
	private EmployeeSchedulingHistoryRepository hisRepository;

	@Autowired
	private IEmployeeSchedulingDao emplDao;

	@Autowired
	private DeploymentOfStaffRepository deployRepo;

	@Autowired
	private DeploymentOfStaffRepository depRepo;

	@Autowired
	private TransferAndDutySchRepository repo;

	@Autowired
	private EmployeeSchedulingMapper mapper;
	
	@Autowired
	private ContractualStaffMasterRepository contractRepo;
	
	@Autowired
	private EmployeeSchedulingDelRepository repositoryDel;

	@Override
	public List<EmployeeSchedulingDTO> findContractualEmpNameById() {
		List<ContractualStaffMaster> empNameList = repository.findContractualEmpNameById();
		List<EmployeeSchedulingDTO> employeeSchedulingDTO = new ArrayList<EmployeeSchedulingDTO>();
		empNameList.forEach(entity -> {
			EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
			dto.setEmplIdNo(entity.getContStaffIdNo());
			dto.setDayPrefixId(entity.getDayPrefixId());
			BeanUtils.copyProperties(entity, dto);
			String s = CommonMasterUtility.getCPDDescription(dto.getDayPrefixId(), MainetConstants.BLANK);
			dto.setDayDesc(s);
			employeeSchedulingDTO.add(dto);
		});
		return employeeSchedulingDTO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(List<EmployeeSchedulingDTO> employeeSchedulingDTOList, EmployeeSchedulingDTO employeeSchedulingDTO,
			List<EmployeeSchedulingDTO> employeeSchedulingCombineDTOList) {

		employeeSchedulingCombineDTOList.forEach(dto -> {
			if(!dto.getEmplDetDto().isEmpty()) {
			EmployeeScheduling entity = new EmployeeScheduling();
			EmployeeSchedulingHist historyEntity = new EmployeeSchedulingHist();
			dto.setLocId(dto.getEmplDetDto().get(0).getLocId());
			dto.setCpdShiftId(dto.getEmplDetDto().get(0).getCpdShiftId());
			dto.setContStaffIdNo(dto.getEmplDetDto().get(0).getContStaffIdNo());
			dto.setDayPrefixId(dto.getEmplDetDto().get(0).getDayPrefixId());
			EmployeeScheduling master = mapper.mapEmployeeSchedulingDtoToEmployeeSchdule(dto);
			List<EmployeeSchedulingDet> detList = new ArrayList<EmployeeSchedulingDet>();

			dto.getEmplDetDto().forEach(detDto -> {
				EmployeeSchedulingDet entityDet = new EmployeeSchedulingDet();
				BeanUtils.copyProperties(detDto, entityDet);
				entityDet.setEmployeeScheduling(master);
				entityDet.setOrgid(dto.getOrgid());
				entityDet.setCreatedBy(dto.getCreatedBy());
				entityDet.setCreatedDate(dto.getCreatedDate());
				entityDet.setLgIpMac(dto.getLgIpMac());
				detList.add(entityDet);
			});
			master.setEmployeeSchedulingdets(detList);
			repository.save(master);
			BeanUtils.copyProperties(dto, historyEntity);

			historyEntity.setEmplScdlId(master.getEmplScdlId());
			hisRepository.save(historyEntity);
			}
		});
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmployeeSchedulingDTO> save(List<EmployeeSchedulingDTO> employeeSchedulingDTOList,
			EmployeeSchedulingDTO employeeSchedulingDTO) {

		List<EmployeeSchedulingDTO> employeeSchedulingDTOListNew = new ArrayList<EmployeeSchedulingDTO>();

		employeeSchedulingDTOList.forEach(data -> {
			EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
			String contStaffIdNo = null;
			Long dayPrefixId = null;
			String splitIds[] = data.getContStaffIdNo().split("\\+");
			for (int i = 1; i < splitIds.length; i++) {
				contStaffIdNo = splitIds[i - 1];
				dayPrefixId = Long.valueOf(splitIds[i]);
			}
			BeanUtils.copyProperties(employeeSchedulingDTO, dto);
			dto.setLocId(data.getLocId());
			dto.setCpdShiftId(data.getCpdShiftId());
			dto.setContStaffIdNo(data.getContStaffIdNo());
			dto.setDayPrefixId(data.getDayPrefixId());
			employeeSchedulingDTOListNew.add(dto);
		});

		List<EmployeeSchedulingDet> detList = new ArrayList<EmployeeSchedulingDet>();
		employeeSchedulingDTOListNew.forEach(dto -> {
			EmployeeScheduling entity = new EmployeeScheduling();
			EmployeeSchedulingHist historyEntity = new EmployeeSchedulingHist();
			EmployeeScheduling master = mapper.mapEmployeeSchedulingDtoToEmployeeSchdule(dto);

			dto.getEmplDetDto().forEach(detDto -> {
				EmployeeSchedulingDet entityDet = new EmployeeSchedulingDet();
				BeanUtils.copyProperties(detDto, entityDet);
				entityDet.setEmployeeScheduling(master);
				entityDet.setOrgid(dto.getOrgid());
				entityDet.setCreatedBy(dto.getCreatedBy());
				entityDet.setCreatedDate(dto.getCreatedDate());
				entityDet.setLgIpMac(dto.getLgIpMac());
				detList.add(entityDet);
			});
			master.setEmployeeSchedulingdets(detList);
			repository.save(master);
			BeanUtils.copyProperties(dto, historyEntity);

			historyEntity.setEmplScdlId(master.getEmplScdlId());
			hisRepository.save(historyEntity);
		});
		return employeeSchedulingDTOListNew;
	}

	@Override
	public List<EmployeeSchedulingDTO> checkIfStaffExists(List<EmployeeSchedulingDTO> employeeSchedulingDTOList,
			EmployeeSchedulingDTO employeeSchedulingDTO) {

		employeeSchedulingDTOList.forEach(data -> {
			Long count = 0l;
			data.setCount(count);
			String contStaffIdNo = null;
			Long dayPrefixId = null;
			String splitIds[] = data.getContStaffIdNo().split("\\+");
			for (int i = 1; i < splitIds.length; i++) {
				contStaffIdNo = splitIds[i - 1];
				dayPrefixId = Long.valueOf(splitIds[i]);
				data.setContStaffIdNo(contStaffIdNo);
				data.setDayPrefixId(dayPrefixId);
				String s = CommonMasterUtility.getCPDDescription(data.getDayPrefixId(), MainetConstants.BLANK);
				data.setDayDesc(s);
			}
			List<EmployeeScheduling> staffDetail = repository.findStaffListByEmpId(contStaffIdNo,
					employeeSchedulingDTO.getVendorId(), employeeSchedulingDTO.getOrgid());
			for (int i = 0; i < staffDetail.size(); i++) {
				if ((staffDetail != null && staffDetail.get(i).getLocId().equals(data.getLocId())
						&& staffDetail.get(i).getCpdShiftId().equals(data.getCpdShiftId())
						&& (employeeSchedulingDTO.getContStaffSchFrom()
								.compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0
								&& employeeSchedulingDTO.getContStaffSchFrom()
										.compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true)
						|| (employeeSchedulingDTO.getContStaffSchTo()
								.compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0
								&& employeeSchedulingDTO.getContStaffSchTo()
										.compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true) {
					count++;
					data.setCount(count);
				}
			}
			ContractualStaffMaster staffData = repository.findByEmpIdAndVendor(data.getContStaffIdNo(),employeeSchedulingDTO.getVendorId(),employeeSchedulingDTO.getOrgid());
			if (staffData != null) {
				if ((staffData.getContStaffAppointDate().after(employeeSchedulingDTO.getContStaffSchFrom()))
						|| (staffData.getContStaffAppointDate().after(employeeSchedulingDTO.getContStaffSchTo()))) {
					data.setMessageDate("true");
				}
			}
		});
		return employeeSchedulingDTOList;
	}

	@Override
	public List<EmployeeSchedulingDTO> searchEmployees(String empTypeId, Long vendorId, Long locId, Long cpdShiftId,
			Date contStaffSchFrom, Date contStaffSchTo, String contStaffName, String contStaffIdNo, Long emplScdlId,
			Long orgId) {

		List<EmployeeScheduling> employeeList = emplDao.search(empTypeId, vendorId, locId, cpdShiftId, contStaffSchFrom,
				contStaffSchTo, contStaffName, contStaffIdNo, emplScdlId, orgId);
		List<EmployeeSchedulingDTO> emplDto = new ArrayList<EmployeeSchedulingDTO>();
		employeeList.forEach(list -> {
			List<EmployeeSchedulingDetDTO> detList = new ArrayList<EmployeeSchedulingDetDTO>();
			EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
			BeanUtils.copyProperties(list, dto);
			dto.setShiftDesc(CommonMasterUtility
					.getNonHierarchicalLookUpObject(dto.getCpdShiftId(), UserSession.getCurrent().getOrganisation())
					.getLookUpDesc());
			Object[] staffNamesObject = repository.findByEmpIdAndVendorId(dto.getContStaffIdNo(),dto.getVendorId(), dto.getOrgid());
			String staffNames = null;
			if(null != staffNamesObject && staffNamesObject.length > 0)
				staffNames = (String) staffNamesObject[0];
			if(staffNames!=null)
			dto.setContStaffName(staffNames.toString());
			//dto.setVendorId(staffNames.getVendorId());
			list.getEmployeeSchedulingdets().forEach(list1 -> {
				EmployeeSchedulingDetDTO entityDet = new EmployeeSchedulingDetDTO();
				BeanUtils.copyProperties(list1, entityDet);
				detList.add(entityDet);
			});
			
			dto.setEmplDetDto(detList);
			emplDto.add(dto);
		});
		return emplDto;
	}

	@Override
	public List<EmployeeSchedulingDTO> getStaffNameByVendorId(Long vendorId,String empCode, Long orgId) {
		List<ContractualStaffMaster> staffListByVendor =contractRepo.getStaffNameByVendorIdAndType(vendorId, empCode, orgId);
		//List<ContractualStaffMaster> staffListByVendor = repository.getStaffNameByVendorId(vendorId, orgId);
		List<EmployeeSchedulingDTO> staffList = new ArrayList<EmployeeSchedulingDTO>();
		staffListByVendor.forEach(staff -> {
			EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
			dto.setDayPrefixId(staff.getDayPrefixId());
			BeanUtils.copyProperties(staff, dto);
			String s = CommonMasterUtility.getCPDDescription(dto.getDayPrefixId(), MainetConstants.BLANK);
			dto.setDayDesc(s);
			staffList.add(dto);
		});
		return staffList;
	}

	@Override
	public ShiftMaster findShiftById(Long cpdShiftId, Long orgid) {
		ShiftMaster master = repository.findByShiftId(cpdShiftId, orgid);
		return master;
	}

	@Override
	public List<HolidayMasterDto> findHolidaysByYear(Date contStaffSchFrom, Date contStaffSchTo, Long orgid) {
		List<HolidayMaster> holidayMas = repository.findHolidaysByYear(contStaffSchFrom, contStaffSchTo, orgid);
		return mapper.mapHolidayMasterToHolidayMasterDto(holidayMas);
	}

	@Override
	public List<EmployeeSchedulingDetDTO> findStaffDetails(Long emplScdlId, Long orgid) {
		List<EmployeeSchedulingDet> staffDetails = repository.findDetailsById(emplScdlId, orgid);
		return mapper.mapEmployeeDetToDetDto(staffDetails);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveData(List<EmployeeSchedulingDetDTO> employeeSchedulingDTOList) {

		employeeSchedulingDTOList.forEach(list1 -> {
			EmployeeSchedulingDet entity = new EmployeeSchedulingDet();
			BeanUtils.copyProperties(list1, entity);
			if (entity.getAttStatus() != null) {
				repositoryDel.updateData(entity.getEmplScdlDetId(), entity.getOrgid(), entity.getAttStatus(),entity.getUpdatedBy(),entity.getUpdatedDate(),entity.getLgIpMacUpd());
			}

		});

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveoverTimeData(EmployeeSchedulingDTO employeeSchedulingDTO) {

		EmployeeScheduling master = mapper.mapEmployeeSchedulingDtoToEmployeeSchdule(employeeSchedulingDTO);
		List<EmployeeSchedulingDet> detList = new ArrayList<EmployeeSchedulingDet>();
		employeeSchedulingDTO.getEmplDetDto().forEach(detDto -> {
			EmployeeSchedulingDet entityDet = new EmployeeSchedulingDet();
			BeanUtils.copyProperties(detDto, entityDet);
			entityDet.setEmployeeScheduling(master);
			detList.add(entityDet);
		});
		master.setEmployeeSchedulingdets(detList);
		repository.save(master);

	}
	
	@Override
	public Long getLatestEmployeeScheduledLocId(String staffId, Long orgId) {
		Long locId = null;
		try {
			locId = repositoryDel.findLatestLocIdByStaffIdAndOrgId(staffId, orgId);
		}catch(Exception e){
			
		}
		return locId;
	}

}
