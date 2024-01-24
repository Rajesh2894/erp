package com.abm.mainet.validitymaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.validitymaster.domain.EmployeeWardZoneMappingDetailEntity;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDetailDto;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDto;
import com.abm.mainet.validitymaster.repository.EmployeeWardZoneMappingRepository;

/**
 * @author cherupelli.srikanth
 * @since 29 Nov 2021
 */

@Service
public class EmployeeWardZoneMappingServiceImpl implements IEmployeeWardZoneMappingService{

	@Autowired
	private EmployeeWardZoneMappingRepository employeeWardZoneMappingRepository;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Override
	public void saveEmployeeWardZoneapping(EmployeeWardZoneMappingDto employeeWardZoneDto) {
		List<EmployeeWardZoneMappingDetailEntity> entityDetailList = new ArrayList<EmployeeWardZoneMappingDetailEntity>();
		
		employeeWardZoneDto.getWardZoneDetalList().forEach(wardZone ->{
			EmployeeWardZoneMappingDetailEntity detail = new EmployeeWardZoneMappingDetailEntity();
			BeanUtils.copyProperties(wardZone, detail);
			entityDetailList.add(detail);
			employeeWardZoneMappingRepository.save(detail);
		});
		
		
	}

	@Override
	public List<EmployeeWardZoneMappingDetailDto> getWardZoneDetailList(Long empId, Long orgId) {
		List<EmployeeWardZoneMappingDetailDto> detailList = new ArrayList<EmployeeWardZoneMappingDetailDto>();
		List<EmployeeWardZoneMappingDetailEntity> detailEntityList = employeeWardZoneMappingRepository.getWardZoneMappingByEmpId(empId, orgId);
		if(CollectionUtils.isNotEmpty(detailEntityList)) {
			detailEntityList.forEach(entity ->{
				EmployeeWardZoneMappingDetailDto detail = new EmployeeWardZoneMappingDetailDto();
				BeanUtils.copyProperties(entity, detail);
				detailList.add(detail);
			});
		}
		
		return detailList;
	}

	@Override
	public boolean checkWardZoneMappingFlag(Long empId, Long orgId, Long ward1, Long ward2, Long ward3, Long ward4,
			Long ward5) {
		AtomicBoolean mappingFlag = new AtomicBoolean(false);
		
		Predicate<Long> p1 = w -> w != null;
		Predicate<Long> p2 = w -> w == 0;
		
		if(p1.and(p2).test(ward1))
			ward1 = null;
		if(p1.and(p2).test(ward2))
			ward2 = null;
		if(p1.and(p2).test(ward3))
			ward3 = null;
		if(p1.and(p2).test(ward4))
			ward4 = null;
		if(p1.and(p2).test(ward5))
			ward5 = null;
		
		List<EmployeeWardZoneMappingDetailDto> wardZoneDetailList = getWardZoneDetailList(empId, orgId);
		if(CollectionUtils.isNotEmpty(wardZoneDetailList)) {
			for (EmployeeWardZoneMappingDetailDto wardZone : wardZoneDetailList) {
				LookUp hierarchicalLookUp = CommonMasterUtility
                        .getHierarchicalLookUp(wardZone.getWard1(), orgId);
				 if ((ward1 != null && ((hierarchicalLookUp.getLookUpCode().equals("ALL")) || (Long
                         .parseLong(String.valueOf(ward1)) == (wardZone.getWard1()))))
                         && ((ward2 == null) || (ward2 != null
                                 && ((wardZone.getWard2() == (-1)) || (Long.parseLong(
                                         String.valueOf(ward2)) == (wardZone.getWard2())))))
                         && ((ward3 == null) || (ward3 != null
                                 && ((wardZone.getWard3() == (-1)) || (Long.parseLong(String
                                         .valueOf(ward3)) == (wardZone.getWard3())))))
                         && ((ward4 == null) || (ward4 != null
                                 && ((wardZone.getWard4() == (-1)) || (Long.parseLong(String
                                         .valueOf(ward4)) == (wardZone.getWard4())))))
                         && ((ward5 == null) || (ward5 != null
                                 && ((wardZone.getWard5() == (-1)) || (Long.parseLong(String
                                         .valueOf(ward5)) == (wardZone.getWard5())))))) {
                     mappingFlag.set(true);
                 }
			}
		}
		return mappingFlag.get();
	}

