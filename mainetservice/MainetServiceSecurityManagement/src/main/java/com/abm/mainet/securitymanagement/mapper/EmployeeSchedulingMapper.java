package com.abm.mainet.securitymanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;

@Component
public class EmployeeSchedulingMapper extends AbstractServiceMapper {

	public ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public EmployeeSchedulingMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	public EmployeeScheduling mapEmployeeSchedulingDtoToEmployeeSchdule(EmployeeSchedulingDTO dto) {
		if(dto==null) {
			return null;
		}
		return map(dto,EmployeeScheduling.class);
	}
	
	public List<HolidayMasterDto> mapHolidayMasterToHolidayMasterDto(List<HolidayMaster> holidayMasterList){
		if(holidayMasterList ==null) {
			return null;
		}
		return map(holidayMasterList,HolidayMasterDto.class);
	}
	
	public List<EmployeeSchedulingDetDTO> mapEmployeeDetToDetDto(List<EmployeeSchedulingDet> empDet){
		if(empDet ==null) {
			return null;
		}
		return map(empDet,EmployeeSchedulingDetDTO.class);
		
	}
}
