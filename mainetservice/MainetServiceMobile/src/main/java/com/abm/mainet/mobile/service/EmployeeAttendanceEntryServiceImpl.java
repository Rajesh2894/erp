package com.abm.mainet.mobile.service;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.EmployeeAttendanceEntity;
import com.abm.mainet.common.domain.EmployeeAttendanceHistoryEntity;
import com.abm.mainet.common.dto.EmployeeAttendanceDTO;
import com.abm.mainet.common.master.repository.EmployeeAttendanceHistoryRepository;
import com.abm.mainet.common.master.repository.EmployeeAttendanceRepositary;


@Service
public class EmployeeAttendanceEntryServiceImpl implements EmployeeAttendanceEntryService{

	@Autowired
	private EmployeeAttendanceRepositary employeeAttendanceRepository;
	@Autowired
	private EmployeeAttendanceHistoryRepository employeeAttendanceHistRepository;
	
	
	@Override
	  @Transactional
	public EmployeeAttendanceDTO saveEmployeeAttendanceData(EmployeeAttendanceDTO dto) {
		if(dto.getEmpAttndId()==null) {
			Long attndId=employeeAttendanceRepository.getEMployeeAttendanceId(dto.getEmpId());
			dto.setEmpAttndId(attndId);
			dto.setUpdatedBy(dto.getEmpId());
			dto.setUpdatedDate(new Date());
		}
		EmployeeAttendanceEntity entity=new EmployeeAttendanceEntity();
		dto.setCreatedDate(new Date());
		BeanUtils.copyProperties(dto, entity);
		entity=employeeAttendanceRepository.save(entity);
		saveHistory(entity);
		return dto;
	}


	private void saveHistory(EmployeeAttendanceEntity entity) {

		EmployeeAttendanceHistoryEntity entityHist=new EmployeeAttendanceHistoryEntity(); 
		BeanUtils.copyProperties(entity, entityHist);
		employeeAttendanceHistRepository.save(entityHist);
		
	}


	@Override
	public EmployeeAttendanceDTO fetchEmployeeAttendance( EmployeeAttendanceDTO dto) {
		EmployeeAttendanceDTO dtos=new EmployeeAttendanceDTO(); 
		EmployeeAttendanceEntity entity= employeeAttendanceRepository.getEMployeeAttendanceDetailsByEmpId(dto.getEmpId());
		if(entity!=null)
		BeanUtils.copyProperties(entity, dtos);
		return dtos;
	}



}