	@Override
	public List<EmployeeWardZoneMappingDto> getWardZoneMappingByOrgId(Long orgId) {
		
		List<EmployeeWardZoneMappingDto> wardZoneMstList = new ArrayList<EmployeeWardZoneMappingDto>();
		List<EmployeeWardZoneMappingDetailEntity> wardZoneMappingEntityList = employeeWardZoneMappingRepository.getWardZoneMappingByOrgId(orgId);
		
		if(CollectionUtils.isNotEmpty(wardZoneMappingEntityList)) {
			List<Long> empIdList = wardZoneMappingEntityList.stream().map(entity -> entity.getEmpLocId()).distinct().collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(empIdList)) {
				empIdList.forEach(empId ->{
					List<EmployeeWardZoneMappingDetailDto> wardZoneDetal = new ArrayList<>();
					EmployeeWardZoneMappingDto dto = new EmployeeWardZoneMappingDto();
					dto.setEmpId(empId);
					dto.setEmpName(employeeService.getEmpNameByEmpId(empId));
					List<EmployeeWardZoneMappingDetailEntity> empWardList = wardZoneMappingEntityList.stream().filter(ward -> ward.getEmpLocId().equals(empId)).collect(Collectors.toList());
					if(CollectionUtils.isNotEmpty(empWardList)) {
						empWardList.forEach(detailEntity ->{
							EmployeeWardZoneMappingDetailDto detail = new EmployeeWardZoneMappingDetailDto();
							BeanUtils.copyProperties(detailEntity, detail);
							wardZoneDetal.add(detail);
						});
						
					}
					dto.setWardZoneDetalList(wardZoneDetal);
					wardZoneMstList.add(dto);
				});
			}
			
			
		}
		return wardZoneMstList;
	}

	@Override
	public EmployeeWardZoneMappingDto getWardZoneMappingByOrgIdAndEmpId(Long orgId, Long employeeId) {
		
		List<EmployeeWardZoneMappingDto> wardZoneMstList = new ArrayList<EmployeeWardZoneMappingDto>();
		List<EmployeeWardZoneMappingDetailEntity> wardZoneMappingEntityList = employeeWardZoneMappingRepository.getWardZoneMappingByOrgIdAndEmpId(orgId, employeeId);
		
		if(CollectionUtils.isNotEmpty(wardZoneMappingEntityList)) {
			List<Long> empIdList = wardZoneMappingEntityList.stream().map(entity -> entity.getEmpLocId()).distinct().collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(empIdList)) {
				empIdList.forEach(empId ->{
					List<EmployeeWardZoneMappingDetailDto> wardZoneDetal = new ArrayList<>();
					EmployeeWardZoneMappingDto dto = new EmployeeWardZoneMappingDto();
					dto.setEmpId(empId);
					dto.setEmpName(employeeService.getEmpNameByEmpId(empId));
					List<EmployeeWardZoneMappingDetailEntity> empWardList = wardZoneMappingEntityList.stream().filter(ward -> ward.getEmpLocId().equals(empId)).collect(Collectors.toList());
					if(CollectionUtils.isNotEmpty(empWardList)) {
						empWardList.forEach(detailEntity ->{
							EmployeeWardZoneMappingDetailDto detail = new EmployeeWardZoneMappingDetailDto();
							BeanUtils.copyProperties(detailEntity, detail);
							wardZoneDetal.add(detail);
						});
						
					}
					dto.setWardZoneDetalList(wardZoneDetal);
					wardZoneMstList.add(dto);
				});
			}
			
			
		}
		return wardZoneMstList.get(0);
	
	}

	
}
